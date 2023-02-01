package com.mertsgrci.klncrud.service;

import com.mertsgrci.klncrud.model.InvoiceMaster;

import java.util.List;

public interface InvoiceMasterService
{
    public List<InvoiceMaster> getAllInvoiceMasters();
    public InvoiceMaster getInvoiceMasterById(Integer id);
}
