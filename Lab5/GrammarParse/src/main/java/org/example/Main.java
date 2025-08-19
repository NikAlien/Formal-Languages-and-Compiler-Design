package org.example;

public class Main {
    public static void main(String[] args) throws Exception {

        Grammar gr = new Grammar("src/main/resources/g2.txt");
//        scanner.printMenu();

        ParserRecursiveDescendent parser = new ParserRecursiveDescendent(gr);
        parser.recursiveDescendentParsing("src/main/resources/PIF.out");

        ParserOutput output = new ParserOutput(gr);
        output.transformRepresentation(parser.getWorkingStack());
        output.printConsole();
        output.printFile("src/main/resources/out2.txt");
    }
}