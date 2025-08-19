package org.example;

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

    public int hashFunction(String key)
    {
        int sum_chars = 0;
        char[] key_characters = key.toCharArray();
        for(char c: key_characters){
            sum_chars += c;
        }
        return sum_chars % bucket;
    }

    public List<Integer> insertItem(String key)
    {
        List<Integer> position = new ArrayList<>();
        int index = hashFunction(key);
        table[index].add(key);


        position.add(index);
        position.add(table[index].size() - 1);
        return position;
    }

    public List<Integer> searchItem(String key) {
        List<Integer> position = new ArrayList<>();
        int index = hashFunction(key);
        position.add(index);

        if(table[index].contains(key)) {
            position.add(table[index].indexOf(key));
            return position;
        }
        return null;
    }

    public void deleteItem(String key)
    {
        int index = hashFunction(key);
        if (!table[index].contains(key)) {
            return;
        }
        table[index].remove(key);
    }

    public void displayHash()
    {
        for (int i = 0; i < bucket; i++) {
            System.out.print(i);
            for (String x : table[i]) {
                System.out.print(" --> " + x);
            }
            System.out.println();
        }
    }

//    public static void main(String[] args)
//    {
//        int[] a = { 15, 11, 27, 8, 12 };
//
//        HashTable h = new HashTable(7);
//
//        for (int x : a) {
//            h.insertItem(x);
//        }
//
//        h.deleteItem(12);
//        h.displayHash();
//    }
}

