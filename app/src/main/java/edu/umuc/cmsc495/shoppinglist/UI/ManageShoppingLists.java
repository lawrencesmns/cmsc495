package edu.umuc.cmsc495.shoppinglist.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import edu.umuc.cmsc495.shoppinglist.R;

public class ManageShoppingLists extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_shopping_lists);

        ListView listView = (ListView) findViewById(R.id.shopping_lists);
        String[] stringArray={"Item1","item2","item3"};
        ArrayAdapter<String> shoppingListAdaptor = new ArrayAdapter<>(this,R.layout.list_item_shopping_list,R.id.list_name,stringArray);
        listView.setAdapter(shoppingListAdaptor);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.manage_shopping_lists_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.shopping_lists);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manage_shopping_lists_menu, menu);
        return true;
    }


}
