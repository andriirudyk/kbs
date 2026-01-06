package com.rudyk.catalog.repositories;

import com.rudyk.catalog.domain.model.Menu;
import com.rudyk.catalog.domain.model.MenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByStatusAndValidFromBeforeAndValidToAfter(
            MenuStatus status, LocalDateTime from, LocalDateTime to);
}

