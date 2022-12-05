package com.example.demo;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookReactionController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/book_reaction_add")
    private Map<String, Object> book_reaction_add(@RequestParam Map<String, String> mp, @RequestHeader("Authorization") String session_id){
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
        sql = "insert into book_reaction_table(";
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

    @PostMapping("/book_reaction_delete")
    private Map<String, Object> book_reaction_delete(@RequestParam Map<String, String> mp, @RequestHeader("Authorization") String session_id){
        String sql = "select * from user_table where session_id=" + session_id;
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0 || ls.get(0).get("expiration_time").toString().compareTo(""+System.currentTimeMillis()) < 0){
            return Map.of("code", 3);
        }
        String op_user_id = ls.get(0).get("user_id").toString();
        sql = "select * from book_reaction_table where book_reaction_id=" + mp.get("book_reaction_id");
        ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0){
            return Map.of("code", 6);
        }
        else if(ls.get(0).get("user_id").equals(op_user_id) == false){
            return Map.of("code", 7);
        }
        sql = "delete from book_reaction_table where book_reaction_id=" + mp.get("book_reaction_id");
        jdbcTemplate.update(sql);
        return Map.of("code", 0);
    }

    @GetMapping("book_reaction_query")
    private Map<String, Object> book_reaction_query(@RequestParam Map<String,String> mp){
        int page = -1;
        String sql = "select * from book_reaction_table ";
        if(mp.containsKey("page")){
            page = Integer.parseInt(mp.get("page"));
            mp.remove("page");
        }
        int flag = 0;
        if(mp.containsKey("book_reaction_id")){
            if(flag == 0){
                sql += " where ";
            }
            else if(flag == 1){
                sql += " and ";
            }
            sql += "book_reaction_id=" + mp.get("book_reaction_id");
            flag = 1;
        }
        if(mp.containsKey("user_id")){
            if(flag == 0){
                sql += " where ";
            }
            else if(flag == 1){
                sql += " and ";
            }
            sql += "user_id='" + mp.get("user_id") + "'";
            flag = 1;
        }
        if(mp.containsKey("book_id")){
            if(flag == 0){
                sql += " where ";
            }
            else if(flag == 1){
                sql += " and ";
            }
            flag = 1;
            sql += "book_id='" + mp.get("book_id") + "'";
        }
        if(mp.containsKey("title")){
            if(flag == 0){
                sql += " where ";
            }
            else if(flag == 1){
                sql += " and ";
            }
            flag = 1;
            sql += "title like '%" + mp.get("title") + "%'";
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

    @PostMapping("book_reaction_modify")
    private Map<String, Object> book_reaction_modify(@RequestParam Map<String, String> mp, @RequestHeader("Authorization") String session_id){
        String sql = "select * from user_table where session_id=" + session_id;
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0 || ls.get(0).get("expiration_time").toString().compareTo(""+System.currentTimeMillis()) < 0){
            return Map.of("code", 3);
        }
        String op_user_id = ls.get(0).get("user_id").toString();
        sql = "select * from book_reaction_table where book_reaction_id=" + mp.get("book_reaction_id");
        ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0){
            return Map.of("code", 6);
        }
        else if(ls.get(0).get("user_id").equals(op_user_id) == false){
            return Map.of("code", 7);
        }
        String book_reaction_id = mp.get("book_reaction_id");
        mp.remove("book_reaction_id");
        int flag = 0;
        sql = "update book_reaction_table set ";
        for(String key : mp.keySet()){
            if(flag == 0){
                sql += key + "='" + mp.get(key) + "'";
                flag = 1;
            }
            else{
                sql += "," + key + "='" + mp.get(key) + "'";
            }
        }
        sql += " where book_reaction_id =" + book_reaction_id;
        jdbcTemplate.update(sql);
        return Map.of("code", 0);
    }
}
