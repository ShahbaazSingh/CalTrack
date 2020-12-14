package com.example.herma.caltrack;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AddMeal extends AppCompatActivity {

    private ListView listView;
    private IngredientAdapter iAdapter;
    private IngredientAdapter cAdapter;
    private ArrayList<Ingredient> ingredientList;
    private ArrayList<Ingredient> confirmIngredientsList;
    private AlertDialog.Builder alertDialog;
    private AlertDialog ad;
    private DatabaseHelper ingredientDatabase;
    private DatabaseHelper mealDatabase;
    private int confirmTotalCalories;
    private int confirmTotalFat;
    private int confirmTotalCarbohydrates;
    private int confirmTotalProtein;
    private String confirmMealName;
    private TextView textViewConfirmTotalMacros;
    private String stringConfirmTotalMacros;
    private View convertView;
    private EditText EditTextConfirmMealName;

                                                                //it's 4am my dudess
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        StringBuffer buffer = new StringBuffer();
        ingredientDatabase = new DatabaseHelper(this, "ingredient_table");
        listView = (ListView)findViewById(R.id.listViewIngredientList);
        ingredientList = new ArrayList<Ingredient>();
        iAdapter = new IngredientAdapter(this,ingredientList);
        int test;
        listView.setAdapter(iAdapter);


        Cursor fill = ingredientDatabase.getAllData();
        Ingredient fillFromDatabaseIngredient;
        while(fill.moveToNext()){
            fillFromDatabaseIngredient = new Ingredient(fill.getString(1), Integer.parseInt(fill.getString(2)),
                    Integer.parseInt(fill.getString(3)), Integer.parseInt(fill.getString(4)),
                    Integer.parseInt(fill.getString(5)), false, Long.parseLong(fill.getString(0)), 1);
            ingredientList.add(fillFromDatabaseIngredient);
        }
        iAdapter.notifyDataSetChanged();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ingredient ingredientItem = (Ingredient)adapterView.getItemAtPosition(i);
                if(ingredientItem.isSelected()){
                    ingredientItem.setSelected(false);
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
                else {
                    ingredientItem.setSelected(true);
                    view.setBackgroundColor(Color.LTGRAY);

                }
                iAdapter.notifyDataSetChanged();

            }
        });

    }

    public void addIngredientOnClick(View view){

        String nameData;
        int caloriesData, fatData, carbohydratesData, proteinData;
        EditText name, calories, fat, carbohydrates, protein;
        String x;
        long dbID;

        name = (EditText)findViewById(R.id.editTextName);
        calories = (EditText)findViewById(R.id.editTextCalories);
        fat = (EditText)findViewById(R.id.editTextFat);
        carbohydrates = (EditText)findViewById(R.id.editTextCarbohydrates);
        protein = (EditText)findViewById(R.id.editTextProtein);

        x = name.getText().toString();
        if(x.isEmpty())
            nameData = "unspecified ingredient name";
        else
            nameData = x;

        x = calories.getText().toString();
        if(x.isEmpty())
            caloriesData = 0;
        else
        caloriesData= Integer.parseInt(x);

        x = fat.getText().toString();
        if(x.isEmpty())
            fatData = 0;
        else
            fatData = Integer.parseInt(x);

        x = carbohydrates.getText().toString();
        if(x.isEmpty())
            carbohydratesData = 0;
        else
            carbohydratesData = Integer.parseInt(x);

        x = protein.getText().toString();
        if(x.isEmpty())
            proteinData = 0;
        else
            proteinData = Integer.parseInt(x);

        dbID = ingredientDatabase.insertData(nameData, caloriesData, fatData, carbohydratesData, proteinData);
        Ingredient currentIngredient = new Ingredient(nameData, caloriesData, fatData, carbohydratesData, proteinData, false, dbID, 1);
        ingredientList.add(currentIngredient);
        iAdapter.notifyDataSetChanged();
    }

    public void createConfirmDialog(View view){
        confirmIngredientsList = new ArrayList<>();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        ad = alertDialog.create();

        LayoutInflater inflater = getLayoutInflater();
        convertView = (View) inflater.inflate(R.layout.confirm_ingredients_list, null);
        ad.setView(convertView);
        ad.setTitle("Confirm Meal");
                                                            //It's 4 am, my dudes

        ListView lvConfirm = (ListView) convertView.findViewById(R.id.lvConfirmIngredientsForMeal);
        lvConfirm.setItemsCanFocus(true);
        cAdapter = new IngredientAdapter(this, confirmIngredientsList);
        lvConfirm.setAdapter(cAdapter);
        mealDatabase = new DatabaseHelper(this, "meal_table");
        confirmTotalCalories = 0;
        confirmTotalFat = 0;
        confirmTotalCarbohydrates = 0;
        confirmTotalProtein = 0;


        if(!ingredientList.isEmpty()){
                for(int i=0; i<ingredientList.size();i++){
                    if(ingredientList.get(i).isSelected()){
                        ingredientList.get(i).setSelected(false);
                        confirmIngredientsList.add(ingredientList.get(i));
                        i--;
                    }
                }
            }

        cAdapter.notifyDataSetChanged();

            for(int i = 0; i < confirmIngredientsList.size(); i++){
                confirmTotalCalories += confirmIngredientsList.get(i).getCalories();
                confirmTotalFat += confirmIngredientsList.get(i).getFat();
                confirmTotalCarbohydrates += confirmIngredientsList.get(i).getCarbohydrates();
                confirmTotalProtein += confirmIngredientsList.get(i).getProtein();
            }
        stringConfirmTotalMacros = "Calories: " +confirmTotalCalories+"   "+"Fat: " +confirmTotalFat+"g   "+"Carbohydrates: " +confirmTotalCarbohydrates+"g   "+"Protein: " +confirmTotalProtein+"g   ";
        textViewConfirmTotalMacros = (TextView)convertView.findViewById(R.id.textViewConfirmTotalMacros);
        textViewConfirmTotalMacros.setText(stringConfirmTotalMacros);

        EditTextConfirmMealName = (EditText)convertView.findViewById(R.id.editTextConfirmMealName);

        iAdapter.notifyDataSetChanged();
        ad.show();

        lvConfirm.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    confirmTotalCalories -= confirmIngredientsList.get(i).getCalories();
                    confirmTotalFat -= confirmIngredientsList.get(i).getFat();
                    confirmTotalCarbohydrates -= confirmIngredientsList.get(i).getCarbohydrates();
                    confirmTotalProtein -= confirmIngredientsList.get(i).getProtein();

                stringConfirmTotalMacros = "Calories: " +confirmTotalCalories+"   "+"Fat: " +confirmTotalFat+"g   "+"Carbohydrates: " +confirmTotalCarbohydrates+"g   "+"Protein: " +confirmTotalProtein+"g   ";
                textViewConfirmTotalMacros = (TextView)convertView.findViewById(R.id.textViewConfirmTotalMacros);
                textViewConfirmTotalMacros.setText(stringConfirmTotalMacros);

                confirmIngredientsList.remove(i);
                cAdapter.notifyDataSetChanged();


                return false;
            }
        });



        Button alertExit = (Button)ad.findViewById(R.id.btnExitAlert);
            alertExit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    ad.dismiss();
                }
            });

        Button alertConfirm = (Button)ad.findViewById(R.id.btnConfirmAddMeal);
            alertConfirm.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    confirmMealName = EditTextConfirmMealName.getText().toString();
                    if(confirmMealName.isEmpty())
                        confirmMealName = "unspecified meal name";
                    mealDatabase.insertData(confirmMealName, confirmTotalCalories, confirmTotalFat, confirmTotalCarbohydrates, confirmTotalProtein);
                    ad.dismiss();
                }
            });
    }


    public void deleteIngredientOnClick(View view){

        if(!ingredientList.isEmpty()){
            for(int i=0; i<ingredientList.size();i++){
                if(ingredientList.get(i).isSelected()) {
                    ingredientList.get(i).setSelected(false);
                    ingredientDatabase.removeData(ingredientList.get(i).getdbID());
                    ingredientList.remove(i);
                    i--;
                }
            }
        }
        iAdapter.notifyDataSetChanged();
    }



}
