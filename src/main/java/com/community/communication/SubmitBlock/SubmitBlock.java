/* 
    SubmitBlock is a layer used only for user-facing forms, 
    seperating font options and not including id/approved fields.
*/

package com.community.communication.SubmitBlock;

import org.json.JSONObject;

import com.community.communication.Approved.Block;
import com.community.communication.Validation.WordCount;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubmitBlock {
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
    boolean fontSerif;
    boolean fontItalic;
    int     fontWidth;
    int     fontWeight;
    int     fontGrade;
    String  fontColour;
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

    public SubmitBlock() {
        address = "";
        name = "";
        roofOption = 1;
        roofColour = "#a42445";
        baseOption = 1;
        baseColour = "#d8c286";
        foregroundOption = 1;
        foregroundColour = "#a4aeb4";

        fontSerif  = false;
        fontItalic = true;
        fontWeight = 450;
        fontWidth  = 85;
        fontGrade  = 0;
        fontColour = "#cdab8f";

        mainText = "";
        linksText = "";
        infoText = "";
        promoText = "";
    }

    public SubmitBlock(Block block) {
        address = block.getAddress();
        name = block.getName();
        roofOption  = block.getRoofOption();
        roofColour  = block.getRoofColour();
        baseOption  = block.getBaseOption();
        baseColour  = block.getBaseColour();
        foregroundOption = block.getForegroundOption();
        foregroundColour = block.getForegroundColour();
        fontSerif   = block.ExtractFontSerif();
        fontItalic  = block.ExtractFontItalic();
        fontWeight  = block.ExtractFontWeight();
        fontWidth   = block.ExtractFontWidth();
        fontGrade   = block.ExtractFontGrade();
        fontColour  = block.getFontColour();
        mainText    = block.getMainText();
        linksText   = block.getLinksText();
        infoText    = block.getInfoText();
        promoText   = block.getPromoText();
    }

    public String GenerateFontOptions() {
        JSONObject json = new JSONObject();

        json.put("serif",  fontSerif);
        json.put("italic", fontItalic);
        json.put("weight", fontWeight);
        json.put("width",  fontWidth);

        if (fontSerif)
            json.put("grade",  fontGrade);

        return json.toString();
    }
}
