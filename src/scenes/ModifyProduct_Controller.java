
package scenes;

import classes.Inventory;
import static classes.Inventory.getAllProducts;
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
import static scenes.Main_SceneController.modifyProductIndex;

/**
 *  author Scott Weekley
 * Student ID: 001026151
 */



public class ModifyProduct_Controller implements Initializable {
   
    /**
     * Initializes the controller class.
     */
    
    int productIndex = modifyProductIndex();
   
    
    ////Create Current Parts associated to Products///////////
    
    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private int productID;
    
    //Table update Methods/////////////////////////////////////////////////////
    
    public void updateAddProductsTableView() {
        ModifyaddProductsTableView.setItems(Inventory.getAllParts());
    }

    public void updateCurrentPartTableView() {
        ModifyaddProductsToDeleteTableView.setItems(currentParts);
    }
    
    //Table FXML Labels//////////////////////////////////////////////
    
    @FXML private TableView<Part> ModifyaddProductsTableView;
    @FXML private TableColumn<Part, Integer> ModifyaddProductPartID;
    @FXML private TableColumn<Part, String> ModifyaddProductPartName;
    @FXML private TableColumn<Part, Integer> ModifyaddProductStockLevel;
    @FXML private TableColumn<Part, Double> ModifyaddProductPrice;
    @FXML private TableView<Part> ModifyaddProductsToDeleteTableView;
    @FXML private TableColumn<Part, Integer> modifyCurrentProductPartId;
    @FXML private TableColumn<Part, String> modifyCurrentProductPartName;
    @FXML private TableColumn<Part, Integer> modifyCurrentProductStock;
    @FXML private TableColumn<Part, Double> modifyCurrentProductPrice;
    
   /////Text input field declarations////////////////////////////////////
    @FXML private TextField productId;
    @FXML private TextField modifyProductName;
    @FXML private TextField modifyProductInventory;
    @FXML private TextField modifyProductPrice;
    @FXML private TextField modifyProductMin;
    @FXML private TextField modifyProductMax;
    @FXML private TextField ModifyaddProductTableSearchField;
 
    ////Add Part to product button///////////////////////////////////////
     @FXML void addPartToProduct(ActionEvent event) 
     {
        Part part = ModifyaddProductsTableView.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        updateCurrentPartTableView();
    }
  //////Search button click///////////////////////////////////////////
      @FXML void searchPartButton(ActionEvent event) throws IOException {
        
        String searchResults = ModifyaddProductTableSearchField.getText(); {
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
            ModifyaddProductsTableView.setItems(tempPartList);
        }
     
           
        }
    }
  //////Remove Parts from Product button//////////////////////////////////
     
      @FXML
    void removePartFromProduct(ActionEvent event) {

        Part part = ModifyaddProductsToDeleteTableView.getSelectionModel().getSelectedItem();

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
        
        String ProductName = modifyProductName.getText();
        String inventory = modifyProductInventory.getText();
        String price = modifyProductPrice.getText();
        String max = modifyProductMax.getText();
        String min = modifyProductMin.getText();
     
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
          Inventory.updateProduct(productIndex, newProduct);
                                  
        Parent ModifyPart = FXMLLoader.load(getClass().getResource("Main_Scene.fxml"));
        Scene scene = new Scene(ModifyPart);
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
        
       Product modifiedProduct = getAllProducts().get(productIndex);
        productID = getAllProducts().get(productIndex).getProductID();
        productId.setText("AUTO GEN: " + productID);
        modifyProductName.setText(modifiedProduct.getProductName());
        modifyProductInventory.setText(Integer.toString(modifiedProduct.getProductInStock()));
        modifyProductPrice.setText(Double.toString(modifiedProduct.getProductPrice()));
        modifyProductMin.setText(Integer.toString(modifiedProduct.getProductMin()));
        modifyProductMax.setText(Integer.toString(modifiedProduct.getProductMax()));
        currentParts = modifiedProduct.getProductParts();
        
    ModifyaddProductPartID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
    ModifyaddProductPartName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
    ModifyaddProductStockLevel.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
    ModifyaddProductPrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
    modifyCurrentProductPartId.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
    modifyCurrentProductPartName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
    modifyCurrentProductStock.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
    modifyCurrentProductPrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
    
        updateAddProductsTableView();
        updateCurrentPartTableView();
        
    }    
    
}
