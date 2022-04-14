package com.muguang.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muguang.core.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询文章个数 state为null则查询发布与未发布的文章
     * @param state
     * @return
     */
    @Select("select count(*) from article where state in (0, 1)")
    int count(@Param("state") Integer state);

    /**
     * 通过 id 降序查询文章
     * @return
     * @param state
     * @param title
     * @param tag
     */
    List<Article> selectPagedArticleByCondition(@Param("state") Integer state, @Param("title") String title, @Param("tag") String tag);

    /**
     * 通过 ID 查询文章
     * @param id
     * @return
     */
    Article selectArticleById(@Param("id") Integer id);

    /**
     * 更新文章
     * @param article
     * @return
     */
    int updateArticle(Article article);

    /**
     * 通过文章 ID 更新文章状态
     * @param id
     * @return
     */
    int updateStateByArticleId(@Param("id") Integer id, @Param("state") Integer state);

    /**
     * 查询最近发布的文章
     * @return
     */
    List<Article> selectRecentArticleByPage();


}
