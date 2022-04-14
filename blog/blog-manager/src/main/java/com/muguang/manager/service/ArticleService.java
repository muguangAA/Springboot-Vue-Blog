package com.muguang.manager.service;

import cn.hutool.core.util.StrUtil;
import com.muguang.core.entity.Article;
import com.muguang.core.entity.Tag;
import com.muguang.core.exception.MessageException;
import com.muguang.core.mapper.ArticleMapper;
import com.muguang.core.utils.TimeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("blogManagerArticleService")
public class ArticleService {

    @Resource
    ArticleMapper articleMapper;

    @Resource
    TagService tagService;

    /**
     * 插入文章
     * @param article
     * @return
     */
    public int insertArticle(Article article){
        String time = TimeUtils.getTime();
        article.setCreateTime(time);
        article.setUpdateTime(time);
        int insert = articleMapper.insert(article);
        if (insert != -1) {
            List<Tag> tags = article.getTags();
            tagService.insertArticleTag(article);
        }
        return insert;
    }

    /**
     * 通过文章 ID 更新文章
     * @param article
     * @return
     */
    public int updateArticle(Article article){
        if (StrUtil.isEmpty(article.getTitle())) {
            throw new MessageException("文章标题不能为空");
        }
        article.setUpdateTime(TimeUtils.getTime());
        int update = articleMapper.updateArticle(article);
        if (update != -1) {
            tagService.deleteArticleTagByArticleId(article);
            tagService.insertArticleTag(article);
        }
        return update;
    }

    /**
     * 通过文章 ID 删除文章，即更新 state = 2
     * @param id
     * @return
     */
    public int deleteById(Integer id) {
        return articleMapper.updateStateByArticleId(id, 2);
    }

}
