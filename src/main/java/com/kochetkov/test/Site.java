package com.kochetkov.test;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class Site {
    private final Logger logger = LoggerFactory.getLogger(Site.class);

    private String link = "";
    private TreeMap<String, Integer> words = new TreeMap<>();


    public Site(String link) {
        this.link = link;

        words = new TreeMap<>();

        try {
            Document document = Jsoup.connect(link).get();
            Elements elements = document.select("html > *");

            for (Element element : elements) {
                String regex = "[\\s,.!?\"'«»’`„“;:\\[\\]()\\\\\\/]+"; // Пробел , . ! ? " ' ; : [ ] ( ) \ / « » ’ ` „ “
                String [] split_words = element.text().split(regex);
                for (int i = 0; i < split_words.length; i++) {
                    split_words[i] = split_words[i].toLowerCase();
                    words.put(split_words[i], words.getOrDefault(split_words[i], 0)  + 1);
                }
            }
            words.remove("");
        } catch (IOException |IllegalArgumentException e) {
            System.out.println("Невозможно открыть ссылку");
            logger.error("unable to open link {}", link, e);
        }
    }

    public void saveWordsToDB() {
        DBHelper dbHelper = new DBHelper();
        String query = "insert into words_on_site values(?, ?, ?)";

        for(Map.Entry<String, Integer> item : words.entrySet()){
           try {
               PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement(query);
               preparedStatement.setString(1, link);
               preparedStatement.setString(2, item.getKey());
               preparedStatement.setInt(3, item.getValue());

               preparedStatement.execute();
           } catch (SQLException e) {
               logger.error("unable insert to database", e);
           }
        }
    }

    public TreeMap<String, Integer> getWords() {
        return words;
    }
}
