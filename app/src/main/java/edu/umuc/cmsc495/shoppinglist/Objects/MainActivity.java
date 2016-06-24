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

        //usage:  all setters and add... and remove... persist to datastorage immediately.
/*
        Recipe recipe = new Recipe(this);
        recipe.setName("testRecipe1");
        recipe.setInstructions("these are the instructions");

        Ingredient i = new Ingredient();
        i.setName("testIngredient");
        i.setMeasurement("Tbsp"); //from spinner or modal
        i.setCountFull("2"); //from spinner or modal
        i.setCountPartial("1/8"); //from spinner or modal
        recipe.addIngredient(i);
        recipe.removeIngredient(i);
        recipe.setInstructions("these are the new instructions");
*/
/*
        ShoppingList sl = new ShoppingList(this);
        sl.setName("testShoppingList1");

        Ingredient j = new Ingredient();
        j.setName("testIngredient");
        j.setMeasurement("Tbsp"); //from spinner or modal
        j.setCountFull("2"); //from spinner or modal
        j.setCountPartial("1/8"); //from spinner or modal
        sl.addIngredient(j);
        //sl.removeIngredient(j); //uncomment to remove this ingredient

        Ingredient k = new Ingredient();
        k.setName("testIngredient1");
        k.setMeasurement("Tsp"); //from spinner or modal
        k.setCountFull("1"); //from spinner or modal
        k.setCountPartial("1/4"); //from spinner or modal
        sl.addIngredient(k);


        ShoppingList sl1 = new ShoppingList(this);
        sl1.loadShoppingList("testShoppingList1");
        String name = sl1.getName();
        for(Ingredient i:sl1.getIngredientList()){
           String tostr = i.toString();
        }
        String created = sl1.getCreatedOn();
        String lastMod = sl1.getLastModifiedOn();
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