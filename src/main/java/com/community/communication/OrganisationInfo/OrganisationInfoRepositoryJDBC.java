package com.community.communication.OrganisationInfo;

import java.time.LocalDateTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.community.communication.Approved.Block;

@Repository
public class OrganisationInfoRepositoryJDBC implements OrganisationInfoRepository {
    private JdbcTemplate Jdbc;
    private RowMapper<Block> organisationInfoMapper;

    public OrganisationInfoRepositoryJDBC(JdbcTemplate IJdbc) {
        this.Jdbc = IJdbc;
        setOrganisationInfoMapper();
    }

    public void setOrganisationInfoMapper() {
        organisationInfoMapper = (rs, i) -> new Block(
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
                rs.getTimestamp("created_date") != null
                ? rs.getTimestamp("created_date").toLocalDateTime()
                : LocalDateTime.now(),
                null
        );
    }

    public Block getInfo(String address){
        String SQL = "SELECT * FROM communities WHERE address = ?";
        return Jdbc.queryForObject(SQL, organisationInfoMapper, address);
    }

    public void reportBlock(int id, String name, String reason) {
        String SQL = "UPDATE communities SET address = NULL, approved = 0 WHERE id = ?";
        Jdbc.update(SQL,id);

        String reportSQL = "INSERT INTO reports (blockID, block_name, reason) VALUES (?, ?, ?)";
        Jdbc.update(reportSQL, id, name, reason);
    }

}
