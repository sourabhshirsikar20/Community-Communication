package com.community.communication.OrganisationInfo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrganisationInfoRepositoryJDBC implements OrganisationInfoRepository {
    private JdbcTemplate Jdbc;
    private RowMapper<OrganisationInfo> organisationInfoMapper;

    public OrganisationInfoRepositoryJDBC(JdbcTemplate IJdbc){
        this.Jdbc = IJdbc;
        setOrganisationInfoMapper();
    }

    public void setOrganisationInfoMapper(){
        organisationInfoMapper = (rs,i) -> new OrganisationInfo(
            rs.getString("MainText"),
            rs.getString("Links"),
            rs.getString("Info"),
            rs.getString("Promo")
        );
    }

    public List<OrganisationInfo> getAllInfo(){
        String SQL = "SELECT * FROM organisationinfo";
        return Jdbc.query(SQL, organisationInfoMapper);
    }


}
