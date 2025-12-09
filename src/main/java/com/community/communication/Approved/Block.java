package com.community.communication.Approved;
import java.time.LocalDateTime;

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
    LocalDateTime createdDate;  

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
        createdDate = LocalDateTime.now();  
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
    // Adding try-catch to handle potential JSON parsing errors. For Manual Database Edits - JSONException → 500 error → ERR_INCOMPLETE_CHUNKED_ENCODING

    public boolean ExtractFontSerif() {
        try {
            if (fontOptions == null || fontOptions.isEmpty()) {
                return false; // Default value
            }
            JSONObject json = new JSONObject(fontOptions);
            return json.getBoolean("serif");
        } catch (JSONException e) {
            return false; // Default on error
        }
    }

    public boolean ExtractFontItalic() {
        try {
            if (fontOptions == null || fontOptions.isEmpty()) {
                return true; // Default value
            }
            JSONObject json = new JSONObject(fontOptions);
            return json.getBoolean("italic");
        } catch (JSONException e) {
            return true; // Default on error
        }
    }

    public int ExtractFontWeight() {
        try {
            if (fontOptions == null || fontOptions.isEmpty()) {
                return 450; // Default value
            }
            JSONObject json = new JSONObject(fontOptions);
            return json.getInt("weight");
        } catch (JSONException e) {
            return 450; // Default on error
        }
    }

    public int ExtractFontWidth() {
        try {
            if (fontOptions == null || fontOptions.isEmpty()) {
                return 85; // Default value
            }
            JSONObject json = new JSONObject(fontOptions);
            return json.getInt("width");
        } catch (JSONException e) {
            return 85; // Default on error
        }
    }

    public int ExtractFontGrade() {
        try {
            if (fontOptions == null || fontOptions.isEmpty()) {
                return 0; // Default value
            }
            JSONObject json = new JSONObject(fontOptions);
            return json.getInt("grade");
        } catch (JSONException e) {
            return 0; // Default on error
        }
    }
    
}
