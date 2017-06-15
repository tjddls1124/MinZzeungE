package com.example.sungin.minzzeunge;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Scanner;

public class Main6Activity_certificate extends AppCompatActivity {
    Person thisPerson = null;
    boolean first_approach = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6_certificate);
        final WebView webView = (WebView) findViewById(R.id.webView);
        Intent intent = getIntent();
        thisPerson = intent.getParcelableExtra("MSG_CERTIFICATION");
        WebSettings webSettings = webView.getSettings();
// 자바스크립트 사용하기
        webSettings.setJavaScriptEnabled(true);
// WebView 내장 Zoom 사용
        webSettings.setBuiltInZoomControls(true);
// 확대,축소 기능을 사용할 수 있도록 설정
        webSettings.setSupportZoom(true);
// 캐쉬 사용 방법을 정의(default : LOAD_DEFAULT)
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        final ProgressDialog dialog;
        dialog = new ProgressDialog(this);
        webView.setWebViewClient(new WebViewClient() {
                                     @Override
                                     public void onPageFinished(WebView view, String url) {
                                         webView.loadUrl("javascript:window.Android.showHTML" +
                                                 "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                                     }

                                     @Override
                                     public void onPageStarted(WebView view, String url,
                                                               Bitmap favicon) {
                                         super.onPageStarted(view, url, favicon);
                                         dialog.setMessage("Loading...");
                                         dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                         dialog.show();
                                     }
                                 }
        );
        webView.setWebChromeClient(new WebChromeClient() {
                                       @Override
                                       public void onProgressChanged(WebView view, int
                                               newProgress) {
                                           super.onProgressChanged(view, newProgress);
                                           if (newProgress >= 100) dialog.dismiss();
                                       }
                                   }
        );


        webView.loadUrl("https://dls.koroad.or.kr/jsp/ool/olq/OL_LcnsTruthYnRtvV.jsp");
        webView.addJavascriptInterface(new MyJavascriptInterface(this), "Android");


    }

    public void onClick(View v){
        finish();

    }

    public class MyJavascriptInterface { //웹뷰에서 자바 스크립트 읽어오기
        private Context ctx;

        MyJavascriptInterface(Context ctx) {
            this.ctx = ctx;
        }
        public void captureBitmap(){

        }

        @JavascriptInterface
        public void showHTML(String html) {

            /*
            CheckCode : 웹뷰에서 HTML 긁어와서 dlg로 띄우기
             */
          /*  new AlertDialog.Builder(ctx).setTitle("HTML").setMessage(html)
                    .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();*/

            /*
            인증성공 : 긁어온 Html에서 인증완료 여부 체크 후, 인텐트로 보내주기
             */

            if (html.contains("도로교통공단 전산 자료와 일치합니다.") && html.contains("암호일련번호가 일치합니다.")) {
                Toast.makeText(getApplicationContext(), "인증에 성공하였습니다!", Toast.LENGTH_SHORT).show();
                thisPerson.isValid = true;
                Intent intent = new Intent(Main6Activity_certificate.this, Main5Activity_Data.class);
                intent.putExtra("remakemsg", thisPerson);
                setResult(RESULT_OK, intent);
                finish();
            }
            else if(first_approach) first_approach = false; //처음 웹뷰가 로드될 때를 제외하고
            else  // 다른 인증되지 않고 다른 페이지에 접근되었을 경우, 인증실패 메세지 출력.
                Toast.makeText(getApplicationContext(), "인증에 실패하였습니다\n다시 시도하여 주십시오", Toast.LENGTH_SHORT).show();

        }

    }
}
