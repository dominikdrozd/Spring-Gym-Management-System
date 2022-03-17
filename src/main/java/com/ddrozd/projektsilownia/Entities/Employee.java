package com.ddrozd.projektsilownia.Entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 2, max = 46, message = "Imie powinno zawierać od 2 do 46 znaków.")
    @NonNull
    private String name;

    @Size(min = 2, max = 46, message = "Nazwisko powinno zawierać od 2 do 46 znaków.")
    @NonNull
    private String surname;

    @Size(min = 2, max = 20, message = "Nazwa Stanowiska powinna zawierać od 2 do 20 znaków.")
    @NonNull
    private String position;
    
    @Size(max = 12, min = 12, message = "Telefon powinien zawierać 12 znaków (przyklad: +48111111111)")
    @NonNull
    private String phone;

    @ManyToOne
    @JoinColumn(name="address_id", nullable = true)
    private Address address;

    @ManyToMany(mappedBy="employees")
    private List<Gym> gyms;

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

}
