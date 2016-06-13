package edu.umuc.cmsc495.shoppinglist.UI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.umuc.cmsc495.shoppinglist.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewShoppingListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewShoppingListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewShoppingListFragment extends Fragment {
    ArrayAdapter<String> mRecipeAdapter;



    private OnFragmentInteractionListener mListener;

    public NewShoppingListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewShoppingListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewShoppingListFragment newInstance(String param1, String param2) {
        NewShoppingListFragment fragment = new NewShoppingListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().closeOptionsMenu();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Test");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //this is dummy data!
        String[] dummyRecipesArray = {
                "Sausage and Pepperoni Pizza",
                "Chicken Cordon Bleu",
                "New York Strip Steak",
                "Oatmeal Raisin Cookies",
                "Chicken Carbonera"
        };

        List<String> dummyRecipes = new ArrayList(Arrays.asList(dummyRecipesArray));
        mRecipeAdapter = new ArrayAdapter(getActivity(),
                R.layout.list_item_new_shopping, R.id.list_item_recipename_textview, dummyRecipes);


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_shopping_list, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_recipesnew);
        listView.setAdapter(mRecipeAdapter);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
