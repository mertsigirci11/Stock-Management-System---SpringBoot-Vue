package com.mertsgrci.klncrud.controller;

import com.mertsgrci.klncrud.dto.InvoiceMasterDto;
import com.mertsgrci.klncrud.model.InvoiceMaster;
import com.mertsgrci.klncrud.service.InvoiceMasterService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invoicemasters")
@CrossOrigin("http://127.0.0.1:5173/")
public class InvoiceMasterController
{
    @Autowired
    private ModelMapper modelMapper;


    private InvoiceMasterService invoiceMasterService;

    public InvoiceMasterController(InvoiceMasterService invoiceMasterService)
    {
        this.invoiceMasterService = invoiceMasterService;
    }

    @GetMapping
    public List<InvoiceMasterDto> getAllInvoiceMasters()
    {
        List<InvoiceMaster> list= invoiceMasterService.getAllInvoiceMasters();
        List<InvoiceMasterDto> listDto = new ArrayList<>();

        for(var item:list)
        {
            InvoiceMasterDto variable = new InvoiceMasterDto();
            variable.setId(item.getId());
            variable.setCustomerName(item.getCustomerName());
            variable.setCustomerBankAccountNumber(item.getCustomerBankAccountNumber());
            variable.setDate(item.getDate());
            variable.setPrice(item.getPrice());
            variable.setVat(item.getVat());
            variable.setCustomerId(item.getId());
            variable.setOfficeId(item.getOffice().getId());
            variable.setCustomerTaxAdministration(item.getCustomerTaxAdministration());
            variable.setStringPrice(item.getStringPrice());
            variable.setTotalPrice(item.getTotalPrice());

            listDto.add(variable);
        }

        return listDto;

        /*
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return invoiceMasterService.getAllInvoiceMasters().stream()
                .map(invoiceMaster -> modelMapper.map(invoiceMaster,InvoiceMasterDto.class)).collect(Collectors.toList());
        */
    }


    @GetMapping("/{id}")
    public ResponseEntity<InvoiceMasterDto> getInvoiceMasterById(@PathVariable Integer id)
    {
        //Getting Entity
        InvoiceMaster invoiceMaster = invoiceMasterService.getInvoiceMasterById(id);

        //Converting Entity to Dto
        //InvoiceMasterDto invoiceMasterDtoResponse = modelMapper.map(invoiceMaster, InvoiceMasterDto.class);
        InvoiceMasterDto variable = new InvoiceMasterDto();

        variable.setId(invoiceMaster.getId());
        variable.setCustomerName(invoiceMaster.getCustomerName());
        variable.setCustomerBankAccountNumber(invoiceMaster.getCustomerBankAccountNumber());
        variable.setDate(invoiceMaster.getDate());
        variable.setPrice(invoiceMaster.getPrice());
        variable.setVat(invoiceMaster.getVat());
        variable.setCustomerId(invoiceMaster.getId());
        variable.setOfficeId(invoiceMaster.getOffice().getId());
        variable.setCustomerTaxAdministration(invoiceMaster.getCustomerTaxAdministration());
        variable.setStringPrice(invoiceMaster.getStringPrice());
        variable.setTotalPrice(invoiceMaster.getTotalPrice());
        return new ResponseEntity<InvoiceMasterDto>(variable,HttpStatus.OK);
        //return new ResponseEntity<InvoiceMasterDto>(invoiceMasterDtoResponse,HttpStatus.OK);
    }


}
