package com.community.communication;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.community.communication.Approved.Block;
import com.community.communication.OrganisationInfo.OrganisationInfoController;
import com.community.communication.OrganisationInfo.OrganisationInfoRepository;

@WebMvcTest(OrganisationInfoController.class)
class OrganisationInfoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrganisationInfoRepository organisationInfoRepository;

    @Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    void testPageLoad_AsManager_SetsManagerFlag() throws Exception {
        String address = "TestAddr";
        Block mockBlock = new Block();
        mockBlock.setAddress(address);
        given(organisationInfoRepository.getInfo(address)).willReturn(mockBlock);

        mockMvc.perform(get("/OrganisationInfo/{address}", address))
                .andExpect(status().isOk())
                .andExpect(view().name("OrganisationInfo.html"))
                .andExpect(model().attribute("isManager", true));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testPageLoad_AsUser_SetsManagerFlagFalse() throws Exception {
        String address = "TestAddr";
        Block mockBlock = new Block();
        mockBlock.setAddress(address);
        given(organisationInfoRepository.getInfo(address)).willReturn(mockBlock);

        mockMvc.perform(get("/OrganisationInfo/{address}", address))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isManager", false));
    }

    @Test
    @WithMockUser(username = "manager", roles = {"MANAGER"})
    void testReportBlock_SubmitsAndRedirects() throws Exception {
        int blockId = 10;
        String blockName = "Offensive Block";
        String reason = "Inappropriate content";


        mockMvc.perform(post("/OrganisationInfo/report")
                        .param("id", String.valueOf(blockId))
                        .param("name", blockName)
                        .param("reason", reason)
                        .with(csrf())) // Required for POST
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Index"));

        //Verify the repository method was actually called with correct data
        verify(organisationInfoRepository).reportBlock(blockId, blockName, reason);
    }
}