package com.muguang.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muguang.core.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 插入文章对应的标签到 article_tag
     * @param articleId
     * @param tagIds
     * @return
     */
    int insertArticleTag(@Param("articleId") Integer articleId, @Param("tagIds") List<Integer> tagIds);

    /**
     * 通过文章 ID 删除对应的标签
     * @param id
     * @return
     */
    int deleteArticleTagByArticleId(@Param("id") Integer id);


    /**
     * 得到每个标签对应的文章数量
     * @return
     */
    List<Map<String, Object>> getPerTagArticlesNums(@Param("tagName") String tagName);

    /**
     * 通过 tagId 删除 article_tag 里的记录
     * @param tagId
     * @return
     */
    int deleteArticleTagByTagId(@Param("tagId") Integer tagId);

    /**
     * 模糊查询tag
     * @param tag_name
     * @return
     */
    List<Tag> selectTagByName(@Param("tag_name") String tag_name);
}
