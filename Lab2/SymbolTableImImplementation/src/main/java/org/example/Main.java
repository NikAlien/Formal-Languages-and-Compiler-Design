package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        SymbolTable ST = new SymbolTable(7);

        ST.insertItem(3);
        ST.insertItem(7);
        ST.insertItem(9);
        ST.insertItem("hey");

        System.out.println(ST.insertItem(10));

        ST.display();
        System.out.println(ST.searchItem(10));

        ST.deleteItem(7);
        ST.display();
    }
}