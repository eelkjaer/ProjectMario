package Model;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    protected ArrayList<Pizza> pizza = new ArrayList<>();

    public FileHandler(String fileapth) throws FileNotFoundException {
        fileHandler(fileapth);
    }

    private ArrayList<Pizza> fileHandler(String filePath) throws FileNotFoundException{
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

                    Pizza tmpPizza = new Pizza(id);
                    pizza.add(tmpPizza);
                }catch (Exception e){
                    System.out.println("Fejl ved linje " + lNum + ":");
                    System.out.println(line);

                }
            }
        }
        return pizza;
    }
    public ArrayList<Pizza> getPizza(){
        return pizza;
    }

    public void fileWriter(){
        ArrayList<Ordre> orders = new ArrayList<>();
        BufferedWriter buffwriter = null;


      try{
          buffwriter = new BufferedWriter(new FileWriter(new File("Data/EkportedFiles.txt")));
          buffwriter.write(String.valueOf(orders));//TODO: hvad skal jeg gøre for at få vores orders ud?
          System.out.println("Write to file done");
      }catch(FileNotFoundException e){
          System.out.println("File not found!");
      }catch(IOException e){
          System.out.println("Error" + e.toString());
        }
      finally{
          try{
              if(buffwriter != null){
                buffwriter.close();
              }
          }catch(IOException e){
              System.out.println("IEO Error" + e.toString());
          }
      }

    }

}
