package kr.heek.goline.utils.volley;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.Listener;

public class PostRequest extends StandardRequest {
    private Map<String, String> params;

    /**
     * Volley Post Request
     * @param url
     * @param listener
     * @param errorListener
     */
	public PostRequest(final String url, final StandardListener listener, final StandardErrorListener errorListener) {
		super(Method.POST, url, listener, errorListener);
		params = new HashMap<>();
	}

    public PostRequest(final String url, final StandardListener listener) {
        this(url, listener, null);
    }
	public PostRequest(final String url) {
		this(url, null, null);
	}

	public void putParam(String key, String value) {
		params.put(key, value);
	}
	public void putParam(String key, int value) {
		params.put(key, String.valueOf(value));
	}
	public void putParam(String key, long value) {
		params.put(key, String.valueOf(value));
	}
	public void putParam(String key, boolean value) {
		params.put(key, String.valueOf(value));
	}


	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params;
	}

}
