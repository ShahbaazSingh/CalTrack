package com.example.herma.caltrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MealPlan extends AppCompatActivity {

    private ListView listView;
    private MealAdapter mAdapter;
    private ArrayList<Meal> mealList;
    private DatabaseHelper mealDatabase;
    private SharedPreferences sp;
    private SharedPreferences timePreference;
    private TextView textViewTotalCalories;
    private TextView textViewTotalMacros;
    private int totalSelectedCalories;
    private int totalSelectedFat;
    private int totalSelectedCarbohydrates;
    private int totalSelectedProtein;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        mealDatabase = new DatabaseHelper(this, "meal_table");
        listView = (ListView)findViewById(R.id.listViewMeals);
        mealList = new ArrayList<Meal>();
        mAdapter = new MealAdapter(this, mealList);

        textViewTotalCalories = (TextView)findViewById(R.id.textViewTotalCalories);
        textViewTotalMacros = (TextView)findViewById(R.id.textViewTotalMacros);


        timePreference = this.getSharedPreferences("time", MODE_PRIVATE);
        sp = this.getSharedPreferences("totalMacrosPreferences", MODE_PRIVATE);

        textViewTotalCalories.setText(String.valueOf(sp.getInt("preferenceCalories",0)));
        textViewTotalMacros.setText("Fat: " +String.valueOf(sp.getInt("preferenceFat",0))+"g   "+"Carbohydrates: " +String.valueOf(sp.getInt("preferenceCarbohydrates",0))+"g   "+"Protein: " +String.valueOf(sp.getInt("preferenceProtein",0))+"g   ");

        listView.setAdapter(mAdapter);

        Cursor fill = mealDatabase.getAllData();
        Meal fillFromDatabaseMeal;
        while(fill.moveToNext()){
            fillFromDatabaseMeal = new Meal(fill.getString(1), Integer.parseInt(fill.getString(2)),
                    Integer.parseInt(fill.getString(3)), Integer.parseInt(fill.getString(4)),
                    Integer.parseInt(fill.getString(5)), false, Long.parseLong(fill.getString(0)), 1);
            mealList.add(fillFromDatabaseMeal);
        }
        mAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meal mealItem = (Meal)adapterView.getItemAtPosition(i);
                if(mealItem.isSelected()){
                    mealItem.setSelected(false);
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
                else {
                    mealItem.setSelected(true);
                    view.setBackgroundColor(Color.LTGRAY);
                }

                mAdapter.notifyDataSetChanged();

            }
        });


    }

    public void deleteMealOnClick(View view){

        if(!mealList.isEmpty()){
            for(int i=0; i<mealList.size();i++){
                if(mealList.get(i).isSelected()) {
                    mealList.get(i).setSelected(false);
                    mealDatabase.removeData(mealList.get(i).getdbID());
                    mealList.remove(i);
                    i--;
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    public void save(String valueKey, int value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(valueKey, value);
        edit.apply();
        textViewTotalCalories.setText(String.valueOf(sp.getInt("preferenceCalories",0)));
        textViewTotalMacros.setText("Fat: " +String.valueOf(sp.getInt("preferenceFat",0))+"g   "+"Carbohydrates: " +String.valueOf(sp.getInt("preferenceCarbohydrates",0))+"g   "+"Protein: " +String.valueOf(sp.getInt("preferenceProtein",0))+"g   ");
    }

    public void addMealsOnClick(View view){

        totalSelectedCalories = sp.getInt("preferenceCalories",0);
        totalSelectedFat = sp.getInt("preferenceFat",0);
        totalSelectedCarbohydrates = sp.getInt("preferenceCarbohydrates",0);
        totalSelectedProtein = sp.getInt("preferenceProtein",0);

        if(!mealList.isEmpty()){
            for(int i=0; i<mealList.size();i++){
                if(mealList.get(i).isSelected()) {
                    mealList.get(i).setSelected(false);
                    totalSelectedCalories += mealList.get(i).getCalories();
                    totalSelectedFat += mealList.get(i).getFat();
                    totalSelectedCarbohydrates += mealList.get(i).getCarbohydrates();
                    totalSelectedProtein += mealList.get(i).getProtein();
                }
            }
        }

        save("preferenceCalories",totalSelectedCalories);
        save("preferenceFat",totalSelectedFat);
        save("preferenceCarbohydrates",totalSelectedCarbohydrates);
        save("preferenceProtein",totalSelectedProtein);
        mAdapter.notifyDataSetChanged();
    }


    public void navigateToAddMeal(View view){

        Intent intent = new Intent(this, AddMeal.class);
        startActivity(intent);
    }

}
