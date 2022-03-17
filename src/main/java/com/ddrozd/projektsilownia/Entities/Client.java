package com.ddrozd.projektsilownia.Entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 46, min = 2, message = "Imie powinno zawierać od 2 do 46 znaków.")
    @NonNull
    private String name;

    @Size(max = 46, min = 2, message = "Nazwisko powinno zawierać od 2 do 46 znaków.")
    @NonNull
    private String surname;

    @ManyToOne
    @JoinColumn(name="address_id", nullable = true)
    private Address address;

    @Size(max = 12, min = 12)
    @NonNull
    private String phone;

    @OneToMany(mappedBy="client", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

}
