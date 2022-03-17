package com.ddrozd.projektsilownia.Entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
public class Gym {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 20, min = 2, message = "Nazwa siłowni powinna zawierać od 2 do 20 znaków.")
    @NonNull
    private String name;

    @ManyToOne
    @JoinColumn(name="address_id", nullable = true)
    @NonNull
    private Address address;

    @Size(min = 12, max = 12)
    @NonNull
    private String phone;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "Gym_Employee",
        joinColumns = { @JoinColumn(name = "gym_id") },
        inverseJoinColumns = { @JoinColumn(name = "employee_id") }
    )
    private List<Employee> employees;

}
