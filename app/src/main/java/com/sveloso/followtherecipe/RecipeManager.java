package com.sveloso.followtherecipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.RecipeBaseHelper;
import database.RecipeCursorWrapper;
import database.RecipeDbSchema;
import database.RecipeDbSchema.RecipeTable;

/**
 * Created by Veloso on 5/31/2016.
 */
public class RecipeManager {

    private static RecipeManager sRecipeManager;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static RecipeManager get(Context context) {
        if (sRecipeManager == null) {
            sRecipeManager = new RecipeManager(context);
        }
        return sRecipeManager;
    }

    private RecipeManager (Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new RecipeBaseHelper(mContext).getWritableDatabase();

    }

    public void addRecipe(Recipe r) {
        ContentValues values = getContentValues(r);

        mDatabase.insert(RecipeTable.NAME, null, values);
    }

    public void updateRecipe(Recipe recipe) {
        String uuidString = recipe.getId().toString();
        ContentValues values = getContentValues(recipe);

        mDatabase.update(RecipeTable.NAME, values,
                RecipeTable.Cols.UUID + " = ?",
                new String [] {uuidString});
    }

    public List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        RecipeCursorWrapper cursor = queryRecipes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                recipes.add(cursor.getRecipe());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return recipes;
    }

    public Recipe getRecipe (UUID id) {
        RecipeCursorWrapper cursor = queryRecipes(
                RecipeTable.Cols.UUID + " = ?",
                new String [] {id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getRecipe();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Recipe recipe) {
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null) {
            return null;
        }

        // Does not create any new files in the system
        // Returns file objects that point to the right location
        return new File(externalFilesDir, recipe.getPhotoFilename());
    }

    public void deleteRecipe(UUID id) {
        String uuidString = id.toString();

        mDatabase.delete(RecipeTable.NAME, RecipeTable.Cols.UUID + " = ?", new String [] {uuidString});
    }

    private static ContentValues getContentValues (Recipe recipe) {
        ContentValues values = new ContentValues();
        values.put(RecipeTable.Cols.UUID, recipe.getId().toString());
        values.put(RecipeTable.Cols.TITLE, recipe.getTitle());
        values.put(RecipeTable.Cols.INGREDIENTS, recipe.getIngredients());
        values.put(RecipeTable.Cols.DIRECTIONS, recipe.getDirections());

        return values;
    }

    private RecipeCursorWrapper queryRecipes (String whereClause, String [] whereArgs) {
        Cursor cursor = mDatabase.query(
                RecipeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new RecipeCursorWrapper(cursor);
    }
}
