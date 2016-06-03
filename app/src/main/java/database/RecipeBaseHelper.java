package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.RecipeDbSchema.RecipeTable;

/**
 * Created by Veloso on 6/1/2016.
 *
 * Class designed to get rid of the grunt work of opening a SQLiteDatabase
 */
public class RecipeBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "recipeBase.db";

    public RecipeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + RecipeTable.NAME + "(" +
                               "_id integer primary key autoincrement, " +
                                RecipeTable.Cols.UUID + ", " +
                                RecipeTable.Cols.TITLE + ", " +
                                RecipeTable.Cols.INGREDIENTS + ", " +
                                RecipeTable.Cols.DIRECTIONS +
                                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
