package com.example.demo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
    private String path = "/home/mdd/文档/系统设计与开发/团队项目/files/";
    
    @PostMapping("/file_upload")
    public Map<String, Object> file_upload(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException{
        String oldName = file.getOriginalFilename();
        String fileName = String.valueOf(System.currentTimeMillis()) + oldName.substring(oldName.lastIndexOf("."));
        String filePath = path + fileName;
        File dest = new File(filePath);
        if(dest.getParentFile().exists() == false){
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        return Map.of("code", 0, "file_name", fileName);
    }

    @GetMapping("/file_download")
    public Map<String, Object> download(HttpServletResponse response, @RequestParam("file_name") String file_name) throws Exception{
        File dest = new File(path + file_name);
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int)dest.length());
        response.setHeader("Content-Disposition", "attachment;filename="+file_name);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(dest));
        byte[] buff = new byte[1024];
        OutputStream os  = response.getOutputStream();
        int i = 0;
        while ((i = bis.read(buff)) != -1) {
            os.write(buff, 0, i);
            os.flush();
        }
        bis.close();
        return Map.of("code", 0);
    }
}
