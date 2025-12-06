package com.community.communication.OrganiseBlock;

import java.util.List;

import com.community.communication.Approved.Block;

public interface BlockRepository {
Block ByAddress(String Address);
boolean Check(String Address);
void Add(Block block);
Block ById(int Id);
void Update(Block block);

//Get all blocks from database. Used by cleanup service to check all blocks
List<Block> findAll();
    
// Delete a block from database. Used by cleanup service to remove expired blocks
void delete(Block block);
    
//Delete block by ID (alternative method)
void deleteById(int id);
}
