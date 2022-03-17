package com.ddrozd.projektsilownia.Entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Positive;
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
public class Pass {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Size(max = 20, min = 2, message = "Nazwa karnetu powinna zawierać od 2 do 20 znaków.")
    @NonNull
    private String name;

    @Positive(message = "Cena nie może być ujemna.")
    @NonNull
    private Double price;

    @Positive(message = "Data trwania karnetu nie może być ujemna.")
    @NonNull
    private Integer expiryTime;

    @OneToMany(mappedBy="pass")
    private List<Order> orders;

}
