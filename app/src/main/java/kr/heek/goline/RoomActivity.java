package kr.heek.goline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kr.heek.goline.utils.volley.PostRequest;
import kr.heek.goline.utils.volley.VolleyManager;

public class RoomActivity extends AppCompatActivity {

    private VolleyManager volley;
    private String room_id;

    private RecyclerView users_list;
    private TextView tvRoomId;
    private Button btnStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        room_id = getIntent().getStringExtra("room_id");
        volley = VolleyManager.getInstance(getApplicationContext());

        users_list = (RecyclerView)findViewById(R.id.users_list);
        tvRoomId = (TextView)findViewById(R.id.room_id);
        btnStart = (Button)findViewById(R.id.start);

        tvRoomId.setText(room_id);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostRequest request = new PostRequest("http://goline.heek.kr/start");
                request.putParam("room_id", room_id);

                volley.pushQueue(request);
            }
        });
    }
}
