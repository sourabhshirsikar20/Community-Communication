package com.community.communication.blockmovenotice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BlockMoveNoticeRepository extends JpaRepository<BlockMoveNotice, Long> {
        //Choose all that is expired and not expired
        List<BlockMoveNotice> findByExpiryDateAfter(LocalDateTime time);
        List<BlockMoveNotice> findByExpiryDateBefore(LocalDateTime time);
        //Check if the address is registered (old or new)
        boolean existsByNewAddressAndExpiryDateAfter(String newAddress, LocalDateTime time);
        boolean existsByOldAddressAndExpiryDateAfter(String oldAddress, LocalDateTime time);
    }
