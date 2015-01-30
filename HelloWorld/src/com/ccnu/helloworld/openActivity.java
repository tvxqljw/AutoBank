package com.ccnu.helloworld;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class openActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.open);
		
		
		final Intent it = new Intent(this, MainActivity.class);
		Timer timer = new Timer(); 
		TimerTask task = new TimerTask() {  
		    @Override  
		    public void run() {   
		    startActivity(it); 
		     } 
		 };
		timer.schedule(task,5000);
		
	}

}
