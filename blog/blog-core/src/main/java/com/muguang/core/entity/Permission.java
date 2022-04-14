package com.muguang.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String permission;

}
