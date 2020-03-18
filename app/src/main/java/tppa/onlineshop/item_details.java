package tppa.onlineshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Console;

public class item_details extends AppCompatActivity {

    protected String NAME, INFO;
    protected int IMG;
    protected Float PRICE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        final String NAME = getIntent().getStringExtra("name");
        String INFO = getIntent().getStringExtra("info");
        int IMG = getIntent().getIntExtra("img",0);
        final Float PRICE = Float.parseFloat(getIntent().getStringExtra("price"));

        TextView nameText = (TextView) findViewById(R.id.text);
        TextView infoTextField = (TextView) findViewById(R.id.info);
        TextView priceTextField = (TextView) findViewById(R.id.price);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        infoTextField.setText(INFO);
        priceTextField.setText("Price: $" + PRICE.toString());
        imageView.setImageResource(IMG);
        imageView.setTag(IMG);
        nameText.setText(NAME);

        Button btn = (Button) findViewById(R.id.shareButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = "Check out this " + NAME + "! It costs only " + PRICE.toString() + " and it's delicious!";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share joy");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(sharingIntent, null));
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle onSaveState) {
        TextView nameText = (TextView) findViewById(R.id.text);
        TextView infoTextField = (TextView) findViewById(R.id.info);
        TextView priceTextField = (TextView) findViewById(R.id.price);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        EditText editText = (EditText) findViewById(R.id.editText);

        String name = nameText.getText().toString();
        String info = infoTextField.getText().toString();
        String price = priceTextField.getText().toString();
        Integer img = (Integer) imageView.getTag();

        String text = editText.getText().toString();

        onSaveState.putString("name", name);
        onSaveState.putString("info", info);
        onSaveState.putString("price", price);
        onSaveState.putInt("img", img);
        onSaveState.putString("order", text);
        super.onSaveInstanceState(onSaveState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle onRestoreState) {
        super.onRestoreInstanceState(onRestoreState);

        TextView nameText = (TextView) findViewById(R.id.text);
        TextView infoTextField = (TextView) findViewById(R.id.info);
        TextView priceTextField = (TextView) findViewById(R.id.price);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        EditText editText = (EditText) findViewById(R.id.editText);

        infoTextField.setText(onRestoreState.getString("info"));
        priceTextField.setText(onRestoreState.getString("price"));
        imageView.setImageResource(onRestoreState.getInt("img"));
        nameText.setText(onRestoreState.getString("name"));
        editText.setText(onRestoreState.getString("order"));

    }
}
