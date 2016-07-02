package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import edu.umuc.cmsc495.shoppinglist.Objects.FileList;
import edu.umuc.cmsc495.shoppinglist.Objects.FileListItem;
import edu.umuc.cmsc495.shoppinglist.Objects.Recipe;
import edu.umuc.cmsc495.shoppinglist.Objects.ShoppingList;
import edu.umuc.cmsc495.shoppinglist.R;

public class ManageRecipes extends AppCompatActivity {


    private List<String> recipeNames =null;
    private List<FileListItem> recipes =null;
    private ListView listView=null;

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pass ShoppingList object of selected list to NewListActivity
               // Recipe recipe = Recipe.loadRecipe(listView.getItemAtPosition(position).toString());
                Recipe recipe = new Recipe(getApplicationContext());
                recipe.loadRecipe(listView.getItemAtPosition(position).toString());
                Intent intent = new Intent(view.getContext(),RecipesList.class);
                intent.putExtra("recipe", recipe);
                startActivity(intent);
            }
        });

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
                        return lhs.compareTo(rhs);
                    }
                });
                setListViewAdapter();
                return true;

            case R.id.menuSortDescending:
                Collections.sort(recipeNames, new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        return rhs.compareTo(lhs);
                    }
                });
                setListViewAdapter();
                return true;

            case R.id.menuSortNewest:
                recipeNames.clear();
                recipes.clear();
                fillRecipeNames();
                //setListViewAdapter();
                Collections.sort(recipes, new Comparator<FileListItem>() {
                    @Override
                    public int compare(FileListItem lhs, FileListItem rhs) {
                        Date date1 = new Date(lhs.getModifiedOn());
                        Date date2 = new Date(rhs.getModifiedOn());
                        if (date1.after(date2))
                            return -1;
                        else if (date1.equals(date2)) // it's equals
                            return 0;
                        else
                            return 1;
                    }


                });
                fillRecipeNames();
                setListViewAdapter();
                return true;

            case R.id.menuSortOldest:
                recipeNames.clear();
                recipes.clear();
                fillRecipeNames();
                setListViewAdapter();
                Collections.sort(recipes, new Comparator<FileListItem>() {
                    @Override
                    public int compare(FileListItem lhs, FileListItem rhs) {
                        Date date1 = new Date(lhs.getModifiedOn());
                        Date date2 = new Date(rhs.getModifiedOn());
                        if (date2.after(date1))
                            return -1;
                        else if (date2.equals(date1)) // it's equals
                            return 0;
                        else
                            return 1;
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
        listView = (ListView)findViewById(R.id.recipes);
        ListsAdapter<String> recipesAdaptor = new ListsAdapter<>(this, R.layout.recipe_list_item, recipeNames);
        listView.setAdapter(recipesAdaptor);
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
        recipes.clear();
    }
}
