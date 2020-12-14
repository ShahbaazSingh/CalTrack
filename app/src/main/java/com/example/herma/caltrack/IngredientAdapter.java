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

public class IngredientAdapter extends ArrayAdapter<Ingredient> {

    private Context mContext;
    private List<Ingredient> ingredientList = new ArrayList<>();
    //Integer[] numberSpinnerChoices = new Integer[]{1,2,3,4,5,6,7,8,9,10};
    private Ingredient currentIngredient;
    //Spinner spinner;
    //boolean touched = false;

    public IngredientAdapter(@NonNull Context context, ArrayList<Ingredient> list){
        super(context, 0, list);
        mContext = context;
        ingredientList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View setView, @NonNull ViewGroup parent){

        View listItem = setView;
        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.ingredient_list_item, parent, false);
        }

        currentIngredient = ingredientList.get(position);

       /* spinner = (Spinner)listItem.findViewById(R.id.ingredientNumberSpinner);
        ArrayAdapter<Integer> sAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, numberSpinnerChoices);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sAdapter);*/

        if(currentIngredient.isSelected())
            listItem.setBackgroundColor(Color.LTGRAY);

        else
            listItem.setBackgroundColor(Color.TRANSPARENT);

        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
                spinner.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        touched = true;
                        return false;
                    }
                });
                if(touched == true){
                    currentIngredient.setNumberSelected(i);
                    touched = false;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView){

            }
        });*/



        TextView name = (TextView)listItem.findViewById(R.id.textViewMealName);
        name.setText(currentIngredient.getName());

        TextView macros = (TextView)listItem.findViewById(R.id.textViewMacros);
        macros.setText(currentIngredient.getString());

        EditText num = (EditText)listItem.findViewById(R.id.editTextNumberSelected);
        num.setText(String.valueOf(currentIngredient.getNumberSelected()));
        //currentIngredient.setNumberSelected(Integer.parseInt(num.getText().toString()));
        return listItem;

    }

}
