package database;

/**
 * Created by Veloso on 6/1/2016.
 */
public class RecipeDbSchema {

    public static final class RecipeTable {
        public static final String NAME = "recipes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String INGREDIENTS = "ingredients";
            public static final String DIRECTIONS = "directions";
        }
    }
}
