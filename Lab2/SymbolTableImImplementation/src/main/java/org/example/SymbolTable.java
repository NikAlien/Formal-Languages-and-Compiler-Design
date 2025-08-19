package org.example;

import java.util.List;

public class SymbolTable {

    private Integer size;
    private HashTable hashTable;

    public SymbolTable(Integer size) {
        this.size = size;
        this.hashTable = new HashTable(size);
    }

    public List<Integer> insertItem(String key) {
        return this.hashTable.insertItem(key);
    }

    public List<Integer> insertItem(Integer key) {
        return this.hashTable.insertItem(key.toString());
    }

    public List<Integer> searchItem(String key) {
        return this.hashTable.searchItem(key);
    }

    public List<Integer> searchItem(Integer key) {
        return this.hashTable.searchItem(key.toString());
    }

    public void deleteItem(String key) {
        this.hashTable.deleteItem(key);
    }

    public void deleteItem(Integer key) {
        this.hashTable.deleteItem(key.toString());
    }

    public void display() {
        this.hashTable.displayHash();
    }
}
