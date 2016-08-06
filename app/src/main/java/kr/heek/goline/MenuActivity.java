package kr.heek.goline;

import android.Manifest;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import kr.heek.goline.utils.UUIDUtils;
import kr.heek.goline.utils.volley.PostRequest;
import kr.heek.goline.utils.volley.StandardRequest;
import kr.heek.goline.utils.volley.VolleyManager;

public class MenuActivity extends AppCompatActivity {
    private VolleyManager volley;

    private EditText editRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        volley = VolleyManager.getInstance(getApplicationContext());

        editRoomId = (EditText) findViewById(R.id.room_id);

        // Create Room
        findViewById(R.id.create_room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, CreateRoomActivity.class));
//                PostRequest req = new PostRequest("http://goline.heek.kr/create", new StandardRequest.StandardListener() {
//                    @Override
//                    public void onResponse(JSONObject o) {
//                        Intent intent = new Intent(MenuActivity.this, RoomActivity.class);
//                        try {
//                            intent.putExtra("room_id", o.getString("room_id"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        startActivity(intent);
//                    }
//                });
//                req.putParam("user_id", UUIDUtils.getDevicesUUID(getApplicationContext()));
//                volley.pushQueue(req);
            }
        });

        // Join Room
        findViewById(R.id.join_room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String room_id = editRoomId.getText().toString();

                PostRequest req = new PostRequest("http://goline.heek.kr:8080/join", new StandardRequest.StandardListener() {
                    @Override
                    public void onResponse(JSONObject o) {
                        Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                        intent.putExtra("room_id", room_id);
                        startActivity(intent);
                    }
                });
                req.putParam("user_id", UUIDUtils.getDevicesUUID(getApplicationContext()));
                req.putParam("room_id", room_id);
                volley.pushQueue(req);
            }
        });

        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE }, 0);
    }
}
