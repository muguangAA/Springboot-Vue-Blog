package com.muguang.manager.service;

import com.muguang.core.entity.Article;
import com.muguang.core.entity.Tag;
import com.muguang.core.mapper.TagMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("blogManagerTagService")
public class TagService {

    @Resource
    private TagMapper tagMapper;

    /**
     * 删除标签
     * @param id
     * @return
     */
    public int deleteTagById(Integer id) {
        int delete = tagMapper.deleteById(id);
        if (delete != -1) {
            tagMapper.deleteArticleTagByTagId(id);
        }
        return delete;
    }

    /**
     * 插入信息到 article_tag
     * @return
     */
    public int insertArticleTag(Article article) {
        List<Integer> tagIds = new ArrayList<>();
        article.getTags().forEach(tag -> tagIds.add(tag.getId()));
        return tagMapper.insertArticleTag(article.getId(), tagIds);
    }

    /**
     * 通过文章 ID 删除文章对应的标签
     * @param article
     * @return
     */
    public int deleteArticleTagByArticleId(Article article){
        return tagMapper.deleteArticleTagByArticleId(article.getId());
    }

    /**
     * 查询 tag 对应的文章数量， tagName 为null时默认搜索所有, 否则为模糊搜索
     * @return
     */
    public List<Map<String, Object>> getPerTagArticlesNums(String tagName) {
        return tagMapper.getPerTagArticlesNums(tagName);
    }

    /**
     * 插入Tag
     * @param tag
     * @return
     */
    public int insertTag(Tag tag) {
        return tagMapper.insert(tag);
    }

    /**
     * 修改 tag
     * @param tag
     * @return
     */
    public int updateTagById(Tag tag) {
        return tagMapper.updateById(tag);
    }
}
