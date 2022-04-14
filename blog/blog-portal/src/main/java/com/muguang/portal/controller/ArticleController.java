package com.muguang.portal.controller;

import com.github.pagehelper.PageInfo;
import com.muguang.core.entity.Article;
import com.muguang.core.entity.ResponseResult;
import com.muguang.portal.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("blogPortalArticleController")
@RequestMapping("/guest")
public class ArticleController {

    @Resource
    ArticleService articleService;

    @GetMapping("/getRecentArticleByPage")
    public ResponseResult<PageInfo<Article>> getRecentArticleByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                       @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        return new ResponseResult<>(200, "操作完成", articleService.getRecentArticleByPage(pageNum, pageSize));
    }

    /**
     * 根据文章 ID 得到文章
     * @param id
     * @return
     */
    @GetMapping("/getArticleById/{id}")
    public ResponseResult<Article> getArticleById(@PathVariable("id") Integer id){
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return new ResponseResult<>(500, "找不到文章");
        } else {
            return new ResponseResult<>(200, "操作成功", article);
        }
    }

    /**
     * 根据文章 title 得到已发布的文章
     * @param title
     * @return
     */
    @GetMapping(value = "/getPublishedArticleByTitle/{title}")
    public ResponseResult<PageInfo<Article>> getPublishedArticleByTitle(@PathVariable("title") String title,
                                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                        @RequestParam(value = "pageSize", defaultValue = "100") Integer pageSize){
        PageInfo<Article> articles = articleService.selectPagedArticleByCondition(pageNum, pageSize, 1, title, null);
        return new ResponseResult<>(200, "操作成功", articles);
    }

    /**
     * 根据分页得到发布和未发布的文章
     * @parampageNum
     * @parampageSize
     * @return
     */
    @GetMapping(value = "/getArticlesByPage")
    public ResponseResult<PageInfo<Article>> getArticlesByPage(@RequestParam("pageNum") Integer pageNum,
                                                               @RequestParam("pageSize") Integer pageSize){
        PageInfo<Article> page = articleService.selectPagedArticleByCondition(pageNum, pageSize, null, null, null);
        return new ResponseResult<>(200, "操作成功", page);
    }

    /**
     * 根据分页得到已发布的文章,
     * @parampageNum
     * @parampageSize
     * @return
     */
    @GetMapping(value = "/getPublishedArticlesByPage")
    public ResponseResult<PageInfo<Article>> getPublishedArticleByPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize){
        PageInfo<Article> page = articleService.selectPagedArticleByCondition(pageNum, pageSize, 1, null, null);
        return new ResponseResult<>(200, "操作成功", page);
    }

    /**
     * 根据 tag 查询到发布和未发布的文章
     * @param tag
     * @parampageNum
     * @parampageSize
     * @return
     */
    @GetMapping("/getArticlesByTag/{tag}")
    public ResponseResult<PageInfo<Article>> getArticlesByTag(@PathVariable("tag") String tag,
                                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                              @RequestParam(value = "pageSize", defaultValue = "100") Integer pageSize) {
        PageInfo<Article> page = articleService.selectPagedArticleByCondition(pageNum, pageSize, null, null, tag);
        return new ResponseResult<>(200, "操作成功", page);
    }

    /**
     * 根据 tag 得到已发布的文章
     * @param tag
     * @parampageNum
     * @parampageSize
     * @return
     */
    @GetMapping("/getPublishedArticlesByTag/{tag}")
    public ResponseResult<PageInfo<Article>> getPublishedArticlesByTag(@PathVariable("tag") String tag, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        PageInfo<Article> page = articleService.selectPagedArticleByCondition(pageNum, pageSize, 1, null, tag);
        return new ResponseResult<>(200, "操作成功", page);
    }
}
