package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;

import edu.umuc.cmsc495.shoppinglist.Objects.Ingredient;
import edu.umuc.cmsc495.shoppinglist.Objects.Recipe;
import edu.umuc.cmsc495.shoppinglist.Objects.ShoppingList;
import edu.umuc.cmsc495.shoppinglist.Objects.UiUtils;
import edu.umuc.cmsc495.shoppinglist.R;
import android.widget.Toast;

import java.util.ArrayList;

public class NewIngredient extends FragmentActivity {

    Ingredient ingOld = null;
    Ingredient ingNew = new Ingredient();
    ShoppingList sl = null;
    Recipe recipe;
    boolean isChanging = false;
    boolean isShoppingList = false;
    boolean isRecipe =false;
    String dialogResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_ingredient);

        Button bMeasurement = (Button) findViewById(R.id.bMeasurement);
        Button bQuantityPartial = (Button) findViewById(R.id.bQuantityPartial);
        Button bQuantityFull= (Button) findViewById(R.id.bQuantityFull);

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

/*
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
*/

        if(ingOld != null){
            setButtonText(bQuantityFull, ingOld.getCountFullString());
            setButtonText(bQuantityPartial, ingOld.getCountPartialString());
            setButtonText(bMeasurement, ingOld.getMeasurement());
            ((EditText)findViewById(R.id.new_ingredient_name)).setText(ingOld.getName());
        }else{
            ingOld = new Ingredient();
            setButtonText(bQuantityFull, "");
            setButtonText(bQuantityPartial, "");
            setButtonText(bMeasurement, "");
        }


        Button btnSave = (Button) findViewById(R.id.save);
        Button btnCancel = (Button) findViewById(R.id.cancel);
        ImageButton btnDelete = (ImageButton) findViewById(R.id.edit_ingredient_delete);
        btnSave.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Prolly redundant here
                Button bMeasurement = (Button)findViewById(R.id.bMeasurement);
                Button bQuantityPartial = (Button)findViewById(R.id.bQuantityPartial);
                Button bQuantityFull= (Button)findViewById(R.id.bQuantityFull);
                ingNew.setName(((EditText)findViewById(R.id.new_ingredient_name)).getText().toString());
                ingNew.setCountPartial(getButtonText(bQuantityPartial));
                ingNew.setCountFull(getButtonText(bQuantityFull));
                ingNew.setMeasurement(getButtonText(bMeasurement));

                Intent intent = null;

                boolean moveToPage = true;

                String errMsg = "";

                try { //HUGE try but it's that or repeated err handling code sections
                    if (isShoppingList) {
                        intent = new Intent(v.getContext(), NewShoppingListActivity.class);
                        if (isChanging) {
                            sl.changeIngredient(ingNew, ingOld);
                        } else {
                            sl.addIngredient(ingNew);
                        }
                    }

                    if (isRecipe) {
                        intent = new Intent(v.getContext(), RecipesList.class);
                        if (isChanging) {
                            recipe.changeIngredient(ingNew, ingOld);
                        } else {
                            recipe.addIngredient(ingNew);
                        }
                        //finish();
                    }
                }catch(Exception e){
                moveToPage = false;
                errMsg = e.getMessage();
            }

                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("list", sl);
                intent.putExtra("recipe", recipe);
                //intent.putExtra("Incoming ingredient", ingNew);

                if(moveToPage){
                    startActivity(intent);
                    finish();
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
                finish();

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
                finish();
            }
        });

        final ListAdapter arrMeasurement = new ArrayAdapter(this,android.R.layout.simple_list_item_single_choice,getMeasurementChoices());
        bMeasurement.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                final ArrayList<String> innerList = getMeasurementChoices();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Pick the measure");
                AlertDialog dialog;

                builder.setSingleChoiceItems(arrMeasurement,
                        getIndex(ingNew.getMeasurement(),innerList), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                ingNew.setMeasurement(innerList.get(item));
                                setButtonText((Button)findViewById(R.id.bMeasurement), innerList.get(item));
                                dialog.dismiss();
                            }
                        });
                dialog = builder.create();
                dialog.show();
                int width = (int)(getResources().getDisplayMetrics().widthPixels*0.70);
                dialog.getWindow().setLayout(width, 1000);
            }
        });

        final ListAdapter arrCountPartial = new ArrayAdapter(this,android.R.layout.simple_list_item_single_choice, UiUtils.getCountPartial());

        bQuantityPartial.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                final ArrayList<String> innerList = UiUtils.getCountPartial();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Pick the partial quantity");
                AlertDialog dialog;
                builder.setSingleChoiceItems(arrCountPartial,
                        getIndex(ingNew.getCountPartialString(),innerList), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                ingNew.setCountPartial(innerList.get(item));
                                setButtonText((Button)findViewById(R.id.bQuantityPartial), innerList.get(item));
                                dialog.dismiss();
                            }
                        });
                dialog = builder.create();
                dialog.show();
                int width = (int)(getResources().getDisplayMetrics().widthPixels*0.70);
                dialog.getWindow().setLayout(width, 1000);
            }
        });

        final ListAdapter arrCountFull = new ArrayAdapter(this,android.R.layout.simple_list_item_single_choice, UiUtils.getCountFull());
        bQuantityFull.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {

                // Creating and Building the Dialog
                final ArrayList<String> innerList = UiUtils.getCountFull();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Pick the whole quantity");
                AlertDialog dialog;
                builder.setSingleChoiceItems(arrCountFull,
                        getIndex(ingNew.getCountFullString(),innerList), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        ingNew.setCountFull(innerList.get(item));
                        setButtonText((Button)findViewById(R.id.bQuantityFull), innerList.get(item));
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                int width = (int)(getResources().getDisplayMetrics().widthPixels*0.70);
                dialog.getWindow().setLayout(width, 1000);
            }
        });



    }

    private String getButtonText(Button b){
        if(b.getText().toString().equals(UiUtils.DEFAULT_RADIO_SELECTION_TEXT)){
            return "";
        }
        else{
            return b.getText().toString();
        }
    }

    private void setButtonText(Button b, String in){
        if(in.equals("")){
            b.setText(UiUtils.DEFAULT_RADIO_SELECTION_TEXT);
        }else
            b.setText(in);
    }

    private int getIndex(String selectedItem,ArrayList<String> list) {

        int index = -1;
        if (selectedItem != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(selectedItem)) {
                    index = i;
                }
            }
        }
    return index;
    }
    public ArrayList<String> getMeasurementChoices() {
        if (isShoppingList) {
            return UiUtils.getMeasurementsShoppingList();
        } else {
            return UiUtils.getMeasurementsRecipe();
        }
    }

}
