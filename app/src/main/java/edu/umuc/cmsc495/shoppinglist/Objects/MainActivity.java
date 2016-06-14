package edu.umuc.cmsc495.shoppinglist.Objects;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.umuc.cmsc495.shoppinglist.R;
import edu.umuc.cmsc495.shoppinglist.UI.NewShoppingListFragment;

public class MainActivity extends AppCompatActivity implements NewShoppingListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    void initialize(){
        Button btnNewShoppingList = (Button) findViewById(R.id.btn_new_list);
        btnNewShoppingList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launch(NewShoppingListFragment.class);
            }
        });

        Button btnManageShoppingList = (Button) findViewById(R.id.btn_manage_list);
        btnManageShoppingList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // launch(du.umuc.cmsc495.shoppinglist.UI.ManageShoppingList.class);
            }
        });

        Button btnNewRecipe = (Button) findViewById(R.id.btn_new_recipe);
        btnNewRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // launch(du.umuc.cmsc495.shoppinglist.UI.NewRecipe.class);
            }
        });

        Button btnManageRecipe = (Button) findViewById(R.id.btn_manage_recipes);
        btnManageRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // launch(du.umuc.cmsc495.shoppinglist.UI.ManageRecipe.class);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
