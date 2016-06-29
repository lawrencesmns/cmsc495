package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.umuc.cmsc495.shoppinglist.Objects.Recipe;
import edu.umuc.cmsc495.shoppinglist.R;

public class RecipesList extends AppCompatActivity {

    private ArrayAdapter<String> mRecipeAdapter;
    private DragSortListView draggableList;
    private Recipe mRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeUI();

        //this is dummy data!
        String[] dummyRecipesArray = {
                "Ice cream", "Apple juice", "Elephant", "Candy", "Beer", "Vodka", "Bread", "Chips",
                "Mozzarella", "New York Strip Steak", "Oatmeal Raisin Cookies", "Chicken", "Radishes",
                "Onions"
        };

        List<String> dummyRecipes = new ArrayList(Arrays.asList(dummyRecipesArray));
        mRecipeAdapter = new ArrayAdapter(this,
                R.layout.list_item_added_ingredient, R.id.list_item_ingredient_textview, dummyRecipes);

        draggableList = (DragSortListView) findViewById(R.id.listview_added_ingredient);
        draggableList.setAdapter(mRecipeAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_recipes_menu, menu);
        return true;
    }

    private void initializeUI(){

        setContentView(R.layout.activity_recipes);



        DragSortListView.DropListener onDrop =
                new DragSortListView.DropListener() {
                    @Override
                    public void drop(int from, int to) {
                        String item = mRecipeAdapter.getItem(from);

                        mRecipeAdapter.remove(item);
                        mRecipeAdapter.insert(item, to);
                    }
                };

        DragSortListView.RemoveListener onRemove =
                new DragSortListView.RemoveListener() {
                    @Override
                    public void remove(int which) {
                        mRecipeAdapter.remove(mRecipeAdapter.getItem(which));
                    }
                };

        DragSortListView.DragScrollProfile ssProfile =
                new DragSortListView.DragScrollProfile() {
                    @Override
                    public float getSpeed(float w, long t) {
                        if (w > 0.8f) {
                            // Traverse all views in a 10 milliseconds
                            return ((float) mRecipeAdapter.getCount()) / 0.01f;
                        } else {
                            return 10.0f * w;
                        }
                    }
                };

        draggableList = (DragSortListView) findViewById(R.id.listview_added_ingredient);
        draggableList.setDropListener(onDrop);
        draggableList.setRemoveListener(onRemove);
        draggableList.setDivider(null);
        draggableList.setDragScrollProfile(ssProfile);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.new_recipe_list_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //        .setAction("Action", null).show();
                Intent intent = new Intent(view.getContext(),NewIngredient.class);
                startActivity(intent);

            }
        });
    }



}
