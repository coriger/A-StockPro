package com.coriger.stock.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.coriger.stock.model.api.InitStockDataResp;
import com.coriger.stock.model.entity.Stock;
import com.coriger.stock.model.entity.StockDay;
import com.coriger.stock.model.entity.StockWeek;
import com.coriger.stock.service.StockDayService;
import com.coriger.stock.service.StockService;
import com.coriger.stock.service.StockWeekService;

@Component
public class StockSyncTask {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockDayService stockDayService;

    @Autowired
    private StockWeekService stockWeekService;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron = "0 0 22 * * ?")
    public void syncStockDay() {
        // 查询所有股票
        List<Stock> stocks = stockService.list();
        for (Stock stock : stocks) {
            // 查询最后一次同步时间
            LocalDate lastSyncTime = stockDayService.getLastSyncTime(stock.getStockCode());
            if (null != lastSyncTime) {
                // 获取lastSyncTime后一天 格式是yyyyMMdd
                String startDate = lastSyncTime.plusDays(1).toString().replaceAll("-", "");
                // 获取当天时间
                String endDate = LocalDate.now().toString().replaceAll("-", "");
                // 查询股票历史数据
                // http://127.0.0.1:8080/api/public/stock_zh_a_hist?symbol=600000&start_date=20240817&end_date=20240818
                String apiUrl = "http://127.0.0.1:8080/api/public/stock_zh_a_hist?symbol=" + stock.getStockCode()
                        + "&start_date=" + startDate + "&end_date=" + endDate;
                try {
                    InitStockDataResp[] stockList = restTemplate.getForObject(apiUrl, InitStockDataResp[].class);
                    if (null == stockList || stockList.length <= 0) {
                        continue;
                    }

                    List<StockDay> batchInsert = new ArrayList<>();
                    for (InitStockDataResp stockResp : stockList) {
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
                        stockDayService.insertBatch(batchInsert);
                    }
                } catch (RestClientException e) {
                }
            } else {
                // 拉取全量数据  新股不做
            }
        }
    }


    @Scheduled(cron = "0 0 22 ? * 5")
    public void syncStockWeek() {
        // 查询所有股票
        List<Stock> stocks = stockService.list();
        for (Stock stock : stocks) {
            // 查询最后一次同步时间
            LocalDate lastSyncTime = stockWeekService.getLastSyncTime(stock.getStockCode());
            if (null != lastSyncTime) {
                // 获取lastSyncTime后一天 格式是yyyyMMdd
                String startDate = lastSyncTime.plusDays(1).toString().replaceAll("-", "");
                // 获取当天时间
                String endDate = LocalDate.now().toString().replaceAll("-", "");
                // 查询股票历史数据
                // http://127.0.0.1:8080/api/public/stock_zh_a_hist?symbol=600000&start_date=20240817&end_date=20240818
                String apiUrl = "http://127.0.0.1:8080/api/public/stock_zh_a_hist?period=weekly&symbol=" + stock.getStockCode() + "&start_date=" + startDate + "&end_date=" + endDate;
                try {
                    InitStockDataResp[] stockList = restTemplate.getForObject(apiUrl, InitStockDataResp[].class);
                    if (null == stockList || stockList.length <= 0) {
                        continue;
                    }

                    List<StockWeek> batchInsert = new ArrayList<>();
                    for (InitStockDataResp stockResp : stockList) {
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
                        stockWeekService.insertBatch(batchInsert);
                    }
                } catch (RestClientException e) {
                }
            } else {
                // 拉取全量数据  新股不做
            }
        }
    }
}
