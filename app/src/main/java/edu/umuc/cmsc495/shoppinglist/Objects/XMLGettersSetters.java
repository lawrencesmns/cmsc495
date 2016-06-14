package edu.umuc.cmsc495.shoppinglist.Objects;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.*;
import edu.umuc.cmsc495.shoppinglist.Objects.*;

/**
 * Created by James on 6/14/2016.
 */
public class XMLGettersSetters {

    private ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        public ArrayList<Recipe> getRecipes(){ return this.recipes; }
        public void setRecipe(Recipe newRecipe){
            this.recipes.add(newRecipe);
            Log.i("This is the recipe:", newRecipe.getRecipeName());
        }

    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

        public ArrayList<Ingredient> getIngredients(){ return this.ingredients; }
        public void setIngredient(Ingredient newIngredient){
            this.ingredients.add(newIngredient);
            Log.i("This is the ingredient:", newIngredient.getName());
        }

    private ArrayList<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();

        public ArrayList<ShoppingList> getShoppingLists(){ return this.shoppingLists; }
        public void setShoppingLists(ShoppingList newShoppingList){
            this.shoppingLists.add(newShoppingList);
            Log.i("This is the ingredient:", newShoppingList.getName());
        }

}
