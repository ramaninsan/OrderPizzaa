package com.example.pizzaaa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.pizzaaa.dataClasses.Orders;
import com.example.pizzaaa.dataClasses.Pizza;
import com.example.pizzaaa.dataClasses.Reviews;

public class DBHelper extends SQLiteOpenHelper {

    // Declaring database version
    static final String DBNAME = "Pizza.db";
    static int VERSION = 1;


    // Table Pizza
    static final String PIZZA_TABLE_NAME = "Pizza";
    static final String COL1 = "itemId";
    static final String COL2 = "itemName";
    static final String COL3 = "quantity";
    static final String COL4 = "price";
    static final String COL5 = "calorie";
    static final String COL6 = "prepTime";
    static final String COL7 = "size";
    static final String COL8 = "image";

    static final String CREATE_PIZZA_TABLE = "create table "+ PIZZA_TABLE_NAME +
            "("+
            COL1+ " INTEGER," +
            COL2 + " TEXT NOT NULL, " +
            COL3 + " INTEGER, " +
            COL4 + " INTEGER, " +
            COL5 + " STRING, " +
            COL6 + " STRING, " +
            COL7 + " STRING, " +
            COL8 + " INTEGER " +
            ")";

    static final String DROP_PIZZA_TABLE = "DROP TABLE IF EXISTS "+ PIZZA_TABLE_NAME;


    // Table Orders
    static final String ORDERS_TABLE_NAME = "Orders";
    static final String COLM1 = "itemId";
    static final String COLM2 = "itemName";
    static final String COLM3 = "quantity";
    static final String COLM4 = "price";
    static final String COLM5 = "calorie";
    static final String COLM6 = "prepTime";
    static final String COLM7 = "size";
    static final String COLM8 = "image";
    static final String COLM9 = "style";
    static final String COLM10 = "toppings";
    static final String COLM11 = "otherDesc";

    static final String CREATE_ORDERS_TABLE = "create table "+ ORDERS_TABLE_NAME +
            "("+
            COLM1+ " INTEGER," +
            COLM2 + " TEXT NOT NULL, " +
            COLM3 + " INTEGER, " +
            COLM4 + " INTEGER, " +
            COLM5 + " STRING, " +
            COLM6 + " STRING, " +
            COLM7 + " STRING, " +
            COLM8 + " INTEGER, " +
            COLM9 + " STRING, " +
            COLM10 + " STRING, " +
            COLM11 + " STRING " +
            ")";

    static final String DROP_ORDERS_TABLE = "DROP TABLE IF EXISTS "+ ORDERS_TABLE_NAME;




    // Table Reviews
    static final String REVIEWS_TABLE_NAME = "Reviews";
    static final String COLMM1 = "pizzaName";
    static final String COLMM2 = "userName";
    static final String COLMM3 = "userPic";
    static final String COLMM4 = "rating";
    static final String COLMM5 = "comment";
    static final String COLMM6 = "likes";

    static final String CREATE_REVIEWS_TABLE = "create table "+ REVIEWS_TABLE_NAME +
            "("+
            COLMM1+ " STRING," +
            COLMM2 + " STRING, " +
            COLMM3 + " INTEGER, " +
            COLMM4 + " STRING, " +
            COLMM5 + " STRING, " +
            COLMM6 + " STRING " +
            ")";

    static final String DROP_REVIEWS_TABLE = "DROP TABLE IF EXISTS "+ REVIEWS_TABLE_NAME;





    public DBHelper(@Nullable Context context) {
        super(context,DBNAME,null,VERSION);
    }



    // creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PIZZA_TABLE);
        db.execSQL(CREATE_ORDERS_TABLE);
        db.execSQL(CREATE_REVIEWS_TABLE);
    }


    // when database version changes it will upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_PIZZA_TABLE);
        db.execSQL(DROP_ORDERS_TABLE);
        db.execSQL(DROP_REVIEWS_TABLE);
        onCreate(db);
    }


  // to add items in database
    public boolean AddPizza(Pizza item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL1,item.getId());
        cv.put(COL2,item.getName());
        cv.put(COL3,item.getQuantity());
        cv.put(COL4,item.getPrice());
        cv.put(COL5,item.getCalories());
        cv.put(COL6,item.getPrepTime());
        cv.put(COL7,item.getSize());
        cv.put(COL8,item.getImage());
        long result = db.insert(PIZZA_TABLE_NAME,null,cv);
        return (result==-1 ? false : true);
    }


    // to add items in database
    public boolean AddOrders(Orders item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLM1,item.getId());
        cv.put(COLM2,item.getName());
        cv.put(COLM3,item.getQuantity());
        cv.put(COLM4,item.getPrice());
        cv.put(COLM5,item.getCalories());
        cv.put(COLM6,item.getPrepTime());
        cv.put(COLM7,item.getSize());
        cv.put(COLM8,item.getImage());
        cv.put(COLM9,item.getStyle());
        cv.put(COLM10,item.getToppings());
        cv.put(COLM11,item.getOtherDesc());
        long result = db.insert(ORDERS_TABLE_NAME,null,cv);
        return (result==-1 ? false : true);
    }




    // to add items in database
    public boolean AddReviews(Reviews item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLMM1,item.getPizzaName());
        cv.put(COLMM2,item.getUsernames());
        cv.put(COLMM3,item.getUserPic());
        cv.put(COLMM4,item.getRating());
        cv.put(COLMM5,item.getComments());
        cv.put(COLMM6,item.getLikes());
        long result = db.insert(REVIEWS_TABLE_NAME,null,cv);
        return (result==-1 ? false : true);
    }




    // to update item details in table
   /* public boolean UpdateItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL3,item.getQuantity());
        long result = db.update(STOCK_TABLE_NAME,cv,"itemId=?",new String[]{String.valueOf(item.getId())});
        return (result==-1 ? false : true);
    }*/




    /*public int DeleteEmployee(int EmpId){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME,"id="+EmpId,null);
    }*/


// fetch items from pizza table
    public Cursor ListItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorObj;
        cursorObj = db.rawQuery("select * from " + PIZZA_TABLE_NAME,null);
        if(cursorObj!=null){
            cursorObj.moveToFirst();
        }
        return cursorObj;

    }

//fetch orders from orders table
    public Cursor ListOrders(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorObj;
        cursorObj = db.rawQuery("select * from " + ORDERS_TABLE_NAME,null);
        if(cursorObj!=null){
            cursorObj.moveToFirst();
        }
        return cursorObj;

    }

// fetcj reviews from Review table
    public Cursor ListReviews(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorObj;
        cursorObj = db.rawQuery("select * from " + REVIEWS_TABLE_NAME,null);
        if(cursorObj!=null){
            cursorObj.moveToFirst();
        }
        return cursorObj;

    }

    public int DeleteOrders(int orderId){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(ORDERS_TABLE_NAME,"itemId="+orderId,null);
    }


    public Cursor getById(String name) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from "+PIZZA_TABLE_NAME+" WHERE itemName like "+name+"";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        return cursor;
    }














    /*public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.SUBJECT, DatabaseHelper.DESC };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, name);
        contentValues.put(DatabaseHelper.DESC, desc);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }


    public int update(long _id, int quantity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, name);
        contentValues.put(DatabaseHelper.DESC, desc);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }*/
}
