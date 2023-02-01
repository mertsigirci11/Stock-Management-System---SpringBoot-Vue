package com.mertsgrci.klncrud.dto;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceMasterDto
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String customerName;
    private String customerTaxAdministration;
    private String customerBankAccountNumber;
    private Date date;
    private String stringPrice;
    private int price;
    private int vat;
    private int totalPrice;
    private Integer officeId;
    private Integer customerId;
}
