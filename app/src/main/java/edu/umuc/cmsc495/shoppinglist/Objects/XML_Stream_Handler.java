package edu.umuc.cmsc495.shoppinglist.Objects;

import android.content.Context;
import android.provider.MediaStore;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by James on 6/15/2016.
 */
public class XML_Stream_Handler{
   // private final static String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
    private static Context context;
    public static boolean writeRecipe(Context context, Recipe recipe){

        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            //root element
            Element rootElement = doc.createElement("Recipe");


            //Name Element
            Element recipeName = doc.createElement("Name");
            recipeName.appendChild(doc.createTextNode(recipe.getName()));
            rootElement.appendChild(recipeName);

            //Created On Element
            Element createdOn = doc.createElement("CreatedOn");
            createdOn.appendChild(doc.createTextNode(recipe.getCreatedOn()));
            rootElement.appendChild(createdOn);

            //Last Modified On Element
            Element lastModifiedOn = doc.createElement("LastModifiedOn");
            lastModifiedOn.appendChild(doc.createTextNode(recipe.getLastModifiedOn()));
            rootElement.appendChild(lastModifiedOn);

            //Instructions Element
            Element recipeInstructions = doc.createElement("Instructions");
            recipeInstructions.appendChild(doc.createTextNode(recipe.getInstructions()));
            rootElement.appendChild(recipeInstructions);

            Element ingredients = doc.createElement("Ingredients");

            //Loops to add all ingredients

            for(Ingredient ing: recipe.getIngredientList()){
                Element ingredient = doc.createElement("Ingredient");

                Element ingName = doc.createElement("IngName");
                ingName.appendChild(doc.createTextNode(ing.getName()));
                ingredient.appendChild(ingName);

                Element measurement = doc.createElement("Measurement");
                measurement.appendChild(doc.createTextNode(ing.getMeasurement()));
                ingredient.appendChild(measurement);

                Element countFull = doc.createElement("CountFull");
                countFull.appendChild(doc.createTextNode(ing.getCountFullString()));
                ingredient.appendChild(countFull);

                Element countPartial = doc.createElement("CountPart");
                countPartial.appendChild(doc.createTextNode(ing.getCountPartialString()));
                ingredient.appendChild(countPartial);

                ingredients.appendChild(ingredient);
            }

            rootElement.appendChild(ingredients);
            doc.appendChild(rootElement);

            //Builds file path to write to
            //String fileLocationPath = context.getFilesDir().getAbsolutePath();
            String newFileName = DataLayer.RECIPE_FILE_PREFIX + recipe.getName() + DataLayer.FILE_EXTENSION;
            //String filePathToCreate = fileLocationPath + "/" + newFileName;

            //Writes to XML file
            FileOutputStream fileos = context.openFileOutput(newFileName ,context.MODE_PRIVATE);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fileos);
            transformer.transform(source, result);

            if(result.getOutputStream() != null){
                try{
                    result.getOutputStream().close();
                }catch(Exception e){}
                result.setOutputStream(null);
            }

            //testingCode
            runTests(newFileName, context);

            return true;
        }catch (Exception e){
            return false;
        }

    }

    public static boolean writeShoppingList(Context context, ShoppingList list){

        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            //root element
            Element rootElement = doc.createElement("ShoppingList");


            //Name Element
            Element listName = doc.createElement("Name");
            listName.appendChild(doc.createTextNode(list.getName()));
            rootElement.appendChild(listName);

            //Created On Element
            Element createdOn = doc.createElement("CreatedOn");
            createdOn.appendChild(doc.createTextNode(list.getCreatedOn()));
            rootElement.appendChild(createdOn);

            //Last Modified On Element
            Element lastModifiedOn = doc.createElement("LastModifiedOn");
            lastModifiedOn.appendChild(doc.createTextNode(list.getLastModifiedOn()));
            rootElement.appendChild(lastModifiedOn);

            Element ingredients = doc.createElement("Ingredients");

            //Loops to add all ingredients

            for(Ingredient ing: list.getIngredientList()){
                Element ingredient = doc.createElement("Ingredient");

                Element ingName = doc.createElement("IngName");
                ingName.appendChild(doc.createTextNode(ing.getName()));
                ingredient.appendChild(ingName);

                Element measurement = doc.createElement("Measurement");
                measurement.appendChild(doc.createTextNode(ing.getMeasurement()));
                ingredient.appendChild(measurement);

                Element countFull = doc.createElement("CountFull");
                countFull.appendChild(doc.createTextNode(ing.getCountFullString()));
                ingredient.appendChild(countFull);

                Element countPartial = doc.createElement("CountPart");
                countPartial.appendChild(doc.createTextNode(ing.getCountPartialString()));
                ingredient.appendChild(countPartial);

                Element isCrossedOut = doc.createElement("IsCrossedOut");
                isCrossedOut.appendChild(doc.createTextNode(ing.getCrossed().toString()));
                ingredient.appendChild(isCrossedOut);

                ingredients.appendChild(ingredient);
            }

            rootElement.appendChild(ingredients);

            Element recipes = doc.createElement("Recipes");

            for(Recipe recipe: list.getRecipesAdded()){
                Element recipeE = doc.createElement("Recipe");

                Element recipeName = doc.createElement("RecipeName");
                recipeName.appendChild(doc.createTextNode(recipe.getName().toString()));
                recipeE.appendChild(recipeName);

                Element ingredientsR = doc.createElement("IngredientsR");

                for(Ingredient i:recipe.getIngredientList()){
                    Element ingredient = doc.createElement("IngredientR");

                    Element ingName = doc.createElement("IngNameR");
                    ingName.appendChild(doc.createTextNode(i.getName()));
                    ingredient.appendChild(ingName);

                    Element measurement = doc.createElement("MeasurementR");
                    measurement.appendChild(doc.createTextNode(i.getMeasurement()));
                    ingredient.appendChild(measurement);

                    Element countFull = doc.createElement("CountFullR");
                    countFull.appendChild(doc.createTextNode(i.getCountFullString()));
                    ingredient.appendChild(countFull);

                    Element countPartial = doc.createElement("CountPartR");
                    countPartial.appendChild(doc.createTextNode(i.getCountPartialString()));
                    ingredient.appendChild(countPartial);

                    Element isCrossedOutR = doc.createElement("IsCrossedOutR");
                    isCrossedOutR.appendChild(doc.createTextNode(i.getCrossed().toString()));
                    ingredient.appendChild(isCrossedOutR);
                    
                    ingredientsR.appendChild(ingredient);

                }

                recipeE.appendChild(ingredientsR);
                recipes.appendChild(recipeE);
            }
            rootElement.appendChild(recipes);

            doc.appendChild(rootElement);

            //Builds file path to write to
           // String fileLocationPath = context.getFilesDir().getAbsolutePath();
            //String newFileName = DataLayer.SHOPPING_LIST_FILE_PREFIX + list.getName()+ DataLayer.FILE_EXTENSION;
            String newFileName = DataLayer.SHOPPING_LIST_FILE_PREFIX + list.getName() + DataLayer.FILE_EXTENSION;
            //String filePathToCreate = fileLocationPath + "/" + newFileName;

            //Writes to XML file
            FileOutputStream fileos = context.openFileOutput(newFileName ,context.MODE_PRIVATE);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fileos);
            transformer.transform(source, result);

            if(result.getOutputStream() != null){
                try{
                    result.getOutputStream().close();
                }catch(Exception e){}
                result.setOutputStream(null);
            }

            //testingCode
            runTests(newFileName, context);

            return true;
        }catch (Exception e){
            return false;
        }
    }
    private static void runTests(String fileName, Context context){
        File[] files = context.getFilesDir().listFiles();
        try{
            FileInputStream in = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            inputStreamReader.close();
        } catch (Exception e){

        }
    }
}
