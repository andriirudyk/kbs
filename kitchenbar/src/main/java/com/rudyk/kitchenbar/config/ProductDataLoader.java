package com.rudyk.kitchenbar.config;

import com.rudyk.kitchenbar.domain.model.Product;

import com.rudyk.kitchenbar.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ProductDataLoader {

    @Bean
    CommandLineRunner loadProducts(ProductRepository productRepository) {
        return args -> {

            if (productRepository.count() > 0) {
                return; // prevent re-seeding on restart
            }

            List<Product> products = List.of(
                    product("Espresso", 2.50, "Drinks", "Strong Italian coffee"),
                    product("Americano", 3.00, "Drinks", "Espresso with hot water"),
                    product("Cappuccino", 3.50, "Drinks", "Coffee with milk foam"),
                    product("Latte", 3.80, "Drinks", "Milky coffee"),
                    product("Mocha", 4.20, "Drinks", "Coffee with chocolate"),

                    product("Cheeseburger", 8.50, "Food", "Beef burger with cheese"),
                    product("Chicken Burger", 8.00, "Food", "Grilled chicken burger"),
                    product("Veggie Burger", 7.50, "Food", "Plant-based burger"),
                    product("BBQ Burger", 9.00, "Food", "Burger with BBQ sauce"),
                    product("Double Burger", 10.50, "Food", "Two beef patties"),

                    product("Margherita Pizza", 9.00, "Pizza", "Tomato, mozzarella, basil"),
                    product("Pepperoni Pizza", 10.50, "Pizza", "Pepperoni and cheese"),
                    product("BBQ Chicken Pizza", 11.50, "Pizza", "Chicken & BBQ sauce"),
                    product("Four Cheese Pizza", 11.00, "Pizza", "Mozzarella, cheddar, parmesan, blue cheese"),
                    product("Veggie Pizza", 10.00, "Pizza", "Vegetable toppings"),

                    product("Caesar Salad", 7.00, "Salads", "Chicken, lettuce, parmesan"),
                    product("Greek Salad", 6.50, "Salads", "Feta cheese and olives"),
                    product("Tuna Salad", 7.50, "Salads", "Tuna with fresh veggies"),
                    product("Avocado Salad", 8.00, "Salads", "Avocado and greens"),
                    product("Pasta Salad", 7.00, "Salads", "Pasta with vegetables"),

                    product("Spaghetti Bolognese", 10.00, "Pasta", "Classic meat sauce"),
                    product("Carbonara", 10.50, "Pasta", "Creamy bacon sauce"),
                    product("Pesto Pasta", 9.50, "Pasta", "Basil pesto"),
                    product("Seafood Pasta", 12.50, "Pasta", "Mixed seafood"),
                    product("Lasagna", 11.00, "Pasta", "Layered meat pasta"),

                    product("Chocolate Cake", 5.00, "Desserts", "Rich chocolate cake"),
                    product("Cheesecake", 5.50, "Desserts", "Creamy cheesecake"),
                    product("Tiramisu", 6.00, "Desserts", "Italian classic"),
                    product("Ice Cream", 4.00, "Desserts", "Vanilla ice cream"),
                    product("Brownie", 4.50, "Desserts", "Chocolate brownie"),

                    product("Cola", 2.50, "Drinks", "Chilled soft drink"),
                    product("Orange Juice", 3.00, "Drinks", "Fresh juice"),
                    product("Mineral Water", 2.00, "Drinks", "Sparkling water"),
                    product("Green Tea", 2.80, "Drinks", "Hot green tea"),
                    product("Black Tea", 2.50, "Drinks", "Classic black tea")
            );

            productRepository.saveAll(products);
        };
    }

    private Product product(String name, double price, String category, String description) {
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        p.setCategory(category);
        p.setDescription(description);
        return p;
    }
}
