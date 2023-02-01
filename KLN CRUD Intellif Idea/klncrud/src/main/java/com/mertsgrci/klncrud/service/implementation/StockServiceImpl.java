package com.mertsgrci.klncrud.service.implementation;

import com.mertsgrci.klncrud.model.Stock;
import com.mertsgrci.klncrud.repository.StockRepository;
import com.mertsgrci.klncrud.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService
{
    private final StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository)
    {
        this.stockRepository = stockRepository;
    }
    @Override
    public Stock createStock(Stock stock)
    {
        return stockRepository.save(stock);
    }

    @Override
    public List<Stock> getAllStocks()
    {
        return stockRepository.findAll();
    }

    @Override
    public Stock updateStock(Stock stock, Integer id)
    {
        Stock stockRequest = stockRepository.findById(id).orElseThrow(null);

        stockRequest.setName(stock.getName());
        stockRequest.setPiece(stock.getPiece());
        stockRequest.setPrice(stock.getPrice());
        stockRequest.setWarehouse(stock.getWarehouse());

        return stockRepository.save(stockRequest);
    }

    @Override
    public void deleteStock(Integer id)
    {
        Stock stock = stockRepository.findById(id).orElseThrow(null);
        stockRepository.delete(stock);
    }
}
