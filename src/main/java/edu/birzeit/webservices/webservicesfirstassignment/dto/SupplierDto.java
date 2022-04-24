package edu.birzeit.webservices.webservicesfirstassignment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data

public class SupplierDto {

    private Long id;
    @NotNull
    @Size(min = 3, max = 250)
    private String name;
    private String contactName;
    private String email;
    private String phone;
    private String address;
    private Boolean isActive;
    private Date CreationDate;
}
