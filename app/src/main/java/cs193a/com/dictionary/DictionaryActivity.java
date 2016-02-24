package cs193a.com.dictionary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class DictionaryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private HashMap<String, String> dictionary;
    private String word;
    private ArrayList<String> definitions;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary); //open view of activity.dictionary.xml

        ListView definitionList = (ListView) findViewById(R.id.definition_list); //grab the listview and set it to name 'definitionList'
        definitionList.setOnItemClickListener(this); //initialize onClickListener here..
        definitions = new ArrayList<>(); //new arrayList which will house the definitions (which will need to be put into an arrayList)
        adapter = new ArrayAdapter<>( //adapt between the arrayList java file and arrayList XML in strings.xml. Necessary to keep XML listView dynamic (activity_dictionary.xml)
                this,
                R.layout.mydictionarylistlayout, //individual layout for each line on listView
                R.id.the_item_text,
                definitions
        );

        //ListView definitionList = (ListView) findViewById(R.id.definition_list);

        definitionList.setAdapter(adapter); //set adapter initialized above to the definitionList listView/arrayList
        readAllDefinitions(); //read in all definitions/words to hashmap
        pick5randomWords(); //picks 5 random words, scrambles etc

        //ArrayList<String> list = new ArrayList<>();
        //dictionary = new HashMap<>();

    }



   //unnecessary method for now
    public void lookup(View view) {
        /*EditText the_word = (EditText) findViewById(R.id.theWord);
        String word = the_word.getText().toString();

        if (dictionary == null) {
            dictionary = new HashMap<>();
            readAllDefinitions();
        }
        String defn = dictionary.get(word);

        TextView the_definition = (TextView) findViewById(R.id.definition);
        if (defn == null) {
            the_definition.setText("word not found");
        } else {
            the_definition.setText(defn);
        }
        //the_definition.setText("defn");*/

    }

    //when the app is initialized, the program needs to read all definitions into a hashmap and split the words from definitions using "/"
    private void readAllDefinitions() {
        dictionary = new HashMap<>();
        Scanner scan = new Scanner(
                getResources().openRawResource(R.raw.grewords)); //initialize hashmap + scanner for text file @ grewords.txt in res/raw
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] pieces = line.split("/"); //split each line into word/definition separated by /


            // pieces = {"abate", "to lessen to subside"}
            //Log.d("helpppppp", "dictionary = " + dictionary); //print errors!
            //Log.d("helpppppp", "pieces = " + pieces); //print errors!


            if (pieces.length >= 2) {
                dictionary.put(pieces[0], pieces[1]); //if applicable, add them into the hashmap dictionary - word string in 0 and definition string in 1
            }
        }
    }

    //pick 5 random words from arrayList
    public void pick5randomWords() {
        ArrayList<String> chosenWords = new ArrayList<>(dictionary.keySet()); //gets all words in the arrayList
        Collections.shuffle(chosenWords); //shuffle arrayList of all words to randomize

        word = chosenWords.get(0); //the 'correct' definition (always initially at the 0 index)
        TextView the_word = (TextView) findViewById(R.id.theWord); //findthe TextView @ the top where the trivia word is displayed
        the_word.setText(word); //set the word up top

        definitions.clear(); //clear the arrayList for definitions (declared all the way up top)
        for (int i = 0; i < 5; i++) { //get 5 random words from chosenWords (list of all words, but shuffled)
            String defn = dictionary.get(chosenWords.get(i));
            definitions.add(defn);
        }

        Collections.shuffle(definitions); //shuffle again so the first answer isn't always the right one

        //notify android to update the listview in order to store 5 dynamic items
        adapter.notifyDataSetChanged();

        }

    //private static final int REQ_CODE_TAKE_PICTURE = 30210; //constant used for photo taking functionality (to map the occurance/data)

    /*public void takePicture(View view) {
        Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(picIntent, REQ_CODE_TAKE_PICTURE);
    }*/

    @Override
    public void onItemClick(AdapterView<?> parent, //list
                            View view, //list item that got clicked on
                            int index, //index that got clicked on
                            long id) { //resourceID that got clicked on
        ListView definitionList = (ListView) findViewById(R.id.definition_list);
        String text = definitionList.getItemAtPosition(index).toString();
        String correctDefinition = dictionary.get(word);
        if (correctDefinition.equals(text)) {
            Toast.makeText(this, "You got it!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "LOL Incorrect", Toast.LENGTH_SHORT).show();
        }

        //pick 5 new words
        pick5randomWords(); //maybe need to comment out

    }



    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQ_CODE_TAKE_PICTURE
                && resultCode == RESULT_OK) {
            Bitmap bmp = (Bitmap) intent.getExtras().get("data"); //get pic taken
            ImageView img = (ImageView) findViewById(R.id.photo); //tell imageview to use that image
            img.setImageBitmap(bmp);
        }
    }*/
}

