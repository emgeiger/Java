import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * Simple Nutrition Calculator - Desktop Java Version
 * A basic Swing application for calculating nutrition values of recipes
 */
public class SimpleNutritionCalculator extends JFrame {
    
    // UI Components
    private JTextField ingredientNameField;
    private JTextField caloriesField;
    private JTextField proteinField;
    private JTextField carbsField;
    private JTextField fatField;
    private JTextField quantityField;
    
    private DefaultListModel<RecipeIngredient> recipeListModel;
    private JList<RecipeIngredient> recipeList;
    
    private JLabel totalCaloriesLabel;
    private JLabel totalProteinLabel;
    private JLabel totalCarbsLabel;
    private JLabel totalFatLabel;
    
    // Data storage
    private List<Ingredient> ingredientDatabase;
    private List<RecipeIngredient> currentRecipe;
    
    public SimpleNutritionCalculator() {
        initializeData();
        setupUI();
        setupEventHandlers();
    }
    
    private void initializeData() {
        ingredientDatabase = new ArrayList<>();
        currentRecipe = new ArrayList<>();
        
        // Add some sample ingredients
        ingredientDatabase.add(new Ingredient("Chicken Breast", 165, 31.0, 0.0, 3.6));
        ingredientDatabase.add(new Ingredient("Brown Rice", 112, 2.6, 23.0, 0.9));
        ingredientDatabase.add(new Ingredient("Broccoli", 25, 3.0, 5.0, 0.3));
        ingredientDatabase.add(new Ingredient("Olive Oil", 884, 0.0, 0.0, 100.0));
        ingredientDatabase.add(new Ingredient("Banana", 89, 1.1, 23.0, 0.3));
        ingredientDatabase.add(new Ingredient("Salmon", 208, 20.4, 0.0, 13.4));
        ingredientDatabase.add(new Ingredient("Sweet Potato", 86, 1.6, 20.1, 0.1));
        ingredientDatabase.add(new Ingredient("Spinach", 23, 2.9, 3.6, 0.4));
    }
    
    private void setupUI() {
        setTitle("Simple Nutrition Calculator - Desktop Version");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create main panels
        JPanel inputPanel = createInputPanel();
        JPanel recipePanel = createRecipePanel();
        JPanel summaryPanel = createSummaryPanel();
        
        add(inputPanel, BorderLayout.NORTH);
        add(recipePanel, BorderLayout.CENTER);
        add(summaryPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setResizable(true);
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add Ingredient"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Ingredient name
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Ingredient:"), gbc);
        gbc.gridx = 1;
        ingredientNameField = new JTextField(15);
        panel.add(ingredientNameField, gbc);
        
        // Quantity
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("Quantity (100g units):"), gbc);
        gbc.gridx = 3;
        quantityField = new JTextField("1.0", 8);
        panel.add(quantityField, gbc);
        
        // Nutrition fields
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Calories (per 100g):"), gbc);
        gbc.gridx = 1;
        caloriesField = new JTextField("0", 8);
        panel.add(caloriesField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 1;
        panel.add(new JLabel("Protein (g):"), gbc);
        gbc.gridx = 3;
        proteinField = new JTextField("0", 8);
        panel.add(proteinField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Carbs (g):"), gbc);
        gbc.gridx = 1;
        carbsField = new JTextField("0", 8);
        panel.add(carbsField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 2;
        panel.add(new JLabel("Fat (g):"), gbc);
        gbc.gridx = 3;
        fatField = new JTextField("0", 8);
        panel.add(fatField, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 3;
        JButton searchButton = new JButton("Search Database");
        panel.add(searchButton, gbc);
        
        gbc.gridx = 1; gbc.gridy = 3;
        JButton addButton = new JButton("Add to Recipe");
        panel.add(addButton, gbc);
        
        gbc.gridx = 2; gbc.gridy = 3;
        JButton clearButton = new JButton("Clear Fields");
        panel.add(clearButton, gbc);
        
        // Event handlers
        searchButton.addActionListener(e -> searchIngredient());
        addButton.addActionListener(e -> addIngredientToRecipe());
        clearButton.addActionListener(e -> clearInputFields());
        
        return panel;
    }
    
    private JPanel createRecipePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Current Recipe"));
        
        recipeListModel = new DefaultListModel<>();
        recipeList = new JList<>(recipeListModel);
        recipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(recipeList);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton removeButton = new JButton("Remove Selected");
        JButton clearRecipeButton = new JButton("Clear Recipe");
        
        removeButton.addActionListener(e -> removeSelectedIngredient());
        clearRecipeButton.addActionListener(e -> clearRecipe());
        
        buttonPanel.add(removeButton);
        buttonPanel.add(clearRecipeButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Recipe Nutrition Summary"));
        
        panel.add(new JLabel("Total Calories:"));
        totalCaloriesLabel = new JLabel("0");
        panel.add(totalCaloriesLabel);
        
        panel.add(new JLabel("Total Protein (g):"));
        totalProteinLabel = new JLabel("0.0");
        panel.add(totalProteinLabel);
        
        panel.add(new JLabel("Total Carbs (g):"));
        totalCarbsLabel = new JLabel("0.0");
        panel.add(totalCarbsLabel);
        
        panel.add(new JLabel("Total Fat (g):"));
        totalFatLabel = new JLabel("0.0");
        panel.add(totalFatLabel);
        
        return panel;
    }
    
    private void setupEventHandlers() {
        // Auto-update totals when recipe changes
        recipeListModel.addListDataListener(new javax.swing.event.ListDataListener() {
            public void intervalAdded(javax.swing.event.ListDataEvent e) { updateTotals(); }
            public void intervalRemoved(javax.swing.event.ListDataEvent e) { updateTotals(); }
            public void contentsChanged(javax.swing.event.ListDataEvent e) { updateTotals(); }
        });
    }
    
    private void searchIngredient() {
        String searchName = ingredientNameField.getText().trim();
        if (searchName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ingredient name to search.");
            return;
        }
        
        // Simple search (case-insensitive contains)
        for (Ingredient ingredient : ingredientDatabase) {
            if (ingredient.getName().toLowerCase().contains(searchName.toLowerCase())) {
                // Populate fields with found ingredient
                ingredientNameField.setText(ingredient.getName());
                caloriesField.setText(String.valueOf(ingredient.getCalories()));
                proteinField.setText(String.valueOf(ingredient.getProtein()));
                carbsField.setText(String.valueOf(ingredient.getCarbs()));
                fatField.setText(String.valueOf(ingredient.getFat()));
                return;
            }
        }
        
        JOptionPane.showMessageDialog(this, 
            "Ingredient not found in database. You can manually enter the nutrition values.");
    }
    
    private void addIngredientToRecipe() {
        try {
            String name = ingredientNameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an ingredient name.");
                return;
            }
            
            int calories = Integer.parseInt(caloriesField.getText());
            double protein = Double.parseDouble(proteinField.getText());
            double carbs = Double.parseDouble(carbsField.getText());
            double fat = Double.parseDouble(fatField.getText());
            double quantity = Double.parseDouble(quantityField.getText());
            
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0.");
                return;
            }
            
            Ingredient ingredient = new Ingredient(name, calories, protein, carbs, fat);
            RecipeIngredient recipeIngredient = new RecipeIngredient(ingredient, quantity);
            
            recipeListModel.addElement(recipeIngredient);
            currentRecipe.add(recipeIngredient);
            
            clearInputFields();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter valid numbers for nutrition values and quantity.");
        }
    }
    
    private void removeSelectedIngredient() {
        int selectedIndex = recipeList.getSelectedIndex();
        if (selectedIndex >= 0) {
            recipeListModel.remove(selectedIndex);
            currentRecipe.remove(selectedIndex);
        }
    }
    
    private void clearRecipe() {
        recipeListModel.clear();
        currentRecipe.clear();
    }
    
    private void clearInputFields() {
        ingredientNameField.setText("");
        caloriesField.setText("0");
        proteinField.setText("0");
        carbsField.setText("0");
        fatField.setText("0");
        quantityField.setText("1.0");
    }
    
    private void updateTotals() {
        double totalCalories = 0;
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalFat = 0;
        
        for (RecipeIngredient ri : currentRecipe) {
            totalCalories += ri.getTotalCalories();
            totalProtein += ri.getTotalProtein();
            totalCarbs += ri.getTotalCarbs();
            totalFat += ri.getTotalFat();
        }
        
        totalCaloriesLabel.setText(String.format("%.0f", totalCalories));
        totalProteinLabel.setText(String.format("%.1f", totalProtein));
        totalCarbsLabel.setText(String.format("%.1f", totalCarbs));
        totalFatLabel.setText(String.format("%.1f", totalFat));
    }
    
    // Inner Classes
    static class Ingredient {
        private String name;
        private int calories; // per 100g
        private double protein; // per 100g
        private double carbs; // per 100g
        private double fat; // per 100g
        
        public Ingredient(String name, int calories, double protein, double carbs, double fat) {
            this.name = name;
            this.calories = calories;
            this.protein = protein;
            this.carbs = carbs;
            this.fat = fat;
        }
        
        // Getters
        public String getName() { return name; }
        public int getCalories() { return calories; }
        public double getProtein() { return protein; }
        public double getCarbs() { return carbs; }
        public double getFat() { return fat; }
    }
    
    static class RecipeIngredient {
        private Ingredient ingredient;
        private double quantity; // in 100g units
        
        public RecipeIngredient(Ingredient ingredient, double quantity) {
            this.ingredient = ingredient;
            this.quantity = quantity;
        }
        
        public double getTotalCalories() {
            return ingredient.getCalories() * quantity;
        }
        
        public double getTotalProtein() {
            return ingredient.getProtein() * quantity;
        }
        
        public double getTotalCarbs() {
            return ingredient.getCarbs() * quantity;
        }
        
        public double getTotalFat() {
            return ingredient.getFat() * quantity;
        }
        
        @Override
        public String toString() {
            return String.format("%s (%.1f × 100g) - Cal: %.0f, P: %.1fg, C: %.1fg, F: %.1fg",
                ingredient.getName(), quantity, getTotalCalories(), 
                getTotalProtein(), getTotalCarbs(), getTotalFat());
        }
    }
    
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new SimpleNutritionCalculator().setVisible(true);
        });
    }
}
