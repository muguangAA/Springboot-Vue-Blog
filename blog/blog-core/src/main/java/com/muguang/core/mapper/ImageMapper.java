package com.muguang.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muguang.core.entity.Image;
import com.muguang.core.utils.TimeUtils;
import com.muguang.core.utils.TransferUtils;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper extends BaseMapper<Image> {

    default Image quickSelectById(String id){
        Image image = this.selectById(id);
        TransferUtils.setImage(image);
        return image;
    }

    default int quickInsert(Image image){
        image.setCreateTime(TimeUtils.getTime());
        return this.insert(image);
    }

}
