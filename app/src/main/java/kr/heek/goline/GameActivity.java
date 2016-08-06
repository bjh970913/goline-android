package kr.heek.goline;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

import kr.heek.goline.utils.volley.VolleyManager;

public class GameActivity extends AppCompatActivity {

    private WebView webView;
    private WebviewInterface webviewInterface;

    private String room_id;

    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        room_id = getIntent().getStringExtra("room_id");
        if(room_id != null) {
            Toast.makeText(getApplicationContext(), "Room id not exist", Toast.LENGTH_SHORT).show();
            finish();
        }

        VolleyManager volley = VolleyManager.getInstance(getApplicationContext());



        setTitle(room_id);

        webView = (WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);

        WebChromeClient client = new WebChromeClient() {
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                Intent intent = new Intent(CreateRoomActivity.this, GameActivity.class);
//                intent.putExtra("room_id", title);
//                startActivity(intent);
//            }
        };

        serviceIntent = new Intent(GameActivity.this, GameService.class);

        webView.setWebChromeClient(client);

        webviewInterface = new WebviewInterface(GameActivity.this, webView, serviceIntent); //JavascriptInterface 객체화
        webView.addJavascriptInterface(webviewInterface, "Android");

        String url = String.format("http://goline.heek.kr:8080/play#room_id=%s&user_id=%s", room_id, Settings.Secure.ANDROID_ID);

        webView.loadUrl(url);

        serviceIntent.putExtra("room_id", room_id);
        startService(serviceIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);

    }
}
