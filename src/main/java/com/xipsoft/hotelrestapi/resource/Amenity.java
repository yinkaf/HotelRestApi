package com.xipsoft.hotelrestapi.resource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class Amenity {
    @NotNull(message = "Amenity Short Description cannot be null")
    @Size(max = 10, message = "Amenity Short Description cannot be longer that 10 characters")
    private String shortDesc;
    @NotNull(message = "Amenity Description cannot be null")
    @Size(max = 200, message = "Amenity Description cannot be longer that 200 characters")
    private String description;

    private boolean chargeable;
    private BigDecimal amount;

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChargeable() {
        return chargeable;
    }

    public void setChargeable(boolean chargeable) {
        this.chargeable = chargeable;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
