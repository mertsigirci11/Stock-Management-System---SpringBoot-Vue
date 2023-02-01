package com.mertsgrci.klncrud.service.implementation;

import com.mertsgrci.klncrud.model.Customer;
import com.mertsgrci.klncrud.repository.CustomerRepository;
import com.mertsgrci.klncrud.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService
{
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository)
    {
        this.customerRepository=customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer)
    {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers()
    {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Customer customer, Integer id)
    {
        Customer customerRequest = customerRepository.findById(id).orElseThrow(null);

        customerRequest.setName(customer.getName());
        customerRequest.setSurname(customer.getSurname());
        customerRequest.setUsername(customer.getUsername());
        customerRequest.setPassword(customer.getPassword());
        customerRequest.setTaxAdministration(customer.getTaxAdministration());

        return customerRepository.save(customerRequest);
    }

    @Override
    public void deleteCustomer(Integer id)
    {
        Customer customer = customerRepository.findById(id).orElseThrow(null);
        customerRepository.delete(customer);
    }
}
