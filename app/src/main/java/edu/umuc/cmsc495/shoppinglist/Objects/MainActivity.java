package edu.umuc.cmsc495.shoppinglist.Objects;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import edu.umuc.cmsc495.shoppinglist.R;
import edu.umuc.cmsc495.shoppinglist.UI.*;

public class MainActivity extends AppCompatActivity {

    DataLayer dataCore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataCore = new DataLayer(this);
        /*Commented out because app crashes due to no chicken xml recipe */
        loadData();     //Loads sample/test data
        testWrite();    //Tests writing a recipe */
        initializeUI();
    }

    /*Loads example files and any other files in the application directory.
    */
    protected void loadData(){
        Context context = this;

        //Sample/Test recipe
        InputStream is = context.getResources().openRawResource(R.raw.sl_test_list);
        Log.d("File open", "test recipe loaded");
        this.dataCore.parseRecipe(is);

        //Sample/Test shopping list
        is = context.getResources().openRawResource(R.raw.sl_test_list);
        this.dataCore.parseList(is);

        //Builds array of app files
        File sourceDir = context.getFilesDir();
        String dirPath = sourceDir.getAbsolutePath();
        File[] appFiles = sourceDir.listFiles();

        for(File child: appFiles){
            Log.d("child prefix", child.getName().substring(0, 2));
            if(child.getName().substring(0,2).equals("r_")){
                Log.d("App Files", child.getName());
                try{
                    is = new FileInputStream(child);
                    this.dataCore.parseRecipe(is);
                }catch(Exception e){
                    Log.e("IO Error", "Couldn't open recipe "+ child.getName());
                }
            }else if(child.getName().substring(0,3).equals("sl_")){
                Log.d("App Files", child.getName());
                try{
                    is = new FileInputStream(child);
                    this.dataCore.parseList(is);
                }catch(Exception e){
                    Log.e("IO Error", "Couldn't open shopping list "+ child.getName());
                }
            }
        }
    }

    protected void testWrite(){

        Recipe testRecipe = new Recipe();
        testRecipe.setName("test_recipe");
        testRecipe.setInstructions("Testy Testerman Testsing Us!");
        testRecipe.addIngredient("Eggs,Each,2");

        this.dataCore.writeRecipe(testRecipe);

        ShoppingList testList = new ShoppingList();
        testList.setName("Test_List_1");
        testList.addIngredient("Eggs,Each,3");
        testList.addIngredient("Cookies,Pack,1");
        testList.addIngredient("Cheerios,Box,1");

        this.dataCore.writeList(testList);
    }

    /*This method initializes all UI elements for this activity.*/
    void initializeUI(){
        setContentView(R.layout.activity_main);
        Button btnNewShoppingList = (Button) findViewById(R.id.btn_new_list);
        btnNewShoppingList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewShoppingListActivity.class);
                startActivity(intent);
            }
        });

        Button btnManageShoppingList = (Button) findViewById(R.id.btn_manage_list);
        btnManageShoppingList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ManageShoppingLists.class);
                startActivity(intent);
            }
        });

        Button btnNewRecipe = (Button) findViewById(R.id.btn_new_recipe);
        btnNewRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),RecipesList.class);
                startActivity(intent);
            }
        });

        Button btnManageRecipe = (Button) findViewById(R.id.btn_manage_recipes);
        btnManageRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ManageRecipes.class);
                startActivity(intent);
            }
        });

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle(R.string.app_name);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle(R.string.app_name);
                actionBar.setDisplayHomeAsUpEnabled(false);
                initializeUI();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}