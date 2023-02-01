package com.mertsgrci.klncrud.service;

import com.mertsgrci.klncrud.model.Warehouse;

import java.util.List;

public interface WarehouseService
{
    public Warehouse createWarehouse(Warehouse warehouse);
    public List<Warehouse> getAllWarehouses();
    public Warehouse updateWarehouse(Warehouse warehouse, Integer id);
    public void deleteWarehouse(Integer id);
}
