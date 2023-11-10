package com.tamuc.usedcarsmanagement.usedcarsPojo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "UsedCars")
public class UsedCarsPojo {

    @Transient
    public static final String SEQUENCE_NAME = "usedcars_sequence";
    @Id
    private String id;
    private String carMake;
    private String carModel;
    private String fuelType;
    private int year;
    private int mileage;
    private double price;
    private String suspension;

    public UsedCarsPojo(String carMake, String carModel, String fuelType, int year, int mileage, double price, String suspension) {
        this.carMake = carMake;
        this.carModel = carModel;
        this.fuelType = fuelType;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
        this.suspension = suspension;
    }
    public UsedCarsPojo() {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSuspension() {
        return suspension;
    }

    public void setSuspension(String suspension) {
        this.suspension = suspension;
    }
    @Override
    public String toString() {
        return "UsedCarsPojo{" +
                "id='" + id + '\'' +
                ", carMake='" + carMake + '\'' +
                ", carModel='" + carModel + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", year=" + year +
                ", mileage=" + mileage +
                ", price=" + price +
                ", suspension='" + suspension + '\'' +
                '}';
    }

}
