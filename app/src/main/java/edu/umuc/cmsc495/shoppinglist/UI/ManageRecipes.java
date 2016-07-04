package edu.umuc.cmsc495.shoppinglist.UI;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import edu.umuc.cmsc495.shoppinglist.Objects.DataLayer;
import edu.umuc.cmsc495.shoppinglist.Objects.FileList;
import edu.umuc.cmsc495.shoppinglist.Objects.FileListItem;
import edu.umuc.cmsc495.shoppinglist.Objects.Ingredient;
import edu.umuc.cmsc495.shoppinglist.Objects.Recipe;
import edu.umuc.cmsc495.shoppinglist.R;

public class ManageRecipes extends AppCompatActivity {


    private List<String> recipeNames =null;
    private List<FileListItem> recipes =null;
    private DragSortListView listView=null;
    private ArrayAdapter<String> recipesAdaptor=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_recipes);

        fillRecipeNames();
        setListViewAdapter();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.manage_recipes_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.recipes);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sorting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuSortAscending:
                Collections.sort(recipeNames,new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        return lhs.compareToIgnoreCase(rhs);
                    }
                });
                setListViewAdapter();
                return true;

            case R.id.menuSortDescending:
                Collections.sort(recipeNames, new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        return rhs.compareToIgnoreCase(lhs);
                    }
                });
                setListViewAdapter();
                return true;

            case R.id.menuSortNewest:
                clearLists();
                fillRecipeNames();
                Collections.sort(recipes, new Comparator<FileListItem>() {
                    @Override
                    public int compare(FileListItem lhs, FileListItem rhs) {
                        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1 = df.parse(lhs.getModifiedOn());
                            date2 = df.parse(rhs.getModifiedOn());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (date1 != null) {
                            if (date1.after(date2))
                                return -1;
                            else if (date1.equals(date2)) // it's equals
                                return 0;
                            else
                                return 1;
                        }
                        return 0;
                    }


                });
                fillRecipeNames();
                setListViewAdapter();
                return true;

            case R.id.menuSortOldest:
                clearLists();
                fillRecipeNames();
                Collections.sort(recipes, new Comparator<FileListItem>() {
                    @Override
                    public int compare(FileListItem lhs, FileListItem rhs) {
                        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1 = df.parse(lhs.getModifiedOn());
                            date2 = df.parse(rhs.getModifiedOn());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (date2 != null) {
                            if (date2.after(date1))
                                return -1;
                            else if (date2.equals(date1)) // it's equals
                                return 0;
                            else
                                return 1;
                        }
                        return 0;
                    }
                });
                fillRecipeNames();
                setListViewAdapter();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    //Sets the Adapter that populates the ListView
    public void setListViewAdapter(){
        listView = (DragSortListView)findViewById(R.id.recipes);

        DragSortListView.DropListener onDrop =
                new DragSortListView.DropListener() {
                    @Override
                    public void drop(int from, int to) {
                        String item = recipesAdaptor.getItem(from);

                        recipesAdaptor.remove(item);
                        recipesAdaptor.insert(item, to);
                    }
                };

        DragSortListView.RemoveListener onRemove =
                new DragSortListView.RemoveListener() {
                    @Override
                    public void remove(int which) {
                        String recipeName = recipesAdaptor.getItem(which);
                        //Create recipe object of the selected shopping list for deletion
                        // Recipe recipe = new Recipe(getContext().getApplicationContext()); //martin commented
                        // recipe.setName(viewHolder.listName.getText().toString()); //martin commented

                        //Kept getting a NullPointer exception without doing the above first
                        DataLayer dataLayer = new DataLayer(getApplicationContext());

                        //Delete recipe and remove the name from the listview
                        //if (dataLayer.deleteRecipe(Recipe.loadRecipe(recipe.getName()))) { //martin commented
                        if (dataLayer.deleteRecipe(recipeName)) {
                            Toast toast = Toast.makeText(getBaseContext(), recipeName + " deleted", Toast.LENGTH_SHORT);
                            toast.setText(recipeName + " deleted");
                            toast.show();
                        }
                        recipesAdaptor.remove(recipesAdaptor.getItem(which));
                    }
                };

        DragSortListView.OnItemClickListener onClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String recipeName = listView.getItemAtPosition(position).toString();
                ComponentName callingActivity = getCallingActivity();
                if(callingActivity.getClassName().equals(NewShoppingListActivity.class.getCanonicalName())){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("RecipeName", recipeName);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }else {
                    Recipe recipe = new Recipe(getApplicationContext());
                    recipe.loadRecipe(recipeName);
                    Intent intent = new Intent(view.getContext(), RecipesList.class);
                    intent.putExtra("recipe", recipe);
                    startActivity(intent);
                }
            }
        };

        DragSortListView.DragScrollProfile ssProfile =
                new DragSortListView.DragScrollProfile() {
                    @Override
                    public float getSpeed(float w, long t) {
                        if (w > 0.8f) {
                            // Traverse all views in 10 milliseconds
                            return ((float) recipesAdaptor.getCount()) / 0.01f;
                        } else {
                            return 10.0f * w;
                        }
                    }
                };

        recipesAdaptor = new ArrayAdapter<>(this, R.layout.recipe_list_item, R.id.list_name,recipeNames);

        listView.setAdapter(recipesAdaptor);
        listView.setDropListener(onDrop);
        listView.setRemoveListener(onRemove);
        listView.setOnItemClickListener(onClick);
        listView.setDragScrollProfile(ssProfile);
    }

    //Populates an ArrayList of Shopping List Names
    public void fillRecipeNames(){
        recipeNames = new ArrayList<>();
        new FileList(getApplicationContext());
        recipes = FileList.recipes;
        for (FileListItem recipe : recipes) {
            if(!recipeNames.contains(recipe.getName().substring(0,recipe.getName().length()-4))) {
                recipeNames.add(recipe.getName().substring(0, recipe.getName().length() - 4));
            }
        }
    }


    public void onStop(){
        super.onStop();
        //Clear the recipes array so the deleted shopping list doesn't
        //display in the listview when leaving and coming back to the activity
        clearLists();
    }

    public void clearLists(){
        recipeNames.clear();
        recipes.clear();
    }
}
