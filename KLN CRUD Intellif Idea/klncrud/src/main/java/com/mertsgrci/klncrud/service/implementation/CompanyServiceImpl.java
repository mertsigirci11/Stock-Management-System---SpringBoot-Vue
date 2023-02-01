package com.mertsgrci.klncrud.service.implementation;

import com.mertsgrci.klncrud.model.Company;
import com.mertsgrci.klncrud.model.Warehouse;
import com.mertsgrci.klncrud.repository.CompanyRepository;
import com.mertsgrci.klncrud.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService
{
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository)
    {
        this.companyRepository=companyRepository;
    }

    @Override
    public Company createCompany(Company company)
    {
        return companyRepository.save(company);
    }

    @Override
    public List<Company> getAllCompanies()
    {
        return companyRepository.findAll();
    }

    @Override
    public Company updateCompany(Company company, Integer id)
    {
        Company companyRequest = companyRepository.findById(id).orElseThrow(null);

        companyRequest.setName(company.getName());
        companyRequest.setEmail(company.getEmail());
        companyRequest.setAddress(company.getAddress());
        companyRequest.setPassword(company.getPassword());
        companyRequest.setPhone(company.getPhone());
        companyRequest.setUsername(company.getUsername());
        companyRequest.setTaxNumber(company.getTaxNumber());

        return companyRepository.save(companyRequest);
    }

    @Override
    public void deleteCompany(Integer id)
    {
        Company company = companyRepository.findById(id).orElseThrow(null);
        companyRepository.delete(company);
    }
}
