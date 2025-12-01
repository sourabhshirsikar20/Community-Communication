/* 
    SubmitBlock is a layer used only for user-facing forms, 
    seperating font options and not including id/approved fields.
*/

package com.community.communication.SubmitBlock;

import com.community.communication.Approved.Block;

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
    @Size(max = 512, message = "Main text cannot exceed 512 characters")
    String mainText;
    @Size(max = 256, message = "Link text cannot exceed 256 characters")
    String linksText;
    @Size(max = 256, message = "Contact Information cannot exceed 256 characters")
    String infoText;
    @Size(max = 256, message = "Promo text cannot exceed 256 characters")
    String promoText;

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
        fontColour = "#99604aff";

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
        return String.format(
            "{\"serif\":%b,\"italic\":%b,\"weight\":%d,\"width\":%d%s}",
            fontSerif, fontItalic, fontWeight, fontWidth,
            fontSerif ?  String.format(",\"grade\":%d", fontGrade) : ""
        );
    }
}
