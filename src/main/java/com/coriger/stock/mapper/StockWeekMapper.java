package com.coriger.stock.mapper;

import java.time.LocalDate;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coriger.stock.model.entity.StockWeek;

public interface StockWeekMapper extends BaseMapper<StockWeek> {

    void insertBatch(List<StockWeek> stockWeekList);

    LocalDate getLastSyncTime(String stockCode);
}
