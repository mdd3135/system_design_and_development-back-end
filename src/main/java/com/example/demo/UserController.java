package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestParam Map<String, String> mp){
        String sql = "select * from user_table where user_id='" + mp.get("user_id") + "'";
        if(jdbcTemplate.queryForList(sql).size() != 0){
            return Map.of("code", 1);
        }
        String now_stamp = String.valueOf(System.currentTimeMillis());
        mp.put("create_time", now_stamp);
        sql = "insert into user_table(";
        int first = 0;
        for(String key : mp.keySet()){
            if(first == 0){
                sql = sql + key;
                first = 1;
            }
            else{
                sql = sql + "," + key;
            }
        }
        sql = sql + ") values (";
        first = 0;
        for(String key : mp.keySet()){
            if(first == 0){
                sql += "'" + mp.get(key) + "'";
                first = 1;
            }
            else{
                sql = sql += "," + "'" + mp.get(key) + "'";
            }
        }
        sql += ")";
        jdbcTemplate.update(sql);
        return Map.of("code", 0);
    }

    @GetMapping("/query_user")
    private Map<String, Object> query_user(@RequestParam Map<String, String>mp){
        int page = -1;
        String sql = "select * from user_table ";
        if(mp.containsKey("page")){
            page = Integer.parseInt(mp.get("page"));
        }
        if(mp.containsKey("user_id")){
            sql += "where user_id='" + mp.get("user_id") + "'";
        }
        if(mp.containsKey("user_name")){
            sql += "where user_name like '%" + mp.get("user_name") + "%'";
        }
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        int size = ls.size();
        if(size == 0){
            return Map.of("code", 4);
        }
        if(page != -1){
            ls = ls.subList(page*10 - 10, min(page*10, size));
        }
        for(int i = 0; i < ls.size(); i++){
            Map<String, Object> tmp = ls.get(i);
            tmp.remove("user_password");
            tmp.remove("session_id");
            tmp.remove("expiration_time");
        }
        return Map.of("code", 0, "size", size, "content", ls);
    }

    private int min(int a, int b){
        if(a < b) return a;
        else return b;
    }

}
