package com.mertsgrci.klncrud.service.implementation;

import com.mertsgrci.klncrud.model.Office;
import com.mertsgrci.klncrud.repository.OfficeRepository;
import com.mertsgrci.klncrud.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeServiceImpl implements OfficeService
{
    private OfficeRepository officeRepository;

    @Autowired
    public OfficeServiceImpl(OfficeRepository officeRepository)
    {
        this.officeRepository = officeRepository;
    }

    @Override
    public Office createOffice(Office office)
    {
        return officeRepository.save(office);
    }

    @Override
    public List<Office> getAllOffices()
    {
        return officeRepository.findAll();
    }

    @Override
    public Office updateOffice(Office office, Integer id)
    {
        Office officeRequest = officeRepository.findById(id).orElseThrow(null);

        officeRequest.setName(office.getName());
        officeRequest.setAddress(office.getAddress());
        officeRequest.setCompany(office.getCompany());

        return officeRepository.save(officeRequest);
    }

    @Override
    public void deleteOffice(Integer id)
    {
        Office office = officeRepository.findById(id).orElseThrow(null);
        officeRepository.delete(office);
    }
}
