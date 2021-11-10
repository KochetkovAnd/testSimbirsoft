package com.kochetkov.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Program start");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите ссылку на сайт: ");
        String answer = scanner.next();
        Site site = new Site(answer);

        site.saveWordsToDB();

        TreeMap<String, Integer> map = site.getWords();
        for(Map.Entry<String, Integer> item : map.entrySet()){
            System.out.printf("Word: %s  Quantity: %s \n", item.getKey(), item.getValue().toString());
        }
        logger.info("Program finish successfully");
    }
}
