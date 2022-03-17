package com.ddrozd.projektsilownia.Entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 95, message = "Adres powinien zawierać maksymalnie 95 znaków (Ulica 15/5).")
    @NonNull
    private String address;

    @Size(max = 35, message = "Miasto powinno mieć maksymalnie 35 znaków.")
    @NonNull
    private String city;

    @Size(max = 6, min = 6, message = "Kod pocztowy zawiera 6 znaków.")
    @NonNull
    private String postcode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="address")
    private List<Employee> employees;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="address")
    private List<Client> clients;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="address")
    private List<Gym> gyms;   

}
