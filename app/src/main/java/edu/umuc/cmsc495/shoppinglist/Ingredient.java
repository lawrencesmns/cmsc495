package edu.umuc.cmsc495.shoppinglist;

/**
 * Created by James on 6/6/2016.
 */
public class Ingredient {
    protected String name;
    protected int Measurement, Count;
    protected boolean isCrossedOut, isValid;

    //TODO: Determine how enum should be implemented on measure/count

    Ingredient(String name, int Measurement, int Count){
        this.name = name;
        this.Measurement = Measurement;
        this.Count = Count;
        this.isCrossedOut = false;

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
    protected void editCount(int Count){
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
