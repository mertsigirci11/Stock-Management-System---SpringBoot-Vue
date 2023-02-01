package com.mertsgrci.klncrud.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="invoiceMaster")
public class InvoiceMaster
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_tax_administration")
    private String customerTaxAdministration;

    @Column(name = "customer_bankA_account_number")
    private String customerBankAccountNumber;

    @Column(name = "date_time")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "string_price")
    private String stringPrice;

    @Column(name = "price")
    private int price;

    @Column(name="vat")
    private int vat;

    @Column(name = "total_price")
    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "office_id")
    @JsonBackReference
    private Office office;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "invoiceMaster")
    @JsonManagedReference
    private List<InvoiceDetail> invoiceDetails;
}
