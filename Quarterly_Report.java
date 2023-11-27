package main;

import java.util.Scanner;

//Main class for the Quarterly Report application
public class Quarterly_Report {

	// Entry point of the application
	public static void main(String[] args) {
		// Initialise and process sales data
		SalesData data = new SalesData();
		data.chooseDepartmentAndQuarter();
	}

	// Nested class representing sales data and operations
	static class SalesData {

		// Constants for department and quarter counts, tax rate, etc.
		private static final int NUM_DEPARTMENTS = 5; // Example: 5 departments
		private static final int NUM_QUARTERS = 4; // 4 quarters in a year
		private static final int MONTHS_PER_QUARTER = 3; // 3 months per quarter
		private static final double TAX_RATE = 0.17; // Tax rate of 17%

		// Arrays for month and department names
		private static final String[] MONTH_NAMES = { "January", "February", "March", "April", "May", "June", "July",
				"August", "September", "October", "November", "December" };

		private static final String[] DEPARTMENT_NAMES = { "Electrical", "Kitchen", "Bathroom", "Soft Furnishing",
				"Accessories" };

		// 3D array to store sales data
		private double[][][] salesData;

		// Constructor to initialise and populate sales data
		public SalesData() {
			salesData = new double[NUM_DEPARTMENTS][NUM_QUARTERS][MONTHS_PER_QUARTER];

			// Populated array with the provided sales data
			// Note: Sales data is in thousands, i.e., 67 means 67,000
			// Note: Scalable so that Quarter 1 and Quarter 4 data can be added if required

			// Quarter 2 data
			salesData[0][1] = new double[] { 67, 63, 78 }; // Electrical
			salesData[1][1] = new double[] { 65, 67, 56 }; // Kitchen
			salesData[2][1] = new double[] { 63, 63, 65 }; // Bathroom
			salesData[3][1] = new double[] { 18, 24, 22 }; // Soft Furnishing
			salesData[4][1] = new double[] { 16, 23, 21 }; // Accessories

			// Quarter 3 data
			salesData[0][2] = new double[] { 78, 104, 103 }; // Electrical
			salesData[1][2] = new double[] { 45, 56, 72 }; // Kitchen
			salesData[2][2] = new double[] { 71, 73, 69 }; // Bathroom
			salesData[3][2] = new double[] { 19, 17, 16 }; // Soft Furnishing
			salesData[4][2] = new double[] { 19, 20, 19 }; // Accessories

		}

		// Method to choose a department, a quarter, and print sales data for that
		// selection
		public void chooseDepartmentAndQuarter() {
			Scanner scanner = new Scanner(System.in);
			boolean continueRunning = true;

			while (continueRunning) {
				try {
					// Prompt the user to choose a department
					System.out.println("Choose a department: ");
					System.out.println("1. Electrical");
					System.out.println("2. Kitchen ");
					System.out.println("3. Bathroom ");
					System.out.println("4. Soft Furnishing");
					System.out.println("5. Accessories");
					int department = scanner.nextInt();

					// Prompt the user to choose a quarter
					System.out.println("Choose a quarter (1-4):");
					int quarter = scanner.nextInt();

					// Check if the input is valid
					if (department >= 1 && department <= NUM_DEPARTMENTS && quarter >= 1 && quarter <= NUM_QUARTERS) {
						// Print sales data for the selected department and quarter
						printSalesDataForDepartmentAndQuarter(department, quarter);

						// User chooses the algorithm to run
						System.out.println("Choose an option:");
						System.out.println("1. Total Sales for selected department and quarter");
						System.out.println("2. Total Sales for selected quarter and add tax");
						System.out.println("3. Find most effective month for each department in the selected quarter");
						int choice = scanner.nextInt();

						// Process the choice using a switch statement
						switch (choice) {
						case 1:
							// Print total sales for the chosen department and quarter
							printTotalSales(department, quarter);
							break;
						case 2:
							// Calculate and print total sales with tax for the chosen quarter
							calculateAndPrintTaxForQuarter(quarter);
							break;
						case 3:
							// Find and display the most effective month for each department in the chosen
							// quarter
							mostEffectiveMonthForDepartments(quarter);
							break;
						default:
							// Handle invalid choice
							System.out.println("Invalid choice. Please select 1, 2, or 3.");
							break;
						}
					} else {
						// Handle invalid input for department and quarter
						System.out.println("Invalid input. Please enter a valid department (1-5) and quarter (1-4).");
					}
				} catch (Exception e) {
					System.out.println("An error occurred. Please try again.");
				} finally {
					// Ask the user if they want to continue
					System.out.println("Do you want to continue? (yes/no)");
					String userInput = scanner.next();
					if ("no".equalsIgnoreCase(userInput)) {
						continueRunning = false;
					}
				}
			}

			scanner.close(); // Close the scanner to avoid resource leaks
		}

		// Method to print sales data for a specific department in a specific quarter
		private void printSalesDataForDepartmentAndQuarter(int department, int quarter) {
			System.out.println(
					"Sales data for " + DEPARTMENT_NAMES[department - 1] + " Department in Quarter " + quarter + ":");
			int startMonthIndex = (quarter - 1) * MONTHS_PER_QUARTER;
			for (int month = 0; month < MONTHS_PER_QUARTER; month++) {
				String formattedNumber = String.format("%.2f", salesData[department - 1][quarter - 1][month] * 1000);
				System.out.println(MONTH_NAMES[startMonthIndex + month] + ": £" + formattedNumber);
			}

			System.out.println();
		}

		// Calculate the total sales for a department in a quarter
		public double calculateTotalSales(int department, int quarter) {
			double total = 0;

			// Sum up sales for each month in the selected quarter
			for (int month = 0; month < MONTHS_PER_QUARTER; month++) {
				total += salesData[department - 1][quarter - 1][month] * 1000;
			}
			return total;
		}

		// Print the total sales for a department in a quarter
		public void printTotalSales(int department, int quarter) {
			
			// Retrieve and display the total sales amount
			double totalSales = calculateTotalSales(department, quarter);
			System.out.println("Total sales for " + DEPARTMENT_NAMES[department - 1] + " Department in Quarter "
					+ quarter + ": £" + String.format("%.2f", totalSales));
		}

		// Calculate and display total sales with tax for a quarter
		public void calculateAndPrintTaxForQuarter(int quarter) {
			double totalSalesForQuarter = 0;
			// Calculate total sales across all departments for the quarter
			for (int dept = 0; dept < NUM_DEPARTMENTS; dept++) {
				totalSalesForQuarter += calculateTotalSales(dept + 1, quarter);
			}
			System.out.println(
					"Total sales for Quarter " + quarter + ": £" + String.format("%.2f", totalSalesForQuarter));
			System.out.println();
			
			// Calculate and display tax amount
			double tax = totalSalesForQuarter * TAX_RATE;
			double totalWithTax = totalSalesForQuarter + tax;

			System.out.println("Total tax (17%): " + String.format("%.2f", totalWithTax - totalSalesForQuarter));
			System.out.println();
			// Print total amount including tax
			System.out.println("Total amount for Quarter " + quarter + " after adding tax: £"
					+ String.format("%.2f", totalWithTax));
		}

		// Find and print the most effective sales month for each department in a quarter
		public void mostEffectiveMonthForDepartments(int quarter) {
			// Iterate through departments to find the month with highest sales
			for (int dept = 0; dept < NUM_DEPARTMENTS; dept++) {
				int mostEffectiveMonthIndex = 0;
				double highestSales = salesData[dept][quarter - 1][0];

				// Find the month with the highest sales
				for (int month = 1; month < MONTHS_PER_QUARTER; month++) {
					double currentMonthSales = salesData[dept][quarter - 1][month];
					if (currentMonthSales > highestSales) {
						highestSales = currentMonthSales;
						mostEffectiveMonthIndex = month;
					}
				}

				// Print the most effective month and its sales
				System.out.println("Quarter " + quarter + ": " + DEPARTMENT_NAMES[dept] + ", "
						+ MONTH_NAMES[mostEffectiveMonthIndex + (quarter - 1) * MONTHS_PER_QUARTER] + ", £"
						+ String.format("%.2f", highestSales * 1000));
			}
		}
	}
}
