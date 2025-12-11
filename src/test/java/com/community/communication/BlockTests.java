package com.community.communication;

import org.json.JSONException;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.community.communication.Approved.Block;
import com.community.communication.SubmitBlock.SubmitBlock;

@SpringBootTest
class BlockTests {

  Block block;
  SubmitBlock submitBlock;
  JSONObject json;

  @BeforeEach
  void SetupBlocks() {
    submitBlock = new SubmitBlock();
    submitBlock.setFontSerif(true);
    submitBlock.setFontItalic(false);
    submitBlock.setFontWidth(500);
    submitBlock.setFontWeight(75);
    submitBlock.setFontGrade(-5);

    block = new Block();
    block.setFontOptions("{\"serif\":false,\"italic\":true,\"weight\":450,\"width\":85}");
  }

	@Test
	void TestFontOptionsCoversionToJSON() {
    // with serif font (should include grade)
    submitBlock.setFontSerif(true);
    try {
      json = new JSONObject(submitBlock.GenerateFontOptions());
    } catch (JSONException e) {
      fail("serif font does not return valid json");
    }
    assertTrue(json.has("grade"), "json conversion ignoring serif-font grade property");

    // without serif font (should not include grade)
    submitBlock.setFontSerif(false);
    try {
      json = new JSONObject(submitBlock.GenerateFontOptions());
    } catch (JSONException e) {
      fail("sans-serif font does not return valid json");
    }
    assertFalse(json.has("grade"), "json conversion not respecting serif flag");
	}

  @Test 
  void TestFontOptionsExtractValues() {
    assertAll("Block extracted font options",
      () -> assertEquals(block.ExtractFontSerif(),  false),
      () -> assertEquals(block.ExtractFontItalic(), true),
      () -> assertEquals(block.ExtractFontWeight(), 450),
      () -> assertEquals(block.ExtractFontWidth(),  85),
      () -> assertEquals(block.ExtractFontGrade(),  0)
    );
  }

}
