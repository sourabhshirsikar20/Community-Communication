package com.community.communication.Approved;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Block {
    int id;
    String address;
    @NotEmpty(message = "Blocks must have a name")
    @Size(max = 128, message = "Names cannot exceed 128 characters")
    String name;
    int roofOption;
    String roofColour;
    int baseOption;
    String baseColour;
    int foregroundOption;
    String foregroundColour;
    @NotEmpty(message = "Main text may not be empty")
    @Size(max = 512, message = "Main text cannot exceed 512 characters")
    String mainText;
    //@NotEmpty(message = "Link text may not be empty")
    @Size(max = 256, message = "Link text cannot exceed 256 characters")
    String linksText;
    //@NotEmpty(message = "Please provide contact information")
    @Size(max = 256, message = "Contact Information cannot exceed 256 characters")
    String infoText;
    //@NotEmpty(message = "Promo text may not be empty")
    @Size(max = 256, message = "Promo text cannot exceed 256 characters")
    String promoText;

    String secretHash;
    boolean approved;

    public Block() {
        id = -1;
        address = "";
        name = "";
        roofOption = 1;
        roofColour = "#a42445";
        baseOption = 1;
        baseColour = "#d8c286";
        foregroundOption = 1;
        foregroundColour = "#a4aeb4";
        mainText = "";
        linksText = "";
        infoText = "";
        promoText = "";
        approved = false;
    }
}
