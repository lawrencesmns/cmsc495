package edu.umuc.cmsc495.shoppinglist.Objects;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import edu.umuc.cmsc495.shoppinglist.R;
import edu.umuc.cmsc495.shoppinglist.UI.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeUI();

        //usage:  Recipe and shoppingList methods setName, setInstructions(recipe only), addIngredient,
        //        changeIngredient, and removeIngredient all persist to datastorage immediately.

/*
        Recipe recipe = new Recipe(this);
        recipe.createNewRecipe(); //auto increments the name and sets it to the first new increment
        recipe.setName("testRecipe2"); //changes the name
        recipe.setInstructions("these are the instructions");

        Ingredient i = new Ingredient();
        i.setName("testIngredient");
        i.setMeasurement("Tbsp"); //from spinner or modal
        i.setCountFull("2"); //from spinner or modal
        i.setCountPartial("1/8"); //from spinner or modal
        recipe.addIngredient(i); //add the ingredient to the object which persists to database

        ShoppingList sl4 = new ShoppingList(this);
        sl4.setName("testShoppingList3");
        Recipe r5 = new Recipe(this);
        r5.loadRecipe("testRecipe1");
        sl4.addRecipe(r5);

        ShoppingList sl5 = new ShoppingList(this);
        sl5.loadShoppingList("testShoppingList3");
        for(Recipe r:sl5.getRecipesAdded()){
            Recipe j = r;
        }

        //recipe.removeIngredient(i); //remove the ingredient

        recipe.setInstructions("these are the new instructions"); //change the instructions
*/
/*
        ShoppingList sl = new ShoppingList(this); //creates a new list with db pernanence via this.
        sl.createNewList(); //auto increments the name and sets it to the first new increment not taken.
        sl.setName("testShoppingList1"); //sets the name of the list
        //create a new ingredient
        Ingredient j = new Ingredient();
        j.setName("testIngredient"); //sets ingredient name
        j.setMeasurement("Tbsp"); //from spinner or modal
        j.setCountFull("2"); //from spinner or modal
        j.setCountPartial("1/8"); //from spinner or modal
        sl.addIngredient(j);
        //sl.removeIngredient(j); //uncomment to remove this ingredient
        //adds a second ingredient
        Ingredient k = new Ingredient();
        k.setName("testIngredient1");
        k.setMeasurement("Tsp"); //from spinner or modal
        k.setCountFull("1"); //from spinner or modal
        k.setCountPartial("1/4"); //from spinner or modal
        sl.addIngredient(k);

        //load a shopping list by name and iterate through ingredients, get some other properties
        ShoppingList sl1 = new ShoppingList(this);
        sl1.loadShoppingList("testShoppingList1");
        String name = sl1.getName();
        for(Ingredient i:sl1.getIngredientList()){
           String tostr = i.toString();
        }
        String created = sl1.getCreatedOn();
        String lastMod = sl1.getLastModifiedOn();
*/

        /*


        //demonstrate auto increment naming for ShoppingList
        ShoppingList sl2 = new ShoppingList(this);
        sl2.createNewList(); //auto increments the name
        String defaultName = sl2.getName();
*/

/*        //demonstrate auto increment naming for Recipe
        Recipe r2 = new Recipe(this);
        r2.createNewRecipe(); //auto increments the name and sets it to the first new increment
        String defaultName = r2.getName();
        */
/*
        //load shoopping list
        ShoppingList sl3 = new ShoppingList(this);
        sl3.loadShoppingList("Untitled Shopping List - 2");
        String sl3Name = sl3.getName();
        for (Ingredient i : sl3.getIngredientList()) {
            Ingredient j = i;
        }
*/

        /*
        //load recipe
        Recipe r4 = new Recipe(this);
        r4.loadRecipe("Untitled Recipe - 3");
        String r4Name = r4.getName();
        for (Ingredient i : r4.getIngredientList()) {
            Ingredient j = i;
        }
*/

/*      //testing the addrecipe ingredients to shopping list.
        Recipe r6 = new Recipe(this);
        r6.setName("grandma's pie");
        Ingredient i1 = new Ingredient();
        i1.setName("apple");
        i1.setCountFull("5");
        r6.addIngredient(i1);
        Ingredient i5 = new Ingredient();
        i5.setName("cinnamon");
        i5.setCountFull("3");
        r6.addIngredient(i5);

        Recipe r7 = new Recipe(this);
        r7.setName("mom's pie");
        Ingredient i2 = new Ingredient();
        i2.setName("apples");
        i2.setCountFull("6");
        r7.addIngredient(i2);

        ShoppingList sl7 = new ShoppingList(this);
        Ingredient i3= new Ingredient();
        i3.setName("apples");
        i3.setCountFull("7");
        sl7.addIngredient(i3);
        Ingredient i4=new Ingredient();
        i4.setName("peaches");
        i4.setCountFull("4");
       // sl7.removeIngredient(i3);
        sl7.addIngredient(i4);


        sl7.addRecipe(r6);
        sl7.addRecipe(r7);

        for(Ingredient i:sl7.getIngredientList()){
            Ingredient j = i;
        }
       // sl7.removeRecipe(r7);
        for(Ingredient i:sl7.getIngredientList()){
            Ingredient j = i;
        }
*/

    }

    /*This method initializes all UI elements for this activity.*/
    void initializeUI(){
        setContentView(R.layout.activity_main);
        Button btnNewShoppingList = (Button) findViewById(R.id.btn_new_list);
        btnNewShoppingList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewShoppingListActivity.class);
                startActivity(intent);
            }
        });

        Button btnManageShoppingList = (Button) findViewById(R.id.btn_manage_list);
        btnManageShoppingList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ManageShoppingLists.class);
                startActivity(intent);
            }
        });

        Button btnNewRecipe = (Button) findViewById(R.id.btn_new_recipe);
        btnNewRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),RecipesList.class);
                startActivity(intent);
            }
        });

        Button btnManageRecipe = (Button) findViewById(R.id.btn_manage_recipes);
        btnManageRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ManageRecipes.class);
                startActivity(intent);
            }
        });

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle(R.string.app_name);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle(R.string.app_name);
                actionBar.setDisplayHomeAsUpEnabled(false);
                initializeUI();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}