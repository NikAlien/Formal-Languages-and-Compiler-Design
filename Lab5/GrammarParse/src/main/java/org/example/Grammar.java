package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Grammar {

    public final String filePath;
    public List<String> setOfNonterminal;
    public List<String> setOfTerminals;
    public String initialState;
    public Map<String, List<String>> setOfProductions = new HashMap<>();


    public Grammar(String filePath) {
        this.filePath = filePath;
        this.initializeElements();
    }

    public void printMenu() {

        String choice = "";
        Scanner in = new Scanner(System.in);

        while(!choice.equals("x")) {
            System.out.println();
            System.out.println("Set of non-terminals -> 1");
            System.out.println("Set of terminals -> 2");
            System.out.println("Initial state -> 3");
            System.out.println("Set of productions -> 4");
            System.out.println("Productions for a non-terminal -> 5");
            System.out.println("CFG check -> 6");
            System.out.println("Exit -> x");
            choice = in.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println(this.setOfNonterminal);
                    break;
                case "2":
                    System.out.println(this.setOfTerminals);
                    break;
                case "3":
                    System.out.println(this.initialState);
                    break;
                case "4":
                {
                    for(Map.Entry<String, List<String>> itr: this.setOfProductions.entrySet())
                        System.out.println(itr.getKey() + " -> " + itr.getValue());
                    break;
                }
                case "5":
                {
                    System.out.println("Your non-terminal -> ");
                    String terminal = in.nextLine().trim();
                    System.out.println(this.setOfProductions.get(terminal));
                    break;
                }
                case "6":
                {
                    System.out.println("CFG -> " + this.checkIfCFG());
                    break;
                }
                case "x":
                    break;
                default:
                    System.out.println("No such option.");
            }

        }
    }

    public List<String> getSetOfNonterminal() {
        return setOfNonterminal;
    }

    public List<String> getSetOfTerminals() {
        return setOfTerminals;
    }

    public String getInitialState() {
        return initialState;
    }

    public Map<String, List<String>> getSetOfProductions() {
        return setOfProductions;
    }

    public boolean isTerminal(String terminal) {
        return setOfTerminals.contains(terminal);
    }

    public boolean isNonTerminal(String terminal) {
        return setOfNonterminal.contains(terminal);
    }

    public boolean checkIfCFG() {
        for(String key: this.setOfProductions.keySet()) {
            if(!this.setOfNonterminal.contains(key))
                return false;
        }
        return true;
    }

    private void initializeElements() {
        try {
            File file = new File(this.filePath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String lineRead = bufferedReader.readLine();
            this.setOfNonterminal = Arrays.asList(lineRead.split("\\s+"));
            lineRead = bufferedReader.readLine();
            this.setOfTerminals = Arrays.asList(lineRead.split("\\s+"));
            lineRead = bufferedReader.readLine();
            this.initialState = lineRead.trim();

            lineRead = bufferedReader.readLine();
            while(lineRead != null) {
                List<String> elem = Arrays.asList(lineRead.split("\\|"));
                List<String> value = setOfProductions.get(elem.get(0));
                if(value == null) {
                    List<String> newValue = new ArrayList<>();
                    newValue.addAll(elem.subList(1, elem.size()));
                    setOfProductions.put(elem.get(0), newValue);
                }
                else {
                    value.addAll(elem.subList(1, elem.size()));
                    setOfProductions.put(elem.get(0), value);
                }
                lineRead = bufferedReader.readLine();
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}