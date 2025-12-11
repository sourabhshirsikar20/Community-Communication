package com.community.communication.blockmovenotice;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "block_move_notice")
public class BlockMoveNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "block_id", nullable = false)
    private Integer blockId;

    @Column(name = "old_address", nullable = false)
    private String oldAddress;

    @Column(name = "new_address", nullable = false)
    private String newAddress;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getBlockId() { return blockId; }
    public void setBlockId(Integer blockId) { this.blockId = blockId; }

    public String getOldAddress() { return oldAddress; }
    public void setOldAddress(String oldAddress) { this.oldAddress = oldAddress; }

    public String getNewAddress() { return newAddress; }
    public void setNewAddress(String newAddress) { this.newAddress = newAddress; }

    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }
}