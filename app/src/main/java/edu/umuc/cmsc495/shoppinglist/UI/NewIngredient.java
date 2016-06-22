package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import edu.umuc.cmsc495.shoppinglist.Objects.UiUtils;
import edu.umuc.cmsc495.shoppinglist.R;

public class NewIngredient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ingredient);

        Spinner wholeQuantity = (Spinner)findViewById(R.id.whole_qty_ingredient);
        Spinner partialQuantity = (Spinner)findViewById(R.id.partial_qty_ingredient);
        Spinner measurements =(Spinner)findViewById(R.id.measurements_ingredient);


        ArrayAdapter wholeQuantityAd = ArrayAdapter.createFromResource(this,R.array.whole_quantities,android.R.layout.simple_spinner_item);
        ArrayAdapter partialQuantityAd = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UiUtils.getCountPartial());
        ArrayAdapter measurementsAd = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UiUtils.getMeasurementsRecipe());

        wholeQuantity.setAdapter(wholeQuantityAd);
        partialQuantity.setAdapter(partialQuantityAd);
        measurements.setAdapter(measurementsAd);
    }

    protected void cancel(View view){
        this.finish();
    }


}
