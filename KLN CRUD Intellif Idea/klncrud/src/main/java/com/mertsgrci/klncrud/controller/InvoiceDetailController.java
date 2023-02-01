package com.mertsgrci.klncrud.controller;

import com.mertsgrci.klncrud.dto.InvoiceDetailDto;
import com.mertsgrci.klncrud.dto.InvoiceMasterDto;
import com.mertsgrci.klncrud.model.InvoiceDetail;
import com.mertsgrci.klncrud.model.InvoiceMaster;
import com.mertsgrci.klncrud.service.InvoiceDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invoicedetails")
@CrossOrigin("http://127.0.0.1:5173/")
public class InvoiceDetailController
{
    @Autowired
    private ModelMapper modelMapper;

    private final InvoiceDetailService invoiceDetailService;

    public InvoiceDetailController(InvoiceDetailService invoiceDetailService)
    {
        this.invoiceDetailService = invoiceDetailService;
    }

    @GetMapping("/{id}")
    public List<InvoiceDetailDto> getInvoiceDetailById(@PathVariable("id") Integer id)
    {
        return invoiceDetailService.getInvoiceDetailById(id).stream()
                .map(invoiceDetail -> modelMapper.map(invoiceDetail,InvoiceDetailDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<InvoiceDetailDto> getAllInvoiceDetails()
    {
        List<InvoiceDetail> list = invoiceDetailService.getAllInvoiceDetails();
        List<InvoiceDetailDto> listDto = new ArrayList<>();

        for(var item:list)
        {
            InvoiceDetailDto variable = new InvoiceDetailDto();
            variable.setId(item.getId());
            variable.setPrice(item.getPrice());
            variable.setPieces((item.getPieces()));
            variable.setAmount(item.getAmount());
            variable.setInvoiceMasterId(item.getInvoiceMaster().getId());
            variable.setProductName(item.getProductName());
            listDto.add(variable);
        }

        return listDto;
    }
}
