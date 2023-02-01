package com.mertsgrci.klncrud.repository;

import com.mertsgrci.klncrud.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer>
{

}
