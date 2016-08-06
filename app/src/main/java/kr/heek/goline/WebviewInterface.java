package kr.heek.goline;


import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class WebviewInterface {

    private WebView mAppView;
    private Activity mContext;
    private Intent mIntent;

    /**
     * 생성자.
     * @param activity : context
     * @param view : 적용될 웹뷰
     * @param intent
     */
    public WebviewInterface(Activity activity, WebView view, Intent intent) {
        mAppView = view;
        mContext = activity;
        mIntent = intent;
    }
    @JavascriptInterface
    public void showToast (String message) { // Show toast for a short time
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void gameOver() {
        mContext.stopService(mIntent);

    }
}
