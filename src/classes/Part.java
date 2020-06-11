
package classes;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Scott Weekley
 * Student ID: 001026151
 */

public abstract class Part {

    //Part Variables///////////////////////////////////////////////////////////
    private final IntegerProperty partID;
    private final StringProperty partName;
    private final DoubleProperty Price;
    private final IntegerProperty stock;
    private final IntegerProperty min;
    private final IntegerProperty max;

    //Default Constructor 
    public Part() {
        partID = new SimpleIntegerProperty();
        partName = new SimpleStringProperty();
        Price = new SimpleDoubleProperty();
        stock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
    }

    public IntegerProperty partIDProperty() {
        return partID;
    }

    public StringProperty partNameProperty() {
        return partName;
    }

    public DoubleProperty partPriceProperty() {
        return Price;
    }

    public IntegerProperty partInvProperty() {
        return stock;
    }

//Getters///////////////////////////////////////////////
    public int getPartID() {
        return this.partID.get();
    }

    public String getPartName() {
        return this.partName.get();
    }

    public double getPartPrice() {
        return this.Price.get();
    }

    public int getPartStock() {
        return this.stock.get();
    }

    public int getPartMin() {
        return this.min.get();
    }

    public int getPartMax() {
        return this.max.get();
    }

    //Setters/////////////////////////////////////////////////////////////////
    
    public void setPartID(int partID) {
        this.partID.set(partID);
    }

    public void setPartName(String name) {
        this.partName.set(name);
    }

    public void setPartPrice(double price) {
        this.Price.set(price);
    }

    public void setPartStock(int inStock) {
        this.stock.set(inStock);
    }

    public void setPartMin(int min) {
        this.min.set(min);
    }

    public void setPartMax(int max) {
        this.max.set(max);
    }

    //Input Validation Method
    public static String isPartValid(String name, int min, int max, int inv, double price, String errorMessage) {
        if (name == null) {
            errorMessage = errorMessage + ("Name field cannot be blank.");
        }
        if (inv < 1) {
            errorMessage = errorMessage + ("Inventory value must be greater than 0.");
        }
        if (price < 1) {
            errorMessage = errorMessage + ("Price value must be greater than $0");
        }
        if (min > max) {
            errorMessage = errorMessage + ("Minimum Value must be less than Maximum value.");
        }
        if (inv < min || inv > max) {
            errorMessage = errorMessage + ("Inventory must reside between minimum and maximum values.");
        }
        return errorMessage;
    }
}
