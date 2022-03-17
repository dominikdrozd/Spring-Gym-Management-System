package com.ddrozd.projektsilownia.Forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AddEmployeeToGymForm {
    
    @Positive(message = "Nie może być wartością ujemną.")
    @NotNull(message = "Musisz wybrać siłownię...")
    private Long gymId;

    public AddEmployeeToGymForm(Long gymId) {
        this.gymId = gymId;
    }

    public AddEmployeeToGymForm() {
    }

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

}
