package com.rudyk.inventoryservice.config;



import com.rudyk.inventoryservice.domain.model.InventoryItem;
import com.rudyk.inventoryservice.repositories.InventoryRepository;
import com.rudyk.inventoryservice.domain.vo.ItemCode;
import com.rudyk.inventoryservice.domain.vo.Quantity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryTestDataInitializer {

    @Bean
    public CommandLineRunner loadTestInventory(InventoryRepository inventoryJpaRepository) {
        return args -> {
            // Створити кілька тестових позицій, якщо їх ще немає
            if (inventoryJpaRepository.count() == 0) {
                InventoryItem item1 = new InventoryItem(new ItemCode("IPA-01"), new Quantity(12));
                InventoryItem item2 = new InventoryItem(new ItemCode("BURGER-03"), new Quantity(5));
                InventoryItem item3 = new InventoryItem(new ItemCode("COLA-01"), new Quantity(20));

                inventoryJpaRepository.save(item1);
                inventoryJpaRepository.save(item2);
                inventoryJpaRepository.save(item3);

                System.out.println("Test inventory items initialized.");
            } else {
                System.out.println("Inventory already initialized.");
            }
        };
    }
}