package com.community.communication.Promos;

import java.util.List;
import java.util.Map;

import com.community.communication.Approved.Block;

public interface PromoRepository {
    Map<Integer, Block> getActivePromos();
    void setPromo(int blockId, int position);
    void removePromo(int position);
    List<Block> getAllBlocks();
}
