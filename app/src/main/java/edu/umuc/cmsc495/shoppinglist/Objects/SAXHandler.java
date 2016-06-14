package edu.umuc.cmsc495.shoppinglist.Objects;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.*;
import edu.umuc.cmsc495.shoppinglist.Objects.*;

import java.util.*;
import java.io.*;
import javax.xml.parsers.SAXParser;

/**
 * Created by James on 6/14/2016.
 */
public class SAXHandler extends DefaultHandler{

    //DataLayer data;
    String elementValue = null;
    Boolean elementOn = false;
    public static XMLGettersSetters data = null;
        public static XMLGettersSetters getXMLData(){ return data; }
        public static void setXMLData(XMLGettersSetters data){ SAXHandler.data = data; }


    public void startElement(String localName, String qName,
                             Attributes attributes) throws SAXException {

            elementOn = true;

        if(localName.)
    }



}
