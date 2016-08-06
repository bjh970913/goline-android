package kr.heek.goline.utils.volley;

import com.android.volley.AuthFailureError;

import java.util.HashMap;
import java.util.Map;

public class DeleteRequest extends StandardRequest {
    /**
     * Volley Post Request
     * @param url
     * @param listener
     * @param errorListener
     */
	public DeleteRequest(final String url, final StandardListener listener, final StandardErrorListener errorListener) {
		super(Method.DELETE, url, listener, errorListener);
	}

    public DeleteRequest(final String url, final StandardListener listener) {
        this(url, listener, null);
    }
	public DeleteRequest(final String url) {
		this(url, null, null);
	}
}
