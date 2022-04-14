package com.muguang.portal.service;

import com.muguang.core.entity.Tag;
import com.muguang.core.mapper.TagMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("blogPortalTagService")
public class TagService {

    @Resource
    private TagMapper tagMapper;

    /**
     * 查询 tag 对应的文章数量， tagName 为null时默认搜索所有, 否则为模糊搜索
     * @return
     */
    public List<Map<String, Object>> getPerTagArticlesNums(String tagName) {
        return tagMapper.getPerTagArticlesNums(tagName);
    }

    /**
     * 模糊查询 tag
     * @param tagName
     * @return
     */
    public List<Tag> selectTagByName(String tagName) {
        return tagMapper.selectTagByName(tagName);
    }

    /**
     * 得到标签列表
     * @return
     */
    public List<Tag> getTagList() {
        return tagMapper.selectList(null);
    }
}
