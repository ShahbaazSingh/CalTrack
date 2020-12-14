package com.example.herma.caltrack;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends ArrayAdapter<Meal> {

    private Context mContext;
    private List<Meal> mealList = new ArrayList<>();
    private Meal currentMeal;

    public MealAdapter(@NonNull Context context, ArrayList<Meal> list){
        super(context, 0, list);
        mContext = context;
        mealList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View setView, @NonNull ViewGroup parent){

        View listItem = setView;
        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.meal_list_item, parent, false);
        }

        currentMeal = mealList.get(position);

        if(currentMeal.isSelected())
            listItem.setBackgroundColor(Color.LTGRAY);

        else
            listItem.setBackgroundColor(Color.TRANSPARENT);


        TextView name = (TextView)listItem.findViewById(R.id.textViewMealName);
        name.setText(currentMeal.getName());

        TextView macros = (TextView)listItem.findViewById(R.id.textViewMealMacros);
        macros.setText(currentMeal.getString());

        return listItem;

    }

}
