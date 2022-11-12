package com.example.demo;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class WatchingStatusController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/watching_status_modify")
    private Map<String, Object> watching_status_modify(@RequestParam Map<String,String> mp, @RequestHeader("Authorization") String session_id){
        String sql = "select * from user_table where session_id='" + session_id + "'";
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0 || ls.get(0).get("expiration_time").toString().compareTo(""+System.currentTimeMillis()) < 0){
            return Map.of("code", 3);
        }
        String user_id = ls.get(0).get("user_id").toString();
        mp.put("user_id", user_id);
        sql = "select * from watching_status_table where user_id='" + user_id + "' and movie_id='" + mp.get("movie_id") + "'";
        ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0){
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String create_time = formatter.format(date);
            mp.put("create_time", create_time);
            mp.put("movie_id", mp.get("movie_id"));
            sql = "insert into watching_status_table(";
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
        }
        else{
            int flag = 0;
            String movie_id = mp.get("movie_id");
            mp.remove("movie_id");
            mp.remove("user_id");
            sql = "update watching_status_table set ";
            for(String key : mp.keySet()){
                if(flag == 0){
                    sql += key + "='" + mp.get(key) + "'";
                    flag = 1;
                }
                else{
                    sql += "," + key + "='" + mp.get(key) + "'";
                }
            }
            sql += " where movie_id='" + movie_id + "' and user_id='" + user_id + "'";
            jdbcTemplate.update(sql); 
        }
        return Map.of("code", 0);
    }

    @GetMapping("/watching_status_query")
    private Map<String, Object> watching_status_query(@RequestParam Map<String, String> mp){
        String sql = "select * from watching_status_table where user_id='" + mp.get("user_id") + "'";
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        int size = ls.size();
        if(size == 0){
            return Map.of("code", 4);
        }
        return Map.of("code", 0, "size", size, "content", ls);
    }
}
