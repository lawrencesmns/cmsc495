package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import edu.umuc.cmsc495.shoppinglist.Objects.Ingredient;
import edu.umuc.cmsc495.shoppinglist.Objects.UiUtils;
import edu.umuc.cmsc495.shoppinglist.R;

public class NewListItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list_item);
        Spinner wholeQuantity = (Spinner)findViewById(R.id.whole_qty_item);
        Spinner partialQuantity = (Spinner)findViewById(R.id.partial_qty_item);
        Spinner measurements =(Spinner)findViewById(R.id.measurements_item);


        ArrayAdapter wholeQuantityAd = ArrayAdapter.createFromResource(this,R.array.whole_quantities,android.R.layout.simple_spinner_item);
        ArrayAdapter partialQuantityAd = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UiUtils.getCountPartial());
        ArrayAdapter measurementsAd = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UiUtils.getMeasurementsShoppingList());

        wholeQuantity.setAdapter(wholeQuantityAd);
        partialQuantity.setAdapter(partialQuantityAd);
        measurements.setAdapter(measurementsAd);

        Button btnSave = (Button) findViewById(R.id.save);
        btnSave.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name, countPartial, countFull, measurement;
                name = ((EditText)findViewById(R.id.new_ingredient_name)).getText().toString();
                countPartial = ((Spinner)findViewById(R.id.partial_qty_item)).getSelectedItem().toString();
                countFull = ((Spinner)findViewById(R.id.whole_qty_item)).getSelectedItem().toString();
                measurement = ((Spinner)findViewById(R.id.measurements_item)).getSelectedItem().toString();
                Ingredient newItem = new Ingredient(name, measurement, countFull, countPartial, false);

                Intent intent = new Intent(v.getContext(),NewShoppingListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Incoming ingredient", newItem.toString());
                startActivity(intent);
            }
        });
    }
}
