package com.ccnu.helloworld;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView tv;// text view  
	private Button b; //submit button
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);       
               
    }
       
    public void gotoadminlogin(View view){
    	Intent intent = new Intent();
    	intent.setClass(this, gotoadminloginActivity.class);
    	startActivity(intent);
    }
    
    public void gotoaccountlogin(View view){
    	Intent intent = new Intent();
    	intent.setClass(this, gotoaccountloginActivity.class);
    	startActivity(intent);
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
