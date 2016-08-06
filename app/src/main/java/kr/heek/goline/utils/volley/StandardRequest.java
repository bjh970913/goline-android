package kr.heek.goline.utils.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StandardRequest extends StringRequest {
    private Map<String, String> headers;

    /**
     * Volley Standard Request
     * @param method HTTP Request Method. (POST, GET)
     * @param url Request url.
     * @param listener On success.
     * @param errorListener On error occured.
     */
	public StandardRequest(final int method, final String url, final StandardListener listener, final StandardErrorListener errorListener) {
		super(method, url, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (response.length() == 0) {
					if (listener != null) {
						listener.onResponse(null);
					}
					return;
				}

				try {
					if (listener != null) {
						JSONObject o = new JSONObject(response);
						listener.onResponse(o);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					if (errorListener != null) {
						errorListener.onErrorResponse("Error");
					}
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				int statusCode;
				String msg = "Network Error", body = "";

				try {
					statusCode = volleyError.networkResponse.statusCode;
				} catch (NullPointerException e) {
					statusCode = -1;
				}

				try {
					body = new String(volleyError.networkResponse.data);
                    JSONObject o = new JSONObject(body);
                    if (o.has("error")) {
                        String message = o.getString("error");
                        if (o.has("message"))
                            message = o.getString("message");

                        errorListener.onErrorResponse(message);
                        return;
                    }
                } catch (Exception e) {
                    msg = "Network Error";
				}

				Log.d("Network Error", statusCode + " " + msg + "\n" + body);
				if (errorListener != null) {
					errorListener.onErrorResponse(msg);
				}
			}
		});
		headers = new HashMap<>();
	}

    public StandardRequest(final int method, final String url, final StandardListener listener) {
        this(method, url, listener, null);
    }

	public StandardRequest(final int method, final String url) {
		this(method, url, null, null);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers;
	}
	public void putHeader(String key, String value) {
		headers.put(key, value);
	}


	public interface StandardListener {
		void onResponse(JSONObject o);
	}

	public interface StandardErrorListener {
		void onErrorResponse(String userMessage);
	}
}
