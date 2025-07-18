# Supabase Database Setup Guide

## Overview

This guide will help you set up a Supabase database for the Nutrition Calculator application with comprehensive nutrition tracking capabilities.

## Prerequisites

- [Supabase Account](https://supabase.com) (free tier available)
- Basic knowledge of SQL and database concepts
- Node.js and npm/yarn for local development

## Quick Start

### 1. Create Supabase Project

1. Go to [supabase.com](https://supabase.com) and sign up/login
2. Click "New Project"
3. Choose your organization
4. Enter project details:
   - **Name**: `nutrition-calculator`
   - **Database Password**: Use a strong password
   - **Region**: Choose closest to your users
5. Click "Create new project"
6. Wait for project to be ready (2-3 minutes)

### 2. Get Project Credentials

1. Go to **Settings** > **API**
2. Copy the following values:
   - **Project URL**: `https://your-project-ref.supabase.co`
   - **Project API Keys** > **anon/public**: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`
   - **Project API Keys** > **service_role**: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`

### 3. Configure Environment

1. Copy `.env.example` to `.env.local`:
   ```bash
   cp .env.example .env.local
   ```

2. Update the following variables in `.env.local`:
   ```env
   VITE_SUPABASE_URL=https://your-project-ref.supabase.co
   VITE_SUPABASE_ANON_KEY=your-anon-key-here
   SUPABASE_SERVICE_ROLE_KEY=your-service-role-key-here
   ```

### 4. Set Up Database Schema

#### Option A: Using Supabase Dashboard (Recommended)

1. Go to **SQL Editor** in your Supabase dashboard
2. Click "New query"
3. Copy and paste the content from `database/migrations/001_initial_nutrition_schema.sql`
4. Click "Run" to execute the schema
5. Repeat for `database/seed/002_nutrition_seed_data.sql` to add sample data

#### Option B: Using Supabase CLI

1. Install Supabase CLI:
   ```bash
   npm install -g supabase
   ```

2. Login to Supabase:
   ```bash
   supabase login
   ```

3. Link your project:
   ```bash
   supabase link --project-ref your-project-ref
   ```

4. Run migrations:
   ```bash
   supabase db push
   ```

### 5. Verify Setup

1. Go to **Table Editor** in Supabase dashboard
2. You should see the following tables:
   - `user_profiles`
   - `foods`
   - `food_categories`
   - `food_brands`
   - `recipes`
   - `recipe_categories`
   - `recipe_ingredients`
   - `food_logs`
   - `daily_nutrition_summaries`

3. Check that sample data is loaded:
   - Browse the `foods` table - should have ~30 sample foods
   - Browse the `food_categories` table - should have main and sub-categories
   - Browse the `recipes` table - should have 2 sample recipes

## Database Schema Overview

### Core Tables

#### `user_profiles`
- Extends Supabase auth with nutrition-specific user data
- Stores dietary preferences, goals, and targets
- Row Level Security (RLS) enabled

#### `foods`
- Comprehensive food database with detailed nutrition info
- Supports barcodes, brands, categories
- Full-text search enabled
- Includes vitamins, minerals, and macronutrients

#### `recipes`
- User-created and public recipes
- JSON-based instructions with steps and timing
- Calculated nutrition from ingredients
- Rating and review system

#### `food_logs`
- Individual food intake entries
- Supports both foods and recipes
- Meal type categorization
- Location and mood tracking

#### `daily_nutrition_summaries`
- Materialized daily aggregations
- Goal tracking and percentages
- Optimized for dashboard queries

### Key Features

#### 🔐 Security
- Row Level Security (RLS) on all user data
- Automatic user profile creation on signup
- Secure API access with JWT tokens

#### 🔍 Search & Discovery
- Full-text search on foods and recipes
- Category-based browsing
- Brand filtering
- Barcode lookup support

#### 📊 Analytics
- Daily, weekly, monthly nutrition summaries
- Goal tracking and progress
- Meal pattern analysis
- Nutrient trend tracking

#### 🎯 Nutrition Goals
- Customizable daily targets
- Macro and micronutrient tracking
- Activity level adjustments
- Dietary restriction support

## API Usage Examples

### Authentication
```javascript
import { createClient } from '@supabase/supabase-js'

const supabase = createClient(
  process.env.VITE_SUPABASE_URL,
  process.env.VITE_SUPABASE_ANON_KEY
)

// Sign up
const { data, error } = await supabase.auth.signUp({
  email: 'user@example.com',
  password: 'password123'
})
```

### Food Search
```javascript
// Search foods by name
const { data: foods } = await supabase
  .from('foods')
  .select('*')
  .textSearch('search_vector', 'banana')
  .limit(10)

// Get foods by category
const { data: fruits } = await supabase
  .from('foods')
  .select('*, food_categories(*)')
  .eq('food_categories.name', 'Fruits')
```

### Log Food Intake
```javascript
// Log a food item
const { data, error } = await supabase
  .from('food_logs')
  .insert({
    food_id: 'food-uuid',
    quantity: 100,
    unit: 'g',
    meal_type: 'breakfast',
    consumed_at: new Date().toISOString(),
    calories: 105,
    protein_g: 1.3,
    carbohydrates_g: 27.0
  })
```

### Get Daily Summary
```javascript
// Get today's nutrition summary
const today = new Date().toISOString().split('T')[0]
const { data: summary } = await supabase
  .from('daily_nutrition_summaries')
  .select('*')
  .eq('user_id', user.id)
  .eq('date', today)
  .single()
```

## Performance Optimization

### Indexes
All critical queries are optimized with appropriate indexes:
- Search queries (GIN indexes on tsvector)
- User-specific queries (user_id indexes)
- Date-based queries (date indexes)
- Foreign key relationships

### Caching Strategy
- Use Supabase's built-in caching for static data
- Implement client-side caching for frequently accessed foods
- Cache daily summaries to reduce computation

### Query Optimization
- Use views for complex joins
- Limit result sets with pagination
- Select only needed columns
- Use RLS for automatic filtering

## Backup and Recovery

### Automated Backups
Supabase provides:
- Daily automated backups (retained for 7 days on free tier)
- Point-in-time recovery
- Manual backup downloads

### Data Export
```bash
# Export specific tables
supabase db dump --table foods > foods_backup.sql
supabase db dump --table recipes > recipes_backup.sql

# Full database export
supabase db dump > full_backup.sql
```

## Monitoring and Maintenance

### Key Metrics to Monitor
- Daily active users
- Food log entries per day
- Search query performance
- API response times
- Storage usage

### Regular Maintenance
- Clean up old food logs (optional)
- Update food database with new items
- Monitor and optimize slow queries
- Review and update RLS policies

## Troubleshooting

### Common Issues

#### "Row Level Security policy violation"
- Ensure user is authenticated
- Check RLS policies are correctly configured
- Verify user has permission for the operation

#### "Food search returning no results"
- Check that `search_vector` is properly generated
- Verify GIN index exists on search columns
- Ensure search terms are properly formatted

#### "Slow query performance"
- Check query execution plan
- Verify appropriate indexes exist
- Consider adding pagination
- Use materialized views for complex aggregations

### Support Resources
- [Supabase Documentation](https://supabase.com/docs)
- [Supabase Community](https://github.com/supabase/supabase/discussions)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

## Next Steps

1. **Set up authentication** in your application
2. **Implement food search** and logging features
3. **Create nutrition dashboard** with daily summaries
4. **Add recipe management** functionality
5. **Implement CRUD operations** for all entities
6. **Add external API integrations** (Edamam, Nutritionix)
7. **Set up automated testing** for database operations

## Security Considerations

- Never expose service role keys in client-side code
- Use environment variables for all sensitive configuration
- Implement proper input validation
- Monitor for unusual access patterns
- Regularly review and update RLS policies
- Use HTTPS for all API communications

---

**Database Setup Complete!** 🎉

Your Supabase nutrition database is now ready for comprehensive nutrition tracking with foods, recipes, user intake logging, and analytics capabilities.
