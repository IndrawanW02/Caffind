package id.ac.binus.caffind.models;

public class CoffeeShopModel {
    int id;
    String name;
    String description;
    String location;
    String operationHours;
    String priceRange;
    byte[] image;

    public CoffeeShopModel(int id, String name, String description, String location, String operationHours, String priceRange, byte[] image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.operationHours = operationHours;
        this.priceRange = priceRange;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOperationHours() {
        return operationHours;
    }

    public void setOperationHours(String operationHours) {
        this.operationHours = operationHours;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
