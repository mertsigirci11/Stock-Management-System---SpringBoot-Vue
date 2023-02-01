package com.mertsgrci.klncrud.controller;

import com.mertsgrci.klncrud.dto.WarehouseDto;
import com.mertsgrci.klncrud.model.Warehouse;
import com.mertsgrci.klncrud.service.CompanyService;
import com.mertsgrci.klncrud.service.WarehouseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/warehouses")
@CrossOrigin("http://127.0.0.1:5173/")
public class WarehouseController {
    @Autowired
    private ModelMapper modelMapper;
    private final WarehouseService warehouseService;
    public WarehouseController(WarehouseService warehouseService)
    {
        this.warehouseService = warehouseService;
    }
    @GetMapping
    public List<WarehouseDto> getAllWarehouses()
    {
        return warehouseService.getAllWarehouses().stream()
                .map(warehouse -> modelMapper.map(warehouse, WarehouseDto.class)).collect(Collectors.toList());
    }
    @PostMapping
    public ResponseEntity<WarehouseDto> createWarehouse(@RequestBody WarehouseDto warehouseDto)
    {
        //Converting Dto to Entity
        Warehouse warehouseRequest = modelMapper.map(warehouseDto, Warehouse.class);

        //Creating Operation
        warehouseService.createWarehouse(warehouseRequest);

        //Converting Entity to Warehouse
        WarehouseDto warehouseDtoResponse = modelMapper.map(warehouseRequest, WarehouseDto.class);

        return new ResponseEntity<WarehouseDto>(warehouseDtoResponse, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<WarehouseDto> updateWarehouse(@PathVariable("id") Integer id, @RequestBody WarehouseDto warehouseDto)
    {
        //Converting Dto to Entity
        Warehouse warehouseRequest = modelMapper.map(warehouseDto, Warehouse.class);

        //Updating Operation
        warehouseService.updateWarehouse(warehouseRequest,id);

        //Converting Entity to Dto
        WarehouseDto warehouseDtoResponse = modelMapper.map(warehouseRequest, WarehouseDto.class);

        return ResponseEntity.ok().body(warehouseDtoResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable("id") Integer id)
    {
        try
        {
            warehouseService.deleteWarehouse(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
