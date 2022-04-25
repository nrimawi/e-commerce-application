package edu.birzeit.webservices.webservicesfirstassignment.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table
public class Supplier {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private String contactName;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private String address;
    @Column
    @ColumnDefault("true")

    private Boolean isActive;

}
