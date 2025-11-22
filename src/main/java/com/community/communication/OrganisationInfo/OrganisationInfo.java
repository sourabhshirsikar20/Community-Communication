package com.community.communication.OrganisationInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganisationInfo {
    private int Id;
    private String MainText;
    private String Links;
    private String Info;
    private String Promo;
    public OrganisationInfo(){
        this.Id = 0;
        this.MainText = "";
        this.Links = "";
        this.Info = "";
        this.Promo = "";
    }

}
