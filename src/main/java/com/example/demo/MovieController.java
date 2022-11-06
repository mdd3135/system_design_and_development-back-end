package com.example.demo;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/movie_add")
    private Map<String, Object> movie_add(@RequestParam Map<String, String> mp){
        String sql = "insert into all_movie_table(";
        int flag = 0;
        for(String key : mp.keySet()){
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
        for(String key : mp.keySet()){
            if(flag == 0){
                sql += "'" + mp.get(key) + "'";
                flag = 1;
            }
            else{
                sql += ",'" + mp.get(key) + "'";
            }
        }
        sql += ")";
        jdbcTemplate.update(sql);
        return Map.of("code", 0);
    }

    @GetMapping("/movie_query")
    private Map<String, Object> movie_query(@RequestParam Map<String, String> mp){
        int page = -1;
        String sql = "select * from all_movie_table ";
        if(mp.containsKey("page")){
            page = Integer.parseInt(mp.get("page"));
            mp.remove("page"); 
        }
        if(mp.containsKey("movie_id")){
            sql += "where movie_id=" + mp.get("movie_id");
        }
        else{
            int flag = 0;
            for(String key : mp.keySet()){
                if(flag == 0){
                    sql += "where " + key + " like '%" + mp.get(key) + "%' ";
                    flag = 1;
                }
                else{
                    sql += "and " + key + " like '%" + mp.get(key) + "%'";
                }
            }
        }
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

    @PostMapping("/movie_delete")
    private Map<String, Object> movie_delete(@RequestParam Map<String, String> mp){
        String sql = "select * from all_movie_table where movie_id=" + mp.get("movie_id");
        if(jdbcTemplate.queryForList(sql).size() == 0){
            return Map.of("code", 5);
        }
        sql = "delete from all_movie_table where movie_id=" + mp.get("movie_id");
        jdbcTemplate.update(sql);
        return Map.of("code", 0);
    }

    @PostMapping("/movie_modify")
    private  Map<String, Object> movie_modify(@RequestParam Map<String, String> mp){
        String movie_id = mp.get("movie_id");
        mp.remove("movie_id");
        String sql = "select * from all_movie_table where movie_id=" + movie_id;
        if(jdbcTemplate.queryForList(sql).size() == 0){
            return Map.of("code", 6);
        }
        
        int flag = 0;
        sql = "update all_movie_table set ";
        for(String key : mp.keySet()){
            if(flag == 0){
                sql += key + "='" + mp.get(key) + "'";
                flag = 1;
            }
            else{
                sql += "," + key + "='" + mp.get(key) + "'";
            }
        }
        sql += " where movie_id =" + movie_id;
        jdbcTemplate.update(sql);
        return Map.of("code", 0);
    }
}
