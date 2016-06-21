package edu.umuc.cmsc495.shoppinglist.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import edu.umuc.cmsc495.shoppinglist.R;

public class NewIngredient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ingredient);

        Spinner wq = (Spinner)findViewById(R.id.whole_qty);
        Spinner pq = (Spinner)findViewById(R.id.partial_qty);
        Spinner meas =(Spinner)findViewById(R.id.measurements);
        ArrayAdapter wqad = ArrayAdapter.createFromResource(this,R.array.whole_quantities,android.R.layout.simple_spinner_item);
        ArrayAdapter pqad=ArrayAdapter.createFromResource(this,R.array.partial_quantities,android.R.layout.simple_spinner_item);
        ArrayAdapter mad = ArrayAdapter.createFromResource(this,R.array.measurements,android.R.layout.simple_spinner_item);

        wq.setAdapter(wqad);
        pq.setAdapter(pqad);
        meas.setAdapter(mad);
    }



}
