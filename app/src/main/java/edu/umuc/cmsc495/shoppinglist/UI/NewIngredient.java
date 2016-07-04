package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import edu.umuc.cmsc495.shoppinglist.Objects.Ingredient;
import edu.umuc.cmsc495.shoppinglist.Objects.Recipe;
import edu.umuc.cmsc495.shoppinglist.Objects.ShoppingList;
import edu.umuc.cmsc495.shoppinglist.Objects.UiUtils;
import edu.umuc.cmsc495.shoppinglist.R;
import android.widget.Toast;

public class NewIngredient extends FragmentActivity {

    Ingredient ingOld = null;
    ShoppingList sl = null;
    Recipe recipe;
    boolean isChanging = false;
    boolean isShoppingList = false;
    boolean isRecipe =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        try{
             ingOld = (Ingredient) intent.getExtras().getSerializable("ingredient");
        }catch(Exception e){
        }

        try{
            isChanging = intent.getExtras().getBoolean("ischanging");
        }catch(Exception e){
        }

        try{
            sl = (ShoppingList) intent.getExtras().getSerializable("shoppinglist");
            if(sl !=null){
                isShoppingList = true;
            }
        }catch(Exception e){
        }

        try{
            recipe = (Recipe) intent.getExtras().getSerializable("recipe");
            if(recipe !=null){
                isRecipe = true;
            }
        }catch(Exception e){
        }


        setContentView(R.layout.activity_new_ingredient);
        Spinner wholeQuantity = (Spinner)findViewById(R.id.whole_qty_item);
        Spinner partialQuantity = (Spinner)findViewById(R.id.partial_qty_item);
        Spinner measurements =(Spinner)findViewById(R.id.measurements_item);


        ArrayAdapter wholeQuantityAd = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UiUtils.getCountFull());

        ArrayAdapter partialQuantityAd = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UiUtils.getCountPartial());

        ArrayAdapter measurementsAd = null;
        if(isRecipe){
            measurementsAd = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UiUtils.getMeasurementsRecipe());
        }

        if(isShoppingList){
            measurementsAd = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UiUtils.getMeasurementsShoppingList());
        }

        wholeQuantity.setAdapter(wholeQuantityAd);
        partialQuantity.setAdapter(partialQuantityAd);
        measurements.setAdapter(measurementsAd);


        if(ingOld != null){
            wholeQuantity.setSelection(((ArrayAdapter<String>)wholeQuantity.getAdapter()).getPosition(ingOld.getCountFullString()));
            partialQuantity.setSelection(((ArrayAdapter<String>)partialQuantity.getAdapter()).getPosition(ingOld.getCountPartialString()));
            measurements.setSelection(((ArrayAdapter<String>)measurements.getAdapter()).getPosition(ingOld.getMeasurement()));
            ((EditText)findViewById(R.id.new_ingredient_name)).setText(ingOld.getName());
        }


        Button btnSave = (Button) findViewById(R.id.save);
        Button btnCancel = (Button) findViewById(R.id.cancel);
        ImageButton btnDelete = (ImageButton) findViewById(R.id.edit_ingredient_delete);
        btnSave.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Ingredient ingNew = new Ingredient();
                //String[] sending = new String[4];
                ingNew.setName(((EditText)findViewById(R.id.new_ingredient_name)).getText().toString());
                ingNew.setCountPartial(((Spinner)findViewById(R.id.partial_qty_item)).getSelectedItem().toString());
                ingNew.setCountFull(((Spinner)findViewById(R.id.whole_qty_item)).getSelectedItem().toString());
                ingNew.setMeasurement(((Spinner)findViewById(R.id.measurements_item)).getSelectedItem().toString());

                Intent intent = null;

                boolean moveToPage = true;

                String errMsg = "";
                if(isShoppingList){
                    intent = new Intent(v.getContext(),NewShoppingListActivity.class);

                    if(isChanging){
                        sl.changeIngredient(ingNew,ingOld);
                    }else{
                        try{
                            sl.addIngredient(ingNew);
                        }catch(Exception e){
                            moveToPage = false;
                            errMsg = e.getMessage();
                        }

                    }

                }

                if(isRecipe) {
                    intent = new Intent(v.getContext(),RecipesList.class);
                    if (isChanging) {
                        recipe.changeIngredient(ingNew, ingOld);
                    } else {
                        try{
                            recipe.addIngredient(ingNew);
                        }catch(Exception e){
                            moveToPage = false;
                            errMsg = e.getMessage();
                        }
                    }

                    //finish();
                }

                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("list", sl);
                intent.putExtra("recipe", recipe);
                //intent.putExtra("Incoming ingredient", ingNew);

                if(moveToPage){
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), errMsg, Toast.LENGTH_SHORT);
                    toast.show();
                }


            }
        });

        btnCancel.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if(isShoppingList) {
                    intent = new Intent(v.getContext(), NewShoppingListActivity.class);
                }
                if(isRecipe) {
                    intent = new Intent(v.getContext(), RecipesList.class);
                }
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("list",sl);
                intent.putExtra("recipe", recipe);
                startActivity(intent);

            }
        });

        btnDelete.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: Add stuff that will remove this ingredient from its parent's list (recipe or shopping list)

                Intent intent = null;
                if(isShoppingList) {
                    sl.removeIngredient(ingOld);
                    intent = new Intent(v.getContext(), NewShoppingListActivity.class);
                }
                if(isRecipe) {
                    recipe.removeIngredient(ingOld);
                    intent = new Intent(v.getContext(), RecipesList.class);
                }
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("list",sl);
                intent.putExtra("recipe", recipe);
                startActivity(intent);
            }
        });
    }

}
