package com.community.communication.Approved;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ApproverRepositoryJDBC implements ApproverRepository{

    private JdbcTemplate jdbc;
    private RowMapper<Block> blockMapper;

    public ApproverRepositoryJDBC(JdbcTemplate aJdbc){
        this.jdbc = aJdbc;
        setBlockMapper();
    }

    private void setBlockMapper() {
        blockMapper = (rs, i) -> new Block(
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
            rs.getBoolean("approved")
        ); 
    }

    @Override
    public List<Block> getUnapprovedBlocks() {
        String sql = "select * from communities where approved = '0'";
        return jdbc.query(sql, blockMapper);
    }
    

    
}
