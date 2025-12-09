package com.community.communication.OrganisationInfo;

import com.community.communication.Approved.Block;

public interface OrganisationInfoRepository {
    Block getInfo(String address);
    void reportBlock(int id, String name, String reason);
}
