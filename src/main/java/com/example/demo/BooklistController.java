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
public class BooklistController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/booklist_add")
    private Map<String, Object> booklist_add(@RequestParam Map<String, String> mp, @RequestHeader("Authorization") String session_id){
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
        sql = "insert into booklist_table(";
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

    @PostMapping("/booklist_delete")
    private Map<String, Object> booklist_delete(@RequestParam Map<String, String> mp, @RequestHeader("Authorization") String session_id){
        String sql = "select * from user_table where session_id=" + session_id;
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0 || ls.get(0).get("expiration_time").toString().compareTo(""+System.currentTimeMillis()) < 0){
            return Map.of("code", 3);
        }
        String op_user_id = ls.get(0).get("user_id").toString();
        sql = "select * from booklist_table where booklist_id=" + mp.get("booklist_id");
        ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0){
            return Map.of("code", 6);
        }
        else if(ls.get(0).get("user_id").equals(op_user_id) == false){
            return Map.of("code", 7);
        }
        sql = "delete from booklist_table where booklist_id=" + mp.get("booklist_id");
        jdbcTemplate.update(sql);
        return Map.of("code", 0);
    }

    @GetMapping("/booklist_query")
    private Map<String, Object> booklist_query(@RequestParam Map<String, String> mp){
        int page = -1;
        String sql = "select * from booklist_table ";
        if(mp.containsKey("page")){
            page = Integer.parseInt(mp.get("page"));
            mp.remove("page");
        }
        if(mp.containsKey("booklist_id")){
            sql += "where booklist_id=" + mp.get("booklist_id");
        }
        else if(mp.containsKey("user_id")){
            sql += "where user_id='" + mp.get("user_id") + "'";
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
        //???movie_id????????????json??????????????????????????????list
        for(int i = 0; i < ls.size(); i++){
            Integer[] book_id_array = new Gson().fromJson(ls.get(i).get("book_id").toString(), Integer[].class);
            List<Integer> book_id_ls = Arrays.asList(book_id_array);
            ls.get(i).put("book_id", book_id_ls);
            String[] collector_id_array = new Gson().fromJson(ls.get(i).get("collector_id").toString(), String[].class);
            List<String> collector_id_ls = Arrays.asList(collector_id_array);
            ls.get(i).put("collector_id", collector_id_ls);
        }
        return Map.of("code", 0, "size", size, "content", ls);
    }

    @PostMapping("/booklist_modify")
    private Map<String, Object> booklist_modify(@RequestParam Map<String, String> mp){
        String booklist_id = mp.get("booklist_id");
        mp.remove("booklist_id");
        String sql = "select * from booklist_table where booklist_id=" + booklist_id;
        if(jdbcTemplate.queryForList(sql).size() == 0){
            return Map.of("code", 6);
        }
        int flag = 0;
        sql = "update booklist_table set ";
        for(String key : mp.keySet()){
            if(flag == 0){
                sql += key + "='" + mp.get(key) + "'";
                flag = 1;
            }
            else{
                sql += "," + key + "='" + mp.get(key) + "'";
            }
        }
        sql += " where booklist_id =" + booklist_id;
        jdbcTemplate.update(sql);
        return Map.of("code", 0);
    }
}
