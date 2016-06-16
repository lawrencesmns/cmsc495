package edu.umuc.cmsc495.shoppinglist.Objects;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 6/14/2016.
 */
public class SAXHandler_ShoppingList extends DefaultHandler{

    //DataLayer data;
    String elementValue = null;
    Boolean elementOn = false;
    ShoppingList tempList;

    public static List<ShoppingList> ShoppingLists = new ArrayList<ShoppingList>();
        public static List<ShoppingList> getXMLData(){ return ShoppingLists; }
        public static void setXMLData(List<ShoppingList> data){ SAXHandler_ShoppingList.ShoppingLists = data; }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {

        //reset
        elementValue = "";
        if(qName.equalsIgnoreCase("ShoppingList")){
            //new recipe
            //Log.i("Handler", "Creating new recipe object");
            this.tempList = new ShoppingList();
        }

    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        elementValue = new String(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equalsIgnoreCase("ShoppingList")) {
            // add it to the list
            ShoppingLists.add(tempList);
            Log.d("List added", tempList.getName());
        } else if (qName.equalsIgnoreCase("Name")) {
            tempList.setName(elementValue);
            //Log.i("list name;", elementValue);
        } else if (qName.equalsIgnoreCase("Ingredient")) {
            tempList.addIngredient(elementValue);
            //Log.i("ingredient;", elementValue);
        } else if (qName.equalsIgnoreCase("emailBody")) {
            tempList.setEmailBody(elementValue);
            //Log.i("email body;", elementValue);
        } else if (qName.equalsIgnoreCase("emailSubject")) {
            tempList.setEmailSubject(elementValue);
            //Log.i("email subject;", elementValue);
        }
    }
}
