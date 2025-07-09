package com.nutrition.calculator.ui;

import com.nutrition.calculator.model.*;
import com.nutrition.calculator.service.InMemoryRecipeService;
import com.nutrition.calculator.service.RecipeService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Main GUI application for the Nutrition Calculator MVP.
 * Simple Swing interface designed for easy mobile porting later.
 */
public class NutritionCalculatorGUI extends JFrame {
    private RecipeService recipeService;
    private Recipe currentRecipe;
    
    // UI Components
    private JTextField recipeNameField;
    private JTextArea recipeDescriptionArea;
    private JSpinner servingsSpinner;
    private JComboBox<String> ingredientNameCombo;
    private JTextField ingredientCaloriesField;
    private JComboBox<MeasurementUnit> unitComboBox;
    private JTextField quantityField;
    private JTable ingredientsTable;
    private DefaultTableModel tableModel;
    private JLabel totalCaloriesLabel;
    private JLabel caloriesPerServingLabel;
    private JList<Recipe> savedRecipesList;
    private DefaultListModel<Recipe> recipeListModel;
    
    public NutritionCalculatorGUI() {
        this.recipeService = new InMemoryRecipeService();
        this.currentRecipe = new Recipe("New Recipe");
        initializeUI();
        loadSavedRecipes();
    }
    
    private void initializeUI() {
        setTitle("🥗 Nutrition Calculator - MVP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create main panels
        add(createRecipePanel(), BorderLayout.NORTH);
        add(createIngredientPanel(), BorderLayout.CENTER);
        add(createSavedRecipesPanel(), BorderLayout.EAST);
        add(createSummaryPanel(), BorderLayout.SOUTH);
        
        // Set window properties
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
    }
    
    private JPanel createRecipePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Recipe Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Recipe name
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Recipe Name:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        recipeNameField = new JTextField(20);
        recipeNameField.setText(currentRecipe.getName());
        panel.add(recipeNameField, gbc);
        
        // Servings
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Servings:"), gbc);
        
        gbc.gridx = 3;
        servingsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        servingsSpinner.setValue(currentRecipe.getServings());
        panel.add(servingsSpinner, gbc);
        
        // Description
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(new JLabel("Description:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.BOTH;
        recipeDescriptionArea = new JTextArea(3, 30);
        recipeDescriptionArea.setText(currentRecipe.getDescription());
        JScrollPane descScrollPane = new JScrollPane(recipeDescriptionArea);
        panel.add(descScrollPane, gbc);
        
        return panel;
    }
    
    private JPanel createIngredientPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Ingredients"));
        
        // Add ingredient form
        panel.add(createAddIngredientForm(), BorderLayout.NORTH);
        
        // Ingredients table
        String[] columnNames = {"Ingredient", "Quantity", "Unit", "Calories", "Total Cal"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        ingredientsTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(ingredientsTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        
        // Table buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> removeSelectedIngredient());
        JButton clearButton = new JButton("Clear All");
        clearButton.addActionListener(e -> clearAllIngredients());
        buttonPanel.add(removeButton);
        buttonPanel.add(clearButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createAddIngredientForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add Ingredient"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        
        // Ingredient name (combo box with common ingredients)
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Ingredient:"), gbc);
        
        gbc.gridx = 1;
        String[] commonIngredients = {
            "Chicken Breast", "Ground Beef", "Salmon", "Eggs", "Milk", "Cheese",
            "Rice", "Pasta", "Bread", "Flour", "Sugar", "Olive Oil", "Butter",
            "Onions", "Tomatoes", "Potatoes", "Carrots", "Broccoli", "Spinach"
        };
        ingredientNameCombo = new JComboBox<>(commonIngredients);
        ingredientNameCombo.setEditable(true);
        panel.add(ingredientNameCombo, gbc);
        
        // Calories per unit
        gbc.gridx = 2;
        panel.add(new JLabel("Cal/Unit:"), gbc);
        
        gbc.gridx = 3;
        ingredientCaloriesField = new JTextField(8);
        panel.add(ingredientCaloriesField, gbc);
        
        // Measurement unit
        gbc.gridx = 4;
        panel.add(new JLabel("Unit:"), gbc);
        
        gbc.gridx = 5;
        unitComboBox = new JComboBox<>(MeasurementUnit.values());
        panel.add(unitComboBox, gbc);
        
        // Quantity
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Quantity:"), gbc);
        
        gbc.gridx = 1;
        quantityField = new JTextField(8);
        panel.add(quantityField, gbc);
        
        // Add button
        gbc.gridx = 2; gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Ingredient");
        addButton.addActionListener(e -> addIngredient());
        panel.add(addButton, gbc);
        
        return panel;
    }
    
    private JPanel createSavedRecipesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Saved Recipes"));
        panel.setPreferredSize(new Dimension(200, 0));
        
        // Recipes list
        recipeListModel = new DefaultListModel<>();
        savedRecipesList = new JList<>(recipeListModel);
        savedRecipesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        savedRecipesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedRecipe();
            }
        });
        JScrollPane listScrollPane = new JScrollPane(savedRecipesList);
        panel.add(listScrollPane, BorderLayout.CENTER);
        
        // Recipe management buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 2, 2));
        
        JButton newButton = new JButton("New Recipe");
        newButton.addActionListener(e -> newRecipe());
        buttonPanel.add(newButton);
        
        JButton saveButton = new JButton("Save Recipe");
        saveButton.addActionListener(e -> saveCurrentRecipe());
        buttonPanel.add(saveButton);
        
        JButton deleteButton = new JButton("Delete Recipe");
        deleteButton.addActionListener(e -> deleteSelectedRecipe());
        buttonPanel.add(deleteButton);
        
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(e -> loadSavedRecipes());
        buttonPanel.add(refreshButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(BorderFactory.createTitledBorder("Nutrition Summary"));
        
        totalCaloriesLabel = new JLabel("Total Calories: 0");
        totalCaloriesLabel.setFont(totalCaloriesLabel.getFont().deriveFont(Font.BOLD, 14f));
        
        caloriesPerServingLabel = new JLabel("Calories per Serving: 0");
        caloriesPerServingLabel.setFont(caloriesPerServingLabel.getFont().deriveFont(Font.BOLD, 14f));
        
        panel.add(totalCaloriesLabel);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(caloriesPerServingLabel);
        
        return panel;
    }
    
    // Event Handlers
    
    private void addIngredient() {
        try {
            String name = (String) ingredientNameCombo.getSelectedItem();
            double calories = Double.parseDouble(ingredientCaloriesField.getText());
            MeasurementUnit unit = (MeasurementUnit) unitComboBox.getSelectedItem();
            double quantity = Double.parseDouble(quantityField.getText());
            
            if (name == null || name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an ingredient name.");
                return;
            }
            
            if (calories < 0 || quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter valid positive numbers for calories and quantity.");
                return;
            }
            
            Ingredient ingredient = new Ingredient(name.trim(), calories, unit);
            currentRecipe.addIngredient(ingredient, quantity);
            
            // Clear form
            ingredientNameCombo.setSelectedIndex(0);
            ingredientCaloriesField.setText("");
            quantityField.setText("");
            
            updateIngredientsTable();
            updateSummary();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for calories and quantity.");
        }
    }
    
    private void removeSelectedIngredient() {
        int selectedRow = ingredientsTable.getSelectedRow();
        if (selectedRow >= 0) {
            currentRecipe.removeIngredient(selectedRow);
            updateIngredientsTable();
            updateSummary();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an ingredient to remove.");
        }
    }
    
    private void clearAllIngredients() {
        if (currentRecipe.getIngredientCount() > 0) {
            int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to clear all ingredients?", 
                "Clear Ingredients", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                currentRecipe.clearIngredients();
                updateIngredientsTable();
                updateSummary();
            }
        }
    }
    
    private void updateIngredientsTable() {
        tableModel.setRowCount(0);
        for (RecipeIngredient ri : currentRecipe.getIngredients()) {
            Object[] row = {
                ri.getIngredient().getName(),
                String.format("%.1f", ri.getQuantity()),
                ri.getIngredient().getUnit().getAbbreviation(),
                String.format("%.0f", ri.getIngredient().getCaloriesPerUnit()),
                String.format("%.0f", ri.getTotalCalories())
            };
            tableModel.addRow(row);
        }
    }
    
    private void updateSummary() {
        updateCurrentRecipeFromForm();
        double totalCalories = currentRecipe.getTotalCalories();
        double caloriesPerServing = currentRecipe.getCaloriesPerServing();
        
        totalCaloriesLabel.setText(String.format("Total Calories: %.0f", totalCalories));
        caloriesPerServingLabel.setText(String.format("Calories per Serving: %.0f", caloriesPerServing));
    }
    
    private void updateCurrentRecipeFromForm() {
        currentRecipe.setName(recipeNameField.getText());
        currentRecipe.setDescription(recipeDescriptionArea.getText());
        currentRecipe.setServings((Integer) servingsSpinner.getValue());
    }
    
    private void newRecipe() {
        currentRecipe = new Recipe("New Recipe");
        updateFormFromCurrentRecipe();
        updateIngredientsTable();
        updateSummary();
        savedRecipesList.clearSelection();
    }
    
    private void saveCurrentRecipe() {
        updateCurrentRecipeFromForm();
        
        if (!currentRecipe.isValid()) {
            JOptionPane.showMessageDialog(this, "Please provide a recipe name and add at least one ingredient.");
            return;
        }
        
        boolean saved = recipeService.saveRecipe(currentRecipe);
        if (saved) {
            JOptionPane.showMessageDialog(this, "Recipe saved successfully!");
            loadSavedRecipes();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to save recipe. Please try again.");
        }
    }
    
    private void loadSelectedRecipe() {
        Recipe selectedRecipe = savedRecipesList.getSelectedValue();
        if (selectedRecipe != null) {
            currentRecipe = selectedRecipe;
            updateFormFromCurrentRecipe();
            updateIngredientsTable();
            updateSummary();
        }
    }
    
    private void deleteSelectedRecipe() {
        Recipe selectedRecipe = savedRecipesList.getSelectedValue();
        if (selectedRecipe != null) {
            int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete '" + selectedRecipe.getName() + "'?", 
                "Delete Recipe", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                boolean deleted = recipeService.deleteRecipe(selectedRecipe.getId());
                if (deleted) {
                    loadSavedRecipes();
                    newRecipe();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete recipe.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a recipe to delete.");
        }
    }
    
    private void loadSavedRecipes() {
        recipeListModel.clear();
        List<Recipe> recipes = recipeService.loadAllRecipes();
        for (Recipe recipe : recipes) {
            recipeListModel.addElement(recipe);
        }
    }
    
    private void updateFormFromCurrentRecipe() {
        recipeNameField.setText(currentRecipe.getName());
        recipeDescriptionArea.setText(currentRecipe.getDescription());
        servingsSpinner.setValue(currentRecipe.getServings());
    }
}
