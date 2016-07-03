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
    public int compareTo(Ingredient i){
        return this.name.compareTo(i.getName());
    }

    //Checks that the ingredient's name has at least one character
    private boolean isValid(){
        if(name.length() > 0){
            return true;
        }else{return false;}
    }



    //Set Methods
    public void setName(String name){
        this.name = name;
    }
    public void setMeasurement(String Measurement){
        this.measurement = Measurement;
    }
    public void setCountFull(String countFull){
        this.countFull = countFull;
    }
    public void setCountPartial(String countPartial){
        this.countPartial = countPartial;
    }
    public void setCrossedOut(Boolean crossedOut){
        this.isCrossedOut = crossedOut;
    }

    //Get Methods
    public String getName(){
        return this.name;
    }
    public String getMeasurement(){
        return this.measurement;
    }
    public String getCountFullString(){
        return this.countFull;
    }
    public String getCountPartialString(){
        return this.countPartial;
    }
    public Float getCountFloat(){
        Float full = Float.parseFloat(this.countFull );
        Float partial = UiUtils.getDecimalFromFraction(this.countPartial);
        return UiUtils.round(full + partial,3);
    }
    public Boolean getCrossed(){
        return this.isCrossedOut;
    }

    //original:
        @Override
        public String toString(){
            return this.getCountFullString() + " " + this.getCountPartialString() + "   " + this.measurement + "    " + this.name;
        }

   // @Override
   // public String toString(){ //lawrence, somehow this changed back to the spaced version?  Changing back to comma separated for UI display --martin
   //     return this.getCountFullString() + "," + this.getCountPartialString() + "," + this.measurement + "," + this.name;
   // }
}
