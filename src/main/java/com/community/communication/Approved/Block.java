package com.community.communication.Approved;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Block {
    int id;
    String address;
    @NotEmpty(message = "Blocks must have a name")
    String name;
    int roofOption;
    String roofColour;
    int baseOption;
    String baseColour;
    int foregroundOption;
    String foregroundColour;
    @NotEmpty(message = "Main text may not be empty")
    String mainText;
    @NotEmpty(message = "Link text may not be empty")
    String linksText;
    @NotEmpty(message = "Please provide contact information")
    String infoText;
    @NotEmpty(message = "Promo text may not be empty")
    String promoText;
    boolean approved;

    public Block() {
        id = -1;
        address = "";
        name = "";
        roofOption = 0;
        roofColour = "#a42445";
        baseOption = 0;
        baseColour = "#d8c286";
        foregroundOption = 0;
        foregroundColour = "#a4aeb4";
        mainText = "";
        linksText = "";
        infoText = "";
        promoText = "";
        approved = false;
    }
}
