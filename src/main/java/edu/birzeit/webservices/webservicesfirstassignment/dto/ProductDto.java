package edu.birzeit.webservices.webservicesfirstassignment.dto;

import edu.birzeit.webservices.webservicesfirstassignment.entity.Category;
import edu.birzeit.webservices.webservicesfirstassignment.entity.Supplier;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Data

public class ProductDto {

    private Long id;
    @NotNull
    @Size(min = 3, max = 250)
    private String name;
    private double price;
    private int quantity;
    private String description;

    private Boolean isActive;
    private Date CreationDate;

    private Category category;
    private Supplier supplier;

}
