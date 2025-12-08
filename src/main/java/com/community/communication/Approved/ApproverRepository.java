package com.community.communication.Approved;

import java.util.List;

public interface ApproverRepository {
    List<Block> getStreetBlocks(String streetAddress);
    List<Block> getApprovedBlocks();
    List<Block> getUnapprovedBlocks();
    void addNewBlock(Block block);
    void addNewStreet(Street street);
    void approve(int id, String secretHash);
    List<Block> getSearch(String value);
    List<Street> getSubStreets(String streetAddress);
    Street getStreet(String streetAddress);
    List<Street> getAllStreets();
}
