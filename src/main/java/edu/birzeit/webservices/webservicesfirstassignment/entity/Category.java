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
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column
    private String description;

    @Column
    @ColumnDefault("true")
    private Boolean isActive;

}
