package com.yummy.project.menu;

import com.yummy.project.state.State;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
    public enum Type {

        appetizer, dish, beverage
    }

    private String name = "";
    private String description = "";
    private Type type = Type.appetizer;
    private Double price = 0.00;
    private String ingredients = "";
    private int calories = 0;
    private String imageName;
    private int cookTime;

    public Item() {
    }

    public Item(String name, Type type, Double price, String ingredients, int calories) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.ingredients = ingredients;
        this.calories = calories;
        this.cookTime = 0;
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

    public void setDescription(String desc) {
        this.description = desc;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getImageName() {
        return this.imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
