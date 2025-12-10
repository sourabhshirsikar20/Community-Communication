package com.community.communication.OrganiseBlock;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.community.communication.Approved.Block;

@Repository
public class BlockRepositoryJDBC implements BlockRepository {
    private JdbcTemplate Jdbc;
    private RowMapper<Block> rowMapper;

    public BlockRepositoryJDBC(JdbcTemplate Jdbc) {
        this.Jdbc = Jdbc;
        setBlockMapper();
    }
  
    private void setBlockMapper() {
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
                rs.getString("font_options"),
                rs.getString("font_colour"),
                rs.getString("main_text"),
                rs.getString("links_text"),
                rs.getString("info_text"),
                rs.getString("promo_text"),
                rs.getString("secret_hash"),
                rs.getBoolean("approved"),
                // Added created_date mapping
                rs.getTimestamp("created_date") != null ? 
                rs.getTimestamp("created_date").toLocalDateTime() : 
                java.time.LocalDateTime.now(),
                null
        );
    }

    public Block ByAddress(String Address) {
        String SQL = "SELECT * FROM communities WHERE address = ?";
        return Jdbc.queryForObject(SQL, rowMapper, Address);
    }

    public Block ById(int Id) {
        String SQL = "SELECT * FROM communities WHERE id = ?";
        return Jdbc.queryForObject(SQL, rowMapper, Id);
    }

    public boolean Check(String Address) {
        try {
            ByAddress(Address);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public void Add(Block block) {
        String SQL = "INSERT INTO communities (address, name, roof_option, roof_colour, base_option, base_colour, foreground_option, foreground_colour, font_options, font_colour, main_text, links_text, info_text, promo_text, approved, secret_hash) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Jdbc.update(SQL, block.getAddress(), block.getName(),
                block.getRoofOption(), block.getRoofColour(),
                block.getBaseOption(), block.getBaseColour(),
                block.getForegroundOption(), block.getForegroundColour(),
                block.getFontOptions(), block.getFontColour(),
                block.getMainText(),
                block.getLinksText(),
                block.getInfoText(),
                block.getPromoText(),
                block.isApproved(),
                block.getSecretHash());
    }

    public void Update(Block block) {
        String SQL = """
        UPDATE communities SET 
            name=?, 
            roof_option=?, 
            roof_colour=?, 
            base_option=?, 
            base_colour=?, 
            foreground_option=?, 
            foreground_colour=?, 
            font_options=?,
            font_colour=?,
            main_text=?, 
            links_text=?, 
            info_text=?, 
            promo_text=?,
            approved=?, 
            secret_hash=?
        WHERE id=?;
    """;
        Jdbc.update(SQL,
                block.getName(),
                block.getRoofOption(),
                block.getRoofColour(),
                block.getBaseOption(),
                block.getBaseColour(),
                block.getForegroundOption(),
                block.getForegroundColour(),
                block.getFontOptions(),
                block.getFontColour(),
                block.getMainText(),
                block.getLinksText(),
                block.getInfoText(),
                block.getPromoText(),
                block.isApproved(),
                block.getSecretHash(),
                block.getId() );
    }
    public void deleteBlock(int blockId) {
        String sql = "DELETE FROM communities WHERE id = ?";
        Jdbc.update(sql, blockId);
    }
    //Get all blocks from database.Used by cleanup service to check all blocks for expiry
    @Override
    public List<Block> findAll() {
        String SQL = "SELECT * FROM communities";
        return Jdbc.query(SQL, rowMapper);
    }
    
    //Delete a block from database.Used by cleanup service to remove expired blocks
    @Override
    public void delete(Block block) {
        String SQL = "DELETE FROM communities WHERE id = ?";
        Jdbc.update(SQL, block.getId());
    }
    
    // Delete block by ID (alternative method)
    @Override
    public void deleteById(int id) {
        String SQL = "DELETE FROM communities WHERE id = ?";
        Jdbc.update(SQL, id);
    }
}
