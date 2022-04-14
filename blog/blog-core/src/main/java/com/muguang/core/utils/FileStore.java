package com.muguang.core.utils;

import cn.hutool.core.net.url.UrlBuilder;
import com.muguang.core.entity.Image;
import com.muguang.core.mapper.ImageMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Component
public class FileStore {

    @javax.annotation.Resource
    private ImageMapper imageMapper;

    private String saveUrlName;

    @Value("${host}")
    private String host;

    @Value("${port}")
    private String port;

    @Value("${FileStore.saveUrl}")
    public void setSaveUrlName(String str) {
        this.saveUrlName = str.substring(1);
        try {
            Files.createDirectories(Paths.get(getSavePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSavePath(){
        return Paths.get(System.getProperty("user.dir"), saveUrlName).toString();
    }

    public Image store(MultipartFile file){
        String name = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String suffix = Objects.requireNonNull(name).substring(name.lastIndexOf("."), name.length());
        String fileName = uuid + suffix;
        Image image = new Image();
        try {
            Files.copy(file.getInputStream(), Paths.get(getSavePath(), fileName), StandardCopyOption.REPLACE_EXISTING);
            image.setName(fileName);
            String url = UrlBuilder.create()
                    .setScheme("http")
                    .setHost(host)
                    .setPort(Integer.parseInt(port))
                    .addPath(saveUrlName)
                    .addPath(fileName).toString();
            image.setUrl(url);
            image.setContentType(file.getContentType());
            if (imageMapper.quickInsert(image) == -1) {
                throw new RuntimeException("图片插入失败");
            }
        } catch (IOException e) {
            throw new RuntimeException("图片存储失败");
        }
        return image;
    }

    public Resource getFile(String fileName){
        try {
            Path file = Paths.get(getSavePath(), fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("图片文件无法读取");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("图片文件读取发生异常");
        }
    }

    public void deleteFile(String fileName) {
        try {
            Path file = Paths.get(getSavePath(), fileName);
            Files.delete(file);
        } catch (IOException e) {
            throw new RuntimeException("图片删除失败");
        }
    }

}
