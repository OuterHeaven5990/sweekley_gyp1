
package scenes;

import classes.Inventory;
import classes.Part;
import classes.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
  author Scott Weekley
 * Student ID: 001026151
 */



public class AddProduct_Controller implements Initializable {
   
    /**
     * Initializes the controller class.
     */
    
    ////Create Current Parts associated to Products///////////
    
    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private int productID;
    
    //Table update Methods/////////////////////////////////////////////////////
    
    public void updateAddProductsTableView() {
        addProductsTableView.setItems(Inventory.getAllParts());
    }

    public void updateCurrentPartTableView() {
        addProductsToDeleteTableView.setItems(currentParts);
    }
    
    //Table FXML Labels//////////////////////////////////////////////
    
    @FXML private TableView<Part> addProductsTableView;
    @FXML private TableColumn<Part, Integer> addProductPartID;
    @FXML private TableColumn<Part, String> addProductPartName;
    @FXML private TableColumn<Part, Integer> addProductStockLevel;
    @FXML private TableColumn<Part, Double> addProductPrice;
    @FXML private TableView<Part> addProductsToDeleteTableView;
    @FXML private TableColumn<Part, Integer> currentProductPartId;
    @FXML private TableColumn<Part, String> currentProductPartName;
    @FXML private TableColumn<Part, Integer> currentProductStock;
    @FXML private TableColumn<Part, Double> currentProductPrice;
    
   /////Text input field declarations////////////////////////////////////
    @FXML private TextField productId;
    @FXML private TextField productName;
    @FXML private TextField productInventory;
    @FXML private TextField productPrice;
    @FXML private TextField productMin;
    @FXML private TextField productMax;
    @FXML private TextField addProductTableSearchField;
 
    ////Add Part to product button///////////////////////////////////////
     @FXML void addPartToProduct(ActionEvent event) 
     {
        Part part = addProductsTableView.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        updateCurrentPartTableView();
    }
  //////Search button click///////////////////////////////////////////
      @FXML void searchPartButton(ActionEvent event) throws IOException {
        
        String searchResults = addProductTableSearchField.getText(); {
          int tempIndex;
          
        if (searchResults.isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Search Error!");
            alert.setHeaderText("");
            alert.setContentText("Search Box empty");
            alert.showAndWait();
        }
        
        else {
            tempIndex = Inventory.lookupPart(searchResults);
            Part temp = Inventory.getAllParts().get(tempIndex);
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            tempPartList.add(temp);
            addProductsTableView.setItems(tempPartList);
        }
     
           
        }
    }
  //////Remove Parts from Product button//////////////////////////////////
     
      @FXML
    void removePartFromProduct(ActionEvent event) {

        Part part = addProductsToDeleteTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("");
        alert.setContentText("Are you sure you want to delete part " + part.getPartName() + " from parts?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.out.println("Part deleted.");
            currentParts.remove(part);
        } else {
            System.out.println("Cancel clicked.");
        }
    }

     
        ///SaveProductButtonClick////////////////////////////////////////////////////
    
    @FXML void saveProductButtonClick(ActionEvent event) throws IOException {
        
        String ProductName = productName.getText();
        String inventory = productInventory.getText();
        String price = productPrice.getText();
        String max = productMax.getText();
        String min = productMin.getText();
     
        if (Integer.parseInt(max) < Integer.parseInt(min))
        {
         Alert confirm = new Alert (Alert.AlertType.INFORMATION);
         confirm.initModality(Modality.NONE);
         confirm.setTitle("Error");
         confirm.setHeaderText("");
         confirm.setContentText("Maximum Value les than Minimum Value");
         Optional <ButtonType> buttonClick = confirm.showAndWait();
         
        }
         
         
         else {
            
        
         Product newProduct = new Product();
            
          newProduct.setProductID(productID);
          newProduct.setProductName(ProductName);
          newProduct.setProductStock(Integer.parseInt(inventory));
          newProduct.setProductPrice(Double.parseDouble(price));
          newProduct.setProductMax(Integer.parseInt(max));
          newProduct.setProductMin(Integer.parseInt(min));
          Inventory.addProduct(newProduct);
                                  
        Parent SavePart = FXMLLoader.load(getClass().getResource("Main_Scene.fxml"));
        Scene scene = new Scene(SavePart);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show(); 
        }
    }
    ////Cancel Button onClick event //////////////////////
     @FXML private void cancelButtonClick(ActionEvent event) throws IOException {
         
         Alert confirm = new Alert (AlertType.CONFIRMATION);
         confirm.initModality(Modality.NONE);
         confirm.setTitle("Confirm");
         confirm.setHeaderText("");
         confirm.setContentText("Are you sure?");
         Optional <ButtonType> buttonClick = confirm.showAndWait();
         
         if(buttonClick.get() == ButtonType.OK){
         Scene scene = new Scene (FXMLLoader.load(getClass().getResource("Main_Scene.fxml")));
         Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
         window.setScene(scene);
         window.show();
         }
         
         else {
             System.out.println("");
         }
  }      
    //////////////////////////////////////////////////////////////////
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        addProductPartID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        addProductPartName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        addProductStockLevel.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        addProductPrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());

        currentProductPartId.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        currentProductPartName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        currentProductStock.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        currentProductPrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());

        productID = Inventory.getProductID();
        productId.setText("AUTO GEN - " + productID);
        
        updateAddProductsTableView();
        updateCurrentPartTableView();
        
    }    
    
}
