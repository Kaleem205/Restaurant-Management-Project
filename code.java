
package restaurantmanagementsystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestaurantManagementSystem {

    public static void main(String[] args) {
        int choice;
        Scanner scan = new Scanner(System.in);
        Menu m = new Menu();

        do {
            System.out.println("Restaurant");
            System.out.println("1) Admin");
            System.out.println("2) Customer");
            System.out.println("3) Exit");
            System.out.println("Enter a choice:");
            choice = scan.nextInt();

            switch (choice) {
                case 1 -> {
                    Admin a1 = new Admin();
                    if (a1.LogIn()) {
                        m.adminMenu();
                    }
                }
                case 2 -> {
                    m.customerMenu();
                }
                case 3 -> {
                    System.out.println("Fuckkkk Offff!");
                }
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 3);
    }
}

class Admin {

    private String username;
    private String password;
    Scanner sc = new Scanner(System.in);

    public Admin() {
        this.username = "kaleem";
        this.password = "kaleem12";
    }

    public boolean LogIn() {
        System.out.print("Enter username: ");
        username = sc.nextLine();

        if (username.equals("kaleem")) {
            System.out.print("Enter password: ");
            password = sc.nextLine();

            if (password.equals("kaleem12")) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Incorrect password!");
                return false;
            }

        } else {
            System.out.println("User not found!");
            return false;
        }
    }
}

class Menu {
    private ArrayList<Cuisine> item = new ArrayList<>();
    private ArrayList<Cuisine> order = new ArrayList<>();
    private final File menuFile = new File("C:\\Users\\Administrator\\Documents\\RestaurantManagementSystem\\menu.txt");
    private final File cartFile = new File("C:\\Users\\Administrator\\Documents\\RestaurantManagementSystem\\cart.txt");
    Scanner sc = new Scanner(System.in);

    public void adminMenu() {
        boolean exit = false;
        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1. Add Cuisine");
            System.out.println("2. View Menu");
            System.out.println("3. Update price of a cuisine");
            System.out.println("4. Delete a cuisine");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addCuisine();
                case 2 -> viewMenu();
                case 3 -> updatePrice();
                case 4 -> deleteCuisine();
                case 5 -> exit = true;
                default -> System.out.println("Invalid choice! Please try again.");
            }
        } while (!exit);
    }

    void addCuisine() {
        System.out.print("Enter the name of the cuisine: ");
        String name = sc.next();
        System.out.print("Enter the price of the cuisine: ");
        String price = sc.next();
        item.add(new Cuisine(name, price));
        saveMenuToFile(item);
    }

    void saveMenuToFile(ArrayList<Cuisine> item) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(menuFile))) {
            for (Cuisine x : item) {//for(int i=0 ; i<= item.lenght()     ; i++)
                writer.write(x.getName() + "," + x.getPrice());
                writer.newLine();
            }
            System.out.println("Menu saved to file successfully.");
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ArrayList<Cuisine> readMenuFromFile() {
        ArrayList<Cuisine> menuList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(menuFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    String price = parts[1];
                    menuList.add(new Cuisine(name, price));
                }
            }
            System.out.println("Menu loaded from file successfully.");
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return menuList;
    }

    void viewMenu() {
        ArrayList<Cuisine> menuList = readMenuFromFile();
        if (menuList.isEmpty()) {
            System.out.println("No Cuisines Available...");
        } else {
            for (Cuisine cuisine : menuList) {
                System.out.println(cuisine);
            }
        }
    }

    void updatePrice() {
        ArrayList<Cuisine> menuList = readMenuFromFile();
        if (menuList.isEmpty()) {
            System.out.println("No Cuisines Available... To add Cuisine choose option 1...");
        } else {
            System.out.print("Enter cuisine name whose price you want to update: ");
            String updatedCuisine = sc.next();
            boolean found = false;

            for (Cuisine cuisine : menuList) {
                if (cuisine.getName().equalsIgnoreCase(updatedCuisine)) {
                    System.out.print("Enter new price: ");
                    String updatedPrice = sc.next();
                    cuisine.setPrice(updatedPrice);
                    saveMenuToFile(menuList);
                    System.out.println("Cuisine's updated price: " + cuisine);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Cuisine not found!");
            }
        }
    }

    void deleteCuisine() {
        ArrayList<Cuisine> menuList = readMenuFromFile();
        if (menuList.isEmpty()) {
            System.out.println("No Cuisines Available... To add Cuisine choose option 1...");
        } else {
            System.out.print("Enter cuisine name you want to delete: ");
            String cuisineToDelete = sc.next();
            boolean found = false;

            for (Cuisine cuisine : menuList) {
                if (cuisine.getName().equalsIgnoreCase(cuisineToDelete)) {
                    menuList.remove(cuisine);
                    saveMenuToFile(menuList);
                    System.out.println("Cuisine deleted successfully.");
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Cuisine not found!");
            }
        }
    }

    public void customerMenu() {
        boolean exit = false;
        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("1. View Menu");
            System.out.println("2. Add to Cart");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> viewMenu();
                case 2 -> addCart();
                case 3 -> exit = true;
                default -> System.out.println("Invalid choice! Please try again.");
            }
        } while (!exit);
    }

    void addCart() {
        ArrayList<Cuisine> menuList = readMenuFromFile();
        if (menuList.isEmpty()) {
            System.out.println("No Cuisines Available... To add Cuisine choose option 1...");
        } else {
            boolean exit = false;
            do {
                System.out.println("Enter Cuisine to order...\nEnter \"Exit\" when order is complete...");
                String chosenCuisine = sc.next();
                if (chosenCuisine.equalsIgnoreCase("exit")) {
                    exit = true;
                } else {
                    boolean found = false;
                    for (Cuisine x : menuList) {
                        if (x.getName().equalsIgnoreCase(chosenCuisine)) {
                            order.add(new Cuisine(x.getName(), x.getPrice()));
                            saveCuisineToCart(order);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Cuisine not found!");
                    }
                }
            } while (!exit);
            System.out.println("Items added to cart: " + readOrderFromCart());
        }
    }

    void saveCuisineToCart(ArrayList<Cuisine> order) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cartFile))) {
            for (Cuisine x : order) {
                writer.write(x.getName() + "," + x.getPrice());
                writer.newLine();
            }
            System.out.println("Cart saved to file successfully.");
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ArrayList<Cuisine> readOrderFromCart() {
        ArrayList<Cuisine> orderList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(cartFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    String price = parts[1];
                    orderList.add(new Cuisine(name, price));
                }
            }
            System.out.println("Cart loaded from file successfully.");
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderList;
    }
}



class Cuisine {

    private String name , price;
  

    public Cuisine(String name, String price) {
        this.name = name;
        this.price = price;
    }

    
    public String getName() {
        return name;
    }

    
    public String getPrice() {
        return price;
    }

    
    public void setPrice(String price) {
        this.price = price;
    }

    
    @Override
    public String toString() {
        return name + " : Rs " + price;
    }
}
