package com.mertsgrci.klncrud.service.implementation;

import com.mertsgrci.klncrud.model.InvoiceDetail;
import com.mertsgrci.klncrud.repository.InvoiceDetailRepository;
import com.mertsgrci.klncrud.service.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceDetailServiceImpl implements InvoiceDetailService
{
    private final InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    public InvoiceDetailServiceImpl(InvoiceDetailRepository invoiceDetailRepository)
    {
        this.invoiceDetailRepository = invoiceDetailRepository;
    }

    @Override
    public List<InvoiceDetail> getInvoiceDetailById(Integer id)
    {
        List<InvoiceDetail> list = invoiceDetailRepository.findAll();
        List<InvoiceDetail> listForId = new ArrayList<>();

        for (InvoiceDetail invoiceDetail:list)
        {
            if (invoiceDetail.getInvoiceMaster().getId() == id)
            {
                listForId.add(invoiceDetail);
            }
        }
        return listForId;
    }

    @Override
    public List<InvoiceDetail> getAllInvoiceDetails() {
        return invoiceDetailRepository.findAll();
    }

}
