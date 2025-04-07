package food_app.models;

public class Restaurant {
    private int restId;
    private String restName;
    private String cuisineType;
    private int restAvail;
    private int closingTime;
    private int openingTime;

    // Getters and Setters

    public int getRestId() {
        return restId;
    }

    public void setRestId(int restId) {
        this.restId = restId;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public int getRestAvail() {
        return restAvail;
    }

    public void setRestAvail(int restAvail) {
        this.restAvail = restAvail;
    }

    public int getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(int closingTime) {
        this.closingTime = closingTime;
    }

    public int getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(int openingTime) {
        this.openingTime = openingTime;
    }
}