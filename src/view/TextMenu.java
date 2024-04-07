package view;

import exceptions.MyException;
import model.adt.dictionary.DictionaryInterface;
import model.adt.dictionary.MyDictionary;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {

    private final Map<String, Command> commands;

    public TextMenu(){
        this.commands = new LinkedHashMap<>();
    }

    public void addCommand(Command c){
            this.commands.put(c.getKey(), c);
    }

    private void printMenu(){
        for(Command c: commands.values()){
            String line = String.format("%4s : %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);
        boolean finished = false;
        while(!finished){
            printMenu();
            System.out.println("Input the option: ");
            String option = scanner.nextLine();
            Command c = commands.get(option);
            if(c == null){
                System.out.println("Invalid Option");
            }
            else {
                if(option == "0") {
                    finished = true;
                }
                c.execute();
            }
        }
        scanner.close();
    }
}
