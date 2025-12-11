package com.community.communication;

import com.community.communication.Approved.Block;

import com.community.communication.blockmovenotice.BlockMoveNotice;
import com.community.communication.blockmovenotice.BlockMoveNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class BlockMoveNoticeServiceTest {

    @Autowired
    BlockService blockService;

    @Autowired
    BlockMoveNoticeService noticeService;

    @Test
    void noticeCreatedWhenAddressChanged() {
        Block block = blockService.createTestBlock("Old street 1");
        block.setAddress("New Avenue 2");
        noticeService.createNotice(block, "Old street 1");

        List<BlockMoveNotice> notices = noticeService.getNotices();
        Assertions.assertFalse(notices.isEmpty());
    }

    @Test
    void expiredNoticeRemoved() {
        Block block = blockService.createTestBlock("Old");
        noticeService.createNotice(block, "Old");

        // вручную делаем срок истёкшим
        BlockMoveNotice notice = noticeService.getNotices().get(0);
        notice.setExpiryDate(LocalDateTime.now().minusDays(5));
        noticeService.removeExpired();

        Assertions.assertTrue(noticeService.getNotices().isEmpty());
    }
}

