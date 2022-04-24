package edu.birzeit.webservices.webservicesfirstassignment.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private double price;
    @Column
    private int quantity;
    @Column
    private String description;

    @Column
    private Boolean isActive;
    @Column
    private Date CreationDate;

    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private  Category category;
    @ManyToOne
    @JoinColumn(name = "supplierId", referencedColumnName = "id")
    private  Supplier supplier;

}
