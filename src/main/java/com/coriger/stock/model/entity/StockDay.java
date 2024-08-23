package com.coriger.stock.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@TableName("t_stock_day")
public class StockDay {

    @TableId(type = IdType.AUTO)
    private Long id; // 股票记录 ID

    @TableField("open")
    private BigDecimal open; // 开盘价

    @TableField("close")
    private BigDecimal close; // 收盘价

    @TableField("high")
    private BigDecimal high; // 最高价

    @TableField("low")
    private BigDecimal low; // 最低价

    @TableField("stock_code")
    private String stockCode; // 股票代码

    @TableField("trade_date")
    private LocalDate tradeDate; // 交易日期

    @TableField("volume")
    private Long volume; // 成交量

    @TableField("amount")
    private BigDecimal amount; // 成交额

}
