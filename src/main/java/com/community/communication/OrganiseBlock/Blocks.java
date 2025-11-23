package com.community.communication.OrganiseBlock;

import lombok.Data;

@Data
public class Blocks {
    private String Address;
    private String Test;

    public Blocks(String Address, String Test) {
        this.Address = Address;
        this.Test = Test;
    }
}
