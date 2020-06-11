/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import classes.Part;
import classes.Inventory;
import static classes.Inventory.validatePartDelete;
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
import sweekley_gyp1.Sweekley_gyp1;


/**
 *
 ** author Scott Weekley
 * Student ID: 001026151
 */
public class Main_SceneController implements Initializable {
    
    
    
/////Table view variables/////////////////////////////////////////////
    
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> inventoryColumn;
    @FXML private TableColumn<Part, Double> priceColumn;
    
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> inventoryLevelColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    
    

    //Table View Methods/////////////////////////////////////
    
    public void updatePartTableView() {
        partTableView.setItems(Inventory.getAllParts());
    }
    
      public void updateProductTableView() {
        productTableView.setItems(Inventory.getAllProducts());
    }
    
 
/////////Exit Button OnClick Event ////////////////////////////////////////// 
      
  @FXML void Exit_Button_Click(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setHeaderText("");
        alert.setTitle("Conformation");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {
            System.out.println("");
        }
    }
    
   ////////Search Button calls/////////////////////////////////////////////////////
    
    @FXML private TextField partsSearchField;
    @FXML private TextField productsSearchField;
    
    @FXML void searchPartButton(ActionEvent event) throws IOException {
        
        String searchResults = partsSearchField.getText(); {
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
            partTableView.setItems(tempPartList);
        }
     
           
        }
    }
    
    
    
    
        @FXML void searchProductButton(ActionEvent event) throws IOException {
        
        String searchResults = productsSearchField.getText(); {
          int tempIndex;
          
        if (searchResults.isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Search Error!");
            alert.setHeaderText("");
            alert.setContentText("Search Box empty");
            alert.showAndWait();
        }
        
        else {
            tempIndex = Inventory.lookupProduct(searchResults);
            Product temp = Inventory.getAllProducts().get(tempIndex);
            ObservableList<Product> tempProductList = FXCollections.observableArrayList();
            tempProductList.add(temp);
            productTableView.setItems(tempProductList);
        }
     
           
        }
    }
    
    //////DELETE BUTTON CALLS /////////////
    
    @FXML void DeletePartsClick(ActionEvent event) throws IOException {
        Part temp = partTableView.getSelectionModel().getSelectedItem();
        
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Product Delete");
            alert.setHeaderText("Confirm?");
            alert.setContentText("Are you sure you want to delete " + temp.getPartName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Inventory.deletePart(temp);
                updatePartTableView();
                System.out.println("Part " + temp.getPartName() + " was removed.");
            } else {
                System.out.println("Part " + temp.getPartName() + " was not removed.");
            }
        }
    
    
    
      @FXML void DeleteProductClick(ActionEvent event) throws IOException {
        Product temp = productTableView.getSelectionModel().getSelectedItem();
        
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Product Delete");
            alert.setHeaderText("Confirm?");
            alert.setContentText("Are you sure you want to delete " + temp.getProductName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                Inventory.deleteProduct(temp);
                updatePartTableView();
                System.out.println("Part " + temp.getProductName() + " was removed.");
            } else {
                System.out.println("Part " + temp.getProductName() + " was not removed.");
            }
        }
    
    
    ///////Add Parts FXML Call /////////////////////////////////////////////////
   @FXML
    void add_Parts_Button_Click(ActionEvent event) throws IOException {

        Parent Add_Parts = FXMLLoader.load(getClass().getResource("Add_Part.fxml"));
        Scene add_PartsScene = new Scene(Add_Parts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(add_PartsScene);
        window.show();
    }
    
    //Add Prouct FXML Call ///////////////////////////////////////////////
    @FXML
    void add_Product_Button_Click(ActionEvent event) throws IOException {

        Parent Add_Product = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene add_Product = new Scene(Add_Product);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(add_Product);
        window.show();
    }
    
    //Modify Prouct FXML Call ///////////////////////////////////////////////
    private static Product modifyProduct;
    private static int indexProduct;
    
    public static int modifyProductIndex() {
        return indexProduct;
    }
    
     @FXML void modify_Product_Button_Click(ActionEvent event) throws IOException {
        modifyProduct = productTableView.getSelectionModel().getSelectedItem();
        indexProduct = Inventory.getAllProducts().indexOf(modifyProduct);
        Parent Modify_Prodcut = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        Scene modify_Product = new Scene(Modify_Prodcut);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(modify_Product);
        window.show();
    }
    
     //Modify Part FXML Call ///////////////////////////////////////////////
    private static Part modifyPart;
    private static int indexPart;
    
    public static int modifyPartIndex() {
        return indexPart;
    }
    
    @FXML void modify_Part_Button_Click(ActionEvent event) throws IOException {
        modifyPart = partTableView.getSelectionModel().getSelectedItem();
        indexPart = Inventory.getAllParts().indexOf(modifyPart);
        Parent Modify_Part = FXMLLoader.load(getClass().getResource("Modify_Part.fxml"));
        Scene modify_Part = new Scene(Modify_Part);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(modify_Part);
        window.show();
    }
    
   ///////////////////////Public Overide////////////////////////////////////////
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partIdColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        inventoryColumn.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        
       productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProperty().asObject());
       productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
       inventoryLevelColumn.setCellValueFactory(cellData -> cellData.getValue().productInvProperty().asObject());
       productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
        
        updateProductTableView();
        updatePartTableView();
    }
    
    public void setMain(Sweekley_gyp1 mainApp) {
        updatePartTableView(); 
        updateProductTableView();
    }
}
