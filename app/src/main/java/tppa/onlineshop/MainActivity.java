package tppa.onlineshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.OpenOption;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements LifecycleObserver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ItemAdapter adapter = new ItemAdapter(this, nameArray, pricesArray, imageArray);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(MainActivity.this, item_details.class);
                intent.putExtra("name", nameArray[position]);
                intent.putExtra("info", infos[position]);
                Log.i("info1", imageArray[position].toString());
                intent.putExtra("img", imageArray[position]);
                intent.putExtra("price", String.valueOf(pricesArray[position]));
                startActivity(intent);
            }
        });

        loadAndUpdate();
    }

    // Lab 5 - load sorting order from file internal storage
    // starts
    private void loadAndUpdate() {
        String filename = "sortOrder.txt";
        String contents = "";
        try {
            FileInputStream fis = getApplication().getApplicationContext().openFileInput(filename);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
            contents = stringBuilder.toString();
            fis.close();
        } catch (FileNotFoundException e) {
            Log.e("file ops", e.getMessage());
        }
        catch (IOException e) {
            Log.e("file op", e.getMessage());
        } finally {
            if (contents.split("\n")[0].equals("NAME")) {
                sortBasedOnFilter("NAME");
            }
            if (contents.split("\n")[0].equals("PRICE")) {
                sortBasedOnFilter("PRICE");
            }
        }
        Log.println(Log.VERBOSE, "info", "Loaded file");
    }
    //ends

    // Lab5 - save infos to file internal storage
    // starts
    private void saveSortOrder(String order) {
        String filename = "sortOrder.txt";
        FileOutputStream fos = null;
        try {
            fos = getApplication().getApplicationContext().openFileOutput(filename, 0);
            fos.write(order.getBytes(), 0, order.length());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.println(Log.VERBOSE, "info", "Saved file");
    }
    // ends

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle", "onStart invoked");

        // Lab 5 - uses shared preferences to retrieve infos
        // starts
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String username = sharedPrefs.getString("username", "");

        if (!username.equals("")) {
            this.setTitle("Hello, " + username + "!");
        }
        // ends

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle", "onResume invoked");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle", "onPause invoked");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle", "onStop invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle", "onDestroy invoked");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle", "onRestart invoked");
    }

    String[] nameArray = {"Banana", "Beer Burger", "Chinese Noodle",
            "Chocolate Cake", "Chupa chups", "French fries", "Pancake", "Pink Macaron", "Sandwich",
            "Strawberry", "Sushi", "Truffle", "Wine"};

    String[] infos = {
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
            "Best food in town",
    };

    Integer[] imageArray = {R.drawable.banana,
            R.drawable.beef_burger,
            R.drawable.chinese_noodle,
            R.drawable.chocolate_cake,
            R.drawable.chupa_chups,
            R.drawable.french_fries,
            R.drawable.pancake,
            R.drawable.pink_macaron,
            R.drawable.sandwich,
            R.drawable.strawberry,
            R.drawable.sushi,
            R.drawable.truffle,
            R.drawable.wine};

    double[] pricesArray = {1.99, 5.99, 5.99, 3.99, 0.99, 3.59, 6.10, 2.49, 4.99, 0.39, 7.99, 4.99, 10.99};

    ListView listView;


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Sort:
                showAlertDialogButtonClicked(this.listView);
                break;
            case R.id.About:
                Intent intent = new Intent(this, AboutAppActivity.class);
                this.startActivity(intent);
                break;
            // Lab 5 - Add preference activity
            // start
            case R.id.Prefs:
                Intent intent_pref = new Intent(this, MyPreferenceActivity.class);
                this.startActivity(intent_pref);
                break;
            // end
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }


    public void showAlertDialogButtonClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search filter");

        final View customLayout = getLayoutInflater().inflate(R.layout.search_filter_layout, null);
        builder.setView(customLayout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CheckBox nameCheckbox = customLayout.findViewById(R.id.name);
                CheckBox priceCheckbox = customLayout.findViewById(R.id.price);
                if (nameCheckbox.isChecked()) {
                    sortBasedOnFilter("NAME");
                    saveSortOrder("NAME");
                }
                else if (priceCheckbox.isChecked()) {
                    sortBasedOnFilter("PRICE");
                    saveSortOrder("PRICE");
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    ItemObject[] array = new ItemObject[13];

    private void sortBasedOnFilter(String data) {
        for (int i = 0; i < 13; i++) {
            array[i] = new ItemObject(nameArray[i], infos[i], imageArray[i], pricesArray[i]);
        }

        filter = data;
        Arrays.sort(array);

        for (int i = 0; i<nameArray.length; i++) {
            nameArray[i] = array[i].name;
            imageArray[i] = array[i].image;
            infos[i] = array[i].description;
            pricesArray[i] = array[i].price;
        }

        ItemAdapter adapter = new ItemAdapter(this, nameArray, pricesArray, imageArray);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public String filter = "NAME";


    public class ItemObject implements Comparable<ItemObject> {

        String name;
        String description;
        Integer image;
        double price;

        ItemObject(String name, String description, Integer image, double price) {
            this.name = name;
            this.description = description;
            this.image = image;
            this.price = price;
        }

        @Override
        public int compareTo(ItemObject o) {
            if (filter.equals("NAME")) {
                return this.name.compareTo(o.name);
            } else {
                return Double.compare(this.price, o.price);
            }
        }
    }
}

