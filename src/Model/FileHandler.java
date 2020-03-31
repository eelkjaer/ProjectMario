/*
@author Andreas Bergmann (cph-ab435@cphbusiness.dk)
 */
package Model;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

//id;navn;pris;fyld
//1;Vesuvio;57;tomatsauce,ost,skinke,oregano

public class FileHandler {
    protected ArrayList<Pizza> tmpPizzas = new ArrayList<>();

    public ArrayList<Pizza> readMenuFromFile(String filePath) throws FileNotFoundException{
        File fh = new File(filePath);
        int lNum = 0;
        if (fh.exists()){
            Scanner file = new Scanner(fh);

            while(file.hasNextLine()){
                lNum++;
                String line = file.nextLine();
                String[] lineArr = line.split(";");

                try{
                    int id = Integer.parseInt(lineArr[0]);
                    String name = lineArr[1];
                    double price = Double.parseDouble(lineArr[2]);
                    String[] fillings = lineArr[3].split(",");

                    Pizza tmpPizza = new Pizza(id,name,price,fillings);
                    tmpPizzas.add(tmpPizza);
                } catch (Exception e){
                    //System.out.println("Fejl ved linje " + lNum + ":");
                    //System.out.println(line);

                }
            }
        }
        return tmpPizzas;
    }

    public void fileWriter(ArrayList<Ordre> orders){
        BufferedWriter buffwriter = null;
        LocalDate ldt = LocalDate.now();
        String fileName = ldt.toString() + "-ordre.csv";
        String filePath = "Data/";


      try{
          buffwriter = new BufferedWriter(new FileWriter(new File(filePath+fileName)));
          buffwriter.write(String.valueOf(orders));
          //System.out.println("Write to file done");
      }catch(FileNotFoundException e){
          //System.out.println("File not found!");
      }catch(IOException e){
          //System.out.println("Error" + e.toString());
        }
      finally{
          try{
              if(buffwriter != null){
                buffwriter.close();
              }
          }catch(IOException e){
              //System.out.println("IEO Error" + e.toString());
          }
      }

    }

}
