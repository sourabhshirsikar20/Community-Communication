package com.community.communication.OrganisationInfo;

import com.community.communication.Approved.Block;

public interface OrganisationInfoRepository {
    Block getInfo(int Id);
}
