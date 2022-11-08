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
import com.mysql.cj.xdevapi.JsonArray;

@RestController
public class MovielistController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/movielist_add")
    private Map<String, Object> movielist_add(@RequestParam Map<String, String> mp, @RequestHeader("Authorization") String session_id){
        String sql = "select * from user_table where session_id='" + session_id + "'";
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0 || ls.get(0).get("expiration_time").toString().compareTo(""+System.currentTimeMillis()) < 0){
            return Map.of("code", 3);
        }
        String user_id = ls.get(0).get("user_id").toString();
        mp.put("user_id", user_id);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String create_time = formatter.format(date);
        mp.put("create_time", create_time);
        sql = "insert into movielist_table(";
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

    @PostMapping("/movielist_delete")
    private Map<String, Object> movielist_delete(@RequestParam Map<String, String> mp, @RequestHeader("Authorization") String session_id){
        String sql = "select * from user_table where session_id=" + session_id;
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0 || ls.get(0).get("expiration_time").toString().compareTo(""+System.currentTimeMillis()) < 0){
            return Map.of("code", 3);
        }
        String op_user_id = ls.get(0).get("user_id").toString();
        sql = "select * from movielist_table where movielist_id=" + mp.get("movielist_id");
        ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0){
            return Map.of("code", 6);
        }
        else if(ls.get(0).get("user_id").equals(op_user_id) == false){
            return Map.of("code", 7);
        }
        sql = "delete from movielist_table where movielist_id=" + mp.get("movielist_id");
        jdbcTemplate.update(sql);
        return Map.of("code", 0);
    }

    @GetMapping("/movielist_query")
    private Map<String, Object> movielist_query(@RequestParam Map<String, String> mp){
        int page = -1;
        String sql = "select * from movielist_table ";
        if(mp.containsKey("page")){
            page = Integer.parseInt(mp.get("page"));
            mp.remove("page");
        }
        if(mp.containsKey("movielist_id")){
            sql += "where movielist_id" + mp.get("movielist_id");
        }
        else if(mp.containsKey("user_id")){
            sql += "where user_id'" + mp.get("user_id") + "'";
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
        //将movie_id字段中的json格式的列表转成真正的list
        for(int i = 0; i < ls.size(); i++){
            Integer[] movie_id_array = new Gson().fromJson(ls.get(i).get("movie_id").toString(), Integer[].class);
            List<Integer> movie_id_ls = Arrays.asList(movie_id_array);
            ls.get(i).put("movie_id", movie_id_ls);
            String[] collector_id_array = new Gson().fromJson(ls.get(i).get("collector_id").toString(), String[].class);
            List<String> collector_id_ls = Arrays.asList(collector_id_array);
            ls.get(i).put("collector_id", collector_id_ls);
        }
        return Map.of("code", 0, "size", size, "content", ls);
    }

    
}
