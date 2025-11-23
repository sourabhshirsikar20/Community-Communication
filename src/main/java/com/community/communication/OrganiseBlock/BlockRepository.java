package com.community.communication.OrganiseBlock;

public interface BlockRepository {
Blocks ByAddress(String Address);
boolean Check(String Address);
void Add(Blocks block);
}
