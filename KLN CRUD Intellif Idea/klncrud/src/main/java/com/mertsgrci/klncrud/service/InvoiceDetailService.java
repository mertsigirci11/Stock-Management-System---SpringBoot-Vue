package com.mertsgrci.klncrud.service;

import com.mertsgrci.klncrud.model.InvoiceDetail;
import com.mertsgrci.klncrud.model.InvoiceMaster;

import java.util.List;

public interface InvoiceDetailService
{
    public List<InvoiceDetail> getInvoiceDetailById(Integer id);
    public List<InvoiceDetail> getAllInvoiceDetails();
}
