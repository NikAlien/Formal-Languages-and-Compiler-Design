package org.example.SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class HashTable {
    private final int bucket;
    private final ArrayList<String>[] table;

    public HashTable(int bucket)
    {
        this.bucket = bucket;
        this.table = new ArrayList[bucket];
        for (int i = 0; i < bucket; i++) {
            table[i] = new ArrayList<>();
        }
    }

    private int hashFunction(String key)
    {
        int sum_chars = 0;
        char[] key_characters = key.toCharArray();
        for(char c: key_characters){
            sum_chars += c;
        }
        return sum_chars % bucket;
    }

    public Pair<Integer, Integer> insertItem(String key)
    {
        int index = hashFunction(key);
        table[index].add(key);

        return new Pair<>(index, table[index].size() - 1);
    }

    public Pair<Integer, Integer> searchItem(String key) {
        int index = hashFunction(key);

        if(table[index].contains(key)) {
            return new Pair<>(index, table[index].indexOf(key));
        }
        return null;
    }

    public boolean deleteItem(String key)
    {
        int index = hashFunction(key);
        if (!table[index].contains(key)) {
            return false;
        }
        return table[index].remove(key);
    }

    public String displayHash()
    {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < bucket; i++) {
            result.append(i);
            for (String x : table[i]) {
                result.append(" --> ").append(x);
            }
            result.append("\n");
        }

        return result.toString();
    }
}

