
package scenes;

import classes.InHouse;
import classes.Inventory;
import classes.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
  author Scott Weekley
 * Student ID: 001026151
 */

public class Add_PartController implements Initializable {
    /**
     * Initializes the controller class.
     */
    private boolean isInHouse = false;
    private int partID = 0;
    
    
    //////Radio Button Controlls//////////////////////////////////////
    
    @FXML private RadioButton inHouseButton;
    @FXML private RadioButton outSourcedButton;
    @FXML private Label inORoutLabel;
    @FXML private TextField inORoutTextBox;
    @FXML ToggleGroup radioButtonGroup = new ToggleGroup();  
  
    
    public void addPartInHouseRaidoButtonSelect(ActionEvent event) {
      isInHouse = true;
      inORoutLabel.setText("Machine ID");
      
    }
    
     public void addPartOutSourcedRaidoButtonSelect(ActionEvent event) {
      isInHouse = false;
      inORoutLabel.setText("Company Name");
      
    }
    
    /////Text input field declarations////////////////////////////////////
    
    @FXML private TextField partId;
    @FXML private TextField partName;
    @FXML private TextField partInventory;
    @FXML private TextField partPrice;
    @FXML private TextField partMin;
    @FXML private TextField partMax;
  
    
    ///SavePartButtonClick////////////////////////////////////////////////////
    
    @FXML void savePartButtonClick(ActionEvent event) throws IOException {
       
        String PartName = partName.getText();
        String inventory = partInventory.getText();
        String price = partPrice.getText();
        String max = partMax.getText();
        String min = partMin.getText();
        String outORin = inORoutTextBox.getText();
        
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
            
            
       
        if (isInHouse == true) {
         InHouse inPart = new InHouse();
            
          inPart.setPartID(partID);
          inPart.setPartName(PartName);
          inPart.setPartStock(Integer.parseInt(inventory));
          inPart.setPartPrice(Double.parseDouble(price));
          inPart.setPartMax(Integer.parseInt(max));
          inPart.setPartMin(Integer.parseInt(min));
          inPart.setMachineID(Integer.parseInt(outORin));
          Inventory.addPart(inPart);
                                  
        }
        
        if (isInHouse == false) {
          Outsourced outPart = new Outsourced();
          outPart.setPartID(partID);
          outPart.setPartName(PartName);
          outPart.setPartStock(Integer.parseInt(inventory));
          outPart.setPartPrice(Double.parseDouble(price));
          outPart.setPartMax(Integer.parseInt(max));
          outPart.setPartMin(Integer.parseInt(min));
          outPart.setCompanyName(outORin);
          Inventory.addPart (outPart);
        }
        
              Parent SavePart = FXMLLoader.load(getClass().getResource("Main_Scene.fxml"));
               Scene scene = new Scene(SavePart);
               Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
               window.setScene(scene);
               window.show(); 
        }
    }
    
    ////Cancel Button onClick event //////////////////////
    @FXML private void cancelButtonClick(ActionEvent event) throws IOException {
         
         Alert confirm = new Alert (Alert.AlertType.CONFIRMATION);
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
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
     
        inHouseButton.setToggleGroup(radioButtonGroup);
        outSourcedButton.setToggleGroup(radioButtonGroup);
        inORoutLabel.setText("");
        partID = Inventory.getPartID();
        partId.setText("AUTO GEN - " + partID);
    }    
    
}
