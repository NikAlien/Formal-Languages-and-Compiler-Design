package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ParserRecursiveDescendent {
    private Grammar grammar;
    private String state;
    private int position;
    private Stack<Pair<String, Integer>> workingStack = new Stack<>();
    private Stack<String> inputStack = new Stack<>();
    List<String> sequence = new ArrayList<>();


    public Stack<Pair<String, Integer>> getWorkingStack() {
        return workingStack;
    }

    public ParserRecursiveDescendent(Grammar grammar) {
        this.grammar = grammar;
    }

    public void config(String filePath) {
        try {
            this.state = "q";
            this.position = 0;
            this.inputStack.push(grammar.initialState);
            File file = new File(filePath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String lineRead = bufferedReader.readLine();
            while (lineRead != null) {
                List<String> elem = Arrays.asList(lineRead.split("\\s+"));
                sequence.add(elem.getFirst());
                lineRead = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  recursiveDescendentParsing(String filePath) throws Exception {
        config(filePath);
        int n = sequence.size();
        while(!state.equals("f") && !state.equals("e")) {
            if (state.equals("q")) {
                if(position == n && inputStack.isEmpty()) {
                    Success();
                }
                else {
                    if(grammar.isNonTerminal(inputStack.peek())) {
                        Expand();
                    }
                    else{
                        if(position != n && inputStack.peek().equals(sequence.get(position))) {
                            Advance();
                        }
                        else {
                            MomentaryInsuccess();
                        }
                    }
                }
            }
            else if(state.equals("b")) {
                if(grammar.isTerminal(workingStack.peek().getFirstElement())) {
                    Back();
                }
                else {
                    AnotherTry();
                }
            }
        }
        if(state.equals("e")) {
            System.out.println("Error!");
        }
        else{
//            System.out.println(this.state);
//            System.out.println(this.position);
//            System.out.println(this.workingStack.toString());
//            System.out.println(this.inputStack.toString());
        }
    }

    public void Expand() {
        String currentSymbol = this.inputStack.pop();
        List<String> firstProd = Arrays.stream(this.grammar.getSetOfProductions().get(currentSymbol).getFirst().split("\\s+")).toList().reversed();
        firstProd.forEach(this.inputStack::push);
        this.workingStack.push(new Pair<>(currentSymbol, 0));
    }

    public void Advance() {
        this.position++;
        String elem = this.inputStack.pop();
        this.workingStack.push(new Pair<>(elem, -1));
    }

    public void MomentaryInsuccess() {
        this.state = "b";
    }

    public void Back() {
        this.position--;
        Pair<String, Integer> elem = this.workingStack.pop();
        this.inputStack.push(elem.getFirstElement());
    }

    public void AnotherTry() throws Exception {
        Pair<String, Integer> head = this.workingStack.pop();
        String value = head.getFirstElement();
        Integer index = head.getSecondElement();

        List<String> prods = this.grammar.setOfProductions.get(value);
        List<String> currentProduction = Arrays.stream(prods.get(index).split("\\s+")).toList();

        for(String prod : currentProduction) {
            if(prod.equals(inputStack.peek()))
                inputStack.pop();
            else
                throw new Exception("This should not be possible");
        }

        if(index + 1 < prods.size()) {
            this.state = "q";
            head.setSecondElement(index + 1);
            this.workingStack.push(head);
            Arrays.stream(prods.get(index + 1).split("\\s+")).toList().reversed().forEach(this.inputStack::push);
        } else if (this.position == 1 && value.equals(grammar.initialState)) {
            this.state = "e";
        }
        else {
            this.inputStack.push(value);
        }
    }

    public void Success() {
        this.state = "f";
    }
}