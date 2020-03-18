package tppa.onlineshop;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter {
    private final Activity context;

    private final Integer[] imageIDs;

    private final String[] names;

    private final double[] prices;

    public ItemAdapter(Activity context, String[] nameArray, double[] pricesArray, Integer[] imageIDs){

        super(context,R.layout.list_item, nameArray);
        this.context = context;
        this.imageIDs = imageIDs;
        this.names = nameArray;
        this.prices = pricesArray;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_item, null,true);

        TextView nameTextField = (TextView) rowView.findViewById(R.id.nameTextView);
        TextView priceTextField = (TextView) rowView.findViewById(R.id.priceTextView);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1ID);

        nameTextField.setText(names[position]);
        priceTextField.setText("Price: $" + prices[position]);
        imageView.setImageResource(imageIDs[position]);

        return rowView;

    };
}
