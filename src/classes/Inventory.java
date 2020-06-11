package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Scott Weekley
 * Student ID: 001026151
 */
public class Inventory {
    private static  ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static  ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partAutoGen = 0;
    private static int productAutoGen = 0;
  
    public Inventory() {
        
    }
   
     public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
     
     public static void addProduct(Product product) {
        allProducts.add(product);
    }
     
     public static int lookupPart(String partName) {
        boolean isFound = false;
        int index = 0;
        if (isInteger(partName)) {
            for (int i = 0; i < allParts.size(); i++) {
                if (Integer.parseInt(partName) == allParts.get(i).getPartID()) {
                    index = i;
                    isFound = true;
                }
            }
        } 
        else {
            for (int i = 0; i < allParts.size(); i++) {
                if (partName.equals(allParts.get(i).getPartName())) {
                    index = i;
                    isFound = true;
                }
            }
        }
        if (isFound = true) {
            return index;
        } 
        else {
            System.out.println("No parts found.");
            return -1;
        }
    }
     
     public static int lookupProduct(String productName) {
        boolean isFound = false;
        int index = 0;

        if (isInteger(productName)) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (Integer.parseInt(productName) == allProducts.get(i).getProductID()) {
                    index = i;
                    isFound = true;
                }
            }
        } else {
            for (int i = 0; i < allProducts.size(); i++) {
                if (productName.equals(allProducts.get(i).getProductName())) {
                    index = i;
                    isFound = true;
                }
            }
        }
        if (isFound = true) {
            return index;
        } 
        else {
            System.out.println("No products found.");
            return -1;
        }
    }
     
    public static void updatePart(int index, Part part) {
        allParts.set(index, part);
    }
    
    public static void updateProduct(int index, Product product) {
        allProducts.set(index, product);
    }

    public static void deletePart(Part oldPart) {
        allParts.remove(oldPart);
    }
    
    public static void deleteProduct(Product product) {
        allProducts.remove(product);
    }

    public static boolean validatePartDelete(Part part) {
        boolean isFound = false;

        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductParts().contains(part)) {
                isFound = true;
            }
        }
        return isFound;
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } 
        catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
     public static int getProductID() {
        productAutoGen++;
        return productAutoGen;
    }
     
       public static int getPartID() {
        partAutoGen++;
        return partAutoGen;
    }
}




