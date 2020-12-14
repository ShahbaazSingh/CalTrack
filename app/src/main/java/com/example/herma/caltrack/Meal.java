package com.example.herma.caltrack;

public class Meal {

    private String name;
    private int calories;
    private int fat;
    private int carbohydrates;
    private int protein;
    private boolean selected;
    private long dbID;
    private int numberSelected;

    public Meal(){

    }

    public Meal(String name, int calories, int fat, int carbohydrates, int protein, boolean selected, long dbID, int numberSelected){
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.selected = selected;
        this.selected = false;
        this.dbID = dbID;
        this.numberSelected = numberSelected;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCalories(int calories){
        this.calories = calories;
    }

    public void setFat(int fat){
        this.fat = fat;
    }

    public void setCarbohydrates(int carbohydrates){
        this.carbohydrates = carbohydrates;
    }

    public void setProtein(int protein){
        this. protein = protein;
    }

    public String getName(){
        return name;
    }

    public int getCalories(){
        return calories;
    }

    public int getFat(){
        return fat;
    }

    public int getCarbohydrates(){
        return carbohydrates;
    }

    public int getProtein(){

        return protein;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected){ this.selected = selected; }

    public void setDbID(long dbID){ this.dbID = dbID; }

    public long getdbID(){ return this.dbID; }

    public void setNumberSelected(int numberSelected){
        this.numberSelected = numberSelected;
    }

    public int getNumberSelected(){
        return numberSelected;
    }

    public String getString(){
        String toString = "Calories: " +calories+"   "+"Fat: " +fat+"g   "+"Carbohydrates: " +carbohydrates+"g   "+"Protein: " +protein+"g   ";
        return toString;
    }

}
