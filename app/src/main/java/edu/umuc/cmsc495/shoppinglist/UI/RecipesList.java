package edu.umuc.cmsc495.shoppinglist.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.EGLDisplay;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.umuc.cmsc495.shoppinglist.Objects.Ingredient;
import edu.umuc.cmsc495.shoppinglist.Objects.Recipe;
import edu.umuc.cmsc495.shoppinglist.R;

public class RecipesList extends AppCompatActivity {

    private ArrayAdapter<Ingredient> mRecipeAdapter;
    private DragSortListView draggableList;
    private Recipe mRecipe = new Recipe(this);
    private final String DEFAULT_KEY = "nothin";
    private final String prefKey = "OldRecipeName";
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private boolean exiting = false;
 //   String oldListName = sharedPref.getString(prefKey,DEFAULT_KEY);

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == Utility.REQUEST_INGREDIENT) {
            //  String[] incomingIngredient = data.getStringArrayExtra("Incoming ingredient");
            //  mRecipe = mRecipe.loadRecipe(oldListName);
            //  Ingredient ing = new Ingredient(incomingIngredient[0], incomingIngredient[1],
            //          incomingIngredient[2], incomingIngredient[3], false);
            //  mRecipe.addIngredient(ing);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_email:
                Utility.composeEmail(mRecipe.getEmailSubject(), mRecipe.getEmailBodyText(),
                        this);
                return true;
            case android.R.id.home:
                exiting = true;
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();

        String title = ((EditText) findViewById((R.id.recipe_title))).getText().toString();
        String instructions = ((EditText) findViewById(R.id.instructions)).getText().toString();
        mRecipe.setName(title);
        mRecipe.setInstructions(instructions);

        if(!exiting){
            editor.putString(prefKey, mRecipe.getName());
        }else{
            editor.putString(prefKey, DEFAULT_KEY);
            Toast toast = Toast.makeText(this, mRecipe.getName() + " recipe saved", Toast.LENGTH_SHORT);
            toast.show();
        }

        editor.commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        String oldListName = sharedPref.getString(prefKey,DEFAULT_KEY);

        Intent intent = getIntent();
        String incomingRecipe = intent.getStringExtra("recipe");

      //  Ingredient incomingIngredient= null;
      //  try{
      //      incomingIngredient = (Ingredient)intent.getExtras().getSerializable("Incoming ingredient");
      //  }catch(Exception e){}

        Recipe r= null;
        try{
            r = (Recipe)intent.getExtras().getSerializable("recipe");
        }catch(Exception e){}

        if(r != null){
            mRecipe = r;
            ((EditText) findViewById(R.id.instructions)).setText(mRecipe.getInstructions());
     //   }else if (incomingIngredient != null) {
            //shoppingList.addIngredient(incomingIngredient);
            //shoppingList = sl;
        } else {
            mRecipe.createNewRecipe();
        }

       // if(incomingRecipe != null){
       //   mRecipe.loadRecipe(incomingRecipe);
        //}else if(oldListName.equals(DEFAULT_KEY) && intent.getStringArrayExtra("Incoming ingredient") == null){
        //    mRecipe.createNewRecipe();
      //  }

        oldListName = mRecipe.getName();

        mRecipeAdapter = new ArrayAdapter(this,
                R.layout.list_item_added_ingredient, R.id.list_item_ingredient_textview, mRecipe.getIngredientList());

        draggableList = (DragSortListView) findViewById(R.id.listview_added_ingredient);
        draggableList.setAdapter(mRecipeAdapter);

        ((EditText) findViewById((R.id.recipe_title))).setText(oldListName);
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
        inflater.inflate(R.menu.new_recipes_menu, menu);
        return true;
    }

    private void initializeUI() {

        setContentView(R.layout.activity_recipes);


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

        DragSortListView.OnItemClickListener onClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRecipe.setInstructions(((EditText) findViewById(R.id.instructions)).getText().toString());
                Intent intent = new Intent(view.getContext(), NewIngredient.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Ingredient i = (Ingredient) parent.getItemAtPosition(position);
                intent.putExtra("ischanging", true);
                intent.putExtra("ingredient", i);
                intent.putExtra("recipe", mRecipe);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
        draggableList.setOnItemClickListener(onClick);
        draggableList.setDivider(null);
        draggableList.setDragScrollProfile(ssProfile);
        draggableList.setEmptyView((TextView)findViewById(android.R.id.empty));

        Toolbar mToolbar = (Toolbar) findViewById(R.id.new_recipe_list_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.requestFocus();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent intent = new Intent(view.getContext(), NewIngredient.class);
                intent.putExtra("recipe", mRecipe);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }
}
