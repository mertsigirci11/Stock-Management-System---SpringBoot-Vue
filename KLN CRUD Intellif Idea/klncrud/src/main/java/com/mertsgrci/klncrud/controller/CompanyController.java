package com.mertsgrci.klncrud.controller;

import com.mertsgrci.klncrud.dto.CompanyDto;
import com.mertsgrci.klncrud.model.Company;
import com.mertsgrci.klncrud.service.CompanyService;
import com.mertsgrci.klncrud.service.implementation.CompanyServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin("http://127.0.0.1:5173/")
public class CompanyController
{
    @Autowired
    private ModelMapper modelMapper;

    private final CompanyService companyService;


    public CompanyController(CompanyService companyService)
    {
        this.companyService = companyService;
    }

    @GetMapping
    public List<CompanyDto> getAllCompanies()
    {
        return companyService.getAllCompanies().stream()
                .map(company->modelMapper.map(company, CompanyDto.class)).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyDto companyDto)
    {
        //Converting Dto to Entity
        Company companyRequest = modelMapper.map(companyDto, Company.class);

        //Creating Operation
        Company company = companyService.createCompany(companyRequest);

        //Converting Entity to Dto
        CompanyDto companyDtoResponse = modelMapper.map(company, CompanyDto.class);

        return new ResponseEntity<CompanyDto>(companyDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable("id") Integer id, @RequestBody CompanyDto companyDto)
    {
        //Converting Dto to Entity
        Company companyRequest = modelMapper.map(companyDto, Company.class);

        //Updating Operation
        Company company = companyService.updateCompany(companyRequest,id);

        //Converting Entity to Dto
        CompanyDto companyDtoResponse = modelMapper.map(company, CompanyDto.class);

        return ResponseEntity.ok().body(companyDtoResponse);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable(name= "id") Integer id)
    {
        try
        {
            companyService.deleteCompany(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}