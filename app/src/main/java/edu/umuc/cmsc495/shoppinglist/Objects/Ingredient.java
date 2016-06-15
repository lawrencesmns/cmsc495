package edu.umuc.cmsc495.shoppinglist.Objects;

/**
 * Created by James on 6/6/2016.
 */
public class Ingredient {
    protected String name, Measurement;
    protected Double Count;
    protected boolean isCrossedOut, isValid, usesMeasure, usesCount;

    //TODO: Determine how enum should be implemented on measure/count

    Ingredient(String name, String Measurement, Double Count){
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
    protected void editMeasurement(String Measurement){
        this.Measurement = Measurement;
    }
    protected void editCount(Double Count){
        this.Count = Count;
    }
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
    protected String getMeasurement(){
        return this.Measurement;
    }
    protected Double getCount(){
        return this.Count;
    }
    protected boolean getCrossed(){
        return this.isCrossedOut;
    }
}
