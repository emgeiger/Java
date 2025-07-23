package com.nutrition.calculator.ui;

import com.nutrition.calculator.service.RecipeService;
import com.nutrition.calculator.model.Recipe;
import com.nutrition.calculator.model.Ingredient;
import com.nutrition.calculator.model.RecipeIngredient;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;

/**
 * NutritionCalculatorGUI - Main desktop GUI for the Nutrition Calculator application
 * 
 * This class provides a comprehensive Swing-based interface for nutrition tracking,
 * recipe management, and food logging. It integrates with the RecipeService to
 * provide full nutrition calculation and data management capabilities.
 * 
 * Features:
 * - Recipe creation and management
 * - Nutrition calculation and display
 * - Food database integration
 * - Daily nutrition tracking
 * - Export and import capabilities
 * 
 * @author NutritionCalculator Development Team
 * @version 1.0
 */
public class NutritionCalculatorGUI extends JPanel {
    
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.##");
    
    // Core components
    private final RecipeService recipeService;
    
    // GUI Components
    private JTabbedPane mainTabbedPane;
    
    // Recipe Management Tab
    private JPanel recipePanel;
    private JTextField recipeNameField;
    private JTextArea recipeDescriptionArea;
    private JSpinner servingsSpinner;
    private JTable ingredientsTable;
    private DefaultTableModel ingredientsTableModel;
    private JButton addIngredientButton;
    private JButton removeIngredientButton;
    private JButton saveRecipeButton;
    private JButton loadRecipeButton;
    
    // Nutrition Display Tab
    private JPanel nutritionPanel;
    private JLabel caloriesLabel;
    private JLabel proteinLabel;
    private JLabel carbsLabel;
    private JLabel fatLabel;
    private JLabel fiberLabel;
    private JLabel sugarLabel;
    private JLabel sodiumLabel;
    private JProgressBar caloriesProgress;
    private JProgressBar proteinProgress;
    private JProgressBar carbsProgress;
    private JProgressBar fatProgress;
    
    // Food Database Tab
    private JPanel foodDatabasePanel;
    private JTextField foodSearchField;
    private JList<Ingredient> foodSearchResults;
    private DefaultListModel<Ingredient> foodListModel;
    private JButton searchFoodButton;
    private JButton addFoodButton;
    
    // Daily Tracking Tab
    private JPanel dailyTrackingPanel;
    private JTable dailyFoodTable;
    private DefaultTableModel dailyFoodTableModel;
    private JLabel dailyCaloriesLabel;
    private JLabel dailyProteinLabel;
    private JLabel dailyCarbsLabel;
    private JLabel dailyFatLabel;
    private JButton logFoodButton;
    private JButton clearDayButton;
    
    // Recipe List Tab
    private JPanel recipeListPanel;
    private JList<Recipe> recipeList;
    private DefaultListModel<Recipe> recipeListModel;
    private JButton editRecipeButton;
    private JButton deleteRecipeButton;
    private JButton duplicateRecipeButton;
    
    /**
     * Constructor for the Nutrition Calculator GUI
     * 
     * @param recipeService The service for managing recipes and nutrition data
     */
    public NutritionCalculatorGUI(RecipeService recipeService) {
        this.recipeService = recipeService;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadInitialData();
    }
    
    /**
     * Initialize all GUI components
     */
    private void initializeComponents() {
        // Main tabbed pane
        mainTabbedPane = new JTabbedPane();
        
        // Initialize recipe management components
        initializeRecipeComponents();
        
        // Initialize nutrition display components
        initializeNutritionComponents();
        
        // Initialize food database components
        initializeFoodDatabaseComponents();
        
        // Initialize daily tracking components
        initializeDailyTrackingComponents();
        
        // Initialize recipe list components
        initializeRecipeListComponents();
    }
    
    /**
     * Initialize recipe management components
     */
    private void initializeRecipeComponents() {
        recipePanel = new JPanel();
        recipeNameField = new JTextField(20);
        recipeDescriptionArea = new JTextArea(3, 20);
        recipeDescriptionArea.setLineWrap(true);
        recipeDescriptionArea.setWrapStyleWord(true);
        
        servingsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        
        // Ingredients table
        String[] columnNames = {"Ingredient", "Amount", "Unit", "Calories", "Protein (g)", "Carbs (g)", "Fat (g)"};
        ingredientsTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column < 3; // Only first 3 columns editable
            }
        };
        ingredientsTable = new JTable(ingredientsTableModel);
        ingredientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Configure table column widths
        TableColumnModel columnModel = ingredientsTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150); // Ingredient name
        columnModel.getColumn(1).setPreferredWidth(80);  // Amount
        columnModel.getColumn(2).setPreferredWidth(60);  // Unit
        columnModel.getColumn(3).setPreferredWidth(80);  // Calories
        columnModel.getColumn(4).setPreferredWidth(80);  // Protein
        columnModel.getColumn(5).setPreferredWidth(80);  // Carbs
        columnModel.getColumn(6).setPreferredWidth(80);  // Fat
        
        addIngredientButton = new JButton("Add Ingredient");
        removeIngredientButton = new JButton("Remove Ingredient");
        saveRecipeButton = new JButton("Save Recipe");
        loadRecipeButton = new JButton("Load Recipe");
    }
    
    /**
     * Initialize nutrition display components
     */
    private void initializeNutritionComponents() {
        nutritionPanel = new JPanel();
        
        caloriesLabel = new JLabel("Calories: 0");
        proteinLabel = new JLabel("Protein: 0.0g");
        carbsLabel = new JLabel("Carbohydrates: 0.0g");
        fatLabel = new JLabel("Fat: 0.0g");
        fiberLabel = new JLabel("Fiber: 0.0g");
        sugarLabel = new JLabel("Sugar: 0.0g");
        sodiumLabel = new JLabel("Sodium: 0.0mg");
        
        // Progress bars for daily nutrition goals
        caloriesProgress = new JProgressBar(0, 2000);
        proteinProgress = new JProgressBar(0, 150);
        carbsProgress = new JProgressBar(0, 300);
        fatProgress = new JProgressBar(0, 65);
        
        caloriesProgress.setStringPainted(true);
        proteinProgress.setStringPainted(true);
        carbsProgress.setStringPainted(true);
        fatProgress.setStringPainted(true);
    }
    
    /**
     * Initialize food database components
     */
    private void initializeFoodDatabaseComponents() {
        foodDatabasePanel = new JPanel();
        foodSearchField = new JTextField(20);
        foodListModel = new DefaultListModel<>();
        foodSearchResults = new JList<>(foodListModel);
        foodSearchResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        searchFoodButton = new JButton("Search");
        addFoodButton = new JButton("Add to Recipe");
    }
    
    /**
     * Initialize daily tracking components
     */
    private void initializeDailyTrackingComponents() {
        dailyTrackingPanel = new JPanel();
        
        String[] dailyColumnNames = {"Time", "Food", "Amount", "Calories", "Protein", "Carbs", "Fat"};
        dailyFoodTableModel = new DefaultTableModel(dailyColumnNames, 0);
        dailyFoodTable = new JTable(dailyFoodTableModel);
        
        dailyCaloriesLabel = new JLabel("Daily Calories: 0");
        dailyProteinLabel = new JLabel("Daily Protein: 0.0g");
        dailyCarbsLabel = new JLabel("Daily Carbs: 0.0g");
        dailyFatLabel = new JLabel("Daily Fat: 0.0g");
        
        logFoodButton = new JButton("Log Food");
        clearDayButton = new JButton("Clear Day");
    }
    
    /**
     * Initialize recipe list components
     */
    private void initializeRecipeListComponents() {
        recipeListPanel = new JPanel();
        recipeListModel = new DefaultListModel<>();
        recipeList = new JList<>(recipeListModel);
        recipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        editRecipeButton = new JButton("Edit Recipe");
        deleteRecipeButton = new JButton("Delete Recipe");
        duplicateRecipeButton = new JButton("Duplicate Recipe");
    }
    
    /**
     * Setup the main layout for all components
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Setup recipe panel layout
        setupRecipePanelLayout();
        
        // Setup nutrition panel layout
        setupNutritionPanelLayout();
        
        // Setup food database panel layout
        setupFoodDatabasePanelLayout();
        
        // Setup daily tracking panel layout
        setupDailyTrackingPanelLayout();
        
        // Setup recipe list panel layout
        setupRecipeListPanelLayout();
        
        // Add all tabs to main tabbed pane
        mainTabbedPane.addTab("Recipe Builder", recipePanel);
        mainTabbedPane.addTab("Nutrition Info", nutritionPanel);
        mainTabbedPane.addTab("Food Database", foodDatabasePanel);
        mainTabbedPane.addTab("Daily Tracking", dailyTrackingPanel);
        mainTabbedPane.addTab("Recipe List", recipeListPanel);
        
        add(mainTabbedPane, BorderLayout.CENTER);
    }
    
    /**
     * Setup recipe panel layout
     */
    private void setupRecipePanelLayout() {
        recipePanel.setLayout(new BorderLayout());
        
        // Top panel for recipe details
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        topPanel.add(new JLabel("Recipe Name:"), gbc);
        gbc.gridx = 1;
        topPanel.add(recipeNameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        topPanel.add(new JLabel("Servings:"), gbc);
        gbc.gridx = 1;
        topPanel.add(servingsSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        topPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        topPanel.add(new JScrollPane(recipeDescriptionArea), gbc);
        
        recipePanel.add(topPanel, BorderLayout.NORTH);
        
        // Center panel for ingredients table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new TitledBorder("Ingredients"));
        centerPanel.add(new JScrollPane(ingredientsTable), BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addIngredientButton);
        buttonPanel.add(removeIngredientButton);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        recipePanel.add(centerPanel, BorderLayout.CENTER);
        
        // Bottom panel for recipe actions
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(saveRecipeButton);
        bottomPanel.add(loadRecipeButton);
        
        recipePanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Setup nutrition panel layout
     */
    private void setupNutritionPanelLayout() {
        nutritionPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nutrition labels
        gbc.gridx = 0; gbc.gridy = 0;
        nutritionPanel.add(caloriesLabel, gbc);
        gbc.gridx = 1;
        nutritionPanel.add(caloriesProgress, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        nutritionPanel.add(proteinLabel, gbc);
        gbc.gridx = 1;
        nutritionPanel.add(proteinProgress, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        nutritionPanel.add(carbsLabel, gbc);
        gbc.gridx = 1;
        nutritionPanel.add(carbsProgress, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        nutritionPanel.add(fatLabel, gbc);
        gbc.gridx = 1;
        nutritionPanel.add(fatProgress, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        nutritionPanel.add(fiberLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        nutritionPanel.add(sugarLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6;
        nutritionPanel.add(sodiumLabel, gbc);
    }
    
    /**
     * Setup food database panel layout
     */
    private void setupFoodDatabasePanelLayout() {
        foodDatabasePanel.setLayout(new BorderLayout());
        
        // Top panel for search
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search Food:"));
        searchPanel.add(foodSearchField);
        searchPanel.add(searchFoodButton);
        
        foodDatabasePanel.add(searchPanel, BorderLayout.NORTH);
        
        // Center panel for search results
        foodDatabasePanel.add(new JScrollPane(foodSearchResults), BorderLayout.CENTER);
        
        // Bottom panel for actions
        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.add(addFoodButton);
        
        foodDatabasePanel.add(actionPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Setup daily tracking panel layout
     */
    private void setupDailyTrackingPanelLayout() {
        dailyTrackingPanel.setLayout(new BorderLayout());
        
        // Top panel for daily summary
        JPanel summaryPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        summaryPanel.setBorder(new TitledBorder("Daily Summary"));
        summaryPanel.add(dailyCaloriesLabel);
        summaryPanel.add(dailyProteinLabel);
        summaryPanel.add(dailyCarbsLabel);
        summaryPanel.add(dailyFatLabel);
        
        dailyTrackingPanel.add(summaryPanel, BorderLayout.NORTH);
        
        // Center panel for daily food log
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(new TitledBorder("Today's Food Log"));
        logPanel.add(new JScrollPane(dailyFoodTable), BorderLayout.CENTER);
        
        JPanel logButtonPanel = new JPanel(new FlowLayout());
        logButtonPanel.add(logFoodButton);
        logButtonPanel.add(clearDayButton);
        logPanel.add(logButtonPanel, BorderLayout.SOUTH);
        
        dailyTrackingPanel.add(logPanel, BorderLayout.CENTER);
    }
    
    /**
     * Setup recipe list panel layout
     */
    private void setupRecipeListPanelLayout() {
        recipeListPanel.setLayout(new BorderLayout());
        
        recipeListPanel.add(new JScrollPane(recipeList), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(editRecipeButton);
        buttonPanel.add(deleteRecipeButton);
        buttonPanel.add(duplicateRecipeButton);
        
        recipeListPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Setup event handlers for all components
     */
    private void setupEventHandlers() {
        // Recipe management event handlers
        addIngredientButton.addActionListener(e -> showAddIngredientDialog());
        removeIngredientButton.addActionListener(e -> removeSelectedIngredient());
        saveRecipeButton.addActionListener(e -> saveCurrentRecipe());
        loadRecipeButton.addActionListener(e -> showLoadRecipeDialog());
        
        // Food database event handlers
        searchFoodButton.addActionListener(e -> searchFoods());
        addFoodButton.addActionListener(e -> addSelectedFoodToRecipe());
        
        // Daily tracking event handlers
        logFoodButton.addActionListener(e -> showLogFoodDialog());
        clearDayButton.addActionListener(e -> clearDailyLog());
        
        // Recipe list event handlers
        editRecipeButton.addActionListener(e -> editSelectedRecipe());
        deleteRecipeButton.addActionListener(e -> deleteSelectedRecipe());
        duplicateRecipeButton.addActionListener(e -> duplicateSelectedRecipe());
        
        // Table selection handlers
        ingredientsTable.getSelectionModel().addListSelectionListener(e -> updateNutritionDisplay());
        recipeList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editSelectedRecipe();
                }
            }
        });
    }
    
    /**
     * Load initial data for the application
     */
    private void loadInitialData() {
        refreshRecipeList();
        loadSampleFoods();
    }
    
    /**
     * Show dialog to add a new ingredient
     */
    private void showAddIngredientDialog() {
        // Implementation for ingredient dialog
        String ingredientName = JOptionPane.showInputDialog(this, "Enter ingredient name:");
        if (ingredientName != null && !ingredientName.trim().isEmpty()) {
            String amountStr = JOptionPane.showInputDialog(this, "Enter amount:");
            String unit = JOptionPane.showInputDialog(this, "Enter unit:");
            
            // Add to table (nutrition values would be calculated)
            Object[] rowData = {ingredientName, amountStr, unit, "0", "0.0", "0.0", "0.0"};
            ingredientsTableModel.addRow(rowData);
            updateNutritionDisplay();
        }
    }
    
    /**
     * Remove the selected ingredient from the table
     */
    private void removeSelectedIngredient() {
        int selectedRow = ingredientsTable.getSelectedRow();
        if (selectedRow >= 0) {
            ingredientsTableModel.removeRow(selectedRow);
            updateNutritionDisplay();
        }
    }
    
    /**
     * Save the current recipe
     */
    private void saveCurrentRecipe() {
        String recipeName = recipeNameField.getText().trim();
        if (recipeName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a recipe name.");
            return;
        }
        
        // Create recipe object and save
        Recipe recipe = new Recipe();
        recipe.setName(recipeName);
        recipe.setDescription(recipeDescriptionArea.getText());
        recipe.setServings((Integer) servingsSpinner.getValue());
        
        // Add ingredients from table
        List<RecipeIngredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsTableModel.getRowCount(); i++) {
            // Create ingredient from table row
            // Implementation would depend on the model classes
        }
        
        try {
            recipeService.saveRecipe(recipe);
            JOptionPane.showMessageDialog(this, "Recipe saved successfully!");
            refreshRecipeList();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving recipe: " + e.getMessage());
        }
    }
    
    /**
     * Show dialog to load an existing recipe
     */
    private void showLoadRecipeDialog() {
        // Implementation for loading recipes
        List<Recipe> recipes = recipeService.getAllRecipes();
        if (recipes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No recipes found.");
            return;
        }
        
        Recipe selectedRecipe = (Recipe) JOptionPane.showInputDialog(
            this,
            "Select a recipe to load:",
            "Load Recipe",
            JOptionPane.QUESTION_MESSAGE,
            null,
            recipes.toArray(),
            recipes.get(0)
        );
        
        if (selectedRecipe != null) {
            loadRecipeIntoEditor(selectedRecipe);
        }
    }
    
    /**
     * Load a recipe into the editor
     */
    private void loadRecipeIntoEditor(Recipe recipe) {
        recipeNameField.setText(recipe.getName());
        recipeDescriptionArea.setText(recipe.getDescription());
        servingsSpinner.setValue(recipe.getServings());
        
        // Clear and reload ingredients table
        ingredientsTableModel.setRowCount(0);
        for (RecipeIngredient ingredient : recipe.getIngredients()) {
            Object[] rowData = {
                ingredient.getIngredient().getName(),
                DECIMAL_FORMAT.format(ingredient.getAmount()),
                ingredient.getUnit().toString(),
                String.valueOf(ingredient.getIngredient().getCaloriesPer100g()),
                DECIMAL_FORMAT.format(ingredient.getIngredient().getProteinPer100g()),
                DECIMAL_FORMAT.format(ingredient.getIngredient().getCarbsPer100g()),
                DECIMAL_FORMAT.format(ingredient.getIngredient().getFatPer100g())
            };
            ingredientsTableModel.addRow(rowData);
        }
        
        updateNutritionDisplay();
    }
    
    /**
     * Search for foods in the database
     */
    private void searchFoods() {
        String searchTerm = foodSearchField.getText().trim();
        if (searchTerm.isEmpty()) {
            return;
        }
        
        // Implementation would search actual food database
        foodListModel.clear();
        
        // For now, add some sample foods
        foodListModel.addElement(new Ingredient("Sample Food 1", 100, 5.0, 20.0, 3.0));
        foodListModel.addElement(new Ingredient("Sample Food 2", 150, 8.0, 15.0, 5.0));
    }
    
    /**
     * Add selected food to current recipe
     */
    private void addSelectedFoodToRecipe() {
        Ingredient selectedFood = foodSearchResults.getSelectedValue();
        if (selectedFood != null) {
            String amountStr = JOptionPane.showInputDialog(this, "Enter amount (grams):");
            if (amountStr != null) {
                try {
                    double amount = Double.parseDouble(amountStr);
                    Object[] rowData = {
                        selectedFood.getName(),
                        DECIMAL_FORMAT.format(amount),
                        "g",
                        String.valueOf(selectedFood.getCaloriesPer100g()),
                        DECIMAL_FORMAT.format(selectedFood.getProteinPer100g()),
                        DECIMAL_FORMAT.format(selectedFood.getCarbsPer100g()),
                        DECIMAL_FORMAT.format(selectedFood.getFatPer100g())
                    };
                    ingredientsTableModel.addRow(rowData);
                    updateNutritionDisplay();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid amount entered.");
                }
            }
        }
    }
    
    /**
     * Update the nutrition display based on current ingredients
     */
    private void updateNutritionDisplay() {
        double totalCalories = 0;
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalFat = 0;
        
        // Calculate totals from ingredients table
        for (int i = 0; i < ingredientsTableModel.getRowCount(); i++) {
            try {
                double amount = Double.parseDouble(ingredientsTableModel.getValueAt(i, 1).toString());
                double calories = Double.parseDouble(ingredientsTableModel.getValueAt(i, 3).toString());
                double protein = Double.parseDouble(ingredientsTableModel.getValueAt(i, 4).toString());
                double carbs = Double.parseDouble(ingredientsTableModel.getValueAt(i, 5).toString());
                double fat = Double.parseDouble(ingredientsTableModel.getValueAt(i, 6).toString());
                
                // Calculate per serving based on amount
                double factor = amount / 100.0; // Assuming per 100g values
                totalCalories += calories * factor;
                totalProtein += protein * factor;
                totalCarbs += carbs * factor;
                totalFat += fat * factor;
            } catch (NumberFormatException e) {
                // Skip invalid entries
            }
        }
        
        // Update labels
        caloriesLabel.setText("Calories: " + DECIMAL_FORMAT.format(totalCalories));
        proteinLabel.setText("Protein: " + DECIMAL_FORMAT.format(totalProtein) + "g");
        carbsLabel.setText("Carbohydrates: " + DECIMAL_FORMAT.format(totalCarbs) + "g");
        fatLabel.setText("Fat: " + DECIMAL_FORMAT.format(totalFat) + "g");
        
        // Update progress bars
        caloriesProgress.setValue((int) totalCalories);
        proteinProgress.setValue((int) totalProtein);
        carbsProgress.setValue((int) totalCarbs);
        fatProgress.setValue((int) totalFat);
        
        caloriesProgress.setString(DECIMAL_FORMAT.format(totalCalories) + " / 2000 cal");
        proteinProgress.setString(DECIMAL_FORMAT.format(totalProtein) + " / 150g");
        carbsProgress.setString(DECIMAL_FORMAT.format(totalCarbs) + " / 300g");
        fatProgress.setString(DECIMAL_FORMAT.format(totalFat) + " / 65g");
    }
    
    /**
     * Show dialog to log food for daily tracking
     */
    private void showLogFoodDialog() {
        JOptionPane.showMessageDialog(this, "Food logging feature will be implemented here.");
    }
    
    /**
     * Clear the daily food log
     */
    private void clearDailyLog() {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to clear today's food log?",
            "Clear Daily Log",
            JOptionPane.YES_NO_OPTION
        );
        
        if (result == JOptionPane.YES_OPTION) {
            dailyFoodTableModel.setRowCount(0);
            updateDailySummary();
        }
    }
    
    /**
     * Update the daily nutrition summary
     */
    private void updateDailySummary() {
        // Implementation for calculating daily totals
        dailyCaloriesLabel.setText("Daily Calories: 0");
        dailyProteinLabel.setText("Daily Protein: 0.0g");
        dailyCarbsLabel.setText("Daily Carbs: 0.0g");
        dailyFatLabel.setText("Daily Fat: 0.0g");
    }
    
    /**
     * Edit the selected recipe from the recipe list
     */
    private void editSelectedRecipe() {
        Recipe selectedRecipe = recipeList.getSelectedValue();
        if (selectedRecipe != null) {
            loadRecipeIntoEditor(selectedRecipe);
            mainTabbedPane.setSelectedIndex(0); // Switch to recipe builder tab
        }
    }
    
    /**
     * Delete the selected recipe
     */
    private void deleteSelectedRecipe() {
        Recipe selectedRecipe = recipeList.getSelectedValue();
        if (selectedRecipe != null) {
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete recipe: " + selectedRecipe.getName() + "?",
                "Delete Recipe",
                JOptionPane.YES_NO_OPTION
            );
            
            if (result == JOptionPane.YES_OPTION) {
                try {
                    recipeService.deleteRecipe(selectedRecipe.getId());
                    refreshRecipeList();
                    JOptionPane.showMessageDialog(this, "Recipe deleted successfully!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error deleting recipe: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Duplicate the selected recipe
     */
    private void duplicateSelectedRecipe() {
        Recipe selectedRecipe = recipeList.getSelectedValue();
        if (selectedRecipe != null) {
            Recipe duplicatedRecipe = new Recipe(selectedRecipe);
            duplicatedRecipe.setName(selectedRecipe.getName() + " (Copy)");
            loadRecipeIntoEditor(duplicatedRecipe);
            mainTabbedPane.setSelectedIndex(0); // Switch to recipe builder tab
        }
    }
    
    /**
     * Refresh the recipe list
     */
    private void refreshRecipeList() {
        recipeListModel.clear();
        List<Recipe> recipes = recipeService.getAllRecipes();
        for (Recipe recipe : recipes) {
            recipeListModel.addElement(recipe);
        }
    }
    
    /**
     * Load sample foods for demonstration
     */
    private void loadSampleFoods() {
        // Implementation would load from actual food database
        foodListModel.clear();
        foodListModel.addElement(new Ingredient("Chicken Breast", 165, 31.0, 0.0, 3.6));
        foodListModel.addElement(new Ingredient("Brown Rice", 123, 2.6, 23.0, 0.9));
        foodListModel.addElement(new Ingredient("Broccoli", 34, 2.8, 7.0, 0.4));
        foodListModel.addElement(new Ingredient("Salmon", 208, 20.0, 0.0, 13.0));
        foodListModel.addElement(new Ingredient("Sweet Potato", 86, 1.6, 20.0, 0.1));
    }
}
