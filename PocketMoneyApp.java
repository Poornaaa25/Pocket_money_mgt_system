package pocketmoneyapp;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PocketMoneyApp {
    private static final String DATA_FILE_PATH = "PocketMoneyApp.txt";
    private static final Map<String, Double> budgetMap = new HashMap<>();
    private static final Map<String, Double> expensesMap = new HashMap<>();
    private static double pocketMoney;
    private static final List<Goal> savingsGoals = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        loadSavedData();

        System.out.print("Enter initial pocket money amount: Rs.");
        pocketMoney = scanner.nextDouble();

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    setBudget(scanner);
                    break;
                case 2:
                    logExpense(scanner);
                    break;
                case 3:
                    viewSpendingOverview();
                    break;
                case 4:
                    viewSavingsGoals();
                    break;
                case 5:
                    setSavingsGoal(scanner);
                    break;
                case 6:
                    contributeToGoal(scanner);
                    break;
                case 7:
                    generateReport();
                    break;
                case 8:
                    saveData();
                    exitProgram();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("1. Set Budget");
        System.out.println("2. Log Expense");
        System.out.println("3. View Spending Overview");
        System.out.println("4. View Savings Goals");
        System.out.println("5. Set Savings Goal");
        System.out.println("6. Contribute to Goal");
        System.out.println("7. Generate Report");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void setBudget(Scanner scanner) {
        System.out.print("Enter category for budget: ");
        String category = scanner.next();

        System.out.print("Enter budget amount: Rs.");
        double amount = scanner.nextDouble();

        budgetMap.put(category, amount);
        pocketMoney -= amount;  // Deduct the budgeted amount from pocket money
        System.out.println("Budget set successfully!");
    }

    private static void logExpense(Scanner scanner) {
        System.out.print("Enter category for expense: ");
        String category = scanner.next();

        if (!budgetMap.containsKey(category)) {
            System.out.println("Error: Category not found. Set budget for the category first.");
            return;
        }

        System.out.print("Enter expense amount: Rs.");
        double expense = scanner.nextDouble();

        expensesMap.put(category, expensesMap.getOrDefault(category, 0.0) + expense);
        pocketMoney -= expense;  // Deduct the expense amount from pocket money
        System.out.println("Expense logged successfully!");
    }

    private static void viewSpendingOverview() {
        System.out.println("\nSpending Overview:");

        for (Map.Entry<String, Double> entry : budgetMap.entrySet()) {
            String category = entry.getKey();
            double budget = entry.getValue();
            double expenses = expensesMap.getOrDefault(category, 0.0);
            double remainingBudget = budget - expenses;

            System.out.printf("%s - Budget: Rs.%.2f, Expenses: Rs.%.2f, Remaining: Rs.%.2f\n",
                    category, budget, expenses, remainingBudget);
        }

        System.out.println();
    }

    private static void viewSavingsGoals() {
        if (savingsGoals.isEmpty()) {
            System.out.println("No savings goals set yet.");
            return;
        }

        System.out.println("\nSavings Goals:");

        for (Goal goal : savingsGoals) {
            System.out.printf("%s - Target: Rs.%.2f, Achieved: Rs.%.2f\n",
                    goal.getName(), goal.getTargetAmount(), goal.getAchievedAmount());
        }

        System.out.println();
    }

    private static void setSavingsGoal(Scanner scanner) {
        System.out.print("Enter name for the savings goal: ");
        String goalName = scanner.next();

        System.out.print("Enter target amount for the goal: Rs.");
        double targetAmount = scanner.nextDouble();

        Goal newGoal = new Goal(goalName, targetAmount);
        savingsGoals.add(newGoal);

        System.out.println("Savings goal set successfully!");
    }

    private static void contributeToGoal(Scanner scanner) {
        if (savingsGoals.isEmpty()) {
            System.out.println("No savings goals set yet. Set a savings goal first.");
            return;
        }

        viewSavingsGoals();

        System.out.print("Enter the name of the goal you want to contribute to: ");
        String goalName = scanner.next();

        for (Goal goal : savingsGoals) {
            if (goal.getName().equalsIgnoreCase(goalName)) {
                System

.out.print("Enter the amount you want to contribute: Rs.");
                double contribution = scanner.nextDouble();

                if (contribution > pocketMoney) {
                    System.out.println("Error: Not enough pocket money to contribute.");
                    return;
                }

                goal.contribute(contribution);
                System.out.println("Contribution to the goal made successfully!");
                return;
            }
        }

        System.out.println("Error: Goal not found. Check the goal name and try again.");
    }

    private static void generateReport() {
        // Add report generation logic here
        System.out.println("Report generated successfully!");
    }
      /* for (Goal goal : savingsGoals) {
    saveValue(writer, "goal", goal.getName(), goal.getTargetAmount(), goal.getAchievedAmount());
} */

    //private static void saveValue(BufferedWriter writer, String goal, double targetAmount, String name, double achievedAmount) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}

    private static class Goal {
        private String name;
        private double targetAmount;
        private double achievedAmount;

        public Goal(String name, double targetAmount) {
            this.name = name;
            this.targetAmount = targetAmount;
        }

        public Goal(String name, double targetAmount, double achievedAmount) {
            this.name = name;
            this.targetAmount = targetAmount;
            this.achievedAmount = achievedAmount;
        }

        public String getName() {
            return name;
        }

        public double getTargetAmount() {
            return targetAmount;
        }

        public double getAchievedAmount() {
            return achievedAmount;
        }

        public void contribute(double amount) {
            achievedAmount += amount;
            if (achievedAmount >= targetAmount) {
                celebrateAchievement(name);
            }
        }
    }
    
    /* private static void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE_PATH))) {
            saveValue(writer, "pocketMoney", pocketMoney);

            for (Map.Entry<String, Double> entry : budgetMap.entrySet()) {
                saveValue(writer, "budget", entry.getValue(), entry.getKey());
            }

            for (Map.Entry<String, Double> entry : expensesMap.entrySet()) {
                saveValue(writer, "expenses", entry.getValue(), entry.getKey());
            }

            //for (Goal goal : savingsGoals) {
               //saveValue(writer, "goal", goal.getTargetAmount(), goal.getName(), goal.getAchievedAmount());
            for (Goal goal : savingsGoals) {
        saveValue(writer, "goal", goal.getTargetAmount(), goal.getName(), goal.getAchievedAmount());
            }

        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    } */
  private static void saveValue(BufferedWriter writer, String key, String... values) {
    try {
        writer.write(key);
        for (String value : values) {
            writer.write("," + value);
        }
        writer.newLine();
    } catch (IOException e) {
        System.out.println("Error writing to file: " + e.getMessage());
    }
}


private static void saveData() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE_PATH))) {
        saveValue(writer, "pocketMoney", Double.toString(pocketMoney));

        for (Map.Entry<String, Double> entry : budgetMap.entrySet()) {
            saveValue(writer, "budget", entry.getKey(), entry.getValue().toString());
        }

        for (Map.Entry<String, Double> entry : expensesMap.entrySet()) {
            saveValue(writer, "expenses", entry.getKey(), entry.getValue().toString());
        }

        for (Goal goal : savingsGoals) {
            saveValue(writer, "goal", goal.getName(), Double.toString(goal.getTargetAmount()), Double.toString(goal.getAchievedAmount()));
        }

    } catch (IOException e) {
        System.out.println("Error saving data to file: " + e.getMessage());
    }
}


    /* private static void saveValue(BufferedWriter writer, String key, double value, String... additionalValues) {
    try {
        writer.write(key + "," + value);
        for (String additionalValue : additionalValues) {
            writer.write("," + additionalValue);
        }
        writer.newLine();
    } catch (IOException e) {
        throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
    }
}*/
   
   private static void loadSavedData() {
    try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE_PATH))) {
        String line;
        while ((line = reader.readLine()) != null) {
            processSavedData(line);
        }
    } catch (FileNotFoundException e) {
        System.out.println("File not found. Creating a new data file.");
        createNewDataFile();
    } catch (IOException e) {
        System.out.println("Error loading data from file: " + e.getMessage());
    }
}


    private static void createNewDataFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE_PATH))) {
            System.out.println("New data file created successfully.");
        } catch (IOException e) {
            System.out.println("Error creating new data file: " + e.getMessage());
        }
    }

    private static void processSavedData(String dataLine) {
    String[] parts = dataLine.split(",");
    if (parts.length >= 2) {
        String key = parts[0].trim();
        String valueString = parts[1].trim();

        switch (key) {
            case "pocketMoney":
                pocketMoney = Double.parseDouble(valueString);
                break;
            case "budget":
                if (parts.length >= 4) {
                    String category = parts[2].trim();
                    double value = Double.parseDouble(valueString);
                    budgetMap.put(category, value);
                }
                break;
            case "expenses":
                if (parts.length >= 4) {
                    String category = parts[2].trim();
                    double value = Double.parseDouble(valueString);
                    expensesMap.put(category, value);
                }
                break;
            case "goal":
                if (parts.length >= 5) {
                    String goalName = parts[2].trim();
                    double targetAmount = Double.parseDouble(parts[3].trim());
                    double achievedAmount = Double.parseDouble(parts[4].trim());
                    Goal goal = new Goal(goalName, targetAmount, achievedAmount);
                    savingsGoals.add(goal);
                }
                break;
            default:
                System.out.println("Unknown data type: " + key);
        }
    }
}

    private static void exitProgram() {
        System.out.println("Exiting Pocket Money Manager. Goodbye!");
        System.exit(0);
    }

    

    private static void celebrateAchievement(String goalName) {
        System.out.println("Congratulations! You've successfully achieved your savings goal: " + goalName + "!");
        System.out.println("Celebrate your accomplishment!");
    }
}
