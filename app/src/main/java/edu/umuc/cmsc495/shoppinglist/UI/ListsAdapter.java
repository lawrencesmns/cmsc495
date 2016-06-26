package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.umuc.cmsc495.shoppinglist.R;

/**
 * Created by Michael on 6/25/2016.
 */
public class ListsAdapter<T> extends ArrayAdapter<String> {
    private int layout;
    private List<String> names;
    public ListsAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        layout = resource;
        names = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mainViewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.listName = (TextView) convertView.findViewById(R.id.list_name);
            viewHolder.listName.setText(names.get(position));
            viewHolder.deleteList = (ImageButton) convertView.findViewById(R.id.list_delete);
            viewHolder.deleteList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Delete " + viewHolder.listName.getText(),Toast.LENGTH_SHORT).show();
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
        this.names.addAll(names);
        this.notifyDataSetChanged();
    }
}
class ViewHolder{
    TextView listName;
    ImageButton deleteList;
}