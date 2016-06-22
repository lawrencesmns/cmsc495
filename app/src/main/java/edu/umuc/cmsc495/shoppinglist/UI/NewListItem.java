package edu.umuc.cmsc495.shoppinglist.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
    }
}
