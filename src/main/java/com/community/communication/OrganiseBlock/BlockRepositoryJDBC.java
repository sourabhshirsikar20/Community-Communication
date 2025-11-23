package com.community.communication.OrganiseBlock;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BlockRepositoryJDBC implements BlockRepository{
    private JdbcTemplate Jdbc;
    private RowMapper<Blocks> rowMapper;
    public BlockRepositoryJDBC(JdbcTemplate Jdbc){
        this.Jdbc = Jdbc;
        setBlockMapper();
    }
    private void setBlockMapper(){
        rowMapper = (rs, i) -> new Blocks(
                rs.getString("Address"),
                rs.getString("Test")
        );
    }
    public Blocks ByAddress(String Address){
        String SQL = "SELECT * FROM block WHERE Address = ?";
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
    public void Add(Blocks block){
        String SQL = "INSERT INTO block (Address,Test) VALUES (?, ?)";
        Jdbc.update(SQL,block.getAddress(), block.getTest());
    }

}
