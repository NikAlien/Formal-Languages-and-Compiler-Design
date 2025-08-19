package org.example;

import org.example.SymbolTable.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;

public class FAScanner {

    public final String filePath;
    public List<String> setOfStates;
    public List<String> alphabet;
    public String initialState;
    public List<String> setFinalStates;
    public Map<Pair<String, String>, List<String>> transitions = new HashMap<>();


    public FAScanner(String filePath) {
        this.filePath = filePath;
        this.initializeElements();
    }

    public void printMenu() {

        String choice = "";
        Scanner in = new Scanner(System.in);

        while (!choice.equals("x")) {
            System.out.println();
            System.out.println("Set of states -> 1");
            System.out.println("Alphabet -> 2");
            System.out.println("Initial state -> 3");
            System.out.println("Set of final states -> 4");
            System.out.println("Transitions -> 5");
            System.out.println("Sequence check -> 6");
            System.out.println("Exit -> x");
            choice = in.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println(this.setOfStates);
                    break;
                case "2":
                    System.out.println(this.alphabet);
                    break;
                case "3":
                    System.out.println(this.initialState);
                    break;
                case "4":
                    System.out.println(this.setFinalStates);
                    break;
                case "5":
                    System.out.println(this.transitions);
                    break;
                case "6": {
                    System.out.println("Your sequence -> ");
                    String seq = in.nextLine().trim();
                    System.out.println(this.checkSequenceDFA(seq));
                }
                case "x":
                    break;
                default:
                    System.out.println("No such option.");
            }

        }
    }

    public boolean checkSequenceDFA(String seq) {
        if (!this.checkIfDFA()) {
            System.out.println("File is not DFA");
            return false;
        } else {
            if (seq.isEmpty() && !this.setFinalStates.contains(initialState))
                return false;
            else if (seq.isEmpty() && this.setFinalStates.contains(initialState))
                return true;

            Pair nextTrans = new Pair(this.initialState, seq.substring(0, 1));
            String nextSeq = seq.substring(1);

            for (int i = 0; i < seq.length() - 1; i++) {
                if (this.transitions.get(nextTrans) != null) {
                    //                System.out.println(this.transitions.get(nextTrans));
                    String nextState = this.transitions.get(nextTrans).getFirst();
                    nextTrans = new Pair(nextState, nextSeq.substring(0, 1));
                    nextSeq = nextSeq.substring(1);
                } else {
                    return false;
                }
            }

            if (nextSeq.isEmpty() && this.transitions.get(nextTrans) != null
                    && this.setFinalStates.contains(this.transitions.get(nextTrans).getFirst()))
                return true;
            else
                return false;
        }
    }

    private boolean checkIfDFA() {
        for (List<String> states : transitions.values()) {
            if (states.size() > 1) {
                return false;
            }
        }
        return true;
    }

    private void initializeElements() {
        try {
            File file = new File(this.filePath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String lineRead = bufferedReader.readLine();
            this.setOfStates = Arrays.asList(lineRead.split("\\s+"));
            lineRead = bufferedReader.readLine();
            this.alphabet = Arrays.asList(lineRead.split("\\s+"));
            lineRead = bufferedReader.readLine();
            this.initialState = lineRead.trim();
            lineRead = bufferedReader.readLine();
            this.setFinalStates = Arrays.asList(lineRead.split("\\s+"));

            lineRead = bufferedReader.readLine();
            while (lineRead != null) {
                List<String> elem = Arrays.asList(lineRead.split("\\s+"));
                List<String> value = transitions.get(new Pair<>(elem.getFirst(), elem.get(1)));
                if (value == null) {
                    List<String> newValue = new ArrayList<>();
                    newValue.add(elem.get(2));
                    transitions.put(new Pair<>(elem.getFirst(), elem.get(1)), newValue);
                } else {
                    value.add(elem.get(2));
                    transitions.put(new Pair<>(elem.getFirst(), elem.get(1)), value);
                }
                lineRead = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}