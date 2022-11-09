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
public class BookController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/book_add")
    private Map<String, Object> book_add(@RequestParam Map<String, String> mp){
        String sql = "insert into all_book_table(";
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

    @GetMapping("/book_query")
    private Map<String, Object> book_query(@RequestParam Map<String, String> mp){
        int page = -1;
        String sql = "select * from all_book_table ";
        if(mp.containsKey("page")){
            page = Integer.parseInt(mp.get("page"));
        }
        if(mp.containsKey("book_id")){
            sql += "where book_id=" + mp.get("book_id");
        }
        else{
            int flag = 0;
            for(String key : mp.keySet()){
                if(flag == 0){
                    sql += "where " + key + " like '%" + mp.get(key) + "%' ";
                    flag = 1;
                }
                else{
                    sql += "and " + key + " like '%" + mp.get(key) + "%' ";
                }
            }
        }
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        int size = ls.size();
        if(size == 0){
            return Map.of("code", 4);
        }
        if(page != -1){
            ls = ls.subList(page * 10 - 10, UserController.min(page * 10, size));
        }  
        return Map.of("code", 0, "size", size, "content", ls);
    }

    public Map<String, Object> book_query(Map<String, String> mp, JdbcTemplate jdbcTemplate){
        int page = -1;
        String sql = "select * from all_book_table ";
        if(mp.containsKey("page")){
            page = Integer.parseInt(mp.get("page"));
        }
        if(mp.containsKey("book_id")){
            sql += "where book_id=" + mp.get("book_id");
        }
        else{
            int flag = 0;
            for(String key : mp.keySet()){
                if(flag == 0){
                    sql += "where " + key + " like '%" + mp.get(key) + "%' ";
                    flag = 1;
                }
                else{
                    sql += "and " + key + " like '%" + mp.get(key) + "%' ";
                }
            }
        }
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        int size = ls.size();
        if(size == 0){
            return Map.of("code", 4);
        }
        if(page != -1){
            ls = ls.subList(page * 10 - 10, UserController.min(page * 10, size));
        }  
        return Map.of("code", 0, "size", size, "content", ls);
    }

    @PostMapping("/book_delete")
    private Map<String, Object> book_delete(@RequestParam Map<String, String> mp){
        String sql = "select * from all_book_table where book_id=" + mp.get("book_id");
        if(jdbcTemplate.queryForList(sql).size() == 0){
            return Map.of("code", 5);  // 要删除的内容不存在
        }
        sql = "delete from all_book_table where book_id=" + mp.get("book_id");
        jdbcTemplate.update(sql);

        return Map.of("code", 0);
    }    

    @PostMapping("/book_modify")
    private Map<String, Object> book_modify(@RequestParam Map<String, String> mp){
        String book_id = mp.get("book_id");
        mp.remove("book_id");
        String sql = "select * from all_book_table where book_id=" + book_id;

        if(jdbcTemplate.queryForList(sql).size() == 0){
            return Map.of("code", 6);  // 要修改的内容不存在
        }
        int flag = 0;
        sql = "update all_book_table set ";
        for(String key : mp.keySet()){
            if(flag == 0){
                sql += key + "='" + mp.get(key) +"'";
                flag = 1;
            }
            else{
                sql += "," + key + "='" + mp.get(key) + "'";
            }
            
        }
        sql += " where book_id =" + book_id;
        jdbcTemplate.update(sql);
        return Map.of("code", 0);
    }
}
