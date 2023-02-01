package com.mertsgrci.klncrud.controller;

import com.mertsgrci.klncrud.dto.CustomerDto;
import com.mertsgrci.klncrud.model.Customer;
import com.mertsgrci.klncrud.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin("http://127.0.0.1:5173/")
public class CustomerController
{
    @Autowired
    private ModelMapper modelMapper;

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers()
    {
        return customerService.getAllCustomers().stream()
                .map(customer -> modelMapper.map(customer,CustomerDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto)
    {
        //Converting Dto to Entity
        Customer customerRequest = modelMapper.map(customerDto,Customer.class);

        //Creating Operation
        customerService.createCustomer(customerRequest);

        //Converting Entity to Dto
        CustomerDto customerDtoResponse = modelMapper.map(customerRequest,CustomerDto.class);

        return new ResponseEntity<CustomerDto>(customerDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Integer id, @RequestBody CustomerDto customerDto)
    {
        //Converting Dto to Entity
        Customer customerRequest = modelMapper.map(customerDto, Customer.class);

        //Updating Operation
        customerService.updateCustomer(customerRequest,id);

        //Converting Entity to Dto
        CustomerDto customerDtoResponse = modelMapper.map(customerRequest, CustomerDto.class);

        return new ResponseEntity<CustomerDto>(customerDtoResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Integer id)
    {
        try
        {
            customerService.deleteCustomer(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
