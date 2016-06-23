package edu.umuc.cmsc495.shoppinglist.Objects;

import java.io.Serializable;

/**
 * Created by James on 6/6/2016.
 */
public class Ingredient implements Serializable {
    private String name = "", measurement = "", countFull = "", countPartial = "";

    private boolean isCrossedOut = false;

    public Ingredient(String name, String measurement, String countFull, String countPartial, boolean isCrossedOut){
        this.name = name;
        this.measurement = measurement;
        this.countFull = countFull;
        this.countPartial = countPartial;
        this.isCrossedOut = isCrossedOut;
    }

    public Ingredient(){

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
    protected void setName(String name){
        this.name = name;
    }
    protected void setMeasurement(String Measurement){
        this.measurement = Measurement;
    }
    protected void setCountFull(String countFull){
        this.countFull = countFull;
    }
    protected void setCountPartial(String countPartial){
        this.countPartial = countPartial;
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
        return this.measurement;
    }
    protected String getCountFullString(){
        return this.countFull;
    }
    protected String getCountPartialString(){
        return this.countPartial;
    }
    protected Float getCountFloat(){
        Float full = Float.parseFloat(this.countFull );
        Float partial = UiUtils.getDecimalFromFraction(this.countPartial);
        return UiUtils.round(full + partial,3);
    }
    protected Boolean getCrossed(){
        return this.isCrossedOut;
    }

    @Override
    public String toString(){
        return this.getCountFullString() + " " + this.getCountPartialString() + "  " + this.measurement + "    " + this.name;
    }
}
