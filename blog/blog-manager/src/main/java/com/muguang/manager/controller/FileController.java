package com.muguang.manager.controller;


import com.muguang.core.entity.Image;
import com.muguang.core.entity.ResponseResult;
import com.muguang.core.mapper.ImageMapper;
import com.muguang.core.utils.FileStore;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
public class FileController {

    @javax.annotation.Resource
    private FileStore fileStore;

    @javax.annotation.Resource
    private ImageMapper imageMapper;

    @PostMapping("/upload")
    public ResponseResult<Image> fileUpload(@RequestBody MultipartFile file){
        Image image = fileStore.store(file);
        return new ResponseResult<>(200, "图片上传成功", image);
    }

    @DeleteMapping("/fileDelete")
    public ResponseResult<Image> fileDelete(@RequestParam("fileName") String fileName) {
        String name = fileName.substring(fileName.lastIndexOf("/")+1);
        fileStore.deleteFile(name);
        return new ResponseResult<>(200, "图片删除成功");
    }


    @GetMapping("/selectImage/{key}")
    public ResponseEntity<Resource> selectImage(@PathVariable("key") String id){
        Image image = imageMapper.quickSelectById(id);
        Resource file = fileStore.getFile(image.getName());
        if (file == null) {
            return ResponseEntity.notFound().build();
        } else {
            MediaType mediaType = MediaType.parseMediaType(image.getContentType());
            return ResponseEntity.ok().contentType(mediaType).body(file);
        }
    }

}
