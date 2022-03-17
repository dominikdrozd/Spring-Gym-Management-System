package com.ddrozd.projektsilownia.Entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Data
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="client_id", nullable = false)
    @NonNull
    private Client client;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable = true)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name="pass_id", nullable = false)
    @NonNull
    private Pass pass;
    
    @Positive(message = "Cena nie może być ujemna.")
    @NonNull
    private Double price;

    @NonNull
    private LocalDateTime boughtDate;

    @NonNull
    private LocalDateTime expirationDate;

    public boolean isActive() {
        Timestamp ts1 = new Timestamp(System.currentTimeMillis());
        Timestamp ts2 = Timestamp.valueOf(getExpirationDate());
        
        return !ts1.after(ts2);
    }

}
