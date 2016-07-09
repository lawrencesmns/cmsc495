package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import edu.umuc.cmsc495.shoppinglist.Objects.Ingredient;
import edu.umuc.cmsc495.shoppinglist.R;

/**
 * Created by asdf on 7/9/2016.
 */


    public class NewShoppingListAdapter extends ArrayAdapter<Ingredient> {

        private ArrayList<Ingredient> items;



        public NewShoppingListAdapter(Context context, int textViewResourceId, ArrayList<Ingredient> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                v = inflater.inflate(R.layout.list_item_added_ingredient_shoppinglist, parent, false);
            }
            Ingredient i = items.get(position);
            if (i != null) {
                TextView tv = (TextView) v.findViewById(R.id.list_item_ingredient_textview);
                if (tv != null) {
                    tv.setText(i.toString());
                    if (i.getCrossed()) {
                        tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }else{
                        tv.setPaintFlags(tv.getPaintFlags()  & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    }

                }
            }
            return v;
        }
    }

