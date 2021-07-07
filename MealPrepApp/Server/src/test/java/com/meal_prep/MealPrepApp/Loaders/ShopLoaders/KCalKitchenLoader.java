package com.meal_prep.MealPrepApp.Loaders.ShopLoaders;

import com.meal_prep.MealPrepApp.components.enums.shop_enums.BadgeType;
import com.meal_prep.MealPrepApp.models.Shop;
import com.meal_prep.MealPrepApp.repositories.ShopRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;

@SpringBootTest
public class KCalKitchenLoader {

    @Autowired
    ShopRepository shopRepository;


    @Test
            public void loadShop(){
    //SHOP
        ArrayList<Integer> mealsPerDay = new ArrayList<>();
        mealsPerDay.add(1);
        mealsPerDay.add(2);
        mealsPerDay.add(3);
        mealsPerDay.add(4);

        ArrayList<Integer> deliveryDays = new ArrayList<>();
        deliveryDays.add(1);
        deliveryDays.add(3);
        deliveryDays.add(5);

        HashMap<Integer, Double> mealPriceByQuantity = new HashMap<>();
        mealPriceByQuantity.put(4, 6.50);
        mealPriceByQuantity.put(6, 6.25);
        mealPriceByQuantity.put(8, 6.00);
        mealPriceByQuantity.put(10, 5.50);

        ArrayList<BadgeType> badges = new ArrayList<>();
        badges.add(BadgeType.ORGANIC);
        badges.add(BadgeType.LOCALLY_SOURCED);

        Shop shop = new Shop("KCal Kitchen", "01035356111", "shopone@theshop.com", 4.6, 25.5, mealsPerDay, deliveryDays,
                "https://mybayutcdn.bayut.com/mybayut/wp-content/uploads/Kcal-Extra.jpg", 4,
                mealPriceByQuantity, badges, "Get a Taste for Nutrition with the Best Healthy Food.");
        shopRepository.save(shop);
}
}
