package com.coriger.stock.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_stock")
public class Stock {

    @TableId(type = IdType.AUTO)
    private Long id; // 股票记录 ID

    @TableField("stock_name")
    private String stockName; // 股票名称

    @TableField("stock_code")
    private String stockCode; // 股票代码

    @TableField("industry")
    private String industry; // 所属行业

}
