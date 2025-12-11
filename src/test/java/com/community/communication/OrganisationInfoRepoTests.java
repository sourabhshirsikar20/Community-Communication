package com.community.communication;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.community.communication.Approved.ApproverRepository;
import com.community.communication.Approved.Block;
import com.community.communication.OrganisationInfo.OrganisationInfoRepository;

@SpringBootTest
@Transactional 
class OrganisationInfoRepoTests {

    @Autowired
    private OrganisationInfoRepository organisationInfoRepository;

    @Autowired
    private ApproverRepository approverRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testReportBlock_UpdatesCommunityAndLogsReport() {
        //Create and approve a block to be reported
        Block block = new Block();
        block.setName("Bad Block");
        block.setAddress("BAD_ADDR_01");
        block.setApproved(true);
        block.setRoofOption(1); block.setRoofColour("#fff");
        block.setBaseOption(1); block.setBaseColour("#fff");
        block.setForegroundOption(1); block.setForegroundColour("#fff");

        approverRepository.addNewBlock(block);

        //Retrieve the ID of the block we just inserted
        Integer blockId = jdbcTemplate.queryForObject(
                "SELECT id FROM communities WHERE address = ?", Integer.class, "BAD_ADDR_01");

        //Report the block
        String reason = "Violation of terms";
        organisationInfoRepository.reportBlock(blockId, "Bad Block", reason);

        //Check 'communities' table updates
        Boolean isApproved = jdbcTemplate.queryForObject(
                "SELECT approved FROM communities WHERE id = ?", Boolean.class, blockId);
        String address = jdbcTemplate.queryForObject(
                "SELECT address FROM communities WHERE id = ?", String.class, blockId);

        assertThat(isApproved).isFalse();
        assertThat(address).isNull();     

        //Check 'reports' table insertion
        Integer reportCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM reports WHERE block_name = ? AND reason = ?",
                Integer.class, "Bad Block", reason);

        assertThat(reportCount).isEqualTo(1); 
    }
}