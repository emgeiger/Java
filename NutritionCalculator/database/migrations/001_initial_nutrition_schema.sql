-- ==================================================
-- SUPABASE NUTRITION DATABASE SCHEMA
-- ==================================================
-- This schema supports comprehensive nutrition tracking
-- including foods, recipes, user intake, and analytics
-- 
-- Created: 2025-07-18
-- Version: 1.0
-- ==================================================

-- Enable necessary extensions
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ==================================================
-- USER PROFILES AND AUTHENTICATION
-- ==================================================

-- User profiles table (extends Supabase auth.users)
CREATE TABLE IF NOT EXISTS user_profiles (
    id UUID REFERENCES auth.users(id) ON DELETE CASCADE PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    email VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    age INTEGER CHECK (age >= 0 AND age <= 150),
    gender VARCHAR(20) CHECK (gender IN ('male', 'female', 'other', 'prefer_not_to_say')),
    height_cm DECIMAL(5,2) CHECK (height_cm > 0),
    weight_kg DECIMAL(5,2) CHECK (weight_kg > 0),
    activity_level VARCHAR(20) CHECK (activity_level IN ('sedentary', 'lightly_active', 'moderately_active', 'very_active', 'extremely_active')),
    dietary_restrictions TEXT[], -- Array of dietary restrictions
    health_goals TEXT[], -- Array of health goals
    daily_calorie_target INTEGER CHECK (daily_calorie_target > 0),
    daily_protein_target DECIMAL(6,2) CHECK (daily_protein_target >= 0),
    daily_carb_target DECIMAL(6,2) CHECK (daily_carb_target >= 0),
    daily_fat_target DECIMAL(6,2) CHECK (daily_fat_target >= 0),
    daily_fiber_target DECIMAL(6,2) CHECK (daily_fiber_target >= 0),
    avatar_url TEXT,
    timezone VARCHAR(50) DEFAULT 'UTC',
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- ==================================================
-- FOOD DATABASE
-- ==================================================

-- Food categories
CREATE TABLE IF NOT EXISTS food_categories (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    icon_url TEXT,
    color_hex VARCHAR(7), -- Hex color code for UI
    parent_category_id UUID REFERENCES food_categories(id),
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- Food brands
CREATE TABLE IF NOT EXISTS food_brands (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    website_url TEXT,
    logo_url TEXT,
    country VARCHAR(100),
    verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- Main foods table
CREATE TABLE IF NOT EXISTS foods (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    brand_id UUID REFERENCES food_brands(id),
    category_id UUID REFERENCES food_categories(id),
    barcode VARCHAR(50) UNIQUE, -- UPC/EAN barcode
    
    -- Serving information
    serving_size DECIMAL(8,2) NOT NULL CHECK (serving_size > 0),
    serving_unit VARCHAR(20) NOT NULL, -- g, ml, cup, piece, etc.
    servings_per_container DECIMAL(6,2),
    
    -- Basic nutrition per serving
    calories DECIMAL(8,2) NOT NULL CHECK (calories >= 0),
    protein_g DECIMAL(6,2) DEFAULT 0 CHECK (protein_g >= 0),
    carbohydrates_g DECIMAL(6,2) DEFAULT 0 CHECK (carbohydrates_g >= 0),
    dietary_fiber_g DECIMAL(6,2) DEFAULT 0 CHECK (dietary_fiber_g >= 0),
    sugars_g DECIMAL(6,2) DEFAULT 0 CHECK (sugars_g >= 0),
    added_sugars_g DECIMAL(6,2) DEFAULT 0 CHECK (added_sugars_g >= 0),
    total_fat_g DECIMAL(6,2) DEFAULT 0 CHECK (total_fat_g >= 0),
    saturated_fat_g DECIMAL(6,2) DEFAULT 0 CHECK (saturated_fat_g >= 0),
    trans_fat_g DECIMAL(6,2) DEFAULT 0 CHECK (trans_fat_g >= 0),
    cholesterol_mg DECIMAL(8,2) DEFAULT 0 CHECK (cholesterol_mg >= 0),
    sodium_mg DECIMAL(8,2) DEFAULT 0 CHECK (sodium_mg >= 0),
    
    -- Vitamins (% Daily Value or mg/mcg)
    vitamin_a_iu DECIMAL(8,2) DEFAULT 0,
    vitamin_c_mg DECIMAL(6,2) DEFAULT 0,
    vitamin_d_iu DECIMAL(8,2) DEFAULT 0,
    vitamin_e_mg DECIMAL(6,2) DEFAULT 0,
    vitamin_k_mcg DECIMAL(6,2) DEFAULT 0,
    thiamin_mg DECIMAL(6,3) DEFAULT 0,
    riboflavin_mg DECIMAL(6,3) DEFAULT 0,
    niacin_mg DECIMAL(6,2) DEFAULT 0,
    vitamin_b6_mg DECIMAL(6,3) DEFAULT 0,
    folate_mcg DECIMAL(6,2) DEFAULT 0,
    vitamin_b12_mcg DECIMAL(6,3) DEFAULT 0,
    biotin_mcg DECIMAL(6,2) DEFAULT 0,
    pantothenic_acid_mg DECIMAL(6,3) DEFAULT 0,
    
    -- Minerals
    calcium_mg DECIMAL(8,2) DEFAULT 0,
    iron_mg DECIMAL(6,2) DEFAULT 0,
    magnesium_mg DECIMAL(6,2) DEFAULT 0,
    phosphorus_mg DECIMAL(8,2) DEFAULT 0,
    potassium_mg DECIMAL(8,2) DEFAULT 0,
    zinc_mg DECIMAL(6,2) DEFAULT 0,
    copper_mg DECIMAL(6,3) DEFAULT 0,
    manganese_mg DECIMAL(6,3) DEFAULT 0,
    selenium_mcg DECIMAL(6,2) DEFAULT 0,
    
    -- Additional properties
    allergens TEXT[], -- Array of allergen information
    ingredients TEXT,
    preparation_instructions TEXT,
    storage_instructions TEXT,
    
    -- Data source and verification
    data_source VARCHAR(100), -- 'USDA', 'manual_entry', 'barcode_scan', etc.
    verified BOOLEAN DEFAULT FALSE,
    verification_date TIMESTAMPTZ,
    
    -- Media
    image_url TEXT,
    thumbnail_url TEXT,
    
    -- Metadata
    created_by UUID REFERENCES auth.users(id),
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),
    
    -- Full-text search
    search_vector tsvector GENERATED ALWAYS AS (
        setweight(to_tsvector('english', name), 'A') ||
        setweight(to_tsvector('english', COALESCE(ingredients, '')), 'B')
    ) STORED
);

-- ==================================================
-- RECIPES
-- ==================================================

-- Recipe categories
CREATE TABLE IF NOT EXISTS recipe_categories (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    icon_url TEXT,
    color_hex VARCHAR(7),
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- Main recipes table
CREATE TABLE IF NOT EXISTS recipes (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category_id UUID REFERENCES recipe_categories(id),
    
    -- Recipe details
    prep_time_minutes INTEGER CHECK (prep_time_minutes >= 0),
    cook_time_minutes INTEGER CHECK (cook_time_minutes >= 0),
    total_time_minutes INTEGER GENERATED ALWAYS AS (prep_time_minutes + cook_time_minutes) STORED,
    servings INTEGER NOT NULL CHECK (servings > 0),
    difficulty_level VARCHAR(20) CHECK (difficulty_level IN ('easy', 'medium', 'hard')),
    
    -- Instructions
    instructions JSONB, -- Array of step objects with text, image, timer, etc.
    
    -- Nutrition (calculated from ingredients)
    calories_per_serving DECIMAL(8,2),
    protein_per_serving DECIMAL(6,2),
    carbs_per_serving DECIMAL(6,2),
    fat_per_serving DECIMAL(6,2),
    fiber_per_serving DECIMAL(6,2),
    
    -- Metadata
    created_by UUID REFERENCES auth.users(id),
    is_public BOOLEAN DEFAULT TRUE,
    rating DECIMAL(3,2) CHECK (rating >= 0 AND rating <= 5),
    rating_count INTEGER DEFAULT 0,
    
    -- Media
    image_url TEXT,
    video_url TEXT,
    
    -- Tags and properties
    tags TEXT[],
    dietary_tags TEXT[], -- vegan, vegetarian, gluten-free, etc.
    
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),
    
    -- Full-text search
    search_vector tsvector GENERATED ALWAYS AS (
        setweight(to_tsvector('english', name), 'A') ||
        setweight(to_tsvector('english', COALESCE(description, '')), 'B') ||
        setweight(to_tsvector('english', array_to_string(tags, ' ')), 'C')
    ) STORED
);

-- Recipe ingredients (many-to-many with foods)
CREATE TABLE IF NOT EXISTS recipe_ingredients (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    recipe_id UUID NOT NULL REFERENCES recipes(id) ON DELETE CASCADE,
    food_id UUID NOT NULL REFERENCES foods(id),
    
    -- Ingredient details
    quantity DECIMAL(8,2) NOT NULL CHECK (quantity > 0),
    unit VARCHAR(20) NOT NULL,
    preparation_note TEXT, -- "diced", "chopped", "cooked", etc.
    is_optional BOOLEAN DEFAULT FALSE,
    
    -- Order in recipe
    sort_order INTEGER DEFAULT 0,
    ingredient_group VARCHAR(100), -- "For the sauce", "For the base", etc.
    
    created_at TIMESTAMPTZ DEFAULT NOW(),
    
    UNIQUE(recipe_id, food_id, preparation_note)
);

-- ==================================================
-- USER FOOD INTAKE TRACKING
-- ==================================================

-- Daily food logs
CREATE TABLE IF NOT EXISTS food_logs (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
    food_id UUID REFERENCES foods(id),
    recipe_id UUID REFERENCES recipes(id),
    
    -- Either food_id or recipe_id must be present
    CONSTRAINT check_food_or_recipe CHECK (
        (food_id IS NOT NULL AND recipe_id IS NULL) OR 
        (food_id IS NULL AND recipe_id IS NOT NULL)
    ),
    
    -- Consumption details
    quantity DECIMAL(8,2) NOT NULL CHECK (quantity > 0),
    unit VARCHAR(20) NOT NULL,
    meal_type VARCHAR(20) CHECK (meal_type IN ('breakfast', 'lunch', 'dinner', 'snack', 'other')),
    consumed_at TIMESTAMPTZ NOT NULL,
    
    -- Calculated nutrition (stored for performance)
    calories DECIMAL(8,2) NOT NULL,
    protein_g DECIMAL(6,2) DEFAULT 0,
    carbohydrates_g DECIMAL(6,2) DEFAULT 0,
    fat_g DECIMAL(6,2) DEFAULT 0,
    fiber_g DECIMAL(6,2) DEFAULT 0,
    sodium_mg DECIMAL(8,2) DEFAULT 0,
    
    -- Optional notes
    notes TEXT,
    
    -- Location/context
    location VARCHAR(255),
    mood_before VARCHAR(50),
    mood_after VARCHAR(50),
    
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- ==================================================
-- NUTRITION GOALS AND TRACKING
-- ==================================================

-- Daily nutrition summaries (materialized for performance)
CREATE TABLE IF NOT EXISTS daily_nutrition_summaries (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
    date DATE NOT NULL,
    
    -- Totals for the day
    total_calories DECIMAL(8,2) DEFAULT 0,
    total_protein_g DECIMAL(6,2) DEFAULT 0,
    total_carbohydrates_g DECIMAL(6,2) DEFAULT 0,
    total_fat_g DECIMAL(6,2) DEFAULT 0,
    total_fiber_g DECIMAL(6,2) DEFAULT 0,
    total_sodium_mg DECIMAL(8,2) DEFAULT 0,
    
    -- Goals vs actual (percentages)
    calorie_goal_percentage DECIMAL(5,2),
    protein_goal_percentage DECIMAL(5,2),
    carb_goal_percentage DECIMAL(5,2),
    fat_goal_percentage DECIMAL(5,2),
    fiber_goal_percentage DECIMAL(5,2),
    
    -- Meal breakdown
    breakfast_calories DECIMAL(8,2) DEFAULT 0,
    lunch_calories DECIMAL(8,2) DEFAULT 0,
    dinner_calories DECIMAL(8,2) DEFAULT 0,
    snack_calories DECIMAL(8,2) DEFAULT 0,
    
    -- Water intake (in ml)
    water_intake_ml INTEGER DEFAULT 0,
    water_goal_ml INTEGER DEFAULT 2000,
    
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),
    
    UNIQUE(user_id, date)
);

-- ==================================================
-- INDEXES FOR PERFORMANCE
-- ==================================================

-- User profiles indexes
CREATE INDEX IF NOT EXISTS idx_user_profiles_username ON user_profiles(username);
CREATE INDEX IF NOT EXISTS idx_user_profiles_email ON user_profiles(email);

-- Foods indexes
CREATE INDEX IF NOT EXISTS idx_foods_name ON foods(name);
CREATE INDEX IF NOT EXISTS idx_foods_barcode ON foods(barcode);
CREATE INDEX IF NOT EXISTS idx_foods_category ON foods(category_id);
CREATE INDEX IF NOT EXISTS idx_foods_brand ON foods(brand_id);
CREATE INDEX IF NOT EXISTS idx_foods_search ON foods USING gin(search_vector);
CREATE INDEX IF NOT EXISTS idx_foods_verified ON foods(verified);

-- Recipes indexes
CREATE INDEX IF NOT EXISTS idx_recipes_name ON recipes(name);
CREATE INDEX IF NOT EXISTS idx_recipes_category ON recipes(category_id);
CREATE INDEX IF NOT EXISTS idx_recipes_created_by ON recipes(created_by);
CREATE INDEX IF NOT EXISTS idx_recipes_public ON recipes(is_public);
CREATE INDEX IF NOT EXISTS idx_recipes_search ON recipes USING gin(search_vector);
CREATE INDEX IF NOT EXISTS idx_recipes_rating ON recipes(rating);

-- Recipe ingredients indexes
CREATE INDEX IF NOT EXISTS idx_recipe_ingredients_recipe ON recipe_ingredients(recipe_id);
CREATE INDEX IF NOT EXISTS idx_recipe_ingredients_food ON recipe_ingredients(food_id);

-- Food logs indexes
CREATE INDEX IF NOT EXISTS idx_food_logs_user ON food_logs(user_id);
CREATE INDEX IF NOT EXISTS idx_food_logs_date ON food_logs(consumed_at);
CREATE INDEX IF NOT EXISTS idx_food_logs_meal_type ON food_logs(meal_type);
CREATE INDEX IF NOT EXISTS idx_food_logs_user_date ON food_logs(user_id, consumed_at);

-- Daily summaries indexes
CREATE INDEX IF NOT EXISTS idx_daily_summaries_user ON daily_nutrition_summaries(user_id);
CREATE INDEX IF NOT EXISTS idx_daily_summaries_date ON daily_nutrition_summaries(date);
CREATE INDEX IF NOT EXISTS idx_daily_summaries_user_date ON daily_nutrition_summaries(user_id, date);

-- ==================================================
-- ROW LEVEL SECURITY (RLS) POLICIES
-- ==================================================

-- Enable RLS on all tables
ALTER TABLE user_profiles ENABLE ROW LEVEL SECURITY;
ALTER TABLE food_logs ENABLE ROW LEVEL SECURITY;
ALTER TABLE daily_nutrition_summaries ENABLE ROW LEVEL SECURITY;
ALTER TABLE recipes ENABLE ROW LEVEL SECURITY;

-- User profiles policies
CREATE POLICY "Users can view own profile" ON user_profiles
    FOR SELECT USING (auth.uid() = id);

CREATE POLICY "Users can update own profile" ON user_profiles
    FOR UPDATE USING (auth.uid() = id);

CREATE POLICY "Users can insert own profile" ON user_profiles
    FOR INSERT WITH CHECK (auth.uid() = id);

-- Food logs policies
CREATE POLICY "Users can view own food logs" ON food_logs
    FOR SELECT USING (auth.uid() = user_id);

CREATE POLICY "Users can insert own food logs" ON food_logs
    FOR INSERT WITH CHECK (auth.uid() = user_id);

CREATE POLICY "Users can update own food logs" ON food_logs
    FOR UPDATE USING (auth.uid() = user_id);

CREATE POLICY "Users can delete own food logs" ON food_logs
    FOR DELETE USING (auth.uid() = user_id);

-- Daily summaries policies
CREATE POLICY "Users can view own daily summaries" ON daily_nutrition_summaries
    FOR SELECT USING (auth.uid() = user_id);

CREATE POLICY "Users can insert own daily summaries" ON daily_nutrition_summaries
    FOR INSERT WITH CHECK (auth.uid() = user_id);

CREATE POLICY "Users can update own daily summaries" ON daily_nutrition_summaries
    FOR UPDATE USING (auth.uid() = user_id);

-- Recipe policies
CREATE POLICY "Everyone can view public recipes" ON recipes
    FOR SELECT USING (is_public = true);

CREATE POLICY "Users can view own recipes" ON recipes
    FOR SELECT USING (auth.uid() = created_by);

CREATE POLICY "Users can insert own recipes" ON recipes
    FOR INSERT WITH CHECK (auth.uid() = created_by);

CREATE POLICY "Users can update own recipes" ON recipes
    FOR UPDATE USING (auth.uid() = created_by);

CREATE POLICY "Users can delete own recipes" ON recipes
    FOR DELETE USING (auth.uid() = created_by);

-- Foods table is publicly readable (no RLS needed for basic food data)
-- Food categories and brands are publicly readable

-- ==================================================
-- TRIGGERS AND FUNCTIONS
-- ==================================================

-- Function to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Apply updated_at triggers
CREATE TRIGGER update_user_profiles_updated_at BEFORE UPDATE ON user_profiles
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_foods_updated_at BEFORE UPDATE ON foods
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_recipes_updated_at BEFORE UPDATE ON recipes
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_food_logs_updated_at BEFORE UPDATE ON food_logs
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_daily_summaries_updated_at BEFORE UPDATE ON daily_nutrition_summaries
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Function to automatically create user profile on signup
CREATE OR REPLACE FUNCTION create_user_profile()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO user_profiles (id, email, full_name)
    VALUES (NEW.id, NEW.email, NEW.raw_user_meta_data->>'full_name');
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Trigger for automatic profile creation
CREATE TRIGGER create_user_profile_trigger
    AFTER INSERT ON auth.users
    FOR EACH ROW EXECUTE FUNCTION create_user_profile();

-- ==================================================
-- VIEWS FOR COMMON QUERIES
-- ==================================================

-- View for recipe nutrition calculation
CREATE OR REPLACE VIEW recipe_nutrition AS
SELECT 
    r.id as recipe_id,
    r.name as recipe_name,
    r.servings,
    SUM(f.calories * ri.quantity / f.serving_size) / r.servings as calories_per_serving,
    SUM(f.protein_g * ri.quantity / f.serving_size) / r.servings as protein_per_serving,
    SUM(f.carbohydrates_g * ri.quantity / f.serving_size) / r.servings as carbs_per_serving,
    SUM(f.total_fat_g * ri.quantity / f.serving_size) / r.servings as fat_per_serving,
    SUM(f.dietary_fiber_g * ri.quantity / f.serving_size) / r.servings as fiber_per_serving
FROM recipes r
JOIN recipe_ingredients ri ON r.id = ri.recipe_id
JOIN foods f ON ri.food_id = f.id
GROUP BY r.id, r.name, r.servings;

-- View for user daily intake summary
CREATE OR REPLACE VIEW user_daily_intake AS
SELECT 
    fl.user_id,
    DATE(fl.consumed_at) as log_date,
    fl.meal_type,
    SUM(fl.calories) as total_calories,
    SUM(fl.protein_g) as total_protein,
    SUM(fl.carbohydrates_g) as total_carbs,
    SUM(fl.fat_g) as total_fat,
    SUM(fl.fiber_g) as total_fiber,
    COUNT(*) as food_items_count
FROM food_logs fl
GROUP BY fl.user_id, DATE(fl.consumed_at), fl.meal_type
ORDER BY fl.user_id, log_date DESC, fl.meal_type;

-- ==================================================
-- COMMENTS
-- ==================================================

COMMENT ON TABLE user_profiles IS 'Extended user profile information for nutrition tracking';
COMMENT ON TABLE foods IS 'Comprehensive food database with detailed nutrition information';
COMMENT ON TABLE recipes IS 'User-created and public recipes with calculated nutrition';
COMMENT ON TABLE food_logs IS 'Individual food intake entries by users';
COMMENT ON TABLE daily_nutrition_summaries IS 'Daily aggregated nutrition data for performance';

-- ==================================================
-- COMPLETION MESSAGE
-- ==================================================

DO $$
BEGIN
    RAISE NOTICE 'Supabase Nutrition Database Schema created successfully!';
    RAISE NOTICE 'Tables created: user_profiles, foods, recipes, food_logs, daily_nutrition_summaries';
    RAISE NOTICE 'Indexes, triggers, and RLS policies applied';
    RAISE NOTICE 'Ready for nutrition data and CRUD operations';
END $$;
