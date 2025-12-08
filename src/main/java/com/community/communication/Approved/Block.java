package com.community.communication.Approved;

import org.json.JSONException;
import org.json.JSONObject;

import com.community.communication.SubmitBlock.SubmitBlock;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Block {
    int id;
    String address;
    String name;
    int roofOption;
    String roofColour;
    int baseOption;
    String baseColour;
    int foregroundOption;
    String foregroundColour;
    String fontOptions;
    String fontColour;
    String mainText;
    String linksText;
    String infoText;
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
        baseColour = "#b88c7aff";
        foregroundOption = 1;
        foregroundColour = "#a4aeb4";
        fontOptions = "{\"serif\":false,\"italic\":true,\"weight\":450,\"width\":85}";
        fontColour = "#e9b36cff";
        mainText = "";
        linksText = "";
        infoText = "";
        promoText = "";
        approved = false;
    }

    public Block(SubmitBlock submitBlock) {
        id = -1;
        address = submitBlock.getAddress();
        name = submitBlock.getName();
        roofOption = submitBlock.getRoofOption();
        roofColour = submitBlock.getRoofColour();
        baseOption = submitBlock.getBaseOption();
        baseColour = submitBlock.getBaseColour();
        foregroundOption = submitBlock.getForegroundOption();
        foregroundColour = submitBlock.getForegroundColour();
        fontOptions = submitBlock.GenerateFontOptions();
        fontColour = submitBlock.getFontColour();
        mainText = submitBlock.getMainText();
        linksText = submitBlock.getLinksText();
        infoText = submitBlock.getInfoText();
        promoText = submitBlock.getPromoText();
        approved = false;
    }

    public boolean ExtractFontSerif() {
        JSONObject json = new JSONObject(fontOptions);
        return json.getBoolean("serif");
    }

    public boolean ExtractFontItalic() {
        JSONObject json = new JSONObject(fontOptions);
        return json.getBoolean("italic");
    }

    public int ExtractFontWeight() {
        JSONObject json = new JSONObject(fontOptions);
        return json.getInt("weight");
    }

    public int ExtractFontWidth() {
        JSONObject json = new JSONObject(fontOptions);
        return json.getInt("width");
    }

    public int ExtractFontGrade() {
        try {
            JSONObject json = new JSONObject(fontOptions);
            return json.getInt("grade");
        } catch (JSONException e) {
            return 0; // grade not found, return neutral value
        }
    }
}
