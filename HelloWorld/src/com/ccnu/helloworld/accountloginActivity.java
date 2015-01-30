package com.ccnu.helloworld;

import com.ccnu.bean.CustomerBean;
import com.ccnu.bean.VAccountBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class accountloginActivity extends Activity{
	private TextView account_name;
	VAccountBean acc = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.services);
		
		Bundle bundle = this.getIntent().getExtras();
		acc = (VAccountBean) bundle.get("a");
		
		account_name = (TextView) findViewById(R.id.h1);
		account_name.setText("Hi "+acc.getCname()+",great to see you.");
		
	}
	
	public void gotodeposit(View view){
		Intent deposit_intent = new Intent();
		deposit_intent.setClass(this, gotodepositActivity.class);
		Bundle bundle=new Bundle();
	 	bundle.putSerializable("a", acc);
	 	deposit_intent.putExtras(bundle);
		startActivity(deposit_intent);
		Log.i("goto","gotodepositActivity");
	}
	
	public void gotodraw(View view){
		Intent draw_intent = new Intent();
		draw_intent.setClass(this, gotodrawActivity.class);
		Bundle bundle=new Bundle();
	 	bundle.putSerializable("a", acc);
	 	draw_intent.putExtras(bundle);
		startActivity(draw_intent);
		Log.i("goto","gotodrawActivity");
	}
	
	public void gototransfer(View view){
		Intent transfer_intent = new Intent();
		transfer_intent.setClass(this, gototransferActivity.class);
		Bundle bundle=new Bundle();
	 	bundle.putSerializable("a", acc);
	 	transfer_intent.putExtras(bundle);
		startActivity(transfer_intent);
		Log.i("goto","gototransferActivity");
	}
	
	public void gotoinformation(View view){
		Intent information_intent = new Intent();
		information_intent.setClass(this, gotoinformationActivity.class);
		Bundle bundle=new Bundle();
	 	bundle.putSerializable("a", acc);
	 	information_intent.putExtras(bundle);				
		startActivity(information_intent);
		Log.i("goto","gotoinformationActivity");
	}
	
	public void gotodetail(View view){
		Intent detail_intent = new Intent();
		detail_intent.setClass(this, gotodetailActivity.class);
		Bundle bundle=new Bundle();
	 	bundle.putSerializable("a", acc);
	 	detail_intent.putExtras(bundle);	
	 	detail_intent.putExtra("type", "customer");
		startActivity(detail_intent);
		Log.i("goto","gotodetailActivity");
	}
	
	public void gotoresetpassword(View view){
		Intent resetpassword_intent = new Intent();
		resetpassword_intent.setClass(this, gotoresetpasswordActivity.class);
		Bundle bundle=new Bundle();
	 	bundle.putSerializable("a", acc);
	 	resetpassword_intent.putExtras(bundle);
		startActivity(resetpassword_intent);
		Log.i("goto","gotoresetpasswordActivity");
	}
	
	public void backtologin(View view){
		Intent intent = new Intent();
		intent.setClass(accountloginActivity.this, gotoaccountloginActivity.class);
		startActivity(intent);
	}

}
