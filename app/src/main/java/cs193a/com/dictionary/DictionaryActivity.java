package cs193a.com.dictionary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Scanner;

public class DictionaryActivity extends AppCompatActivity {
    private HashMap<String, String> dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        dictionary = new HashMap<>();
        readAllDefinitions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dictionary, menu);
        return true;
    }


    public void lookup(View view) {
        EditText the_word = (EditText) findViewById(R.id.theWord);
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
        //the_definition.setText("defn");

    }


    private void readAllDefinitions() {
        Scanner scan = new Scanner(
                getResources().openRawResource(R.raw.grewords));
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] pieces = line.split("/");
            // pieces = {"abate", "to lessen to subside"}
            dictionary.put(pieces[0], pieces[1]);
            }
        }

    private static final int REQ_CODE_TAKE_PICTURE = 30210;

    public void takePicture(View view) {
        Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(picIntent, REQ_CODE_TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQ_CODE_TAKE_PICTURE
                && resultCode == RESULT_OK) {
            Bitmap bmp = (Bitmap) intent.getExtras().get("data"); //get pic taken
            ImageView img = (ImageView) findViewById(R.id.photo); //tell imageview to use that image
            img.setImageBitmap(bmp);
        }
    }
}

