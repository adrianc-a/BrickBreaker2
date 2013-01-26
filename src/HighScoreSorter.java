import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: adrian
 * Date: 1/12/13
 * Time: 4:24 PM
 * Reads file "HighScores2.txt" located at the home file and sorts them
 */
public class HighScoreSorter {
    ArrayList<String> scores = new ArrayList<String>();
    public void Sorter() {
        URL words = getClass().getClassLoader().getResource("HighScores2.txt");
        //System.out.println(words);
        try {
            InputStreamReader a = new InputStreamReader(words.openStream());
            BufferedReader br = new BufferedReader(a);
            String strLine;
            while ((strLine = br.readLine()) != null) {
                // System.out.println(strLine);
                /* add strLine to the array, at index i
                 * increment i
                 */
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
}
