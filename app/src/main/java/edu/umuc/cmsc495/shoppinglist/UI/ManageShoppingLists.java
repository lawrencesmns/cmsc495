package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
        inflater.inflate(R.menu.sorting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = getApplicationContext();
        Toast toast;
        CharSequence text;
        switch (item.getItemId()) {
            case R.id.menuSortAscending:
                // User chose the "Settings" item, show the app settings UI...

                text = "Sorted A-Z";
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                toast.show();
                return true;

            case R.id.menuSortDescending:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                text = "Sorted Z-A";
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                toast.show();
                return true;

            case R.id.menuSortNewest:
                text = "Sorted Newest to Oldest";
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                toast.show();
                return true;

            case R.id.menuSortOldest:
                text = "Sorted Oldest to Newest";
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                toast.show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
