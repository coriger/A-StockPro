package com.coriger.stock.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coriger.stock.mapper.StockDayMapper;
import com.coriger.stock.model.api.InitStockDataResp;
import com.coriger.stock.model.entity.Stock;
import com.coriger.stock.model.entity.StockDay;

@Service
public class StockDayService extends ServiceImpl<StockDayMapper, StockDay> {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockDayMapper stockDayMapper;

    public void initStocksDay() {
        // 获取股票列表
        List<Stock> list = stockService.list();
        for (Stock stock : list) {
            try {
                String apiUrl = "http://127.0.0.1:8080/api/public/stock_zh_a_hist?symbol=" + stock.getStockCode();
                InitStockDataResp[] stockDayList = restTemplate.getForObject(apiUrl, InitStockDataResp[].class);
                List<StockDay> batchInsert = new ArrayList<>();
                for (InitStockDataResp stockResp : stockDayList) {
                    StockDay stockDay = new StockDay();
                    stockDay.setStockCode(stock.getStockCode());
                    stockDay.setOpen(stockResp.get开盘());
                    stockDay.setClose(stockResp.get收盘());
                    stockDay.setHigh(stockResp.get最高());
                    stockDay.setLow(stockResp.get最低());
                    stockDay.setTradeDate(stockResp.get日期().toLocalDate());
                    stockDay.setVolume(stockResp.get成交量());
                    stockDay.setAmount(stockResp.get成交额());
                    batchInsert.add(stockDay);
                }
                // 批量插入
                if (!batchInsert.isEmpty()) {
                    stockDayMapper.insertBatch(batchInsert);
                }
            } catch (RestClientException e) {
            }
        }
    }

    public LocalDate getLastSyncTime(String stockCode) {
        return stockDayMapper.getLastSyncTime(stockCode);
    }

    public void insertBatch(List<StockDay> batchInsert) {
        stockDayMapper.insertBatch(batchInsert);
    }

}
