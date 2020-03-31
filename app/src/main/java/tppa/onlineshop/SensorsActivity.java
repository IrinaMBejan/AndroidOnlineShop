package tppa.onlineshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;

// Lab 6 - display information from the sensors available on the device.(3 p)
public class SensorsActivity extends AppCompatActivity implements SensorEventListener, LocationListener {

    private SensorManager sensorsManager;
    private LocationManager locationManager;

    String[] sensorsName;
    String[] sensorsValue;
    String[] permissions = {
            ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        this.requestPermissions(permissions, 1);

        sensorsManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        locationManager =  (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        List<Sensor> sensorList = sensorsManager.getSensorList(Sensor.TYPE_ALL);
        sensorsName = new String[sensorList.size()];
        sensorsValue = new String[sensorList.size()];

        for (int i = 0; i < sensorList.size(); i++) {
            Sensor s = sensorList.get(i);
            sensorsName[i] = s.getName();
            sensorsValue[i] = "Power: " + String.valueOf(s.getPower()) + ", Max delay: " +
            String.valueOf(s.getMaxDelay()) + ", Max range: " + String.valueOf(s.getMaximumRange());
        }

        SensorItemAdapter adapter = new SensorItemAdapter(this, sensorsName, sensorsValue);
        ListView listView = (ListView) findViewById(R.id.sensorsList);
        listView.setAdapter(adapter);

        Log.i("sensors", sensorList.get(0).getName().toString());

        // Lab 6 - Add support for location services
        for (String perm : permissions) {
            if (this.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                Log.e("asking for permision", "Permissions not granted!");
                return;
            }
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        TextView latidView = (TextView) findViewById(R.id.latidView);
        TextView longitView = (TextView) findViewById(R.id.longitView);
        latidView.setText(String.valueOf(location.getLatitude()));
        longitView.setText(String.valueOf(location.getLongitude()));

        Log.i("latid", String.valueOf(location.getLatitude()));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onLocationChanged(Location location) {
        TextView latidView = (TextView) findViewById(R.id.latidView);
        TextView longitView = (TextView) findViewById(R.id.longitView);
        latidView.setText(String.valueOf(location.getLatitude()));
        longitView.setText(String.valueOf(location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


