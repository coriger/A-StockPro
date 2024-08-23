package com.coriger.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coriger.stock.mapper.StockMapper;
import com.coriger.stock.model.api.InitStockResp;
import com.coriger.stock.model.entity.Stock;

@Service
public class StockService extends ServiceImpl<StockMapper, Stock>{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockMapper stockMapper;

    /**
     * 初始化股票数据
     */
    public void initStocks() {
        String apiUrl = "http://127.0.0.1:8080/api/public/stock_comment_em";
        InitStockResp[] list = restTemplate.getForObject(apiUrl, InitStockResp[].class);
        for(InitStockResp stockResp : list) {
            // 判断股票是否存在，存在则更新，不存在则新增
            Stock stock = getOne(new QueryWrapper<Stock>().eq("stock_code", stockResp.get代码()));
            if(stock == null) {
                stock = new Stock();
                stock.setStockCode(stockResp.get代码());
                stock.setStockName(stockResp.get名称());
                save(stock);
            }else{
                // 存在的话可以根据股票代码更新股票名称
                // 判断名称是否不一致
                if(!stock.getStockName().equals(stockResp.get名称())) {
                    stock.setStockName(stockResp.get名称());
                    updateById(stock);
                }
            }
        }
    }

}
