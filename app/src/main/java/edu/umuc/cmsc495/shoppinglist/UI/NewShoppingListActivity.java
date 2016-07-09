package edu.umuc.cmsc495.shoppinglist.UI;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.ArrayList;

import edu.umuc.cmsc495.shoppinglist.Objects.Ingredient;
import edu.umuc.cmsc495.shoppinglist.Objects.Recipe;
import edu.umuc.cmsc495.shoppinglist.Objects.ShoppingList;
import edu.umuc.cmsc495.shoppinglist.R;

public class NewShoppingListActivity extends AppCompatActivity {

    private ArrayAdapter<Ingredient> mRecipeAdapter;
    private DragSortListView draggableList;
    private ShoppingList shoppingList = new ShoppingList(this);
    private final String DEFAULT_KEY = "nada";
    private final String prefKey = "OldListName";
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String mRecipeName = null;
    private boolean exiting = false;
    private boolean mRemoveRecipe = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_email:
                Utility.composeEmail(shoppingList.getEmailSubject(), shoppingList.getEmailBodyText(),
                        this);

                return true;
            case R.id.action_addRecipe:
                return addOrRemoveRecipe();
            case android.R.id.home:
                exiting = true;
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case Utility.REQUEST_RECIPE:
                    mRecipeName = data.getStringExtra("RecipeName");
                    mRemoveRecipe = false;
                    break;
                case Utility.REQUEST_RECIPE_FOR_REMOVAL:
                    mRecipeName = data.getStringExtra("RecipeName");
                    mRemoveRecipe = true;
                    break;
            }
        }
    }

    private boolean addOrRemoveRecipe() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Would you like to add or remove a recipe?")
        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), ManageRecipes.class);
                startActivityForResult(intent, Utility.REQUEST_RECIPE);
            }
        })
        .setNegativeButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), ManageRecipes.class);
                startActivityForResult(intent, Utility.REQUEST_RECIPE_FOR_REMOVAL);
            }
        });
        alertBuilder.create().show();

        return true;
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
            editor.putString(prefKey, DEFAULT_KEY);
            Toast toast = Toast.makeText(this, shoppingList.getName() + " shopping list saved", Toast.LENGTH_SHORT);
            toast.show();
        }

        editor.commit();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String oldListName = sharedPref.getString(prefKey, DEFAULT_KEY);

        //Collect intent info
        Intent intent = getIntent();

        ShoppingList sl = null;
        try{
            sl = (ShoppingList)intent.getExtras().getSerializable("list");
        }catch(Exception e){}

        if(sl != null){
            shoppingList = sl;
        } else {
            if(shoppingList == null || shoppingList.getName().equals("")){
                shoppingList.createNewList();
            }

        }

        oldListName = shoppingList.getName();

        //These variables are initialized in the onActivityResult method
        if(mRecipeName != null){
            Recipe recipe = new Recipe(this);
            recipe.loadRecipe(mRecipeName);
            if(mRemoveRecipe){
                shoppingList.removeRecipe(recipe);
            }else
                shoppingList.addRecipe(recipe);
        }

        mRecipeAdapter = new  NewShoppingListAdapter(this, R.layout.list_item_added_ingredient_shoppinglist, (ArrayList<Ingredient>) shoppingList.ingredientList);

        draggableList = (DragSortListView) findViewById(R.id.listview_added_ingredient);
        draggableList.setAdapter(mRecipeAdapter);

        ((EditText) findViewById(R.id.shopping_list_title)).setText(oldListName);


    }



    //Generic UI method that initializes button clicks other non-changing UI elements
    private void initializeUI() {

        setContentView(R.layout.activity_new_shopping_list);



        DragSortListView.DropListener onDrop =
                new DragSortListView.DropListener() {
                    @Override
                    public void drop(int from, int to) {
                        Ingredient item = mRecipeAdapter.getItem(from);

                        mRecipeAdapter.remove(item);
                        //mRecipeAdapter.notifyDataSetChanged();
                        mRecipeAdapter.insert(item, to);
                        mRecipeAdapter.notifyDataSetChanged();
                     }
                };

        DragSortListView.RemoveListener onRemove =
                new DragSortListView.RemoveListener() {
                    @Override
                    public void remove(int which) {
                        Ingredient selected = mRecipeAdapter.getItem(which);
                        shoppingList.removeIngredient(selected);
                        mRecipeAdapter.remove(selected);
                        mRecipeAdapter.notifyDataSetChanged();
                    }
                };

        DragSortListView.OnItemClickListener onClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), NewIngredient.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Ingredient i = (Ingredient)parent.getItemAtPosition(position);
                intent.putExtra("ischanging", true);
                intent.putExtra("ingredient",i);
                intent.putExtra("shoppinglist", shoppingList);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        };

        DragSortListView.OnItemLongClickListener onCheck = new AdapterView.OnItemLongClickListener()  {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Ingredient i = (Ingredient)parent.getItemAtPosition(position);
                TextView row = (TextView) view.findViewById(R.id.list_item_ingredient_textview);
                if(!i.getCrossed()){
                    row.setPaintFlags(row.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    i.setCrossedOut(true);
                }else{
                    row.setPaintFlags(row.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    i.setCrossedOut(false);
                }
                shoppingList.changeIngredient(i,i);
                return true;
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
        draggableList.setOnItemClickListener(onClick);
        draggableList.setOnItemLongClickListener(onCheck);

        draggableList.setEmptyView((TextView)findViewById(android.R.id.empty));
        Toolbar mToolbar = (Toolbar) findViewById(R.id.new_shopping_list_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.requestFocus();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewIngredient.class);
                intent.putExtra("shoppinglist", shoppingList);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //Hides keyboard when Shopping List title EditText looses focus
        final EditText listTitle = (EditText)findViewById(R.id.shopping_list_title);
        View.OnFocusChangeListener ofcl = new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);

                //martin added 3 lines below so the title saves if a user exits the edittext
                if(v.getId() == R.id.shopping_list_title && !v.hasFocus()){
                    shoppingList.setName(listTitle.getText().toString());
                }
            }
        };

        if (listTitle != null) {
            listTitle.setOnFocusChangeListener(ofcl);
        }

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
