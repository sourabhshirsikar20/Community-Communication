package com.community.communication.Promos;

import com.community.communication.Approved.Block;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PromoRepositoryJDBC implements PromoRepository {

    private final JdbcTemplate jdbc;
    private final RowMapper<Block> blockMapper;

    public PromoRepositoryJDBC(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        // Reusing the block mapper logic you have elsewhere
        this.blockMapper = (rs, i) -> new Block(
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
                rs.getTimestamp("created_date") != null ? rs.getTimestamp("created_date").toLocalDateTime() : null,
                null
        );
    }

    @Override
    public Map<Integer, Block> getActivePromos() {
        String sql = """
            SELECT p.promo_position, c.* FROM promos p 
            JOIN communities c ON p.block_id = c.id 
            ORDER BY p.promo_position ASC
        """;
        
        Map<Integer, Block> promoMap = new HashMap<>();
        
        jdbc.query(sql, (rs) -> {
            int pos = rs.getInt("promo_position");
            Block block = blockMapper.mapRow(rs, 0); 
            promoMap.put(pos, block);
        });
        
        return promoMap;
    }

    @Override
    public void setPromo(int blockId, int position) {
        removePromo(position);
        String sql = "INSERT INTO promos (block_id, promo_position) VALUES (?, ?)";
        jdbc.update(sql, blockId, position);
    }

    @Override
    public void removePromo(int position) {
        String sql = "DELETE FROM promos WHERE promo_position = ?";
        jdbc.update(sql, position);
    }

    @Override
    public List<Block> getAllBlocks() {
        String sql = "SELECT * FROM communities WHERE approved = 1";
        return jdbc.query(sql, blockMapper);
    }
}