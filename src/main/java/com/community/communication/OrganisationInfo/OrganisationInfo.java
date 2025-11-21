package com.community.communication.OrganisationInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganisationInfo {
    private String MainText;
    private String Links;
    private String Info;
    private String Promo;
    public OrganisationInfo(){
        this.MainText = "";
        this.Links = "";
        this.Info = "";
        this.Promo = "";
    }

}
