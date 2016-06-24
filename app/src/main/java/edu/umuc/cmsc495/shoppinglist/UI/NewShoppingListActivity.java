package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.LinkedList;
import java.util.List;

import edu.umuc.cmsc495.shoppinglist.Objects.Ingredient;
import edu.umuc.cmsc495.shoppinglist.Objects.ShoppingList;
import edu.umuc.cmsc495.shoppinglist.R;

public class NewShoppingListActivity extends AppCompatActivity {

    private ArrayAdapter<Ingredient> mRecipeAdapter;
    private DragSortListView draggableList;
    List<Ingredient> ingredients = new LinkedList<Ingredient>();
    private final String WORKING_LIST_NAME = "_____temp shopping list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeUI();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_shopping_list_actions, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Save current adapter as a temporary shopping list
        ShoppingList shoppingList = new ShoppingList();
        for(Ingredient i : ingredients)
                shoppingList.addIngredient(i);
        shoppingList.setName(WORKING_LIST_NAME);

        Boolean isSaved = shoppingList.save(); //Does this overrwrite existing list?
        if(!isSaved){
            //TODO: create a dialog saying there isn't enough space and the list will be lost
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        //Crappy hack because the draggableListView doesnt show the first item in list
        Ingredient dummyIngredient = new Ingredient("","","","",true);
        ingredients.add(dummyIngredient);

        /*Restore saved adapter
        ShoppingList shoppingList;
        shoppingList = loadShoppingList(WORKING_LIST_NAME);
        List<Ingredients> shoppingList.getIngredients();
        if(ingredient != null)
            shoppingList.addIngredient(incoming);
         */

        //TODO: Delete this and uncomment above once the loadShoppingList method is working
        ingredients.add(new Ingredient("a", "2", "2", "2", false));
        ingredients.add(new Ingredient("b", "2", "2", "2", false));

        //Get new ingredient if there is any
        Intent intent = getIntent();
        String incoming = intent.getStringExtra("Incoming ingredient");

        if(incoming != null){
            String[] incomingSplit = incoming.split(",");
            Ingredient incomingIngredient = new Ingredient(incomingSplit[0], incomingSplit[1],
                    incomingSplit[2], incomingSplit[3], false);
            ingredients.add(incomingIngredient);
        }

        mRecipeAdapter = new ArrayAdapter(this,
                R.layout.list_item_added_ingredient, R.id.list_item_ingredient_textview, ingredients);

        draggableList = (DragSortListView) findViewById(R.id.listview_added_ingredient);
        draggableList.setAdapter(mRecipeAdapter);
    }

    private void saveList(){
        String title = ((EditText) findViewById(R.id.shopping_list_title)).getText().toString();
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(title);

        for(Ingredient i : ingredients){
            shoppingList.addIngredient(i);
        }

        shoppingList.save();
    }

    private void initializeUI(){

        setContentView(R.layout.activity_new_shopping_list);


        DragSortListView.DropListener onDrop =
                new DragSortListView.DropListener() {
                    @Override
                    public void drop(int from, int to) {
                        Ingredient item = mRecipeAdapter.getItem(from);

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
                            // Traverse all views in 10 milliseconds
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

        Toolbar mToolbar = (Toolbar) findViewById(R.id.new_shopping_list_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //TODO: Prompt user to save unfinished lists

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //         .setAction("Action", null).show();
                Intent intent = new Intent(view.getContext(),NewIngredient.class);
                startActivity(intent);
            }
        });
    }



}
