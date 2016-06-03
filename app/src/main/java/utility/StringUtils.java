package utility;

/**
 * Created by Veloso on 6/2/2016.
 */
public class StringUtils {

    public static int countLines(String str){
        String[] lines = str.split("\r\n|\r|\n");
        return lines.length;
    }
}
