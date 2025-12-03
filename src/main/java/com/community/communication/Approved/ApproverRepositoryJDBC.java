package com.community.communication.Approved;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ApproverRepositoryJDBC implements ApproverRepository {

    private JdbcTemplate jdbc;
    private RowMapper<Block> blockMapper;
    private RowMapper<Street> streetMapper;

    public ApproverRepositoryJDBC(JdbcTemplate aJdbc) {
        this.jdbc = aJdbc;
        setBlockMapper();
        setStreetMapper();
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
            rs.getString("font_options"),
            rs.getString("font_colour"),
            rs.getString("main_text"),
            rs.getString("links_text"),
            rs.getString("info_text"),
            rs.getString("promo_text"),
            rs.getString("secret_hash"),
            rs.getBoolean("approved")
        ); 
    }

    private void setStreetMapper() {
        streetMapper = (rs, i) -> new Street(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("address"),
            rs.getString("description")
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
                font_options, font_colour,
                main_text, 
                links_text, 
                info_text, 
                promo_text
            ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)
        """;
                
        jdbc.update(sql, 
            block.getName(), block.getAddress(),
            block.getRoofOption(), block.getRoofColour(),
            block.getBaseOption(), block.getBaseColour(),
            block.getForegroundOption(), block.getForegroundColour(),
            block.getFontOptions(), block.getFontColour(),
            block.getMainText(),
            block.getLinksText(),
            block.getInfoText(),
            block.getPromoText()
        );
    }
    @Override
    public void approve(int id, String secretHash) {
        String sql = "UPDATE communities SET approved = 1, secret_Hash = ? WHERE id = ?";
        jdbc.update(sql,secretHash, id);
    }
    @Override
    public List<Block> getStreetBlocks(String streetAddress) {
        String sql = "SELECT * FROM communities WHERE " +
                        "approved = '1' AND " +
                        "SUBSTRING(address, 1, ?) = ? AND " +
                        "LENGTH(address) = ?" ;
        return jdbc.query(sql, blockMapper,  streetAddress.length(), streetAddress, streetAddress.length()+2);
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

    public List<Block> getSearch(String value) {
        String sql = "SELECT * FROM communities WHERE approved = '1' AND name LIKE ?";
        String input = "%" + value +"%";
        return jdbc.query(sql, blockMapper,input);
    }

    public List<Street> getSubStreets(String streetAddress) {
        if (streetAddress.equals("root"))
            streetAddress = "";
        String sql = "SELECT * FROM streets WHERE " +
                        "SUBSTRING(address, 1, ?) = ? AND " +
                        "LENGTH(address) = ?" ;
        return jdbc.query(sql, streetMapper,  streetAddress.length(), streetAddress, streetAddress.length()+2);
    }

    public Street getStreet(String streetAddress) {
        String sql = "SELECT * FROM streets WHERE address = ?";
        List<Street> result = jdbc.query(sql, streetMapper, streetAddress);
        if (result.size() == 1)
            return result.get(0);
        return null; // address field is enforced unique, so should never be reached in practice
    }
}
