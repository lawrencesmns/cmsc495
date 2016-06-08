package edu.umuc.cmsc495.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.umuc.cmsc495.shoppinglist.barcode.BarcodeCaptureActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    public void startBarcodeCaptureActivity() {
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        startActivity(intent);
    }

    public void initialize(){
        Button btnBarcodeTest = (Button) findViewById(R.id.barcode_test);

        btnBarcodeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBarcodeCaptureActivity();
            }
        });
    }
}
