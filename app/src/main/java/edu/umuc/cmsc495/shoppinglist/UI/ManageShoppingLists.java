package edu.umuc.cmsc495.shoppinglist.UI;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import edu.umuc.cmsc495.shoppinglist.Objects.FileList;
import edu.umuc.cmsc495.shoppinglist.Objects.FileListItem;
import edu.umuc.cmsc495.shoppinglist.Objects.ShoppingList;
import edu.umuc.cmsc495.shoppinglist.R;

public class ManageShoppingLists extends AppCompatActivity {

    private List<String> listNames=null;
    private List<FileListItem> lists=null;
    private ListView listView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_shopping_lists);

        fillListNames();
        setListViewAdapter();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.manage_shopping_lists_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.shopping_lists);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pass ShoppingList object of selected list to NewShoppingListActivity
                //ShoppingList list = ShoppingList.loadShoppingList(listView.getItemAtPosition(position).toString());
                String name = listView.getItemAtPosition(position).toString();

                ShoppingList sl = new ShoppingList(getApplicationContext());
                sl.loadShoppingList(name);

                Intent intent = new Intent(view.getContext(),NewShoppingListActivity.class);
                intent.putExtra("list", sl);
                startActivity(intent);

                /*intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Incoming ingredient", name);
                setResult(Activity.RESULT_OK,intent);
                finish();*/
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sorting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuSortAscending:
                // User chose the "Settings" item, show the app settings UI...
                Collections.sort(listNames,new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        return lhs.compareToIgnoreCase(rhs);
                    }
                });
                setListViewAdapter();
                return true;

            case R.id.menuSortDescending:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Collections.sort(listNames, new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        return rhs.compareToIgnoreCase(lhs);
                    }
                });
                setListViewAdapter();
                return true;

            case R.id.menuSortNewest:
                clearLists();
                fillListNames();
                setListViewAdapter();
                Collections.sort(lists, new Comparator<FileListItem>() {
                    @Override
                    public int compare(FileListItem lhs, FileListItem rhs) {
                        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1 = df.parse(lhs.getModifiedOn());
                            date2 = df.parse(rhs.getModifiedOn());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (date1 != null) {
                            if (date1.after(date2))
                                return -1;
                            else if (date1.equals(date2)) // it's equals
                                return 0;
                            else
                                return 1;
                        }
                        return 0;
                    }


                });
                fillListNames();
                setListViewAdapter();
                return true;

            case R.id.menuSortOldest:
                clearLists();
                fillListNames();
                setListViewAdapter();
                Collections.sort(lists, new Comparator<FileListItem>() {
                    @Override
                    public int compare(FileListItem lhs, FileListItem rhs) {
                        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
                        Date date1 = null;
                        Date date2 = null;
                        try {
                            date1 = df.parse(lhs.getModifiedOn());
                            date2 = df.parse(rhs.getModifiedOn());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (date2 != null) {
                            if (date2.after(date1))
                                return -1;
                            else if (date2.equals(date1)) // it's equals
                                return 0;
                            else
                                return 1;
                        }
                        return 0;
                    }
                });
                fillListNames();
                setListViewAdapter();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    //Sets the Adapter that populates the ListView
    public void setListViewAdapter(){
        listView = (ListView)findViewById(R.id.shopping_lists);
        ArrayAdapter<String> shoppingListAdaptor = new ArrayAdapter<>(this, R.layout.list_item_shopping_list, R.id.list_name, listNames);
        listView.setAdapter(shoppingListAdaptor);
    }

    //Populates an ArrayList of Shopping List Names
    public void fillListNames(){
        listNames = new ArrayList<>();
        new FileList(this);
        lists = FileList.shoppingLists;
        for (FileListItem list : lists) {
            if(!listNames.contains(list.getName().substring(0,list.getName().length()-4))) {
                listNames.add(list.getName().substring(0, list.getName().length() - 4));
            }
        }
    }

    public void onStop(){
        super.onStop();
        //Clear the lists array so the deleted shopping list doesn't
        //display in the listview when leaving and coming back to the activity
        clearLists();
    }

    public void clearLists(){
        listNames.clear();
        lists.clear();
    }

}
