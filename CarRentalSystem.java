import java.util.*;

// Base Car class
abstract class Car {
    private String carId;
    private String model;
    private boolean isAvailable;

    public Car(String carId, String model) {
        this.carId = carId;
        this.model = model;
        this.isAvailable = true;
    }

    public String getCarId()
     { return carId; }
    public String getModel()
     { return model; }
    public boolean isAvailable()
     { return isAvailable; }

    public void rentCar()
     { isAvailable = false; }
    public void returnCar()
     { isAvailable = true; }

    public abstract double calculateRentalPrice(int days);

    @Override
    public String toString() {
        return "Car ID: " + carId + ", Model: " + model + ", Available: " + isAvailable;
    }
}

// Sedan class
class Sedan extends Car {
    public Sedan(String carId, String model) {
        super(carId, model);
    }

    @Override
    public double calculateRentalPrice(int days) {
        return days * 80.0; // Example rate for Sedan
    }
}

// SUV class
class SUV extends Car {
    public SUV(String carId, String model) {
        super(carId, model);
    }

    @Override
    public double calculateRentalPrice(int days) {
        return days * 120.0; // Example rate for SUV
    }
}

// Customer class
class Customer {
    private String name;
    private String customerId;

    public Customer(String name, String customerId) {
        this.name = name;
        this.customerId = customerId;
    }

    public String getName() { return name; }
    public String getCustomerId() { return customerId; }
}

// Rental class
class Rental {
    private Customer customer;
    private Car car;
    private int days;

    public Rental(Customer customer, Car car, int days) {
        this.customer = customer;
        this.car = car;
        this.days = days;
        this.car.rentCar(); // Mark car as rented
    }

    public double getTotalPrice() {
        return car.calculateRentalPrice(days);
    }

    public void returnCar() {
        car.returnCar();
    }

  
    public String toString() {
        return "\nRental Info:\nCustomer: " + customer.getName() +
               "\nCar: " + car.getModel() +
               "\nDays: " + days +
               "\nTotal Price: ₹" + getTotalPrice();
    }
}

// Main class
public class CarRentalSystem {
    private static List<Car> carList = new ArrayList<>();
    private static List<Rental> rentalList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeCars();
        while (true) {
            System.out.println("\n=== Car Rental System ===");
            System.out.println("1. View Available Cars");
            System.out.println("2. Rent a Car");
            System.out.println("3. Return a Car");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1: viewCars(); break;
                case 2: rentCar(); break;
                case 3: returnCar(); break;
                case 4: System.out.println("Thank you for using the system!"); return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void initializeCars() {
        carList.add(new Sedan("S001", "Honda City"));
        carList.add(new SUV("U001", "Toyota Fortuner"));
        carList.add(new Sedan("S002", "Hyundai Verna"));
        carList.add(new SUV("U002", "Mahindra XUV700"));
    }

    private static void viewCars() {
        System.out.println("\n--- Available Cars ---");
        for (Car car : carList) {
            System.out.println(car);
        }
    }

    private static void rentCar() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = new Customer(name, customerId);

        viewCars();
        System.out.print("Enter Car ID to rent: ");
        String carId = scanner.nextLine();
        Car selectedCar = null;
        for (Car car : carList) {
            if (car.getCarId().equalsIgnoreCase(carId) && car.isAvailable()) {
                selectedCar = car;
                break;
            }
        }

        if (selectedCar == null) {
            System.out.println("Car not available or invalid ID.");
            return;
        }

        System.out.print("Enter rental duration (days): ");
        int days = scanner.nextInt();
        scanner.nextLine(); // consume newline
        Rental rental = new Rental(customer, selectedCar, days);
        rentalList.add(rental);
        System.out.println("✅ Car rented successfully!");
        System.out.println(rental);
    }

    private static void returnCar() {
        System.out.print("Enter Car ID to return: ");
        String carId = scanner.nextLine();
        Rental foundRental = null;

        for (Rental rental : rentalList) {
            if (rental.toString().contains(carId)) {
                foundRental = rental;
                break;
            }
        }

        if (foundRental != null) {
            foundRental.returnCar();
            rentalList.remove(foundRental);
            System.out.println("✅ Car returned successfully!");
        } else {
            System.out.println("Rental record not found.");
        }
    }
}
