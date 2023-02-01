package com.mertsgrci.klncrud.repository;

import com.mertsgrci.klncrud.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer>
{

}
