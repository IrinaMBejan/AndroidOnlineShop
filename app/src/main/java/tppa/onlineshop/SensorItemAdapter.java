package tppa.onlineshop;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SensorItemAdapter extends ArrayAdapter {
    private final Activity context;

    private final String[] sensorNames;

    private final String[] sensorValues;

    public SensorItemAdapter(Activity context, String[] sensorNames, String[] sensorValues){

        super(context,R.layout.sensor_item, sensorNames);
        this.context = context;
        this.sensorNames = sensorNames;
        this.sensorValues = sensorValues;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.sensor_item, null,true);

        TextView sensorNameTextField = (TextView) rowView.findViewById(R.id.sensorName);
        TextView sensorValueTextField = (TextView) rowView.findViewById(R.id.sensorValue);

        sensorNameTextField.setText(sensorNames[position]);
        sensorValueTextField.setText(sensorValues[position]);

        return rowView;

    };
}
