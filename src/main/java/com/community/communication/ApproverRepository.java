package com.community.communication;

import java.util.List;

public interface ApproverRepository {
    List<Block> getUnapprovedBlocks();
}
