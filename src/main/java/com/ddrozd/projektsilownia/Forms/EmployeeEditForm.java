package com.ddrozd.projektsilownia.Forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EmployeeEditForm {
    @Size(max = 46, min = 2, message = "Imie powinno zawierać od 2 do 46 znaków.")
    @NotEmpty(message = "Imie nie może być puste.")
    private String name;

    @Size(max = 46, min = 2, message = "Nazwisko powinno zawierać od 2 do 46 znaków.")
    @NotEmpty(message = "Nazwisko nie może być puste.")
    private String surname;

    @Pattern(regexp = "\\+[0-9]{2}[0-9]{9}$", message = "Numer telefonu powinien wyglądać tak: +00000000000")
    @Size(max = 12, min = 12)
    @NotEmpty(message = "Telefon nie może być pusty.")
    private String phone;

    @Size(min = 2, max = 20, message = "Nazwa Stanowiska powinna zawierać od 2 do 20 znaków.")
    @NotEmpty(message = "Stanowisko nie może być puste.")
    private String position;

    @Size(max = 95, message = "Adres powinien zawierać maksymalnie 95 znaków (Ulica 15/5).")
    @NotEmpty(message = "Adres nie może być pusty.")
    private String address;

    @Size(max = 35, message = "Miasto powinno mieć maksymalnie 35 znaków.")
    @NotEmpty(message = "Miasto nie może być puste.")
    private String city;

    @Size(max = 6, min = 6, message = "Kod pocztowy zawiera 6 znaków.")
    @Pattern(regexp = "[0-9]{2}\\-[0-9]{3}", message = "Kod pocztowy powinien wyglądać tak: 00-000")
    @NotEmpty(message = "Kod Pocztowy nie może być pusty.")
    private String postcode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    
}
