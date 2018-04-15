package guo.guo.mainitem._10webview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import guo.guo.R;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 *  参考  http://www.jianshu.com/p/3c94ae673e2a
 *
 */
public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    //private TextView tvTitle;
    //private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        //new 或者在xml中配置
        webView = (WebView) findViewById(R.id.webView1);

        //配置 web setting
        WebSettings webSettings = webView.getSettings();
        //与js交互 必须使用   若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）

        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webView.setWebChromeClient(new WebChromeClient());
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true);//支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true);//设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAllowFileAccess(true);//设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);//支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式


        webView.addJavascriptInterface(this, "DangJianMain"); // 第二个参数给js用的
        //webView.loadUrl("http://dangjian.pinganlinshu.com:85/Platform/DJ/JDH/ActivityRoomReal.aspx");
        webView.loadUrl("http://lingangdj.jndv.org/Platform/DJ/JDH/QCJSIndex.aspx");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        //相应 alert事件
        webView.setWebChromeClient(new WebChromeClient() {

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            new AlertDialog.Builder(WebViewActivity.this)
                    .setTitle("JsAlert")
                    .setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    })
                    .setCancelable(false)
                    .show();
            return true;
        }
    });
}
    @JavascriptInterface
    public void getAndroidCookie(){ //js回调回来的方法,名字任意,只要有 @JavascriptInterface注解就可以
        getCookie();
    }
    String dataStringSessionId;
    public void getCookie(){
            RequestQueue queue = Volley.newRequestQueue(this);
            String urlV = "http://lingangdj.jndv.org";
            StringRequest stringRequest = new StringRequest(Request.Method.POST,urlV, new Response.Listener<String>() {
                public void onResponse(String response) {
                    //初始化cookie
                    CookieSyncManager.createInstance(WebViewActivity.this);
                    CookieManager cookieManager = CookieManager.getInstance();
                    cookieManager.setAcceptCookie(true);
                    cookieManager.removeAllCookie();
                    cookieManager.setCookie(urlV, dataStringSessionId);
                    cookieManager.setCookie(urlV, "cookieUserBMID=BMID=1&"
                            + "UserAccount=ma"
                            +"&passWord=111111");
//                        +MainApplication.sCookieVlues);//cookies是在HttpClient中获得的cookie
                    CookieSyncManager.getInstance().sync();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("","=======cookie测试登录失败");
                }
            }){
        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            dataStringSessionId = response.headers.get("Set-Cookie");
//			Log.i("","=======获取cookie"+response.headers.get("Set-Cookie"));

            return super.parseNetworkResponse(response);
        }
        };
        queue.add(stringRequest);
    }
    // back键 控制网页后退
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //销毁 webview
    @Override
    protected void onDestroy() {
        if (webView != null){
            webView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            webView.clearHistory();
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
