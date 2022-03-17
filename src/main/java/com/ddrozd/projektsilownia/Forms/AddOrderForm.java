package com.ddrozd.projektsilownia.Forms;

import javax.validation.constraints.Positive;

import com.ddrozd.projektsilownia.Validators.ClientExists;
import com.ddrozd.projektsilownia.Validators.PassExists;


public class AddOrderForm {
    
    @Positive(message = "Id karnetu musi być większe niż 0.")
    @PassExists(message = "Nie istnieje podany karnet.")
    private Long passId;

    @Positive(message = "Id klienta musi być większe niż 0.")
    @ClientExists(message = "Nie istnieje podany client")
    private Long clientId;

    public AddOrderForm() {
    }

    public Long getPassId() {
        return passId;
    }

    public void setPassId(Long passId) {
        this.passId = passId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

}
