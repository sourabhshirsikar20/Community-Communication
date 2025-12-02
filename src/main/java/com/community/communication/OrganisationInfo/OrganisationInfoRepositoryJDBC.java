package com.community.communication.OrganisationInfo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.community.communication.Approved.Block;

@Repository
public class OrganisationInfoRepositoryJDBC implements OrganisationInfoRepository {
    private JdbcTemplate Jdbc;
    private RowMapper<Block> organisationInfoMapper;

    public OrganisationInfoRepositoryJDBC(JdbcTemplate IJdbc){
        this.Jdbc = IJdbc;
        setOrganisationInfoMapper();
    }

    public void setOrganisationInfoMapper(){
        organisationInfoMapper = (rs,i) -> new Block(
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
                rs.getBoolean("approved")
        );
    }

    public Block getInfo(int Id){
        String SQL = "SELECT * FROM communities WHERE id = ?";
        return Jdbc.queryForObject(SQL, organisationInfoMapper, Id);
    }

}
