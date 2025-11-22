package com.community.communication.OrganisationInfo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
                rs.getInt("Id"),
            rs.getString("MainText"),
            rs.getString("Links"),
            rs.getString("Info"),
            rs.getString("Promo")
        );
    }

    public OrganisationInfo getInfo(int Id){
        String SQL = "SELECT * FROM organisationinfo WHERE Id = ?";
        return Jdbc.queryForObject(SQL, organisationInfoMapper, Id);
    }

}
