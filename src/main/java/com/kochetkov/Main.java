package com.kochetkov;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите ссылку на сайт: ");
        String answer = scanner.next();
        Site site = new Site(answer);

        site.saveWordsToDB();

        TreeMap<String, Integer> map = site.getWords();
        for(Map.Entry<String, Integer> item : map.entrySet()){
            System.out.printf("Word: %s  Quantity: %s \n", item.getKey(), item.getValue().toString());
        }
    }
}
