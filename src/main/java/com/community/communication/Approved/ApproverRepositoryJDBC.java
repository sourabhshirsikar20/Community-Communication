package com.community.communication.Approved;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ApproverRepositoryJDBC implements ApproverRepository {

    private JdbcTemplate jdbc;
    private RowMapper<Block> blockMapper;

    public ApproverRepositoryJDBC(JdbcTemplate aJdbc) {
        this.jdbc = aJdbc;
        setBlockMapper();
    }

    private void setBlockMapper() {
        blockMapper = (rs, i) -> new Block(
                rs.getInt("id"),
                rs.getString("address"),
                rs.getString("name"),
                rs.getInt("roof_option"),
                rs.getString("roof_colour"),
                rs.getInt("base_option"),
                rs.getString("base_colour"),
                rs.getInt("foreground_option"),
                rs.getString("foreground_colour"),
                rs.getString("main_text"),
                rs.getString("links_text"),
                rs.getString("info_text"),
                rs.getString("promo_text"),
                rs.getBoolean("approved"),
                // Added created_date mapping
                rs.getTimestamp("created_date") != null ? 
                    rs.getTimestamp("created_date").toLocalDateTime() : 
                    LocalDateTime.now()
        );
    }

    @Override
    public List<Block> getUnapprovedBlocks() {
        String sql = "SELECT * FROM communities WHERE approved = '0'";
        return jdbc.query(sql, blockMapper);
    }

    @Override
    public void addNewBlock(Block block) {
        
        // created_date is handled by database DEFAULT CURRENT_TIMESTAMP
        
        String sql = """
            INSERT INTO communities (
                name, address,
                roof_option, roof_colour, 
                base_option, base_colour, 
                foreground_option, foreground_colour, 
                main_text, 
                links_text, 
                info_text, 
                promo_text
            ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)
        """;

        jdbc.update(sql,
                block.getName(), block.getAddress(),
                block.getRoofOption(), block.getRoofColour(),
                block.getBaseOption(), block.getBaseColour(),
                block.getForegroundOption(), block.getForegroundColour(),
                block.getMainText(),
                block.getLinksText(),
                block.getInfoText(),
                block.getPromoText()
        );
    }

    public void approve(int id) {
        String sql = "UPDATE communities SET approved = 1 WHERE id = ?";
        jdbc.update(sql, id);
    }

    @Override
    public List<Block> getApprovedBlocks() {
        
        String sql = """
            SELECT * FROM communities 
            WHERE approved = '1' 
            AND DATE_ADD(created_date, INTERVAL 6 MONTH) > NOW()
            """;
        return jdbc.query(sql, blockMapper);
    }
}
