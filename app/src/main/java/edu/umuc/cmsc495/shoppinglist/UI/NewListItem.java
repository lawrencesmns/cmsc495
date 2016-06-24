package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
        Button btnCancel = (Button) findViewById(R.id.cancel);
        ImageButton btnDelete = (ImageButton) findViewById(R.id.edit_ingredient_delete);
        btnSave.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = ((EditText)findViewById(R.id.new_ingredient_name)).getText().toString() + ",";
                String partialQty = ((Spinner)findViewById(R.id.partial_qty_item)).getSelectedItem().toString() + ",";
                String wholeQty = ((Spinner)findViewById(R.id.whole_qty_item)).getSelectedItem().toString() + ",";
                String measurements = ((Spinner)findViewById(R.id.measurements_item)).getSelectedItem().toString();

                Intent intent = new Intent(v.getContext(),NewShoppingListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Incoming ingredient", new Ingredient(name,partialQty,wholeQty,measurements,false));
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDelete.setOnClickListener(new ImageButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: Add stuff that will remove this ingredient from its parent's list (recipe or shopping list)
                finish();
            }
        });
    }
}
