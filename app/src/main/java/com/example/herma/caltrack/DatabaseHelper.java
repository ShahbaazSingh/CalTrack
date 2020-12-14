package com.example.herma.caltrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "IngredientsAndMeals.db";
    public static String TABLE_NAME_INGREDIENT = "ingredient_table";
    public static String TABLE_NAME_MEAL = "meal_table";
    public String tableName;
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "CALORIES";
    public static final String COL_4 = "FAT";
    public static final String COL_5 = "CARBOHYDRATES";
    public static final String COL_6 = "PROTEIN";

    public DatabaseHelper(@Nullable Context context, String tableName) {
        super(context, DATABASE_NAME, null, 1);
        this.tableName = tableName;
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME_INGREDIENT+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, CALORIES INT, FAT INT, CARBOHYDRATES INT, PROTEIN INT)");
        sqLiteDatabase.execSQL("create table "+TABLE_NAME_MEAL+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, CALORIES INT, FAT INT, CARBOHYDRATES INT, PROTEIN INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertData(String name, int calories, int fat, int carbohydrates, int protein) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, calories);
        contentValues.put(COL_4, fat);
        contentValues.put(COL_5, carbohydrates);
        contentValues.put(COL_6, protein);
        long result = db.insert(tableName, null, contentValues);
        return result;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+tableName,null);
        return res;
    }

    public void removeData(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+tableName+" where ID="+id);
    }

}
