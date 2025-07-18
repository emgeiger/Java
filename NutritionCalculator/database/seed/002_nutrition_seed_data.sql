-- ==================================================
-- SUPABASE NUTRITION DATABASE SEED DATA
-- ==================================================
-- Initial data for food categories, brands, and sample foods
-- 
-- Created: 2025-07-18
-- Version: 1.0
-- ==================================================

-- ==================================================
-- FOOD CATEGORIES SEED DATA
-- ==================================================

INSERT INTO food_categories (id, name, description, color_hex) VALUES
-- Main categories
('550e8400-e29b-41d4-a716-446655440001', 'Fruits', 'Fresh and dried fruits', '#FF6B6B'),
('550e8400-e29b-41d4-a716-446655440002', 'Vegetables', 'Fresh and cooked vegetables', '#4ECDC4'),
('550e8400-e29b-41d4-a716-446655440003', 'Grains & Cereals', 'Whole grains, cereals, and bread products', '#45B7D1'),
('550e8400-e29b-41d4-a716-446655440004', 'Proteins', 'Meat, fish, eggs, and plant proteins', '#96CEB4'),
('550e8400-e29b-41d4-a716-446655440005', 'Dairy & Alternatives', 'Milk, cheese, yogurt, and plant alternatives', '#FFEAA7'),
('550e8400-e29b-41d4-a716-446655440006', 'Fats & Oils', 'Cooking oils, nuts, and healthy fats', '#DDA0DD'),
('550e8400-e29b-41d4-a716-446655440007', 'Beverages', 'Water, juices, and other drinks', '#87CEEB'),
('550e8400-e29b-41d4-a716-446655440008', 'Snacks & Sweets', 'Processed snacks and confectionery', '#F0A500'),
('550e8400-e29b-41d4-a716-446655440009', 'Condiments & Sauces', 'Spices, sauces, and flavor enhancers', '#E17055'),
('550e8400-e29b-41d4-a716-446655440010', 'Prepared Foods', 'Ready-to-eat and frozen meals', '#6C5CE7');

-- Subcategories for Fruits
INSERT INTO food_categories (id, name, description, color_hex, parent_category_id) VALUES
('550e8400-e29b-41d4-a716-446655440011', 'Citrus Fruits', 'Oranges, lemons, limes, grapefruits', '#FF8A65', '550e8400-e29b-41d4-a716-446655440001'),
('550e8400-e29b-41d4-a716-446655440012', 'Berries', 'Strawberries, blueberries, raspberries', '#E91E63', '550e8400-e29b-41d4-a716-446655440001'),
('550e8400-e29b-41d4-a716-446655440013', 'Tropical Fruits', 'Bananas, mangoes, pineapples', '#FF9800', '550e8400-e29b-41d4-a716-446655440001');

-- Subcategories for Vegetables
INSERT INTO food_categories (id, name, description, color_hex, parent_category_id) VALUES
('550e8400-e29b-41d4-a716-446655440014', 'Leafy Greens', 'Spinach, lettuce, kale, arugula', '#4CAF50', '550e8400-e29b-41d4-a716-446655440002'),
('550e8400-e29b-41d4-a716-446655440015', 'Root Vegetables', 'Carrots, potatoes, beets, onions', '#8D6E63', '550e8400-e29b-41d4-a716-446655440002'),
('550e8400-e29b-41d4-a716-446655440016', 'Cruciferous', 'Broccoli, cauliflower, Brussels sprouts', '#66BB6A', '550e8400-e29b-41d4-a716-446655440002');

-- Subcategories for Proteins
INSERT INTO food_categories (id, name, description, color_hex, parent_category_id) VALUES
('550e8400-e29b-41d4-a716-446655440019', 'Plant Proteins', 'Beans, lentils, tofu, tempeh', '#81C784', '550e8400-e29b-41d4-a716-446655440004');

-- ==================================================
-- FOOD BRANDS SEED DATA
-- ==================================================

INSERT INTO food_brands (id, name, website_url, country, verified) VALUES
('550e8400-e29b-41d4-a716-446655441001', 'Organic Valley', 'https://www.organicvalley.coop', 'USA', true),
('550e8400-e29b-41d4-a716-446655441002', 'Dole', 'https://www.dole.com', 'USA', true),
('550e8400-e29b-41d4-a716-446655441003', 'Quaker', 'https://www.quakeroats.com', 'USA', true),
('550e8400-e29b-41d4-a716-446655441004', 'Kellogg''s', 'https://www.kelloggs.com', 'USA', true),
('550e8400-e29b-41d4-a716-446655441005', 'General Mills', 'https://www.generalmills.com', 'USA', true),
('550e8400-e29b-41d4-a716-446655441006', 'Tyson', 'https://www.tysonfoods.com', 'USA', true),
('550e8400-e29b-41d4-a716-446655441007', 'Birds Eye', 'https://www.birdseye.com', 'USA', true),
('550e8400-e29b-41d4-a716-446655441008', 'Ben & Jerry''s', 'https://www.benjerry.com', 'USA', true),
('550e8400-e29b-41d4-a716-446655441009', 'KIND', 'https://www.kindsnacks.com', 'USA', true),
('550e8400-e29b-41d4-a716-446655441010', 'Generic/Store Brand', NULL, 'Various', true);

-- ==================================================
-- RECIPE CATEGORIES SEED DATA
-- ==================================================

INSERT INTO recipe_categories (id, name, description, color_hex) VALUES
('550e8400-e29b-41d4-a716-446655442001', 'Breakfast', 'Morning meals and brunch items', '#FFD54F'),
('550e8400-e29b-41d4-a716-446655442002', 'Lunch', 'Midday meals and light dishes', '#81C784'),
('550e8400-e29b-41d4-a716-446655442003', 'Dinner', 'Evening meals and hearty dishes', '#E57373'),
('550e8400-e29b-41d4-a716-446655442004', 'Snacks', 'Quick bites and appetizers', '#FFB74D'),
('550e8400-e29b-41d4-a716-446655442005', 'Desserts', 'Sweet treats and confections', '#F06292'),
('550e8400-e29b-41d4-a716-446655442006', 'Beverages', 'Smoothies, juices, and drinks', '#64B5F6'),
('550e8400-e29b-41d4-a716-446655442007', 'Vegetarian', 'Plant-based meals', '#4CAF50'),
('550e8400-e29b-41d4-a716-446655442008', 'Vegan', 'Completely plant-based meals', '#66BB6A'),
('550e8400-e29b-41d4-a716-446655442009', 'Low Carb', 'Ketogenic and low-carbohydrate meals', '#9575CD'),
('550e8400-e29b-41d4-a716-446655442010', 'High Protein', 'Protein-rich meals for fitness', '#FF8A65');

-- ==================================================
-- SAMPLE FOODS SEED DATA
-- ==================================================

-- Fruits
INSERT INTO foods (id, name, category_id, serving_size, serving_unit, calories, protein_g, carbohydrates_g, dietary_fiber_g, sugars_g, total_fat_g, vitamin_c_mg, potassium_mg, data_source, verified) VALUES

-- Citrus Fruits
('550e8400-e29b-41d4-a716-446655443001', 'Orange, medium', '550e8400-e29b-41d4-a716-446655440011', 154, 'g', 62, 1.2, 15.4, 3.1, 12.2, 0.2, 70, 237, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443002', 'Lemon, medium', '550e8400-e29b-41d4-a716-446655440011', 60, 'g', 17, 0.6, 5.4, 1.6, 1.5, 0.2, 31, 80, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443003', 'Grapefruit, half', '550e8400-e29b-41d4-a716-446655440011', 123, 'g', 52, 0.9, 13.2, 2.0, 8.5, 0.2, 38, 166, 'USDA', true),

-- Berries
('550e8400-e29b-41d4-a716-446655443004', 'Strawberries', '550e8400-e29b-41d4-a716-446655440012', 100, 'g', 32, 0.7, 7.7, 2.0, 4.9, 0.3, 59, 153, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443005', 'Blueberries', '550e8400-e29b-41d4-a716-446655440012', 100, 'g', 57, 0.7, 14.5, 2.4, 10.0, 0.3, 9.7, 77, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443006', 'Raspberries', '550e8400-e29b-41d4-a716-446655440012', 100, 'g', 52, 1.2, 11.9, 6.5, 4.4, 0.7, 26, 151, 'USDA', true),

-- Tropical Fruits
('550e8400-e29b-41d4-a716-446655443007', 'Banana, medium', '550e8400-e29b-41d4-a716-446655440013', 118, 'g', 105, 1.3, 27.0, 3.1, 14.4, 0.4, 10, 422, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443008', 'Mango, sliced', '550e8400-e29b-41d4-a716-446655440013', 100, 'g', 60, 0.8, 15.0, 1.6, 13.7, 0.4, 36, 168, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443009', 'Pineapple, chunks', '550e8400-e29b-41d4-a716-446655440013', 100, 'g', 50, 0.5, 13.1, 1.4, 9.9, 0.1, 48, 109, 'USDA', true);

-- Vegetables
INSERT INTO foods (id, name, category_id, serving_size, serving_unit, calories, protein_g, carbohydrates_g, dietary_fiber_g, sugars_g, total_fat_g, vitamin_a_iu, vitamin_c_mg, calcium_mg, iron_mg, potassium_mg, data_source, verified) VALUES

-- Leafy Greens
('550e8400-e29b-41d4-a716-446655443010', 'Spinach, fresh', '550e8400-e29b-41d4-a716-446655440014', 100, 'g', 23, 2.9, 3.6, 2.2, 0.4, 0.4, 469, 28, 99, 2.7, 558, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443011', 'Kale, fresh', '550e8400-e29b-41d4-a716-446655440014', 100, 'g', 35, 2.9, 4.4, 4.1, 0.8, 1.5, 681, 93, 254, 1.6, 348, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443012', 'Lettuce, romaine', '550e8400-e29b-41d4-a716-446655440014', 100, 'g', 17, 1.2, 3.3, 2.1, 1.2, 0.3, 436, 4, 33, 0.9, 194, 'USDA', true),

-- Root Vegetables
('550e8400-e29b-41d4-a716-446655443013', 'Carrot, medium', '550e8400-e29b-41d4-a716-446655440015', 61, 'g', 25, 0.5, 5.8, 1.7, 2.9, 0.1, 509, 4, 20, 0.2, 195, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443014', 'Sweet Potato, baked', '550e8400-e29b-41d4-a716-446655440015', 100, 'g', 90, 2.0, 20.7, 3.3, 6.8, 0.1, 961, 19, 38, 0.7, 475, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443015', 'Onion, medium', '550e8400-e29b-41d4-a716-446655440015', 110, 'g', 44, 1.2, 10.3, 1.9, 4.7, 0.1, 0, 8, 26, 0.2, 161, 'USDA', true);

-- Proteins
INSERT INTO foods (id, name, category_id, brand_id, serving_size, serving_unit, calories, protein_g, carbohydrates_g, total_fat_g, saturated_fat_g, cholesterol_mg, sodium_mg, data_source, verified) VALUES

-- Plant Proteins
('550e8400-e29b-41d4-a716-446655443020', 'Black Beans, cooked', '550e8400-e29b-41d4-a716-446655440019', NULL, 100, 'g', 132, 8.9, 23.7, 8.7, 0.5, 0, 2, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443021', 'Quinoa, cooked', '550e8400-e29b-41d4-a716-446655440003', NULL, 100, 'g', 120, 4.4, 21.8, 2.8, 1.9, 0, 13, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443022', 'Tofu, extra firm', '550e8400-e29b-41d4-a716-446655440019', NULL, 100, 'g', 144, 17.3, 2.8, 8.7, 1.3, 0, 14, 'USDA', true);

-- Grains & Cereals
INSERT INTO foods (id, name, category_id, brand_id, serving_size, serving_unit, calories, protein_g, carbohydrates_g, dietary_fiber_g, sugars_g, total_fat_g, sodium_mg, data_source, verified) VALUES

('550e8400-e29b-41d4-a716-446655443023', 'Brown Rice, cooked', '550e8400-e29b-41d4-a716-446655440003', NULL, 100, 'g', 123, 2.6, 25.0, 1.8, 0.4, 1.0, 1, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443024', 'Whole Wheat Bread', '550e8400-e29b-41d4-a716-446655440003', NULL, 28, 'g (1 slice)', 81, 3.6, 13.8, 1.9, 1.4, 1.1, 144, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443025', 'Oatmeal, cooked', '550e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655441003', 100, 'g', 68, 2.4, 12.0, 1.7, 0.3, 1.4, 4, 'USDA', true);

-- Dairy
INSERT INTO foods (id, name, category_id, brand_id, serving_size, serving_unit, calories, protein_g, carbohydrates_g, sugars_g, total_fat_g, saturated_fat_g, cholesterol_mg, calcium_mg, sodium_mg, data_source, verified) VALUES

('550e8400-e29b-41d4-a716-446655443026', 'Milk, 2% fat', '550e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655441001', 240, 'ml (1 cup)', 122, 8.1, 11.4, 11.4, 4.8, 3.1, 20, 293, 115, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443027', 'Greek Yogurt, plain', '550e8400-e29b-41d4-a716-446655440005', NULL, 100, 'g', 59, 10.2, 3.6, 3.6, 0.4, 0.3, 5, 110, 36, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443028', 'Cheese, Cheddar', '550e8400-e29b-41d4-a716-446655440005', NULL, 28, 'g (1 oz)', 113, 7.0, 0.4, 0.4, 9.3, 5.9, 29, 204, 174, 'USDA', true);

-- Nuts & Seeds
INSERT INTO foods (id, name, category_id, serving_size, serving_unit, calories, protein_g, carbohydrates_g, dietary_fiber_g, total_fat_g, saturated_fat_g, vitamin_e_mg, magnesium_mg, data_source, verified) VALUES

('550e8400-e29b-41d4-a716-446655443029', 'Almonds', '550e8400-e29b-41d4-a716-446655440006', 28, 'g (1 oz)', 164, 6.0, 6.1, 3.5, 14.2, 1.1, 7.3, 76, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443030', 'Walnuts', '550e8400-e29b-41d4-a716-446655440006', 28, 'g (1 oz)', 185, 4.3, 3.9, 1.9, 18.5, 1.7, 0.2, 45, 'USDA', true),
('550e8400-e29b-41d4-a716-446655443031', 'Chia Seeds', '550e8400-e29b-41d4-a716-446655440006', 28, 'g (2 tbsp)', 138, 4.7, 11.9, 9.8, 8.7, 0.9, 0.5, 95, 'USDA', true);

-- ==================================================
-- SAMPLE RECIPE SEED DATA
-- ==================================================

-- Insert a sample breakfast smoothie recipe
INSERT INTO recipes (id, name, description, category_id, prep_time_minutes, cook_time_minutes, servings, difficulty_level, instructions, tags, dietary_tags, is_public) VALUES
('550e8400-e29b-41d4-a716-446655444001', 'Green Power Smoothie', 'A nutrient-packed smoothie with spinach, banana, and berries', '550e8400-e29b-41d4-a716-446655442001', 5, 0, 2, 'easy', 
'[
  {"step": 1, "text": "Add spinach to blender first", "time_minutes": 1},
  {"step": 2, "text": "Add frozen banana and berries", "time_minutes": 1},
  {"step": 3, "text": "Pour in almond milk and protein powder", "time_minutes": 1},
  {"step": 4, "text": "Blend on high for 60 seconds until smooth", "time_minutes": 2},
  {"step": 5, "text": "Pour into glasses and serve immediately", "time_minutes": 1}
]',
'["smoothie", "breakfast", "healthy", "green"]',
'["vegan", "dairy-free", "gluten-free"]',
true);

-- Insert recipe ingredients for the smoothie
INSERT INTO recipe_ingredients (recipe_id, food_id, quantity, unit, sort_order) VALUES
('550e8400-e29b-41d4-a716-446655444001', '550e8400-e29b-41d4-a716-446655443010', 50, 'g', 1), -- Spinach
('550e8400-e29b-41d4-a716-446655444001', '550e8400-e29b-41d4-a716-446655443007', 1, 'medium', 2), -- Banana
('550e8400-e29b-41d4-a716-446655444001', '550e8400-e29b-41d4-a716-446655443005', 75, 'g', 3), -- Blueberries
('550e8400-e29b-41d4-a716-446655444001', '550e8400-e29b-41d4-a716-446655443004', 50, 'g', 4); -- Strawberries

-- Insert Plant Chicken Al Pastor Tacos recipe
INSERT INTO recipes (id, name, description, category_id, prep_time_minutes, cook_time_minutes, servings, difficulty_level, instructions, tags, dietary_tags, is_public) VALUES
('550e8400-e29b-41d4-a716-446655444002', 'Plant Chicken Al Pastor Tacos', 'Plant-based twist on a Mexican classic with smoky-sweet chipotle and pineapple marinade, juicy plant chicken pieces, and zesty homemade pineapple salsa', '550e8400-e29b-41d4-a716-446655442003', 15, 10, 3, 'medium',
'[
  {"step": 1, "text": "Make the marinade: combine olive oil, pineapple juice, vinegar, chipotle peppers, adobo sauce, brown sugar, chili powder, smoked paprika, cumin, oregano, cinnamon, garlic, salt and pepper in a jar or small blender. Blend until smooth.", "time_minutes": 5},
  {"step": 2, "text": "Cook the plant chicken: Heat olive oil in a pan over medium heat. Add the plant chicken pieces, onion, and pineapple. Stir to coat everything in the oil and cook for 5-6 minutes, stirring frequently.", "time_minutes": 6},
  {"step": 3, "text": "Add marinade: When the plant chicken is almost done, lower the heat and add the marinade. Stir and cook for an additional 2 minutes.", "time_minutes": 2},
  {"step": 4, "text": "Make the pineapple salsa: In a small bowl, combine the chopped pineapple, jalapeño, red onion, cilantro, and lime juice. Season with salt to taste.", "time_minutes": 3},
  {"step": 5, "text": "Warm tortillas: Heat tortillas in a dry skillet or on a griddle until soft and slightly browned.", "time_minutes": 2},
  {"step": 6, "text": "Assemble tacos: Spread vegan sour cream on tortillas (optional). Add generous portion of plant chicken mixture. Top with pineapple salsa and serve with lime wedges.", "time_minutes": 3}
]',
'["tacos", "mexican", "plant-based", "al-pastor", "pineapple", "dinner"]',
'["vegan", "dairy-free", "plant-based"]',
true);

-- Insert recipe ingredients for the Plant Chicken Al Pastor Tacos
-- Note: Using existing foods from seed data where possible, some ingredients may need to be added separately
INSERT INTO recipe_ingredients (recipe_id, food_id, quantity, unit, sort_order, ingredient_group, notes) VALUES
-- Marinade ingredients
('550e8400-e29b-41d4-a716-446655444002', NULL, 2, 'tbsp', 1, 'Marinade', 'Olive oil - needs food entry'),
('550e8400-e29b-41d4-a716-446655444002', '550e8400-e29b-41d4-a716-446655443009', 60, 'ml', 2, 'Marinade', 'Pineapple juice (from pineapple)'),
('550e8400-e29b-41d4-a716-446655444002', NULL, 2, 'tbsp', 3, 'Marinade', 'Apple cider vinegar - needs food entry'),
('550e8400-e29b-41d4-a716-446655444002', NULL, 2, 'small', 4, 'Marinade', 'Chipotle peppers - needs food entry'),
('550e8400-e29b-41d4-a716-446655444002', NULL, 1, 'tbsp', 5, 'Marinade', 'Adobo sauce - needs food entry'),
('550e8400-e29b-41d4-a716-446655444002', NULL, 0.5, 'tbsp', 6, 'Marinade', 'Brown sugar - needs food entry'),
('550e8400-e29b-41d4-a716-446655444002', NULL, 0.5, 'tbsp', 7, 'Marinade', 'Chili powder - needs food entry'),
('550e8400-e29b-41d4-a716-446655444002', NULL, 1, 'tsp', 8, 'Marinade', 'Smoked paprika - needs food entry'),
('550e8400-e29b-41d4-a716-446655444002', NULL, 1, 'tsp', 9, 'Marinade', 'Ground cumin - needs food entry'),
('550e8400-e29b-41d4-a716-446655444002', NULL, 0.5, 'tsp', 10, 'Marinade', 'Dried oregano - needs food entry'),
('550e8400-e29b-41d4-a716-446655444002', NULL, 0.25, 'tsp', 11, 'Marinade', 'Ground cinnamon - needs food entry'),
('550e8400-e29b-41d4-a716-446655444002', NULL, 1, 'clove', 12, 'Marinade', 'Garlic - needs food entry'),
-- Main ingredients
('550e8400-e29b-41d4-a716-446655444002', '550e8400-e29b-41d4-a716-446655443022', 200, 'g', 13, 'Protein', 'Plant chicken pieces (using tofu as substitute)'),
('550e8400-e29b-41d4-a716-446655444002', '550e8400-e29b-41d4-a716-446655443015', 25, 'g', 14, 'Vegetables', 'White onion, sliced thinly'),
('550e8400-e29b-41d4-a716-446655444002', '550e8400-e29b-41d4-a716-446655443009', 50, 'g', 15, 'Fruits', 'Fresh pineapple slices'),
-- Pineapple salsa ingredients
('550e8400-e29b-41d4-a716-446655444002', '550e8400-e29b-41d4-a716-446655443009', 75, 'g', 16, 'Salsa', 'Fresh pineapple, chopped'),
('550e8400-e29b-41d4-a716-446655444002', NULL, 1, 'small', 17, 'Salsa', 'Jalapeño, seeds removed and chopped - needs food entry'),
('550e8400-e29b-41d4-a716-446655444002', '550e8400-e29b-41d4-a716-446655443015', 25, 'g', 18, 'Salsa', 'Red onion, chopped'),
('550e8400-e29b-41d4-a716-446655444002', NULL, 2, 'tbsp', 19, 'Salsa', 'Fresh cilantro, chopped - needs food entry'),
('550e8400-e29b-41d4-a716-446655444002', '550e8400-e29b-41d4-a716-446655443002', 0.5, 'medium', 20, 'Salsa', 'Lime juice'),
-- Taco assembly
('550e8400-e29b-41d4-a716-446655444002', NULL, 6, 'small', 21, 'Tacos', 'Corn or flour tortillas - needs food entry'),
('550e8400-e29b-41d4-a716-446655444002', NULL, 2, 'tbsp', 22, 'Tacos', 'Vegan sour cream (optional) - needs food entry');

-- ==================================================
-- COMPLETION MESSAGE
-- ==================================================

DO $$
BEGIN
    RAISE NOTICE 'Supabase Nutrition Database Seed Data inserted successfully!';
    RAISE NOTICE 'Inserted: % food categories', (SELECT COUNT(*) FROM food_categories);
    RAISE NOTICE 'Inserted: % food brands', (SELECT COUNT(*) FROM food_brands);
    RAISE NOTICE 'Inserted: % recipe categories', (SELECT COUNT(*) FROM recipe_categories);
    RAISE NOTICE 'Inserted: % foods', (SELECT COUNT(*) FROM foods);
    RAISE NOTICE 'Inserted: % recipes with ingredients', (SELECT COUNT(*) FROM recipes);
    RAISE NOTICE 'Database is ready for nutrition tracking!';
END $$;
