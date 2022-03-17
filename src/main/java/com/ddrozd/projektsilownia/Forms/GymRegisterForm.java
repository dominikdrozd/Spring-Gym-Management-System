package com.ddrozd.projektsilownia.Forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ddrozd.projektsilownia.Validators.PasswordMatches;

@PasswordMatches(message = "Hasła nie zgadzają się.")
public class GymRegisterForm {    

    @Size(max = 46, min = 2, message = "Nazwa powinno zawierać od 2 do 46 znaków.")
    @NotEmpty(message = "Nazwa nie może być pusta.")
    private String name;

    @Pattern(regexp = "\\+[0-9]{2}[0-9]{9}$", message = "Numer telefonu powinien wyglądać tak: +00000000000")
    @Size(max = 12, min = 12)
    @NotEmpty(message = "Telefon nie może być pusty.")
    private String phone;

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


    public GymRegisterForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
