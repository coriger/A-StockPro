package com.coriger.stock.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_stock")
public class Stock {

    @TableId(type= IdType.AUTO)
    private Long id;
    
    private String name;
}
