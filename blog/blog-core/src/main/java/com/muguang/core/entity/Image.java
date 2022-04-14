package com.muguang.core.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private static final long serialVersionUID = 1L;

    @TableId
    private String name;
    private String url;
    private String contentType;
    private String createTime;

}
