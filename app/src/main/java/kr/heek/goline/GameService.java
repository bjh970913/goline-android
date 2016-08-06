package kr.heek.goline;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import kr.heek.goline.utils.volley.PostRequest;
import kr.heek.goline.utils.volley.StandardRequest;
import kr.heek.goline.utils.volley.VolleyManager;

public class GameService extends Service {
    private final static int MIN_TIME = 2000;   // millis
    private final static int MIN_DISTANCE = 1;  // meter
    private final static int MIN_UPDATE = 2000;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private VolleyManager volley;

    private long last_updated = 0;

    public GameService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        volley = VolleyManager.getInstance(getApplicationContext());

        final String room_id = intent.getStringExtra("room_id");
        final String user_id = Settings.Secure.ANDROID_ID;

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                long time = location.getTime();

                if (time - last_updated < MIN_UPDATE) {
                    return;
                }
                last_updated = time;

                PostRequest request = new PostRequest("http://goline.heek.kr:8080/update", new StandardRequest.StandardListener() {
                    @Override
                    public void onResponse(JSONObject o) {
                        int status = 0;
                        try {
                            status = o.getInt("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (status == -1) {
                            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            locationManager.removeUpdates(locationListener);
                        }
                    }
                });
                request.putParam("room_id", room_id);
                request.putParam("user_id", user_id);
                request.putParam("latitude", String.valueOf(location.getLatitude()));
                request.putParam("longitude", String.valueOf(location.getLongitude()));
                volley.pushQueue(request);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return START_NOT_STICKY;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("hyeonsu", "GameService onDestory");
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("hyeonsu", "GameService permission check failed");

                return;
            }

            locationManager.removeUpdates(locationListener);
            Log.d("hyeonsu", "GameService remove location listener");

        }
    }
}
