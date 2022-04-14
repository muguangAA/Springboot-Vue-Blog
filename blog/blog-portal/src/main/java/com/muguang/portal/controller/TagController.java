package com.muguang.portal.controller;

import com.muguang.core.entity.ResponseResult;
import com.muguang.core.entity.Tag;
import com.muguang.portal.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController("blogPortalTagController")
@RequestMapping("/guest")
public class TagController {

    @Resource
    private TagService tagServiceAdmin;

    @GetMapping("/getTagList")
    public ResponseResult<List<Tag>> getTagList() {
        List<Tag> tagList = tagServiceAdmin.getTagList();
        return new ResponseResult<>(200, "查询成功", tagList);
    }

    /**
     * 模糊搜索标签
     * @param tagName
     * @return
     */
    @GetMapping("/searchTags")
    public ResponseResult<List<Tag>> searchTags(@RequestParam String tagName){
        List<Tag> tags = tagServiceAdmin.selectTagByName(tagName);
        return new ResponseResult<>(200, "查询成功", tags);
    }

    /**
     * 查询 tag 对应的文章数量， tagName 为null时默认搜索所有, 否则为模糊搜索
     * @return
     */
    @GetMapping("/getTagArticleNums/{tagName}")
    public ResponseResult<List<Map<String, Object>>> getTagArticleNums(@PathVariable("tagName") String tagName){
        List<Map<String, Object>> perTagArticlesNums = null;
        if (tagName == null || "".equals(tagName)) {
            perTagArticlesNums = tagServiceAdmin.getPerTagArticlesNums(null);
        } else {
            perTagArticlesNums = tagServiceAdmin.getPerTagArticlesNums(tagName);
        }
        return new ResponseResult<>(200, "查询成功", perTagArticlesNums);
    }

    @GetMapping("/getPerTagArticleNums")
    public ResponseResult<List<Map<String, Object>>> getTagArticleNums(){
        List<Map<String, Object>> perTagArticlesNums = tagServiceAdmin.getPerTagArticlesNums(null);
        return new ResponseResult<>(200, "查询成功", perTagArticlesNums);
    }

}
