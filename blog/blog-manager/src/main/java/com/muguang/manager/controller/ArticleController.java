package com.muguang.manager.controller;

import com.muguang.core.entity.Article;
import com.muguang.core.entity.ResponseResult;
import com.muguang.manager.service.ArticleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("blogManagerArticleController")
@RequestMapping("/admin")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @GetMapping("/authenticated")
    @PreAuthorize("hasAuthority('get')")
    public ResponseResult<Article> authenticated(){
        return new ResponseResult<>(200, "认证成功");
    }

    @Transactional
    @PostMapping("/saveTemplate")
    public ResponseResult<Article> saveTemplate(@RequestBody Article article){
        article.setState(0);
        if (article.getId() != null) {
            if (articleService.updateArticle(article) == -1) {
                return new ResponseResult<>(500, "更新失败");
            } else {
                return new ResponseResult<>(200, "更新成功");
            }
        } else {
            if (articleService.insertArticle(article) != -1){
                return new ResponseResult<>(200, "创建成功");
            } else {
                return new ResponseResult<>(500, "创建失败");
            }
        }
    }

    @Transactional
    @PutMapping("/putArticle")
    public ResponseResult<Article> putArticle(@RequestBody Article article){
        article.setState(1);
        //当 id 为空时 添加文章
        if (article.getId() == null || article.getId() == -1) {
            if (articleService.insertArticle(article) != -1){
                return new ResponseResult<>(200, "发布成功");
            }
        }
        //当 id 不为空，更新文章
        if (articleService.updateArticle(article) != -1) {
            return new ResponseResult<>(200, "发布成功");
        }
        return new ResponseResult<>(500, "发布失败");
    }

    @Transactional
    @DeleteMapping("/deleteArticle")
    public ResponseResult<Article> deleteArticle(@RequestBody Article article){
        if (articleService.deleteById(article.getId()) != -1) {
            return new ResponseResult<>(200, "删除成功");
        }
        return new ResponseResult<>(500, "删除失败");
    }

    @GetMapping("/test")
    public ResponseResult<Article> test() {
        return new ResponseResult<>(200, "success");
    }

}
