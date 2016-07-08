package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.umuc.cmsc495.shoppinglist.Objects.DataLayer;
import edu.umuc.cmsc495.shoppinglist.Objects.Recipe;
import edu.umuc.cmsc495.shoppinglist.Objects.ShoppingList;
import edu.umuc.cmsc495.shoppinglist.R;

/**
 * Created by Michael on 6/25/2016.
 */
public class ListsAdapter<T> extends ArrayAdapter<String> {
    private int layout;
    private List<String> names;
    private Context context;
    public ListsAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        layout = resource;
        names = objects;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder mainViewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.listName = (TextView) convertView.findViewById(R.id.list_name);
            viewHolder.listName.setText(names.get(position));
            viewHolder.deleteList = (ImageButton) convertView.findViewById(R.id.list_delete);
            final View finalConvertView = convertView;

            viewHolder.deleteList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    //Prompt user to confirm deletion of shopping list
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    builder.setMessage("Are you sure you want to delete " + viewHolder.listName.getText() + "?")

                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (context.getClass().getSimpleName().equals("ManageShoppingLists")) {

                                        //Create shopping list object of the selected shopping list for deletion
                                        //ShoppingList shoppingList = new ShoppingList(getContext().getApplicationContext()); //martin commented
                                        //shoppingList.setName(viewHolder.listName.getText().toString()); //martin commented

                                        //Kept getting a NullPointer exception without doing the above first
                                        DataLayer dataLayer = new DataLayer(getContext().getApplicationContext());

                                        //Delete shopping list and remove the name from the listview
                                        //if (dataLayer.deleteShoppingList(ShoppingList.loadShoppingList(shoppingList.getName()))) { //martin commented
                                        if (dataLayer.deleteShoppingList(viewHolder.listName.getText().toString())) {
                                            names.remove(viewHolder.listName.getText());
                                            upDateList(names);
                                            Toast.makeText(getContext(), viewHolder.listName.getText() + " deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    }else if(context.getClass().getSimpleName().equals("ManageRecipes")){
                                        //Create recipe object of the selected shopping list for deletion
                                        // Recipe recipe = new Recipe(getContext().getApplicationContext()); //martin commented
                                        // recipe.setName(viewHolder.listName.getText().toString()); //martin commented

                                        //Kept getting a NullPointer exception without doing the above first
                                        DataLayer dataLayer = new DataLayer(getContext().getApplicationContext());

                                        //Delete recipe and remove the name from the listview
                                        //if (dataLayer.deleteRecipe(Recipe.loadRecipe(recipe.getName()))) { //martin commented
                                        if (dataLayer.deleteRecipe(viewHolder.listName.getText().toString())) {
                                            names.remove(viewHolder.listName.getText());
                                            upDateList(names);
                                            Toast.makeText(getContext(), viewHolder.listName.getText() + " deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.show();

                }
            });
            convertView.setTag(viewHolder);
        }
        else{
            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.listName.setText(names.get(position));
        }

        return convertView;

    }

    public void upDateList(List<String> names){
        this.names = names;
        //this.names.addAll(names);
        this.notifyDataSetChanged();
    }


}
class ViewHolder{
    TextView listName;
    ImageButton deleteList;
}