package com.community.communication.OrganiseBlock;

import com.community.communication.Approved.Block;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BlockRepositoryJDBC implements BlockRepository{
    private JdbcTemplate Jdbc;
    private RowMapper<Block> rowMapper;
    public BlockRepositoryJDBC(JdbcTemplate Jdbc){
        this.Jdbc = Jdbc;
        setBlockMapper();
    }
    private void setBlockMapper(){
        rowMapper = (rs, i) -> new Block(
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
                rs.getBoolean("approved")
        );
    }
    public Block ByAddress(String Address){
        String SQL = "SELECT * FROM communities WHERE address = ?";
        return Jdbc.queryForObject(SQL, rowMapper, Address);
    }
    public boolean Check(String Address){
        try {
            ByAddress(Address);
            return true;
        }
        catch(EmptyResultDataAccessException e){
            return false;
        }
    }
}
