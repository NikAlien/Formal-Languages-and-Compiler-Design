package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ParserOutput {

    private Map<Integer, List<String>> table = new HashMap<>();
    private Grammar grammar;

    public ParserOutput(Grammar gr) {
        grammar = gr;
    }

    Stack<Pair<String, Integer>> reverserStack(Stack<Pair<String, Integer>> og) {
        Stack<Pair<String, Integer>> newStack = new Stack<>();

        while(!og.empty()) {
            newStack.push(og.pop());
        }

        return newStack;
    }

    public void transformRepresentation(Stack<Pair<String, Integer>> workingStack) {
        Stack<Pair<String, Integer>> workStack = reverserStack(workingStack);
        Queue<Integer> parents =  new LinkedList<>();
        Integer index = 1;
        int parent = 0;

        while(!workStack.empty()) {
            Pair<String, Integer> elemStack = workStack.pop();
            if(elemStack.getSecondElement() == -1)
                continue;

            if(index == 1) {
                List<String> elem = Arrays.asList(elemStack.getFirstElement(), "0", "0");
                table.put(index, elem);
                parent = index;
                index++;
            }
            else {
                parent = parents.remove();
            }
            int sibling = 0;

            List<String> prods = Arrays.stream(this.grammar.getSetOfProductions().get(elemStack.getFirstElement())
                    .get(elemStack.getSecondElement()).split("\\s+")).toList();

            for(String a : prods) {
                List<String> elemNext = Arrays.asList(a, Integer.toString(parent), Integer.toString(sibling));
                table.put(index, elemNext);
                sibling = index;

                if(grammar.isNonTerminal(a)) {
                    parents.add(index);
                }
                index++;
            }

        }

    }

    public void printConsole() {

        System.out.printf("---------------------------------------------------%n");
        System.out.printf("| %5s | %20s | %6s | %6s |%n", "INDEX", "INFO", "PARENT", "L-SIB");
        System.out.printf("---------------------------------------------------%n");

        for(Integer index : table.keySet()) {
            List<String> elem = table.get(index);
            System.out.printf("| %5s | %20s | %6s | %6s |%n",
                    index.toString(), elem.getFirst(), elem.get(1), elem.getLast());
        }

        System.out.printf("--------------------------------------------------%n");
    }

    public void printFile(String filePath) {

        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }

            FileWriter myWriter = new FileWriter(filePath);
            myWriter.append("---------------------------------------------------\n");
            myWriter.append(String.format("| %5s | %20s | %6s | %6s |%n", "INDEX", "INFO", "PARENT", "L-SIB"));
            myWriter.append("---------------------------------------------------\n");
            for(Integer index : table.keySet()) {
                List<String> elem = table.get(index);
                myWriter.append(String.format("| %5s | %20s | %6s | %6s |%n",
                        index.toString(), elem.getFirst(), elem.get(1), elem.getLast()));
            }
            myWriter.append("---------------------------------------------------\n");
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
