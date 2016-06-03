package database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.sveloso.followtherecipe.Recipe;

import java.util.UUID;

import database.RecipeDbSchema.RecipeTable;

/**
 * Created by Veloso on 6/1/2016.
 */
public class RecipeCursorWrapper extends CursorWrapper{

    public RecipeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Recipe getRecipe() {
        String uuidString = getString(getColumnIndex(RecipeTable.Cols.UUID));
        String title = getString(getColumnIndex(RecipeTable.Cols.TITLE));
        String ingredients = getString(getColumnIndex(RecipeTable.Cols.INGREDIENTS));
        String directions = getString(getColumnIndex(RecipeTable.Cols.DIRECTIONS));

        Recipe recipe = new Recipe(UUID.fromString(uuidString));
        recipe.setTitle(title);
        recipe.setIngredients(ingredients);
        recipe.setDirections(directions);

        return recipe;
    }

}
