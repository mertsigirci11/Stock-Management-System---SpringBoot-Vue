package com.mertsgrci.klncrud.service;

import com.mertsgrci.klncrud.model.Customer;

import java.util.List;

public interface CustomerService
{
    public Customer createCustomer(Customer customer);
    public List<Customer> getAllCustomers();
    public Customer updateCustomer(Customer customer, Integer id);
    public void deleteCustomer(Integer id);
}
