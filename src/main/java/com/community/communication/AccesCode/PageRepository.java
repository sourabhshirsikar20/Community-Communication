package com.community.communication.AccesCode;

import com.community.communication.AccesCode.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface PageRepository extends JpaRepository<Page, Long> {
    Optional<Page> findByPageName(String pageName);
}