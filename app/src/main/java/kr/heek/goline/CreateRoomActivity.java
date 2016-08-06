package kr.heek.goline;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import kr.heek.goline.utils.UUIDUtils;

public class CreateRoomActivity extends AppCompatActivity {

    private WebView webView;
    private LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        webView = (WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);

        WebChromeClient client = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (title.equals("-1")) {
                    return;
                }

                Intent intent = new Intent(CreateRoomActivity.this, GameActivity.class);
                intent.putExtra("room_id", title);
                startActivity(intent);
            }
        };

        webView.setWebChromeClient(client);

        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            loadWebView(37.5666805, 126.9784147);
        }

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                loadWebView(location.getLatitude(), location.getLongitude());

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                }
                locationManager.removeUpdates(listener);
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, listener);
    }

    private void loadWebView(double latitude, double longitude) {
        String url = String.format("http://goline.heek.kr:8080/create#latitude=%f&longitude=%f&user_id=%s", latitude, longitude, UUIDUtils.getDevicesUUID(getApplicationContext()));
        Log.i("CREATEROOMACTIVITY", url);
        webView.loadUrl(url);
    }
}
