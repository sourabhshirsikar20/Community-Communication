package com.community.communication.Approved;

import java.util.List;

public interface ApproverRepository {
    List<Block> getApprovedBlocks();
    List<Block> getUnapprovedBlocks();
    void addNewBlock(Block block);
    void approve(int id, String secretHash);
}
