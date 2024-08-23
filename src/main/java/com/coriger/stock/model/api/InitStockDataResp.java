package com.coriger.stock.model.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InitStockDataResp {

    private LocalDateTime 日期;
    private String 股票代码;
    private BigDecimal 开盘;
    private BigDecimal 收盘;
    private BigDecimal 最高;
    private BigDecimal 最低;
    private long 成交量;
    private BigDecimal 成交额;
}
