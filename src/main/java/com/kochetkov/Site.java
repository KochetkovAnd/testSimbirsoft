package com.kochetkov;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class Site {
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
        } catch (IOException exception) {
            System.out.println("Невозможно открыть ссылку");
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
               e.printStackTrace();
           }
        }
    }

    public TreeMap<String, Integer> getWords() {
        return words;
    }
}
