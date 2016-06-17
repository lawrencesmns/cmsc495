package edu.umuc.cmsc495.shoppinglist.UI;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.umuc.cmsc495.shoppinglist.R;

public class NewShoppingListActivity extends AppCompatActivity {

    private ArrayAdapter<String> mRecipeAdapter;

    private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {
                    String item = mRecipeAdapter.getItem(from);

                    mRecipeAdapter.remove(item);
                    mRecipeAdapter.insert(item, to);
                }
            };

    private DragSortListView.RemoveListener onRemove =
            new DragSortListView.RemoveListener() {
                @Override
                public void remove(int which) {
                    mRecipeAdapter.remove(mRecipeAdapter.getItem(which));
                }
            };




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

        DragSortListView draggableList = (DragSortListView) findViewById(R.id.listview_added_ingredient);
        draggableList.setDropListener(onDrop);
        draggableList.setRemoveListener(onRemove);
        draggableList.setDivider(null);
        draggableList.setSelector(R.drawable.ic_menu_black_24dp);
        draggableList.setAdapter(mRecipeAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_shopping_list_actions, menu);
        return true;
    }

    private void initializeUI(){

        setContentView(R.layout.activity_new_shopping_list);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.new_shopping_list_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //TODO: Prompt user to save unfinished lists

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
