package org.example.SymbolTable;

import java.util.List;

public class SymbolTable {

    private Integer size;
    private HashTable hashTable;

    public SymbolTable(Integer size) {
        this.size = size;
        this.hashTable = new HashTable(size);
    }

    public Pair<Integer, Integer> insertItem(String key) {
        return this.hashTable.insertItem(key);
    }

    public Pair<Integer, Integer> insertItem(Integer key) {
        return this.hashTable.insertItem(key.toString());
    }

    public Pair<Integer, Integer> searchItem(String key) {
        return this.hashTable.searchItem(key);
    }

    public Pair<Integer, Integer> searchItem(Integer key) {
        return this.hashTable.searchItem(key.toString());
    }

    public boolean deleteItem(String key) {
        return this.hashTable.deleteItem(key);
    }

    public boolean deleteItem(Integer key) {
        return this.hashTable.deleteItem(key.toString());
    }

    public String display() {
        return this.hashTable.displayHash();
    }
}
