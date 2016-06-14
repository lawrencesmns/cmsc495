package edu.umuc.cmsc495.shoppinglist.Objects;

/**
 * Created by James on 6/6/2016.
 */
public class Ingredient {
    protected String name;
    protected int Measurement, Count;
    protected boolean isCrossedOut, isValid, usesMeasure, usesCount;

    //TODO: Determine how enum should be implemented on measure/count

    Ingredient(String name, int Measurement, int Count){
        this.name = name;
        this.Measurement = Measurement;
        this.Count = Count;
        this.isCrossedOut = false;

    }

    /**
     * comapreTo compares the names of the current ingredient to the given ingredient
     * @param i
     * @return int
     */
    protected int compareTo(Ingredient i){
        return this.name.compareTo(i.getName());
    }

    //Checks that the ingredient's name has at least one character
    private boolean isValid(){
        if(name.length() > 0){
            return true;
        }else{return false;}
    }

    //Set Methods
    protected void editName(String name){
        this.name = name;
    }
    protected void editMeasurement(int Measurement){
        this.Measurement = Measurement;
    }
    protected void addMeasurement(int add) { this.Measurement += add; }
    protected void subMeasurement(int sub) { this.Measurement += sub; }
    protected void editCount(int Count){
        this.Count = Count;
    }
    protected void addCount(int add) { this.Count += add; }
    protected void subCount(int sub) { this.Count += sub; }
    protected void CrossOutItem(){
        this.isCrossedOut = true;
    }
    protected void UncrossItem(){
        this.isCrossedOut = false;
    }

    //Get Methods
    protected String getName(){
        return this.name;
    }
    protected int getMeasurement(){
        return this.Measurement;
    }
    protected int getCount(){
        return this.Count;
    }
    protected boolean getCrossed(){
        return this.isCrossedOut;
    }
}
