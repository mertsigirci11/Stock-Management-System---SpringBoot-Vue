package com.mertsgrci.klncrud.service;

import com.mertsgrci.klncrud.model.Stock;

import java.util.List;

public interface StockService
{
    public Stock createStock(Stock stock);
    public List<Stock> getAllStocks();
    public Stock updateStock(Stock stock, Integer id);
    public void deleteStock(Integer id);
}
