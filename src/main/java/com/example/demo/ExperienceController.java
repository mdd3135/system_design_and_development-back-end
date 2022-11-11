package com.example.demo;

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
public class ExperienceController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/experience_add")
    private Map<String, Object> experience_add(@RequestParam Map<String, String> mp, @RequestHeader("Authorization") String session_id){
        String sql = "select * from user_table where session_id='" + session_id + "'";
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0 || ls.get(0).get("expiration_time").toString().compareTo(""+System.currentTimeMillis()) < 0){
            return Map.of("code", 3);  // 校验是否登陆
        }
        String user_id = ls.get(0).get("user_id").toString();
        mp.put("user_id", user_id);
        
        sql = "insert into experience_table(";
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

    @GetMapping("/experience_query")
    private Map<String, Object> experience_query(@RequestParam Map<String, String> mp){
        int page = -1;
        String sql = "select * from experience_table ";
        if(mp.containsKey("page")){
            page = Integer.parseInt(mp.get("page"));
            mp.remove("page");
        }
        int flag = 0;
        for(String key : mp.keySet()){
            if(flag == 0){
                sql += "where " + key + "='" + mp.get(key) + "' ";
                flag = 1;
            }
            else{
                sql += "and " + key + "='" + mp.get(key) + "' ";
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

    @PostMapping("/experience_delete")
    private Map<String, Object> experience_delete(@RequestParam Map<String, String> mp, @RequestHeader("Authorization") String session_id){
        String sql = "select * from user_table where session_id=" + session_id;
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0 || ls.get(0).get("expiration_time").toString().compareTo(""+System.currentTimeMillis()) < 0){
            return Map.of("code", 3);
        }
        String op_user_id = ls.get(0).get("user_id").toString();
        sql = "select * from experience_table where experience_id=" + mp.get("experience_id");
        ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0){
            return Map.of("code", 6);
        }
        else if(ls.get(0).get("user_id").equals(op_user_id) == false){
            return Map.of("code", 7);
        }
        sql = "delete from experience_table where experience_id=" + mp.get("experience_id");
        jdbcTemplate.update(sql);
        return Map.of("code", 0);
    }

    @PostMapping("/experience_modify")
    private Map<String, Object> experience_modify(@RequestParam Map<String, String> mp, @RequestHeader("Authorization") String session_id){
        String sql = "select * from user_table where session_id=" + session_id;
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0 || ls.get(0).get("expiration_time").toString().compareTo(""+System.currentTimeMillis()) < 0){
            return Map.of("code", 3);
        }
        String op_user_id = ls.get(0).get("user_id").toString();
        sql = "select * from experience_table where experience_id=" + mp.get("experience_id");
        ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0){
            return Map.of("code", 6);
        }
        else if(ls.get(0).get("user_id").equals(op_user_id) == false){
            return Map.of("code", 7);
        }

        String experience_id = mp.get("experience_id");
        mp.remove("experience_id");
        sql = "select * from experience_table where experience_id=" + experience_id;

        if(jdbcTemplate.queryForList(sql).size() == 0){
            return Map.of("code", 6);  // 要修改的内容不存在
        }
        int flag = 0;
        sql = "update experience_table set ";
        for(String key : mp.keySet()){
            if(flag == 0){
                sql += key + "='" + mp.get(key) +"'";
                flag = 1;
            }
            else{
                sql += "," + key + "='" + mp.get(key) + "'";
            }
            
        }
        sql += " where experience_id =" + experience_id;
        jdbcTemplate.update(sql);
        return Map.of("code", 0);
    }
}

