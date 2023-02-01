package com.mertsgrci.klncrud.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="customer")
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name ="surname")
    private String surname;

    @Column(name ="username")
    private String username;

    @Column(name ="password")
    private String password;

    @Column(name ="tax_administration")
    private String taxAdministration;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customer")
    @JsonManagedReference
    private List<InvoiceMaster> invoiceMasters;
}
