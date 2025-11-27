package com.community.communication.OrganiseBlock;

import com.community.communication.Approved.Block;

public interface BlockRepository {
Block ByAddress(String Address);
boolean Check(String Address);
void Add(Block block);
Block ById(int Id);
void Update(Block block);
}
