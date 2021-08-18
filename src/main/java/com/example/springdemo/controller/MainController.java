package com.example.springdemo.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class MainController {

    @Value("/home/sevo/IdeaProjects/sevo/springDemo/upload")
    private String uploadDir;

    @GetMapping("/")
    public String mainPage(){
        return "index";
    }

    @GetMapping(value = "/image",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@RequestParam("picUrl") String picUrl) throws IOException {
        InputStream in = new FileInputStream(uploadDir + File.separator + picUrl);
        return IOUtils.toByteArray(in);
    }

}
