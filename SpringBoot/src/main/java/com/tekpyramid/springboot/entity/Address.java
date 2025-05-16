package com.tekpyramid.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "addresses" )
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;
    private String street;
    private String city;
    private String state;
    private String country;
    @OneToOne
    @JsonIgnore
    private Employee employee;

}
