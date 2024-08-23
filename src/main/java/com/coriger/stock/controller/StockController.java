package com.coriger.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coriger.stock.model.entity.Stock;
import com.coriger.stock.service.StockDayService;
import com.coriger.stock.service.StockService;
import com.coriger.stock.service.StockWeekService;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockDayService stockDayService;

    @Autowired
    private StockWeekService stockWeekService;    

    /**
     * 初始化个股数据
     */
    @RequestMapping("/init")
    public void initStocks() {
        stockService.initStocks();
    }

    /**
     * 初始化个股日线数据
     */
    @RequestMapping("/initDay")
    public void initStocksDay() {
        stockDayService.initStocksDay();
    }

    /**
     * 初始化个股周线数据
     */
    @RequestMapping("/initWeek")
    public void initStocksWeek() {
        stockWeekService.initStocksWeek();
    }

}
