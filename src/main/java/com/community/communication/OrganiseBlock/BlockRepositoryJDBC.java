package com.community.communication.OrganiseBlock;

import com.community.communication.Approved.Block;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
                rs.getString("reportReason")
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
        String SQL = "INSERT INTO communities (address, name, roof_option, roof_colour, base_option, base_colour, foreground_option, foreground_colour, main_text, links_text, info_text, promo_text,approved, secret_hash) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
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
            address=?, 
            name=?, 
            roof_option=?, 
            roof_colour=?, 
            base_option=?, 
            base_colour=?, 
            foreground_option=?, 
            foreground_colour=?, 
            main_text=?, 
            links_text=?, 
            info_text=?, 
            promo_text=?,
            approved=?, 
            secret_hash=?
        WHERE id=?;
    """;
        Jdbc.update(SQL, block.getAddress(),
                block.getName(),
                block.getRoofOption(),
                block.getRoofColour(),
                block.getBaseOption(),
                block.getBaseColour(),
                block.getForegroundOption(),
                block.getForegroundColour(),
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
}
