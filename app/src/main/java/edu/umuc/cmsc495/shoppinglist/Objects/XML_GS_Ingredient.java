package edu.umuc.cmsc495.shoppinglist.Objects;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by James on 6/14/2016.
 *
 * XML Getter/Setter class for Ingredient object
 */
public class XML_GS_Ingredient extends XML_GettersSetters{

    private ArrayList<String> ingredientName = new ArrayList<String>();
    public ArrayList<String> getIngredientName(){ return this.ingredientName; }
    public void setIngredientName(String name){
        this.ingredientName.add(name);
        Log.i("ingredient name:", name);
    }

    private ArrayList<String> measurement = new ArrayList<String>();
    public ArrayList<String> getMeasurement(){ return this.measurement; }
    public void setRecipeName(String measurement){
        this.measurement.add(measurement);
        Log.i("ingredient measurement:", measurement);
    }

    private ArrayList<Integer> count = new ArrayList<Integer>();
    public ArrayList<Integer> getCount(){ return this.count; }
    public void setCount(Integer count){
        this.count.add(count);
        Log.i("ingredient count:", count.toString());
    }

}
