package com.community.communication.OrganiseBlock;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Blocks {
    int id;
    String address;
    String name;
    int roofOption;
    String roofColour;
    int baseOption;
    String baseColour;
    int foregroundOption;
    String foregroundColour;
    String mainText;
    String linksText;
    String infoText;
    String promoText;
    boolean approved;
    public Blocks() {
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
