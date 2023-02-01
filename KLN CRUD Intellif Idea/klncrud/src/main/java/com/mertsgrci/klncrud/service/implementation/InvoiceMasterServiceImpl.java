package com.mertsgrci.klncrud.service.implementation;

import com.mertsgrci.klncrud.model.InvoiceMaster;
import com.mertsgrci.klncrud.repository.InvoiceMasterRepository;
import com.mertsgrci.klncrud.service.InvoiceMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceMasterServiceImpl implements InvoiceMasterService
{
    private final InvoiceMasterRepository invoiceMasterRepository;

    @Autowired
    public InvoiceMasterServiceImpl(InvoiceMasterRepository invoiceMasterRepository)
    {
        this.invoiceMasterRepository = invoiceMasterRepository;
    }

    @Override
    public List<InvoiceMaster> getAllInvoiceMasters()
    {
        return invoiceMasterRepository.findAll();
    }

    @Override
    public InvoiceMaster getInvoiceMasterById(Integer id)
    {
        return invoiceMasterRepository.findById(id).orElseThrow(null);
    }
}