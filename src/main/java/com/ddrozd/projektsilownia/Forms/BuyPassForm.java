package com.ddrozd.projektsilownia.Forms;

import javax.validation.constraints.Positive;

import com.ddrozd.projektsilownia.Validators.PassExists;

public class BuyPassForm {
    
    @Positive(message = "Id karnetu musi być większe niż 0.")
    @PassExists(message = "Nie istnieje podany karnet.")
    private Long passId;

    public BuyPassForm() {
    }

    public Long getPassId() {
        return passId;
    }

    public void setPassId(Long passId) {
        this.passId = passId;
    }
    
}
