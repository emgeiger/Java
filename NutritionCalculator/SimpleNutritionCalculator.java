import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple Nutrition Calculator MVP - Standalone version for immediate testing
 * This version demonstrates the core functionality without external dependencies
 */
public class SimpleNutritionCalculator extends JFrame {
    
    // Simple ingredient class
    static class Ingredient {
        private String name;
        private double caloriesPerUnit;
        private String unit;
        
        public Ingredient(String name, double caloriesPerUnit, String unit) {
            this.name = name;
            this.caloriesPerUnit = caloriesPerUnit;
            this.unit = unit;
        }
        
        // Getters
        public String getName() { return name; }
        public double getCaloriesPerUnit() { return caloriesPerUnit; }
        public String getUnit() { return unit; }
    }
    
    // Simple recipe ingredient class
    static class RecipeIngredient {
        private Ingredient ingredient;
        private double quantity;
        
        public RecipeIngredient(Ingredient ingredient, double quantity) {
            this.ingredient = ingredient;
            this.quantity = quantity;
        }
        
        public double getTotalCalories() {
            return ingredient.getCaloriesPerUnit() * quantity;
        }
        
        public Ingredient getIngredient() { return ingredient; }
        public double getQuantity() { return quantity; }
    }
    
    // UI Components
    private JTextField recipeNameField;
    private JSpinner servingsSpinner;
    private JComboBox<String> ingredientNameCombo;
    private JTextField caloriesField;
    private JComboBox<String> unitCombo;
    private JTextField quantityField;
    private DefaultListModel<String> ingredientListModel;
    private JList<String> ingredientList;
    private JLabel totalCaloriesLabel;
    private JLabel caloriesPerServingLabel;
    
    // Data
    private List<RecipeIngredient> currentIngredients;
    
    public SimpleNutritionCalculator() {
        currentIngredients = new ArrayList<>();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("🥗 Nutrition Calculator - MVP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create panels
        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
        
        // Set window properties
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(600, 500));
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Recipe Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Recipe name
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Recipe Name:"), gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        recipeNameField = new JTextField("My Recipe", 20);
        panel.add(recipeNameField, gbc);
        
        // Servings
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(new JLabel("Servings:"), gbc);
        
        gbc.gridx = 3;
        servingsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        panel.add(servingsSpinner, gbc);
        
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Add ingredient form
        panel.add(createAddIngredientPanel(), BorderLayout.NORTH);
        
        // Ingredients list
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Recipe Ingredients"));
        
        ingredientListModel = new DefaultListModel<>();
        ingredientList = new JList<>(ingredientListModel);
        JScrollPane scrollPane = new JScrollPane(ingredientList);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        listPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Remove button
        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> removeSelectedIngredient());
        listPanel.add(removeButton, BorderLayout.SOUTH);
        
        panel.add(listPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createAddIngredientPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add Ingredient"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        
        // Common ingredients for dropdown
        String[] commonIngredients = {
            "Chicken Breast", "Ground Beef", "Salmon", "Eggs", "Milk", "Cheese",
            "Rice", "Pasta", "Bread", "Flour", "Sugar", "Olive Oil", "Butter",
            "Onions", "Tomatoes", "Potatoes", "Carrots", "Broccoli", "Spinach"
        };
        
        // Ingredient name
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Ingredient:"), gbc);
        
        gbc.gridx = 1;
        ingredientNameCombo = new JComboBox<>(commonIngredients);
        ingredientNameCombo.setEditable(true);
        panel.add(ingredientNameCombo, gbc);
        
        // Calories per unit
        gbc.gridx = 2;
        panel.add(new JLabel("Calories/Unit:"), gbc);
        
        gbc.gridx = 3;
        caloriesField = new JTextField(8);
        panel.add(caloriesField, gbc);
        
        // Unit
        gbc.gridx = 4;
        panel.add(new JLabel("Unit:"), gbc);
        
        gbc.gridx = 5;
        String[] units = {"grams", "oz", "lbs", "mL", "cups", "tbsp", "tsp", "pieces"};
        unitCombo = new JComboBox<>(units);
        panel.add(unitCombo, gbc);
        
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
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout());
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
    
    private void addIngredient() {
        try {
            String name = (String) ingredientNameCombo.getSelectedItem();
            double calories = Double.parseDouble(caloriesField.getText());
            String unit = (String) unitCombo.getSelectedItem();
            double quantity = Double.parseDouble(quantityField.getText());
            
            if (name == null || name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an ingredient name.");
                return;
            }
            
            if (calories < 0 || quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter valid positive numbers.");
                return;
            }
            
            Ingredient ingredient = new Ingredient(name.trim(), calories, unit);
            RecipeIngredient recipeIngredient = new RecipeIngredient(ingredient, quantity);
            currentIngredients.add(recipeIngredient);
            
            // Update display
            updateIngredientList();
            updateSummary();
            
            // Clear form
            ingredientNameCombo.setSelectedIndex(0);
            caloriesField.setText("");
            quantityField.setText("");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for calories and quantity.");
        }
    }
    
    private void removeSelectedIngredient() {
        int selectedIndex = ingredientList.getSelectedIndex();
        if (selectedIndex >= 0) {
            currentIngredients.remove(selectedIndex);
            updateIngredientList();
            updateSummary();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an ingredient to remove.");
        }
    }
    
    private void updateIngredientList() {
        ingredientListModel.clear();
        for (RecipeIngredient ri : currentIngredients) {
            String display = String.format("%s - %.1f %s (%.0f calories)", 
                ri.getIngredient().getName(),
                ri.getQuantity(),
                ri.getIngredient().getUnit(),
                ri.getTotalCalories());
            ingredientListModel.addElement(display);
        }
    }
    
    private void updateSummary() {
        double totalCalories = currentIngredients.stream()
                .mapToDouble(RecipeIngredient::getTotalCalories)
                .sum();
        
        int servings = (Integer) servingsSpinner.getValue();
        double caloriesPerServing = totalCalories / servings;
        
        totalCaloriesLabel.setText(String.format("Total Calories: %.0f", totalCalories));
        caloriesPerServingLabel.setText(String.format("Calories per Serving: %.0f", caloriesPerServing));
    }
    
    public static void main(String[] args) {
        // Set system look and feel - simplified to avoid Java version issues
        SwingUtilities.invokeLater(() -> {
            SimpleNutritionCalculator app = new SimpleNutritionCalculator();
            app.setVisible(true);
            System.out.println("🥗 Simple Nutrition Calculator started successfully!");
        });
    }
}
