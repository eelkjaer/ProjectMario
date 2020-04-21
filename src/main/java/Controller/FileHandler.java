/*
@author Andreas Bergmann (cph-ab435@cphbusiness.dk)
@author Emil Elkjær Nielsen (cph-en93@cphbusiness.dk)
 */
package Controller;

import Model.Customer;
import Model.Ordre;
import Model.Pizza;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public class FileHandler {
    protected final ArrayList<Pizza> tmpPizzas = new ArrayList<>();
    protected final ArrayList<Customer> tmpCustomers = new ArrayList<>();
    protected final ArrayList<String> tmpStats = new ArrayList<>();
    // Læser vores menu Fil // throws FileNotFoundException hvis et forsøg på, at åbne en af vores filer er mislykkedes
    //
    private static final boolean debug = false;

    public boolean doesFileExist(String filePath) {
        File fh = new File(filePath);

        return fh.exists();
    }

    public ArrayList<Pizza> readMenuFromFile(String filePath) throws FileNotFoundException{
        File fh = new File(filePath);
        int lNum = 0;
        if (fh.exists()){
            Scanner file = new Scanner(fh);

            while(file.hasNextLine()){//Returnerer True, hvis der er en anden linje i input til denne scanner.
                lNum++;
                String line = file.nextLine();//den aktuelle linje og returnerer det input, der blev sprunget over.(Bruges i en fejl meddelse)
                String[] lineArr = line.split(";");
                //id;navn;pris;fyld
                //1;Vesuvio;57;tomatsauce,ost,skinke,oregano
                try{
                    int id = Integer.parseInt(lineArr[0]);
                    String name = lineArr[1];
                    double price = Double.parseDouble(lineArr[2]);
                    String[] fillings = lineArr[3].split(",");//Tager alt det der er ( , ) sepereret så vi ikke behøver at læse hver ingredients for sig selv

                    Pizza tmpPizza = new Pizza(id,name,price,fillings);
                    tmpPizzas.add(tmpPizza);// Vi udfylder agrumenterne i vores nye objekt og adder en tmpPizza med de informationer vi har fra vores fil
                } catch (Exception e){ //error handling kode
                    if(debug){
                        System.out.println("Fejl ved linje " + lNum + ":");
                        System.out.println(line);
                    }
                }
            }
        }
        return tmpPizzas;
    }
    // Læser vores customer fil
    public ArrayList<Customer> readCustomersFromFile(String filePath) throws FileNotFoundException{
        File fh = new File(filePath);
        int lNum = 0;
        if (fh.exists()){
            Scanner file = new Scanner(fh);

            while(file.hasNextLine()){
                lNum++;
                String line = file.nextLine();
                String[] lineArr = line.split(";");
                //navn;telefon;mail;tidlOrdre
                //Emil Elkjær;60146057;cph-en93@cphbusiness.dk;5

                try{
                    String name = lineArr[0];
                    int telefon = Integer.parseInt(lineArr[1]);
                    String mail = lineArr[2];
                    int tidlOrdre = Integer.parseInt(lineArr[3]);

                    Customer tmpCust = new Customer(name,telefon,mail,tidlOrdre);
                    tmpCustomers.add(tmpCust);
                } catch (Exception e){
                    if(debug){
                        System.out.println("Fejl ved linje " + lNum + ":");
                        System.out.println(line);
                    }


                }
            }
        }
        return tmpCustomers;
    }

    public ArrayList<String> readStatsFromFile(String filePath) throws FileNotFoundException{
        File fh = new File(filePath);
        int lNum = 0;
        if (fh.exists()){
            Scanner file = new Scanner(fh);

            while(file.hasNextLine()){
                lNum++;
                String line = file.nextLine();
                String[] lineArr = line.split(";");
                //navn;antal;salg
                //Vesuvio;0;0.0

                try{
                    String navn = lineArr[0];
                    int antal = Integer.parseInt(lineArr[1]);
                    double salg = Double.parseDouble(lineArr[2]);

                    String tmpStat = navn + ";" + antal + ";" + salg;

                    tmpStats.add(tmpStat);
                } catch (Exception e){
                    if(debug){
                        System.out.println("Fejl ved linje " + lNum + ":");
                        System.out.println(line);
                    }


                }
            }
        }
        return tmpStats;
    }

    public void saveCustomersToFile(ArrayList<Customer> customers){
    //Skriver til customer Fil med BufferedWriter

        String fileName = "customers.csv";
        String filePath = "Data/";
        try (BufferedWriter buffwriter = new BufferedWriter(new FileWriter(new File(filePath + fileName)))) {
            buffwriter.write("navn;telefon;mail;tidlOrdre");
            buffwriter.newLine();
            for (Customer c : customers) {
                String name = c.getName();
                int phone = c.getPhoneNo();
                String mail = c.getMail();
                int orders = c.getPrevOrder();

                String str = name + ";" + phone + ";" + mail + ";" + orders;
                buffwriter.write(str);
                buffwriter.newLine();
            }
            if(debug){
                System.out.println("Write to file done");
            }
        } catch (FileNotFoundException e) {
            if(debug){
                System.out.println("File not found!");
            }
        } catch (IOException e) {
            if(debug){
                System.out.println("Error" + e.toString());
            }
        }
    }
    public void saveOrdersToFile(ArrayList<Ordre> orders){
        BufferedWriter buffwriter = null;
        LocalDate ldt = LocalDate.now();
        String fileName = ldt.toString() + "-ordre.csv";
        String filePath = "Export/";


      try{
          buffwriter = new BufferedWriter(new FileWriter(new File(filePath+fileName)));
          buffwriter.write(String.valueOf(orders));
          if(debug){
              System.out.println("Write to file done");
          }
      }catch(FileNotFoundException e){
          if(debug){
              System.out.println("File not found!");
          }
      }catch(IOException e){
          if(debug){
              System.out.println("Error" + e.toString());
          }
        }
      finally{
          try{
              if(buffwriter != null){
                buffwriter.close();
              }
          }catch(IOException e){
              if(debug){
                  System.out.println("IEO Error" + e.toString());
              }
          }
      }

    }
    //Gemmer navn/salg/antal I Mario's Statistik mappe
    public void saveStatsToFile(ArrayList<String> stats){
        BufferedWriter buffwriter = null;
        LocalDate ldt = LocalDate.now();
        String fileName = ldt.toString() + "-statistik.csv";
        String filePath = "Export/";


        try{
            buffwriter = new BufferedWriter(new FileWriter(new File(filePath+fileName)));
            buffwriter.write("navn;antal;salg");
            buffwriter.newLine();
            for(String str: stats){
                buffwriter.write(str);
                buffwriter.newLine();
            }
        }catch(FileNotFoundException e){
            if(debug){
                System.out.println("File not found!");
            }
        }catch(IOException e){
            if(debug){
                System.out.println("Error" + e.toString());
            }
        }
        finally{
            try{
                if(buffwriter != null){
                    buffwriter.close();
                }
            }catch(IOException e){
                if(debug){
                    System.out.println("IEO Error" + e.toString());
                }
            }
        }

    }

}
