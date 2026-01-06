package com.rudyk.inventoryservice.repositories;



import com.rudyk.inventoryservice.domain.model.InventoryItem;
import com.rudyk.inventoryservice.domain.vo.ItemCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository <InventoryItem, Long>{
    Optional<InventoryItem> findByItemCode(ItemCode itemCode);

    
}