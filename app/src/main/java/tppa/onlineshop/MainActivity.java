package tppa.onlineshop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle", "onStart invoked");
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
                }
                else if (priceCheckbox.isChecked()) {
                    sortBasedOnFilter("PRICE");
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

    String filter = "NAME";


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

