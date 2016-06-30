package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import edu.umuc.cmsc495.shoppinglist.Objects.Ingredient;
import edu.umuc.cmsc495.shoppinglist.Objects.ShoppingList;
import edu.umuc.cmsc495.shoppinglist.R;

public class NewShoppingListActivity extends AppCompatActivity {

    private ArrayAdapter<Ingredient> mRecipeAdapter;
    private DragSortListView draggableList;
    private ShoppingList shoppingList;
    private final String WORKING_LIST_NAME = "New Shopping List";
    private final String prefKey = "NewShoppingList";
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private boolean exiting = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_email:
                Utility.composeEmail(shoppingList.getEmailSubject(), shoppingList.getEmailBodyText(),
                        this);
                return true;
            case R.id.action_addRecipe:
                //ToDo: Add ingredients to working shopping list based on recipe
                return true;
            case android.R.id.home:
                exiting = true;
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

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
        String title = ((EditText) findViewById(R.id.shopping_list_title)).getText().toString();
        shoppingList.setName(title);
        if (!exiting) {
            editor.putString(prefKey, shoppingList.getName());
        } else {
            editor.putString(prefKey, WORKING_LIST_NAME);
            Toast toast = Toast.makeText(this, title + " saved", Toast.LENGTH_SHORT);
            toast.show();
        }

        editor.commit();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String oldListName = sharedPref.getString(prefKey, WORKING_LIST_NAME);

        ((EditText) findViewById(R.id.shopping_list_title)).setText(oldListName);

        try {
            //Modified by Michael - to show list contents if launched from manage shopping lists
            //activity
            Bundle existingList = getIntent().getExtras();
            if( existingList.getSerializable("list") !=null) {
                shoppingList = (ShoppingList) existingList.getSerializable("list");
            }else {
                shoppingList = shoppingList.loadShoppingList(oldListName);
            }
        } catch (Exception e) {
            Utility.debugAlert(this, e.getMessage());
            shoppingList = new ShoppingList(this);
        }


        //Get new ingredient if there is any
        Intent intent = getIntent();
        String incoming = intent.getStringExtra("Incoming ingredient");

        if (incoming != null) {
            String[] incomingSplit = incoming.split(",");
            Ingredient incomingIngredient = new Ingredient(incomingSplit[0], incomingSplit[1],
                    incomingSplit[2], incomingSplit[3], false);
            shoppingList.addIngredient(incomingIngredient);
        }

        mRecipeAdapter = new ArrayAdapter(this,
                R.layout.list_item_added_ingredient, R.id.list_item_ingredient_textview,
                shoppingList.ingredientList);

        draggableList = (DragSortListView) findViewById(R.id.listview_added_ingredient);
        draggableList.setAdapter(mRecipeAdapter);
    }

    //Generic UI method that initializes button clicks oother no-changing UI elements
    private void initializeUI() {

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //         .setAction("Action", null).show();
                Intent intent = new Intent(view.getContext(), NewIngredient.class);
                startActivity(intent);
            }
        });


    }


}
