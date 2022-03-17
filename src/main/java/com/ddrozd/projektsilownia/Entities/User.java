package com.ddrozd.projektsilownia.Entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
@Table(name="users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique=true)
    @Size(max = 50, min = 10, message = "Email powinien zawierać od 10 do 50 znaków.")
    @NonNull
    private String email;
    
    @NonNull
    private String password;

    @Size(max = 20, min = 3, message = "Rola powinna zawierać od 3 do 20 znaków.")
    @NonNull
    private String role;

    @Column(columnDefinition = "boolean default true")
    private Boolean enabled;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Client client;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Employee employee;

}
