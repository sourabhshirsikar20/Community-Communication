package com.community.communication.Approved;

import java.util.List;

public interface ApproverRepository {
    List<Block> getStreetBlocks(String streetAddress);
    List<Block> getApprovedBlocks();
    List<Block> getUnapprovedBlocks();
    void addNewBlock(Block block);
    void approve(int id, String secretHash);
    void deleteBlock(int id);
    List<Block> getSearch(String value);
    List<Street> getSubStreets(String streetAddress);
    Street getStreet(String streetAddress);
    String GenAddr(String StAddress);
    void updateAddress(String OldAddr, String NewAddr);
    boolean checkStreet(String streetAddress);
}
