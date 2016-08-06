package kr.heek.goline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class GameActivity extends AppCompatActivity {

    private WebView webView;
    private String room_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (getIntent().hasExtra("room_id")) {
            // Opened by Intent
            room_id = getIntent().getStringExtra("room_id");
        } else {
            // Opened by URI
            room_id = getIntent().getData().getQueryParameter("room_id");
        }

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

        webView.setWebChromeClient(client);
        webView.loadUrl("http://goline.heek.kr:8080/play");

        Intent intent = new Intent(GameActivity.this, GameService.class);
        intent.putExtra("room_id", room_id);
        startService(intent);
    }
}
