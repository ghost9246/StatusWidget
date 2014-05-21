package com.exam;

import android.*;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class coinBlockIntroActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button btnGetWallpaper = (Button)this.findViewById(R.id.btnGetWallpaper);
        btnGetWallpaper.setOnClickListener(new OnClickListener(){
                        public void onClick(View v) {
                                Uri uri = Uri.parse("http://andytsui.wordpress.com/tag/wallpaper/");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                        }});
    }
}