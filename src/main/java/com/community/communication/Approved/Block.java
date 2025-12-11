package com.community.communication.Approved;
import java.time.LocalDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import com.community.communication.SubmitBlock.SubmitBlock;
import com.community.communication.Validation.WordCount;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    @NotEmpty(message = "Main text may not be empty")
    @Size(min = 10, message = "Main text must be at least 10 characters")
    @WordCount(max = 512, message = "Main text cannot exceed 512 words")
    private String mainText;
    @WordCount(max = 256, message = "Links text cannot exceed 256 words")
    private String linksText;
    @WordCount(max = 256, message = "Info text cannot exceed 256 words")
    private String infoText;
    @WordCount(max = 256, message = "Promo text cannot exceed 256 words")
    private String promoText;
    String secretHash;
    boolean approved;
    LocalDateTime createdDate;  
    String reportReason;

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
        reportReason = ""; 
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
        reportReason = "";
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
