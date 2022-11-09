package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/recommend_query")
    private Map<String, Object> recommend_query(@RequestParam Map<String, String> mp){
        String sql = "";
        int page = -1;
        if(mp.containsKey("date")){
            sql = "select * from recommend_table where date='" + mp.get("date") + "'";
            List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
            if(ls.size() != 0){
                return Map.of("code", 0, "size", 1, "content", ls);
            }
            else{
                MovieController movieController = new MovieController();
                BookController bookController = new BookController();
                List<Object> movie_ls = (List<Object>) movieController.movie_query(Map.of(), jdbcTemplate).get("content");
                List<Object> book_ls = (List<Object>) bookController.book_query(Map.of(), jdbcTemplate).get("content");
                List<Object> movie_id_ls = (List<Object>) new ArrayList<Object>();
                List<Object> book_id_ls = (List<Object>) new ArrayList<Object>();
                for(int i = 0; i < movie_ls.size(); i++){
                    movie_id_ls.add(((Map<String, Object>) movie_ls.get(i)).get("movie_id"));
                }
                for(int i = 0; i < book_ls.size(); i++){
                    book_id_ls.add(((Map<String, Object>) book_ls.get(i)).get("book_id"));
                }
                Random r = new Random();
                Object movie_id = movie_id_ls.get(r.nextInt(movie_id_ls.size()));
                Object book_id = book_id_ls.get(r.nextInt(book_id_ls.size()));
                Map<String, Object> mp_insert = Map.of("movie_id", movie_id, "book_id", book_id, "date", mp.get("date"));
                sql = "insert into recommend_table(";
                int flag = 0;
                for(String key : mp_insert.keySet()){
                    if(flag == 0){
                        sql += key;
                        flag = 1;
                    }
                    else{
                        sql += "," + key;
                    }
                }
                sql += ") values (";
                flag = 0;
                for(String key : mp_insert.keySet()){
                    if(flag == 0){
                        sql += "'" + mp_insert.get(key) + "'";
                        flag = 1;
                    }
                    else{
                        sql += ",'" + mp_insert.get(key) + "'";
                    }
                }
                sql += ")";
                jdbcTemplate.update(sql);
                sql = "select * from recommend_table where date='" + mp.get("date") + "'";
                ls = jdbcTemplate.queryForList(sql);
                return Map.of("code", 0, "size", 1, "content", ls);
            }
        }
        else{
            if(mp.containsKey("page")){
                page =Integer.parseInt(mp.get("page"));
                mp.remove("page");
            }
            sql = "select * from recommend_table";
            List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
            int size = ls.size();
            if(size == 0){
                return Map.of("code", 4);
            }
            if(page != -1){
                ls = ls.subList(page*10 - 10, UserController.min(page*10, size));
            }
            return Map.of("code", 0, "size", size, "content", ls);
        }
    }
}
