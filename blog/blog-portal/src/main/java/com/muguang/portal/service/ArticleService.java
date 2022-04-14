package com.muguang.portal.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.muguang.core.entity.Article;
import com.muguang.core.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("blogPortalArticleService")
public class ArticleService {

    @Resource
    ArticleMapper articleMapper;

    public PageInfo<Article> getRecentArticleByPage(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = articleMapper.selectRecentArticleByPage();
        return new PageInfo<>(articles);
    }

    /**
     * 分页查询文章
     */
    public PageInfo<Article> selectPagedArticleByCondition(int pageNum, int pageSize, Integer state, String title, String tag){
        //count(0)会报错，自定义一个
        PageHelper.startPage(pageNum, pageSize, false);
        List<Article> articles = articleMapper.selectPagedArticleByCondition(state, title, tag);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        pageInfo.setTotal(articleMapper.count(null));
        return pageInfo;
    }

    /**
     * 通过文章 ID 查询文章
     * @param id
     * @return
     */
    public Article getArticleById(Integer id){
        return articleMapper.selectArticleById(id);
    }

}
