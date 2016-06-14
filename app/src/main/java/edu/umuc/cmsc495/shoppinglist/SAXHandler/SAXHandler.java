package edu.umuc.cmsc495.shoppinglist.SAXHandler;

import org.xml.sax.helpers.*;
import edu.umuc.cmsc495.shoppinglist.Objects.*;

import java.util.*;
import java.io.*;
import javax.xml.parsers.SAXParser;

/**
 * Created by James on 6/14/2016.
 */
public class SAXHandler extends DefaultHandler{

    DataLayer data;
    String elementValue = null;
    Boolean elementOn = false;

    //Constructor
    protected SAXHandler(DataLayer dataObject){
        data = dataObject;
    }


}
