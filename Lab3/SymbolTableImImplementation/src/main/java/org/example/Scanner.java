package org.example;

import org.example.SymbolTable.Pair;
import org.example.SymbolTable.SymbolTable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Scanner {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";

    private final FAScanner id_FAscanner = new FAScanner("src/main/resources/FA_Id.in");
    private final FAScanner integer_FAscanner = new FAScanner("src/main/resources/FA_integer.in");

    private final String tokenFile;
    private final String programFile;

    private List<String> separators;
    private List<String> operators;
    private List<String> reservedWords;
    private final SymbolTable symbolTable = new SymbolTable(100);

    private StringBuilder errors = new StringBuilder();

    public Scanner(String tokenFile, String programFile) {
        this.tokenFile = tokenFile;
        this.programFile = programFile;
        this.generateTokensList();

        try {
            File file = new File("src/main/resources/PIF.out");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scanProgramFile() {
        try {
            File file = new File(this.programFile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String lineRead = bufferedReader.readLine();
            boolean flagString = false;

            while (lineRead != null) {

//                System.out.println(lineRead);
                List<String> token = this.detectToken(lineRead);
//                System.out.println(token);

                for(String elem: token) {
                    if(this.separators.contains(elem) || this.operators.contains(elem) || this.reservedWords.contains(elem)) {
//                        System.out.println("Elem is token --> " + elem);
                        generatePIF(elem, new Pair<>(-1, -1));
                        if(elem.equals("\""))
                            flagString = !flagString;
                    }
                    else {
                        if(checkConst(elem) || flagString) {
                            Pair<Integer, Integer> index = symbolTable.searchItem(elem);
                            if(index == null)
                                index = symbolTable.insertItem(elem);
                            generatePIF("CONST", index);
                        }
                        else if(checkId(elem) || flagString) {
                            Pair<Integer, Integer> index = symbolTable.searchItem(elem);
                            if(index == null)
                                index = symbolTable.insertItem(elem);
                            generatePIF("IDENTIFIER", index);
                        }
                        else {
                            errors.append(RED + "Lexical error at line -> " + lineRead + "\nElement: " + elem + RESET);
                            errors.append("\n");
                        }
                    }
                }
                lineRead = bufferedReader.readLine();
            }
            generateST();
            if(errors.isEmpty())
                System.out.println(GREEN + "Lexically correct" + RESET);
            else
                System.out.println(errors.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkId(String token) {
        return id_FAscanner.checkSequenceDFA(token);
    }

    private boolean checkConst(String token) {
        return integer_FAscanner.checkSequenceDFA(token) || token.matches("^\"[^\"]*\"$");
    }

    private List<String> detectToken(String line) {
        List <String> listNoSpace = Arrays.asList(line.split("\\s+"));
        listNoSpace = listNoSpace.stream().filter(elem -> !Objects.equals(elem, "")).collect(Collectors.toList());

        List<String> result = new ArrayList<>();

        for(String elem : listNoSpace) {

            while(!elem.isBlank() && this.separators.contains(elem.substring(0, 1))){
                result.add(elem.substring(0, 1));
                elem = elem.substring(1);
            }

            int index = 0;
            while(!elem.isBlank() && this.separators.contains(elem.substring(elem.length() - 1))) {
                result.add(result.size() - index, elem.substring(elem.length() - 1));
                elem = elem.substring(0, elem.length() - 1);
                index++;
            }

            if(elem.contains("(")) {
                int pos = elem.indexOf('(');
                result.add(result.size()- index, elem.substring(pos + 1));
                index++;
                result.add(result.size() - index, "(");
                index++;
                elem = elem.substring(0, pos);
            }

            if(!elem.isBlank())
                result.add(result.size() - index, elem);
        }

        int start = -1;

        for(int i = 0; i < result.size(); i++) {
            if (result.get(i).contains("\"")) {
                if (start == -1)
                    start = i;
                else {
                    String concatenated = String.join(" ", result.subList(start, i + 1));
                    for (int j = i; j >= start; j--) {
                        result.remove(j);
                    }
                    result.add(start, concatenated);
                    start = -1;
                }
            }
        }
        return result;
    }

    private void generatePIF(String token, Pair<Integer, Integer> position) {
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/PIF.out", true);
            BufferedWriter bufferedWriter = new BufferedWriter(myWriter);
            bufferedWriter.write( token + " --> " + position + "\n");
            bufferedWriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void generateST() {
        try {
            File file = new File("src/main/resources/ST.out");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }

            FileWriter myWriter = new FileWriter("src/main/resources/ST.out");
            myWriter.write(symbolTable.display());
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void generateTokensList() {
        try {
            File file = new File(this.tokenFile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String lineRead = bufferedReader.readLine();
            this.separators = Arrays.asList(lineRead.split("\\s+"));
            lineRead = bufferedReader.readLine();
            this.operators = Arrays.asList(lineRead.split("\\s+"));
            lineRead = bufferedReader.readLine();
            this.reservedWords = Arrays.asList(lineRead.split("\\s+"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
