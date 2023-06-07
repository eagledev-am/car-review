package com.example.carstore1;

public class Car {
    private int id ;
    private String model;
    private String color ;

    private String description;

    private String image;

    private double dpl;

    public Car(){

        this.model = "";
        this.color = "";
        this.description ="";
        this.image = "";
        this.dpl = 0.0;
    }
    public Car(int id, String model, String color, double dpl) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.dpl = dpl;
    }

    public Car(int id, String model, String color, String description, String image, double dpl) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.description = description;
        this.image = image;
        this.dpl = dpl;
    }

    public Car(String model, String color, String description, String image, double dpl) {
        this.model = model;
        this.color = color;
        this.description = description;
        this.image = image;
        this.dpl = dpl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDpl(double dpl) {
        this.dpl = dpl;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public double getDpl() {
        return dpl;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", dpl=" + dpl +
                '}';
    }
}
