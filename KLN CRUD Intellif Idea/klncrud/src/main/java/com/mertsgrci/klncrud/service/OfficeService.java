package com.mertsgrci.klncrud.service;

import com.mertsgrci.klncrud.model.Office;

import java.util.List;

public interface OfficeService
{
    public Office createOffice(Office office);
    public List<Office> getAllOffices();
    public Office updateOffice(Office office, Integer id);
    public void deleteOffice(Integer id);
}
