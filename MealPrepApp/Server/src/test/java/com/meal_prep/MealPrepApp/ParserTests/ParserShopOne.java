package com.meal_prep.MealPrepApp.ParserTests;

import com.meal_prep.MealPrepApp.components.enums.food_enums.AllergenType;
import com.meal_prep.MealPrepApp.components.enums.food_enums.FilterType;
import com.meal_prep.MealPrepApp.components.enums.shop_enums.BadgeType;
import com.meal_prep.MealPrepApp.models.Menu;
import com.meal_prep.MealPrepApp.models.Shop;
import com.meal_prep.MealPrepApp.models.food.Food;
import com.meal_prep.MealPrepApp.models.food.FoodItem;
import com.meal_prep.MealPrepApp.models.food.SetMeal;
import com.meal_prep.MealPrepApp.repositories.FoodItemRepository;
import com.meal_prep.MealPrepApp.repositories.MenuRepository;
import com.meal_prep.MealPrepApp.repositories.SetMealRepository;
import com.meal_prep.MealPrepApp.repositories.ShopRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ParserShopOne {

    @Autowired
    FoodItemRepository foodItemRepository;

    @Autowired
    SetMealRepository setMealRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    ShopRepository shopRepository;

    ArrayList<SetMeal> setMealList = new ArrayList<>();
    ArrayList<FoodItem> customFoodList = new ArrayList<>();


    // List header names in CSV file
    String[] HEADERS = { "SHOP_NAME", "MEAL_NAME", "PROTEIN (grams)", "CARB (grams)", "FAT (grams)",
            "TOTAL_CALORIES (KCal)", "TOTAL_WEIGHT (grams)", "INGREDIENTS (e.g. Chicken, Cauliflower…)",
            "IMAGE_URL", "SET_MEAL? (TRUE/FALSE - FALSE=Individual food item for a custom meal)",
            "SET_MEAL: CATEGORY (e.g. Breakfast - Muscle Food - Snack)", "MAIN_PROTEIN (Optional)",
            "DESCRIPTION (Optional)", "PRICE (optional if there is a set price for all meals)", "CELERY (X=true)",
            "EGG (X=true)", "GLUTEN (X=true)", "LUPIN (X=true)", "MILK (X=true)", "MUSHROOM (X=true)", "MUSTARD (X=true)",
            "PEANUTS (X=true)", "SESAME (X=true)", "SOYA (X=true)", "SULPHITES (X=true)", "TREE_NUTS (X=true)",
            "WHEAT (X=true)", "FISH (X=true)", "CRUSTACEANS (X=true)", "MOLLUSCS (X=true)", "Pescatarian (X=true)",
            "Vegetarian (X=true)", "Vegan (X=true)", "Paleo (X=true)", "Keto (X=true)"};


    public Integer convertStringToInteger(String numberString) {
        int number;
        try {
            number = Integer.parseInt(numberString);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            // HACK!
            number = 0;
        }
        return number;
    }

    public Double convertStringToDouble(String doubleString) {
        double number;
        try {
            number = Double.parseDouble(doubleString);

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            // HACK!
            number = 0;
        }
        return number;
    }

    public Boolean convertStringToBoolean(String booleanString) {
        boolean value;
        try {
            value = Boolean.parseBoolean(booleanString);

        } catch (NumberFormatException ex) {
            value = false;
        }
        return value;
    }



    @Test
    public void givenCSVFile_whenRead_thenContentsAsExpected() throws IOException {

        // import CSV file
        Reader in = new FileReader("src/test/parser_test_files/shop_one_dummy.csv");

        // put CSV file into iterable called records
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader(HEADERS)
                .withFirstRecordAsHeader()
                .parse(in);

        // for each line in the CSV file check it matches with the expected (above).
        for (CSVRecord record : records) {
            // Parse Strings
            String shop_name = record.get("SHOP_NAME").toLowerCase();
            String meal_name = record.get("MEAL_NAME").toLowerCase();

            String protein_amount_string = record.get("PROTEIN (grams)");
            Integer protein_amount = convertStringToInteger(protein_amount_string);

            String carb_amount_string = record.get("CARB (grams)");
            Integer carb_amount = convertStringToInteger(carb_amount_string);

            String fat_amount_string = record.get("FAT (grams)");
            Integer fat_amount = convertStringToInteger(fat_amount_string);

            String total_calories_string = record.get("TOTAL_CALORIES (KCal)");
            Integer total_calories = convertStringToInteger(total_calories_string);

            String total_weight_string = record.get("TOTAL_WEIGHT (grams)");
            Integer total_weight = convertStringToInteger(total_weight_string);

            String ingredients = record.get("INGREDIENTS (e.g. Chicken, Cauliflower…)").toLowerCase();
            String image_url = record.get("IMAGE_URL").toLowerCase();

            String set_meal_string = record.get("SET_MEAL? (TRUE/FALSE - FALSE=Individual food item for a custom meal)").toLowerCase();
            Boolean set_meal = convertStringToBoolean(set_meal_string);

            String category = record.get("SET_MEAL: CATEGORY (e.g. Breakfast - Muscle Food - Snack)").toLowerCase();
            String main_protein = record.get("MAIN_PROTEIN (Optional)").toLowerCase();
            String description = record.get("DESCRIPTION (Optional)").toLowerCase();

            String price_string = record.get("PRICE (optional if there is a set price for all meals)");

            String contains_celery = record.get("CELERY (X=true)");
            String contains_corn = record.get("CORN (X=true)");
            String contains_egg = record.get("EGG (X=true)");
            String contains_gluten = record.get("GLUTEN (X=true)");
            String contains_lupin = record.get("LUPIN (X=true)");
            String contains_milk = record.get("MILK (X=true)");
            String contains_mushroom = record.get("MUSHROOM (X=true)");
            String contains_mustard = record.get("MUSTARD (X=true)");
            String contains_peanuts = record.get("PEANUTS (X=true)");
            String contains_sesame = record.get("SESAME (X=true)");
            String contains_soya = record.get("SOYA (X=true)");
            String contains_sulphites = record.get("SULPHITES (X=true)");
            String contains_tree_nuts = record.get("TREE_NUTS (X=true)");
            String contains_wheat = record.get("WHEAT (X=true)");
            String contains_fish = record.get("FISH (X=true)");
            String contains_crustaceans = record.get("CRUSTACEANS (X=true)");
            String contains_molluscs = record.get("MOLLUSCS (X=true)");
            String pescatarian = record.get("Pescatarian (X=true)");
            String vegetarian = record.get("Vegetarian (X=true)");
            String vegan = record.get("Vegan (X=true)");
            String paleo = record.get("Paleo (X=true)");
            String keto = record.get("Keto (X=true)");


            // create new FoodItem from CSV file
            ArrayList<String> csvIngredientsList = new ArrayList<>();
            String[] elements = ingredients.split(",");
            List<String> fixedLengthList = Arrays.asList(elements);
            ArrayList<String> listOfIngredients = new ArrayList<String>(fixedLengthList);

            ArrayList<AllergenType> allergenList = new ArrayList<>();

            if (contains_celery.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.CELERY);
            }
            if (contains_corn.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.CORN);
            }
            if (contains_egg.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.EGG);
            }
            if (contains_gluten.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.GLUTEN);
            }
            if (contains_lupin.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.LUPIN);
            }
            if (contains_milk.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.MILK);
            }
            if (contains_mushroom.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.MUSHROOMS);
            }
            if (contains_mustard.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.MUSTARD);
            }
            if (contains_peanuts.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.PEANUTS);
            }
            if (contains_sesame.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.SESAME);
            }
            if (contains_soya.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.SOYA);
            }
            if (contains_sulphites.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.SULPHITES);
            }
            if (contains_tree_nuts.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.TREE_NUTS);
            }
            if (contains_wheat.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.WHEAT);
            }
            if (contains_fish.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.FISH);
            }
            if (contains_crustaceans.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.CRUSTACEANS);
            }
            if (contains_molluscs.equalsIgnoreCase("x")) {
                allergenList.add(AllergenType.MOLLUSCS);
            }

            ArrayList<FilterType> filterList = new ArrayList<>();

            if (pescatarian.equalsIgnoreCase("x")) {
                filterList.add(FilterType.PESCATARIAN);
            }
            if (vegetarian.equalsIgnoreCase("x")) {
                filterList.add(FilterType.VEGETARIAN);
            }
            if (vegan.equalsIgnoreCase("x")) {
                filterList.add(FilterType.VEGAN);
            }
            if (paleo.equalsIgnoreCase("x")) {
                filterList.add(FilterType.PALEO);
            }
            if (keto.equalsIgnoreCase("x")) {
                filterList.add(FilterType.KETO);
            }



            if (set_meal) {

                // if price field is blank - set meal has a default price (calculated from shop object)
                if (price_string.equals("")){
                    SetMeal setMeal = new SetMeal(shop_name, meal_name, protein_amount, carb_amount,
                            fat_amount, total_calories, total_weight, listOfIngredients, allergenList, filterList,
                            image_url, description, main_protein, category);

                    setMealRepository.save(setMeal);
                    setMealList.add(setMeal);
                }
                else {
                    // otherwise price is set directly
                    Double price = convertStringToDouble(price_string);
                    SetMeal setMeal = new SetMeal(shop_name, meal_name, protein_amount, carb_amount,
                            fat_amount, total_calories, total_weight, listOfIngredients, allergenList, filterList,
                            image_url, description, main_protein, category, price);

                    setMealRepository.save(setMeal);
                    setMealList.add(setMeal);
                }
            }
            else {
                // if not a set meal - create a custom foodItem
                Double price = convertStringToDouble(price_string);
                FoodItem foodItem = new FoodItem(shop_name, meal_name, protein_amount, carb_amount,
                        fat_amount, total_calories, total_weight, listOfIngredients, allergenList, filterList,
                        image_url, description, main_protein, category, price);
                foodItemRepository.save(foodItem);
                customFoodList.add(foodItem);

            }
        }

        // dummy menu
        Menu menu = new Menu("Shop One", setMealList, customFoodList);
        menuRepository.save(menu);

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

        Shop shop = new Shop("Shop One", "01035356591", "shopone@theshop.com", 5.0, 25.5, mealsPerDay, deliveryDays,
                "https://sm.pcmag.com/pcmag_uk/review/p/phase-one-/phase-one-capture-one-pro_q8qp.jpg", 4,
                mealPriceByQuantity, badges);
        shopRepository.save(shop);

        }



    }
