package com.community.communication.Approved;

import java.util.List;

public interface ApproverRepository {
    List<Block> getUnapprovedBlocks();
    void approve(int id);
}
