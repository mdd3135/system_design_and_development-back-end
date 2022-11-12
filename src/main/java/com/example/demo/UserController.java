package com.example.demo;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
// @RunWith(SpringRunner.class)
public class UserController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    MailService mailService;

    @Autowired
    JavaMailSender mailSender;

    @PostMapping("/user_register")
    private Map<String, Object> user_register(@RequestParam Map<String, String> mp){
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

    @GetMapping("/user_query")
    private Map<String, Object> user_query(@RequestParam Map<String, String>mp){
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

    public Map<String, Object> user_query(Map<String, String>mp, JdbcTemplate jdbcTemplate){
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

    @PostMapping("user_login")
    private Map<String, Object> user_login(@RequestParam Map<String, String> mp){
        String sql = "select * from user_table where user_id='" + mp.get("user_id") + "' and user_password='" + mp.get("user_password") + "'";
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        if(ls.size() != 0){
            String now_stamp = String.valueOf(System.currentTimeMillis());
            String expiration_time = String.valueOf(Long.valueOf(now_stamp) + (long)(7L * 24L * 3600L * 1000L));
            sql = "update user_table set session_id = '" + now_stamp + "', expiration_time ='" + expiration_time + "' where user_id = '" + mp.get("user_id") + "'";
            jdbcTemplate.update(sql);
            return Map.of("code", 0, "session_id", now_stamp);
        }
        return Map.of("code", 2);
    }

    @PostMapping("user_modify")
    private Map<String, Object> user_modify(@RequestParam Map<String, String> mp, @RequestHeader("Authorization") String session_id){
        String sql = "select * from user_table where session_id='" + session_id + "'";
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0 || ls.get(0).get("expiration_time").toString().compareTo(""+System.currentTimeMillis()) < 0){
            return Map.of("code", 3);
        }
        int flag = 0;
        sql = "update user_table set ";
        for(String key : mp.keySet()){
            if(flag == 0){
                sql = sql + key + "='" + mp.get(key) + "'";
                flag = 1;
            }
            else{
                sql += "," + key + "='" + mp.get(key) + "'";
            }
        }
        sql += " where session_id = '" + session_id + "'";
        jdbcTemplate.update(sql);
        return Map.of("code", 0);
    }

    @PostMapping("user_code_send")
    private Map<String, Object> user_code_send(@RequestParam Map<String, String> mp){
        String user_id = mp.get("user_id");
        String sql = "select * from user_table where user_id='" + user_id + "'";
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0){
            return Map.of("code", 3);
        }
        String user_email = ls.get(0).get("user_email").toString();
        String verified_code = "";
        Random r = new Random();
        for(int i = 0; i < 6; i++){
            verified_code = verified_code + r.nextInt(10);
        }
        sql = "update user_table set verified_code='" + verified_code + "' where user_id='" + user_id + "'";
        jdbcTemplate.update(sql);
        MailController mailController = new MailController();
        mailController.mySendSimpleMail(user_email, "书影足迹验证码", "验证码：" +  verified_code, mailSender);
        return Map.of("code", 0);
    }

    @PostMapping("user_pwd_change")
    private Map<String, Object> user_pwd_change(@RequestParam Map<String, String> mp){
        String user_id = mp.get("user_id");
        String sql = "select * from user_table where user_id='" + user_id + "'";
        List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql);
        if(ls.size() == 0){
            return Map.of("code", 3);
        }
        String verified_code = mp.get("verified_code");
        String true_verified_code = ls.get(0).get("verified_code").toString();
        if(true_verified_code.compareTo(verified_code) != 0){
            return Map.of("code", 8);
        }
        sql = "update user_table set user_password='" + mp.get("user_password") + "' where user_id='" + user_id + "'";
        return Map.of("code", 0);
    }
    

    public static int min(int a, int b){
        if(a < b) return a;
        else return b;
    }
}
