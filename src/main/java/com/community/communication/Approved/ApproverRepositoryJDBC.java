package com.community.communication.Approved;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.community.communication.blockmovenotice.BlockMoveNoticeRepository;
import java.time.LocalDateTime;


@Repository
public class ApproverRepositoryJDBC implements ApproverRepository  {

    private JdbcTemplate jdbc;
    private final BlockMoveNoticeRepository blockMoveNoticeRepository;
    private RowMapper<Block> blockMapper;
    private RowMapper<Street> streetMapper;

    public ApproverRepositoryJDBC(JdbcTemplate aJdbc,BlockMoveNoticeRepository blockMoveNoticeRepository)  {
        this.jdbc = aJdbc;
        this.blockMoveNoticeRepository = blockMoveNoticeRepository;
        setBlockMapper();
        setStreetMapper();
    }

    private void setBlockMapper() {
        blockMapper = (rs, i) -> new Block(
                rs.getInt("id"),
                rs.getString("address"),
                rs.getString("name"),
                rs.getInt("roof_option"),
                rs.getString("roof_colour"),
                rs.getInt("base_option"),
                rs.getString("base_colour"),
                rs.getInt("foreground_option"),
                rs.getString("foreground_colour"),
                rs.getString("font_options"),
                rs.getString("font_colour"),
                rs.getString("main_text"),
                rs.getString("links_text"),
                rs.getString("info_text"),
                rs.getString("promo_text"),
                rs.getString("secret_hash"),
                rs.getBoolean("approved"),
                // Added created_date mapping
                rs.getTimestamp("created_date") != null ? 
                    rs.getTimestamp("created_date").toLocalDateTime() : 
                    null, // Allow null for unapproved blocks
                null
        );
    }
    private void setStreetMapper() {
        streetMapper = (rs, i) -> new Street(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("description"),
                rs.getString("img_option")
        );
    }

    @Override
    public List<Block> getUnapprovedBlocks() {
        String sql = """
            SELECT c.*, 
            (SELECT reason FROM reports WHERE blockID = c.id ORDER BY id DESC LIMIT 1) as reason 
            FROM communities c 
            WHERE c.approved = '0'
            """;

            RowMapper<Block> unapprovedMapper = (rs, i) -> new Block(
            rs.getInt("id"),
            rs.getString("address"),
            rs.getString("name"),
            rs.getInt("roof_option"),
            rs.getString("roof_colour"),
            rs.getInt("base_option"),
            rs.getString("base_colour"),
            rs.getInt("foreground_option"),
            rs.getString("foreground_colour"),
            rs.getString("font_options"),
            rs.getString("font_colour"),
            rs.getString("main_text"),
            rs.getString("links_text"),
            rs.getString("info_text"),
            rs.getString("promo_text"),
            rs.getString("secret_hash"),
            rs.getBoolean("approved"),
            rs.getTimestamp("created_date") != null ? 
                    rs.getTimestamp("created_date").toLocalDateTime() : 
                    null,
            rs.getString("reason")
            );
        return jdbc.query(sql, unapprovedMapper);
    }

    @Override
    public void addNewBlock(Block block) {
        
        // created_date is handled by database DEFAULT CURRENT_TIMESTAMP
        
        String sql = """
            INSERT INTO communities (
                name, address,
                roof_option, roof_colour, 
                base_option, base_colour, 
                foreground_option, foreground_colour, 
                font_options, font_colour,
                main_text, 
                links_text, 
                info_text, 
                promo_text,
                approved,
                created_date
            ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
        """;
                
        jdbc.update(sql, 
            block.getName(), block.getAddress(),
            block.getRoofOption(), block.getRoofColour(),
            block.getBaseOption(), block.getBaseColour(),
            block.getForegroundOption(), block.getForegroundColour(),
            block.getFontOptions(), block.getFontColour(),
            block.getMainText(),
            block.getLinksText(),
            block.getInfoText(),
            block.getPromoText(),
            false,  // Not approved yet
            null    // NULL created_date - will be set on approval
        );
    }

    @Override
    public void removeBlock(String address) {
        String sql = "DELETE FROM communities WHERE address = ?";
        jdbc.update(sql, address);
    }

    @Override
    public void moveBlock(String address, String new_address) {
        String sql = "UPDATE communities SET address = ? WHERE address = ?";
        jdbc.update(sql, new_address, address);
    }

    @Override
    public void addNewStreet(Street street) {
        // id is autoincrementing, so not included
        String sql = "INSERT INTO streets (name, address, description, img_option) VALUES (?,?,?,?)";
        jdbc.update(sql, street.getName(), street.getAddress(), street.getDescription(), street.getImg_option());
    }

    @Override
    public void removeStreet(String address) {
        if (address.equals("root"))
            throw new IllegalArgumentException("Cannot rmove the root street");

        String sql = "DELETE FROM streets WHERE address = ?";
        jdbc.update(sql, address);
    }

    @Override
    public void approve(int id, String secretHash) {
        int newAddress = 1;
        String newAddressString = null;
        List<Block> newStreet = getStreetBlocks("00");
        LocalDateTime now = LocalDateTime.now();
        while (true) {
            if (newAddress > 99) {
                System.out.println("No space in the database");
                return;
            }
            //check weather to add 2 or 3 zeros before the address
            final String candidateAddress = (newAddress < 10)
                    ? "000" + newAddress
                    : "00" + newAddress;
            //Check if its occupied by an existing block
            boolean occupiedByBlock = newStreet.stream()
                    .anyMatch(block -> candidateAddress.equals(block.getAddress()));
            //Check if its reserved by a Ghost block
            boolean reservedByMoveNotice =
                    blockMoveNoticeRepository.existsByOldAddressAndExpiryDateAfter(candidateAddress, now);
            if (!occupiedByBlock && !reservedByMoveNotice) {
                newAddressString = candidateAddress;
                break;
            }
            newAddress++;
        }
        String sql = "UPDATE communities SET approved = 1, address = ?, secret_Hash = ?, created_date = NOW() WHERE id = ?";
        jdbc.update(sql, newAddressString, secretHash, id);
    }
    @Override
    public void deleteBlock(int id) {
        String sql = "DELETE FROM communities WHERE id = ?";
        jdbc.update(sql, id);
    }
    @Override
    public List<Block> getStreetBlocks(String streetAddress) {
        String sql = "SELECT * FROM communities WHERE " +
                        "approved = '1' AND " +
                        "SUBSTRING(address, 1, ?) = ? AND " +
                        "LENGTH(address) = ? "+
                        "ORDER BY CAST(address AS UNSIGNED)";
        return jdbc.query(sql, blockMapper,  streetAddress.length(), streetAddress, streetAddress.length()+2);
    }
    @Override
    public List<Block> getApprovedBlocks() {
        
        String sql = """
            SELECT * FROM communities 
            WHERE approved = '1' 
            AND created_date IS NOT NULL
            AND DATE_ADD(created_date, INTERVAL 6 MONTH) > NOW()
        """;
        return jdbc.query(sql, blockMapper);
    }

    public List<Block> getSearch(String value) {
        String sql = "SELECT * FROM communities WHERE approved = '1' AND name LIKE ?";
        String input = "%" + value +"%";
        return jdbc.query(sql, blockMapper,input);
    }

    public List<Street> getSubStreets(String streetAddress) {
        if (streetAddress.equals("root"))
            streetAddress = "";
        String sql = "SELECT * FROM streets WHERE " +
                        "SUBSTRING(address, 1, ?) = ? AND " +
                        "LENGTH(address) = ?" ;
        return jdbc.query(sql, streetMapper,  streetAddress.length(), streetAddress, streetAddress.length()+2);
    }

    public Street getStreet(String streetAddress) {
        String sql = "SELECT * FROM streets WHERE address = ?";
        List<Street> result = jdbc.query(sql, streetMapper, streetAddress);
        if (result.size() == 1)
            return result.get(0);
        return null; // address field is enforced unique, so should never be reached in practice
    }
    
    public String GenAddr(String StAddress){
        if(StAddress.equals("root")) {
            StAddress = "";
        }
        List<Block> Exists = getStreetBlocks(StAddress);
        List<Street> ExistsSt = getSubStreets(StAddress);
        int Nxt = 1;
        String NxtAddr = "";
        while(true){
            if (Nxt < 10) {
                NxtAddr = StAddress + "0" + Nxt;
            } else {
                NxtAddr = StAddress + Nxt;
            }
            boolean Ctr = false;
            for(Block Addr : Exists){
                if(Addr.address.equals(NxtAddr)){
                    Ctr = true;
                    break;
                }
            }
            for(Street Addr : ExistsSt){
                if(Addr.address.equals(NxtAddr)){
                    Ctr = true;
                    break;
                }
            }
            if(!Ctr){
                break;
            }
            Nxt++;
        }
        return NxtAddr;
    }
    public void updateAddress(String OldAddr, String NewAddr) {
        String sql = "UPDATE communities SET address = ? WHERE address = ?";
        jdbc.update(sql, NewAddr, OldAddr);
    }

    public List<Street> getAllStreets() {
        String sql = "SELECT * FROM streets";
        return jdbc.query(sql, streetMapper);
    }
    
    public boolean checkStreet(String streetAddress) {
        String sql = "SELECT * FROM streets WHERE address = ?";
        List<Street> result = jdbc.query(sql, streetMapper, streetAddress);
        if (result.size() == 1) {
            return true;
        }
        return false; // address field is enforced unique, so should never be reached in practice
    }
}
