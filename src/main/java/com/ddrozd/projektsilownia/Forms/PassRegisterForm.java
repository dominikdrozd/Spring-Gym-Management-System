package com.ddrozd.projektsilownia.Forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class PassRegisterForm {    

    @Size(max = 20, min = 2, message = "Nazwa karnetu powinna zawierać od 2 do 20 znaków.")
    @NotEmpty(message = "Nazwa musi być podana.")
    private String name;

    @Positive(message = "Cena nie może być ujemna.")
    @NotNull(message = "Cena musi być podana.")
    private Double price;

    @Positive(message = "Czas trwania karnetu nie może być ujemny.")
    @NotNull(message = "Czas trwania musi być podany.")
    private Integer expiryTime;

    public PassRegisterForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Integer expiryTime) {
        this.expiryTime = expiryTime;
    }

}
