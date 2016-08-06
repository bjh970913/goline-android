package kr.heek.goline.utils.volley;

import android.content.Context;

import com.android.volley.Response.Listener;

public class GetRequest extends StandardRequest {
    /**
     * Volley Get Request
     * @param url
     * @param listener
     * @param errorListener
     */
	public GetRequest(final String url, final StandardListener listener, final StandardErrorListener errorListener) {
		super(Method.GET, url, listener, errorListener);
	}

    public GetRequest(final String url, final StandardListener listener) {
        this(url, listener, null);
    }

    public GetRequest(final String url) {
        this(url, null, null);
    }

}
