package com.mertsgrci.klncrud.service;

import com.mertsgrci.klncrud.model.Company;

import java.util.List;

public interface CompanyService
{
    public Company createCompany(Company company);
    public List<Company> getAllCompanies();
    public Company updateCompany(Company companies, Integer id);
    public void deleteCompany(Integer id);
}
