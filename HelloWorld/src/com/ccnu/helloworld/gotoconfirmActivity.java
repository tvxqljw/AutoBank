package com.ccnu.helloworld;

import java.util.Timer;
import java.util.TimerTask;

import com.ccnu.bean.CustomerBean;
import com.ccnu.bean.VAccountBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class gotoconfirmActivity extends Activity {
	private TextView tv_title,tv_content;
	VAccountBean acc = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.confirm);
		Bundle bundle = this.getIntent().getExtras();
		acc = (VAccountBean) bundle.get("a");

		String title = bundle.getString("title");
		String content = bundle.getString("content");
		
		tv_title = (TextView) findViewById(R.id.confirm_title);
		tv_content = (TextView) findViewById(R.id.confirm_content);
		
		tv_title.setText(title);
		tv_content.setText(content);
		
		
		
		final Intent it = new Intent(this, accountloginActivity.class);
		Bundle b=new Bundle();
	 	b.putSerializable("a", acc);
	 	it.putExtras(b);
		Timer timer = new Timer(); 
		TimerTask task = new TimerTask() {  
		    @Override  
		    public void run() {   
		    startActivity(it); 
		     } 
		 };
		timer.schedule(task,5000);
		
		
	}
	
	public void backtoservices(View view){
		Intent intent = new Intent();
		intent.setClass(gotoconfirmActivity.this,accountloginActivity.class);
		Bundle bundle=new Bundle();
	 	bundle.putSerializable("a", acc);
	 	intent.putExtras(bundle);
		startActivity(intent);
	}

}
