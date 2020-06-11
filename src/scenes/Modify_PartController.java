
package scenes;

import classes.InHouse;
import classes.Inventory;
import static classes.Inventory.getAllParts;
import classes.Outsourced;
import classes.Part;
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
import static scenes.Main_SceneController.modifyPartIndex;

/**
  author Scott Weekley
 * Student ID: 001026151
 */

public class Modify_PartController implements Initializable {
  private int partID;
  private boolean isInHouse;
  int partIndex = modifyPartIndex();
    
     //////Radio Button Controlls//////////////////////////////////////
    
    @FXML private RadioButton modifyInHouseButton;
    @FXML private RadioButton modifyOutSourcedButton;
    @FXML private Label modifyInORoutLabel;
    @FXML private TextField modifyInORoutTextBox;
    @FXML ToggleGroup radioButtonGroup = new ToggleGroup();
    
    @FXML public void ModifyaddPartInHouseRaidoButtonSelect(ActionEvent event) {
      isInHouse = true;
      modifyInORoutLabel.setText("Machine ID");
      
    }
    
     @FXML public void ModifyaddPartOutSourcedRaidoButtonSelect(ActionEvent event) {
      isInHouse = false;
      modifyInORoutLabel.setText("Company Name");
      
    }
    
   /////Text input field declarations////////////////////////////////////
    
    @FXML private TextField modifyPartId;
    @FXML private TextField modifyPartName;
    @FXML private TextField modifyPartInventory;
    @FXML private TextField modifyPartPrice;
    @FXML private TextField modifyPartMin;
    @FXML private TextField modifyPartMax;
    
    ///SavePartButtonClick////////////////////////////////////////////////////
    
    @FXML void savePartButtonClick(ActionEvent event) throws IOException {
      
        String PartName = modifyPartName.getText();
        String inventory = modifyPartInventory.getText();
        String price = modifyPartPrice.getText();
        String max = modifyPartMax.getText();
        String min = modifyPartMin.getText();
        String outORin = modifyInORoutTextBox.getText();
        
        modifyInHouseButton.setToggleGroup(radioButtonGroup);
        modifyOutSourcedButton.setToggleGroup(radioButtonGroup);
        
        if(radioButtonGroup.getSelectedToggle().equals(modifyInHouseButton)) {
            isInHouse = true;
        }
        
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
          Inventory.updatePart(partIndex, inPart);
                                  
        }
        
        else {
          Outsourced outPart = new Outsourced();
          outPart.setPartID(partID);
          outPart.setPartName(PartName);
          outPart.setPartStock(Integer.parseInt(inventory));
          outPart.setPartPrice(Double.parseDouble(price));
          outPart.setPartMax(Integer.parseInt(max));
          outPart.setPartMin(Integer.parseInt(min));
          outPart.setCompanyName(outORin);
          Inventory.updatePart(partIndex, outPart);
        }
        
              Parent SavePart = FXMLLoader.load(getClass().getResource("Main_Scene.fxml"));
               Scene scene = new Scene(SavePart);
               Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
               window.setScene(scene);
               window.show(); 
        }
    
    }
    ////Cancel Button onClick event //////////////////////////////////////////
    
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        modifyInHouseButton.setToggleGroup(radioButtonGroup);
        modifyOutSourcedButton.setToggleGroup(radioButtonGroup);
       
       Part modifiedPart = getAllParts().get(partIndex);
       partID = getAllParts().get(partIndex).getPartID();
       modifyPartId.setText("AutoSet to: " + partID);
       modifyPartName.setText(modifiedPart.getPartName());
       modifyPartInventory.setText(Integer.toString(modifiedPart.getPartStock()));
       modifyPartPrice.setText(Double.toString(modifiedPart.getPartPrice()));
       modifyPartMin.setText(Integer.toString(modifiedPart.getPartMin()));
       modifyPartMax.setText(Integer.toString(modifiedPart.getPartMax()));
    if (modifiedPart instanceof InHouse) {
            modifyInORoutTextBox.setText(Integer.toString(((InHouse) getAllParts().get(partIndex)).getPartMachineID()));
            modifyInORoutLabel.setText("Machine ID");
            modifyInHouseButton.setSelected(true);

        } if (modifiedPart instanceof Outsourced) {
            modifyInORoutTextBox.setText(((Outsourced) getAllParts().get(partIndex)).getCompanyName());
            modifyInORoutLabel.setText("Company Name");
            modifyOutSourcedButton.setSelected(true);
            
       
            
    
}
    }
}
