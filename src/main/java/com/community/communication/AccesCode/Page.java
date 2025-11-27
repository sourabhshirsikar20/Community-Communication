package com.community.communication.AccesCode;

import jakarta.persistence.*;

@Entity
@Table(name = "pages")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "page_name", unique = true)
    private String pageName;

    @Column(name = "secret_hash")
    private String secretHash;

    private String name;

    @Column(name = "main_text", columnDefinition = "TEXT")
    private String mainText;

    @Column(name = "links_text", columnDefinition = "TEXT")
    private String linksText;

    @Column(name = "info_text", columnDefinition = "TEXT")
    private String infoText;

    @Column(name = "promo_text", columnDefinition = "TEXT")
    private String promoText;

    // getters and setters
    public Long getId() { return id; }

    public String getPageName() { return pageName; }
    public void setPageName(String pageName) { this.pageName = pageName; }

    public String getSecretHash() { return secretHash; }
    public void setSecretHash(String secretHash) { this.secretHash = secretHash; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMainText() { return mainText; }
    public void setMainText(String mainText) { this.mainText = mainText; }

    public String getLinksText() { return linksText; }
    public void setLinksText(String linksText) { this.linksText = linksText; }

    public String getInfoText() { return infoText; }
    public void setInfoText(String infoText) { this.infoText = infoText; }

    public String getPromoText() { return promoText; }
    public void setPromoText(String promoText) { this.promoText = promoText; }
}