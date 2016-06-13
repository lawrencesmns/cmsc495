package edu.umuc.cmsc495.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import edu.umuc.cmsc495.shoppinglist.UI.NewShoppingListFragment;

public class MainActivity extends AppCompatActivity implements NewShoppingListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    /*This method initializes all UI elements for this activity.*/
    void initialize(){
        setContentView(R.layout.activity_main);
        Button btnNewShoppingList = (Button) findViewById(R.id.btn_new_list);
        btnNewShoppingList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launch(NewShoppingListFragment.class);
            }
        });

        Button btnManageShoppingList = (Button) findViewById(R.id.btn_manage_list);
        btnManageShoppingList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // launch(edu.umuc.cmsc495.shoppinglist.UI.ManageShoppingList.class);
            }
        });

        Button btnNewRecipe = (Button) findViewById(R.id.btn_new_recipe);
        btnNewRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // launch(edu.umuc.cmsc495.shoppinglist.UI.NewRecipe.class);
            }
        });

        Button btnManageRecipe = (Button) findViewById(R.id.btn_manage_recipes);
        btnManageRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // launch(edu.umuc.cmsc495.shoppinglist.UI.ManageRecipe.class);
            }
        });

    }

    void launch(Class<?> cls){
        if(cls.getClass().isInstance(Fragment.class)){
            Fragment newFragment = null;
            try {
                newFragment = (Fragment) cls.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_activity_container, newFragment).commit();
        }else if(cls.getClass().isInstance(Activity.class)) {
            Intent intent = new Intent(this, cls);
            startActivity(intent);
        }
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
                initialize();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
