package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_addRecipe:
                saveList();
                break;
        }

        return true;
    }

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

        ingredients.remove(0); //is there one added?  check out mainActivity.java for recipe and shopping list object usage.  the sequences presented there work as expected.
        //Save current adapter as a temporary shopping list
        ShoppingList shoppingList = new ShoppingList(this);
        for(Ingredient i : ingredients)
                shoppingList.addIngredient(i); //list saves automatically on the following method calls: setName, addIngredient, changeIngredient, removeIngredient.  you don't need to call save from the UI.
        try {
            shoppingList.setName(WORKING_LIST_NAME); //saves automatically behind the scenes
        }catch(Exception e){
            //show popup box with e.getMessage();  friendly exception messages will be passed up the stack.  If they aren't friednly let me know.  -Martin

        }

      //  Boolean isSaved = shoppingList.save(); //Does this overrwrite existing list?
      //  if(!isSaved){
            //TODO: create a dialog saying there isn't enough space and the list will be lost
      //  }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        //Crappy hack because the draggableListView doesnt show the first item in list
        Ingredient dummyIngredient = new Ingredient("","","","",true); //not needed?  Not sure what this code is trying to do - martin
        ingredients.add(dummyIngredient); //not needed?  Not sure what this code is trying to do - martin

        //Restore saved adapter
        ShoppingList shoppingList = new ShoppingList(this);
        try {
            shoppingList = shoppingList.loadShoppingList(WORKING_LIST_NAME);
        }catch(NullPointerException e){
                e.printStackTrace();
            }


        for(Ingredient i : shoppingList.getIngredientList()){
            ingredients.add(i);
        }

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

    private void saveList(){ //list saves automatically on the following method calls: setName, addIngredient, changeIngredient, removeIngredient.  you don't need to call save from the UI.
        String title = ((EditText) findViewById(R.id.shopping_list_title)).getText().toString();
        ShoppingList shoppingList = new ShoppingList(); //should use a class level var and the new ShoppingList(this); constructor?
        shoppingList.setName(title); //if using class level var and the user changed the name, this would be set?

        ingredients.remove(0); //ingredients here is a class var not a reference to the object?
        for(Ingredient i : ingredients){ //should be for(Ingredient i : shoppingList.getIngredients())
            shoppingList.addIngredient(i); //should happen on an event when a user adds an ingredient?
        }

        //shoppingList.save(); //not needed - martin
        Toast toast = Toast.makeText(this, "List saved!", Toast.LENGTH_SHORT);
        toast.show();
        finish();
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
