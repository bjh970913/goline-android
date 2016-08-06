package kr.heek.goline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import kr.heek.goline.R;

/**
 * Created by junha on 2016. 7. 18..
 */


public class StringAdapter extends RecyclerView.Adapter<kr.heek.goline.adapter.StringAdapter.ViewHolder> {
    private List<String> strings;

    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public View root;
        public TextView text;

        public ViewHolder(View v) {
            super(v);

//            root = v;
            text = (TextView)v.findViewById(R.id.this_is_text);
        }
    }

    public StringAdapter(List<String> strings, Context context) {
        this.strings = strings;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_string, parent, false);

        ViewHolder vh = new ViewHolder(v);
        vh.itemView.setOnClickListener(onRowClickListener);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String str = strings.get(position);

//        holder.itemView.setTag(R.string.TAG_KEY_POSITION, position);
        holder.text.setText(str);
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }


    /**
     * `Row` Click Listener.
     */
    private final View.OnClickListener onRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            User user = getUserByView(view);
//
//            Intent intent = new Intent(context, UserProfileActivity.class);
//            intent.putExtra("user_id", user.id);
//            context.startActivity(intent);
        }
    };
}