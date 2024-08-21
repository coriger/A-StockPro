package com.coriger.stock.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coriger.stock.mapper.StockMapper;
import com.coriger.stock.model.entity.Stock;

@Service
public class StockService extends ServiceImpl<StockMapper, Stock>{

    public Stock getStock(Integer id) {
        return null;
    }

}
