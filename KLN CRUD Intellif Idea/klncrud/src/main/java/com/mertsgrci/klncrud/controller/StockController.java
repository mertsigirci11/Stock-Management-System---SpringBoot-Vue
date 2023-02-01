package com.mertsgrci.klncrud.controller;

import com.mertsgrci.klncrud.dto.StockDto;
import com.mertsgrci.klncrud.model.Stock;
import com.mertsgrci.klncrud.service.StockService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin("http://127.0.0.1:5173/")
public class StockController
{
    @Autowired
    private ModelMapper modelMapper;

    private final StockService stockService;

    public StockController(StockService stockService)
    {
        this.stockService = stockService;
    }

    @GetMapping
    public List<StockDto> getAllStocks()
    {
        return stockService.getAllStocks().stream()
                .map(stock -> modelMapper.map(stock,StockDto.class)).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<StockDto> createStock(@RequestBody StockDto stockDto)
    {
        //Converting Dto to Entity
        Stock stockRequest = modelMapper.map(stockDto,Stock.class);

        //Creating Operation
        stockService.createStock(stockRequest);

        //Converting Entity to Dto
        StockDto stockDtoResponse = modelMapper.map(stockRequest, StockDto.class);

        return new ResponseEntity<StockDto>(stockDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockDto> updateStock(@PathVariable("id") Integer id, @RequestBody StockDto stockDto)
    {
        //Converting Dto to Entity
        Stock stockRequest = modelMapper.map(stockDto,Stock.class);

        //Updating Operation
        stockService.updateStock(stockRequest,id);

        //Converting Entity to Dto
        StockDto stockDtoResponse = modelMapper.map(stockRequest, StockDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(stockDtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable("id") Integer id)
    {
        try
        {
            stockService.deleteStock(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
