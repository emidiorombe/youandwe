package br.com.office.test;

import android.os.Bundle;
import org.apache.cordova.*;

public class OfficeCloudTestActivity extends DroidGap {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.loadUrl("file:///android_asset/www/index.html");
    }
}