import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

// Class 1: Employee
class Employee {
    private String name;
    private double salary;
    private double productivityRating; // scale of 1.0 to 10.0

    public Employee(String name, double salary, double productivityRating) {
        this.name = name;
        this.salary = salary;
        this.productivityRating = productivityRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getProductivityRating() {
        return productivityRating;
    }

    public void setProductivityRating(double productivityRating) {
        this.productivityRating = productivityRating;
    }

    // Required method 1 (excluding getters/setters)
    public void givePerformanceReview(double bonus, double newRating) {
        this.salary += bonus;
        this.productivityRating = newRating;
    }

    // Required method 2 (excluding getters/setters)
    public String getEmployeeSummary() {
        return String.format("Employee: %-15s | Salary: $%,10.2f | Productivity: %.1f/10.0", 
                name, salary, productivityRating);
    }
}

// Class 2: DepartmentManager
class DepartmentManager {
    private String departmentName;
    private ArrayList<Employee> teamMembers;

    public DepartmentManager(String departmentName) {
        this.departmentName = departmentName;
        this.teamMembers = new ArrayList<>();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public ArrayList<Employee> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(ArrayList<Employee> teamMembers) {
        this.teamMembers = teamMembers;
    }

    // Required method 1 (excluding getters/setters): Hire new employee
    public void hireEmployee(String name, double salary, double rating) {
        Employee newEmp = new Employee(name, salary, rating);
        teamMembers.add(newEmp);
        System.out.println("[SUCCESS] Hired new team member: " + name);
    }

    // Required method 2 (excluding getters/setters): Audit and fire underperformers 
    // (earning above-average salary but below-average productivity)
    public void auditAndTrimStaff() {
        if (teamMembers.isEmpty()) {
            System.out.println("No employees in the department to audit.");
            return;
        }

        double totalSalary = 0;
        double totalProductivity = 0;

        for (Employee emp : teamMembers) {
            totalSalary += emp.getSalary();
            totalProductivity += emp.getProductivityRating();
        }

        double avgSalary = totalSalary / teamMembers.size();
        double avgProductivity = totalProductivity / teamMembers.size();

        System.out.println("\n--- DEPARTMENT PERFORMANCE AUDIT ---");
        System.out.printf("Average Salary: $%,.2f | Average Productivity: %.2f/10.0\n", avgSalary, avgProductivity);

        ArrayList<Employee> toRemove = new ArrayList<>();
        for (Employee emp : teamMembers) {
            if (emp.getSalary() > avgSalary && emp.getProductivityRating() < avgProductivity) {
                toRemove.add(emp);
            }
        }

        if (toRemove.isEmpty()) {
            System.out.println("No employees met the termination criteria (above-average salary & below-average productivity).");
        } else {
            System.out.println("The following underperforming employees have been let go:");
            for (Employee emp : toRemove) {
                System.out.println(" - " + emp.getEmployeeSummary());
                teamMembers.remove(emp);
            }
        }
    }

    public void displayTeamBySalary() {
        if (teamMembers.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        ArrayList<Employee> sortedList = new ArrayList<>(teamMembers);
        sortedList.sort(Comparator.comparingDouble(Employee::getSalary).reversed());

        System.out.println("\n--- TEAM RANKED BY SALARY (HIGH TO LOW) ---");
        for (Employee emp : sortedList) {
            System.out.println(emp.getEmployeeSummary());
        }
    }

    public void displayTeamByProductivity() {
        if (teamMembers.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        ArrayList<Employee> sortedList = new ArrayList<>(teamMembers);
        sortedList.sort(Comparator.comparingDouble(Employee::getProductivityRating).reversed());

        System.out.println("\n--- TEAM RANKED BY PRODUCTIVITY (HIGH TO LOW) ---");
        for (Employee emp : sortedList) {
            System.out.println(emp.getEmployeeSummary());
        }
    }
}

// Class 3: Main Test Class
public class EmployeeDatabaseApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DepartmentManager manager = new DepartmentManager("Engineering & TechReclaim");

        // Pre-populate some employees for testing
        manager.hireEmployee("Alice Smith", 85000.0, 8.5);
        manager.hireEmployee("Bob Jones", 110000.0, 5.2); // Above avg salary, low productivity -> audit target
        manager.hireEmployee("Charlie Brown", 62000.0, 7.8);
        manager.hireEmployee("Diana Prince", 95000.0, 9.1);

        int choice = 0;
        do {
            System.out.println("\n=======================================");
            System.out.println("    TECHRECLAIM EMPLOYEE DATABASE       ");
            System.out.println("=======================================");
            System.out.println("1. View Team Ranked by Salary");
            System.out.println("2. View Team Ranked by Productivity");
            System.out.println("3. Hire New Employee");
            System.out.println("4. Run Performance Audit (Fire Underperformers)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        manager.displayTeamBySalary();
                        break;
                    case 2:
                        manager.displayTeamByProductivity();
                        break;
                    case 3:
                        System.out.print("Enter employee name: ");
                        String name = scanner.nextLine();
                        
                        double salary = -1;
                        while (salary < 0) {
                            System.out.print("Enter annual salary ($): ");
                            if (scanner.hasNextDouble()) {
                                salary = scanner.nextDouble();
                                if (salary < 0) System.out.println("Salary cannot be negative.");
                            } else {
                                System.out.println("Invalid input. Please enter a number.");
                                scanner.next();
                            }
                        }

                        double rating = -1;
                        while (rating < 0 || rating > 10) {
                            System.out.print("Enter productivity rating (1.0 - 10.0): ");
                            if (scanner.hasNextDouble()) {
                                rating = scanner.nextDouble();
                                if (rating < 1.0 || rating > 10.0) {
                                    System.out.println("Rating must be between 1.0 and 10.0.");
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a number.");
                                scanner.next();
                            }
                        }
                        scanner.nextLine(); // consume newline
                        manager.hireEmployee(name, salary, rating);
                        break;
                    case 4:
                        manager.auditAndTrimStaff();
                        break;
                    case 5:
                        System.out.println("Exiting application. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select between 1 and 5.");
                }
            } else {
                System.out.println("[ERROR] Invalid input. Please enter an integer.");
                scanner.next(); // clear invalid token
            }
        } while (choice != 5);

        scanner.close();
    }
}

