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
import com.coriger.stock.mapper.StockWeekMapper;
import com.coriger.stock.model.api.InitStockDataResp;
import com.coriger.stock.model.entity.Stock;
import com.coriger.stock.model.entity.StockWeek;

@Service
public class StockWeekService extends ServiceImpl<StockWeekMapper, StockWeek> {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockDayMapper stockDayMapper;

    @Autowired
    private StockWeekMapper stockWeekMapper;

    public void initStocksWeek() {
        // 获取股票列表
        List<Stock> list = stockService.list();
        for (Stock stock : list) {
            try {
                String apiUrl = "http://127.0.0.1:8080/api/public/stock_zh_a_hist?period=weekly&symbol=" + stock.getStockCode();
                InitStockDataResp[] stockDayList = restTemplate.getForObject(apiUrl, InitStockDataResp[].class);
                List<StockWeek> batchInsert = new ArrayList<>();
                for (InitStockDataResp stockResp : stockDayList) {
                    StockWeek stockWeek = new StockWeek();
                    stockWeek.setStockCode(stock.getStockCode());
                    stockWeek.setOpen(stockResp.get开盘());
                    stockWeek.setClose(stockResp.get收盘());
                    stockWeek.setHigh(stockResp.get最高());
                    stockWeek.setLow(stockResp.get最低());
                    stockWeek.setTradeDate(stockResp.get日期().toLocalDate());
                    stockWeek.setVolume(stockResp.get成交量());
                    stockWeek.setAmount(stockResp.get成交额());
                    batchInsert.add(stockWeek);
                }
                // 批量插入
                if (!batchInsert.isEmpty()) {
                    stockWeekMapper.insertBatch(batchInsert);
                }
            } catch (RestClientException e) {
            }
        }
    }

    public LocalDate getLastSyncTime(String stockCode) {
        return stockWeekMapper.getLastSyncTime(stockCode);
    }

    public void insertBatch(List<StockWeek> batchInsert) {
        stockWeekMapper.insertBatch(batchInsert);
    }

}
