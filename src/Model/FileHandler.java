package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    protected ArrayList<Pizza> pizza = new ArrayList<>();
    protected ArrayList<Order> orders = new ArrayList<>();

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
                    int idNumber = Integer.parseInt(lineArr[0]);
                    String name = lineArr[1];
                    double pris = Double.parseDouble(lineArr[2]);
                    String[] fillings = lineArr[3].split(",");

                    Pizza tmpPizza = new Pizza(idNumber, name, pris, fillings);
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

    private ArrayList<Ordre> fileWriter(){
        

    }
}
