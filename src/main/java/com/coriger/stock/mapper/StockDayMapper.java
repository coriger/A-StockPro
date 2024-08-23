package com.coriger.stock.mapper;

import java.time.LocalDate;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coriger.stock.model.entity.StockDay;

public interface StockDayMapper extends BaseMapper<StockDay> {

    void insertBatch(List<StockDay> stockDayList);

    LocalDate getLastSyncTime(String stockCode);
}
