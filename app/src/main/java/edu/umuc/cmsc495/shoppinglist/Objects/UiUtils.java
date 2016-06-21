package edu.umuc.cmsc495.shoppinglist.Objects;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by asdf on 6/20/2016.
 */
public class UiUtils {
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
        public static final String emailNewLine(){
            return "\n";
        }
        public static final String getAppName(){
            return "Grocery Butler";
        }
        public static String getDateTimeNow(){
            return SimpleDateFormat.getDateTimeInstance(
                    DateFormat.MEDIUM, DateFormat.SHORT).format(new Date());
        }
        public static String formatDate(Date in){
            return SimpleDateFormat.getDateTimeInstance(
                    DateFormat.MEDIUM, DateFormat.SHORT).format(in);
        }

        public static final ArrayList<String> getPartialQuantities() {
            ArrayList<String> spinnerArray =  new ArrayList<>();
            spinnerArray.add("1/8");
            spinnerArray.add("1/4");
            spinnerArray.add("1/3");
            spinnerArray.add("1/2");
            spinnerArray.add("2/3");
            spinnerArray.add("3/4");
            return spinnerArray;
        }

        public static final ArrayList<String> getMeasurementsShoppingList(){
            ArrayList<String> spinnerArray =  new ArrayList<>();
            spinnerArray.add("Box");
            spinnerArray.add("Bag");
            spinnerArray.add("Bottle");
            spinnerArray.add("Can");
            ArrayList<String> recipeMeasurements = getMeasurementsRecipe(); //put the small stuff at the end, after all, who buys one tsp of salt?
            Collections.reverse(recipeMeasurements);
            for(String s: recipeMeasurements)
                spinnerArray.add(s);
            return spinnerArray;
        }

        public static final ArrayList<String> getMeasurementsRecipe(){
            ArrayList<String> spinnerArray =  new ArrayList<>();
            spinnerArray.add("Pinch");
            spinnerArray.add("Tsp");
            spinnerArray.add("Tbsp");
            spinnerArray.add("Oz");
            spinnerArray.add("Cup");
            spinnerArray.add("Pint");
            spinnerArray.add("Liter"); //one metric unit just for fun.  Gotta have a little fun!
            spinnerArray.add("Quart");
            spinnerArray.add("Pound");
            spinnerArray.add("Gallon");
            return spinnerArray;
        }

        public static Float getDecimalFromFraction(String in){
            Float output = 0.00f;
            String[] operands = in.split("/");
            Float f = Float.parseFloat(operands[0]) / Float.parseFloat(operands[1]);
            return output;
        }
}
