package com.muguang.manager.controller;

import com.muguang.core.entity.ResponseResult;
import com.muguang.core.entity.Tag;
import com.muguang.manager.service.TagService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("blogManagerTagController")
@RequestMapping("/admin")
public class TagController {

    @Resource
    private TagService tagService;

    /**
     * 添加 tag
     * @param tag
     * @return
     */
    @Transactional
    @PostMapping("/addTag")
    public ResponseResult<Tag> addTag(@RequestBody Tag tag){
        if (tag.getId() == null || tag.getId() == -1) {
            int insert = tagService.insertTag(tag);
            if (insert == -1){
                return new ResponseResult<>(500, "添加失败");
            } else {
                return new ResponseResult<>(200, "添加成功");
            }
        }
        return new ResponseResult<>(500, "标签已存在");
    }

    /**
     * 删除 tag
     * @param tag
     * @return
     */
    @Transactional
    @DeleteMapping("/deleteTag")
    public ResponseResult<Tag> deleteTag(@RequestBody Tag tag){
        Integer id = tag.getId();
        if (id == null) {
            return new ResponseResult<>(500, "获取不到标签id");
        }
        int delete = tagService.deleteTagById(tag.getId());
        if (delete == -1) {
            return new ResponseResult<>(500, "删除失败");
        } else {
            return new ResponseResult<>(200, "删除成功");
        }
    }

    /**
     * 修改 tag
     * @param tag
     * @return
     */
    @Transactional
    @PutMapping("/putTag")
    public ResponseResult<Tag> putTag(@RequestBody Tag tag){
        Integer id = tag.getId();
        if (id == null) {
            return new ResponseResult<>(500, "获取不到标签id");
        }
        int result = tagService.updateTagById(tag);
        if (result == -1) {
            return new ResponseResult<>(500, "修改失败");
        } else {
            return new ResponseResult<>(200, "修改成功");
        }
    }

}
