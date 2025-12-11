package com.community.communication.blockmovenotice;

import com.community.communication.Approved.Block;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlockMoveNoticeService {
    private final BlockMoveNoticeRepository repo;
    public BlockMoveNoticeService(BlockMoveNoticeRepository repo) {
        this.repo = repo;
    }
    //Notice for 3 days
    public void createNotice(Block block, String oldAddress) {
        BlockMoveNotice notice = new BlockMoveNotice();
        notice.setBlockId(block.getId());
        notice.setOldAddress(oldAddress);
        notice.setNewAddress(block.getAddress());
        notice.setExpiryDate(LocalDateTime.now().plusDays(3));
        repo.save(notice);
    }
    public List<BlockMoveNotice> getActiveNotices() {
        LocalDateTime now = LocalDateTime.now();
        //Delete expired
        List<BlockMoveNotice> expired = repo.findByExpiryDateBefore(now);
        if (!expired.isEmpty()) {
            repo.deleteAll(expired);
        }
        return repo.findByExpiryDateAfter(now);
    }
    //Chech if the address reserved or not
    public boolean isAddressReserved(String address) {
        return repo.existsByOldAddressAndExpiryDateAfter(address, LocalDateTime.now());
    }
}