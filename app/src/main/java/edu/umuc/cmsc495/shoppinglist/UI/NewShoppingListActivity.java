package edu.umuc.cmsc495.shoppinglist.UI;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.umuc.cmsc495.shoppinglist.R;

public class NewShoppingListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();

        //this is dummy data!
        String[] dummyRecipesArray = {
                "Ice cream", "Apple juice", "Elephant", "Candy", "Beer", "Vodka", "Bread", "Chips",
                "Mozzarella", "New York Strip Steak", "Oatmeal Raisin Cookies", "Chicken", "Radishes",
                "Onions"
        };

        List<String> dummyRecipes = new ArrayList(Arrays.asList(dummyRecipesArray));
        ArrayAdapter<String> mRecipeAdapter = new ArrayAdapter(this,
                R.layout.list_item_new_shopping, R.id.list_item_recipename_textview, dummyRecipes);


        // Inflate the layout for this fragment
        ((ListView) findViewById(R.id.listview_recipesnew)).setAdapter(mRecipeAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_shopping_list_actions, menu);
        return true;
    }

    private void initialize(){

        setContentView(R.layout.activity_new_shopping_list);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.new_shopping_list_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



}
