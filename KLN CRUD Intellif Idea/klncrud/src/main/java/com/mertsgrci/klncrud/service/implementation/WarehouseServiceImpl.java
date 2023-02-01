package com.mertsgrci.klncrud.service.implementation;

import com.mertsgrci.klncrud.model.Warehouse;
import com.mertsgrci.klncrud.repository.WarehouseRepository;
import com.mertsgrci.klncrud.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService
{
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository)
    {
        this.warehouseRepository=warehouseRepository;
    }
    @Override
    public Warehouse createWarehouse(Warehouse warehouse)
    {
        return warehouseRepository.save(warehouse);
    }

    @Override
    public List<Warehouse> getAllWarehouses()
    {
        return warehouseRepository.findAll();
    }

    @Override
    public Warehouse updateWarehouse(Warehouse warehouse, Integer id)
    {
        Warehouse warehouse1 = warehouseRepository.findById(id).orElseThrow(null);

        warehouse1.setName(warehouse.getName());
        //Altta hata çıkabilir
        warehouse1.setCompany(warehouse.getCompany());
        return warehouseRepository.save(warehouse1);
    }

    @Override
    public void deleteWarehouse(Integer id)
    {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(null);
        warehouseRepository.delete(warehouse);
    }
}
