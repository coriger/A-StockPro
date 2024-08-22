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
import com.coriger.stock.service.StockService;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @RequestMapping("/list")
    public List<Stock> getStocks() {
        return stockService.list();
    }

    @GetMapping("/{id}")
    public Stock getStockById(@PathVariable Long id) {
        return stockService.getById(id);
    }

    @PostMapping
    public boolean createStock(@RequestBody Stock stock) {
        return stockService.save(stock);
    }

    @PutMapping("/{id}")
    public boolean updateStock(@PathVariable Long id, @RequestBody Stock stock) {
        stock.setId(id);
        return stockService.updateById(stock);
    }

    @DeleteMapping("/{id}")
    public boolean deleteStock(@PathVariable Long id) {
        return stockService.removeById(id);
    }

}
