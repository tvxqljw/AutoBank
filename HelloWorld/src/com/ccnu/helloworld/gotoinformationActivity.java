package com.ccnu.helloworld;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.ccnu.bean.VAccountBean;
import com.ccnu.util.util_functions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class  gotoinformationActivity extends Activity{

	VAccountBean acc = null;
	private TextView tv_name,tv_state,tv_aid,tv_balance;
		
	
	public Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			switch(msg.what){
			case 1:{
				String json_res = (String) msg.obj;
				
					//json parse
					try {
						
						JSONObject obj = new JSONObject(json_res);
						JSONObject acc_obj = obj.getJSONObject("account");
						VAccountBean account = new VAccountBean();
						account.setAid(acc_obj.getString("aid"));
						account.setCname(acc_obj.getString("cname"));
						account.setAbalance((double)acc_obj.getInt("abalance"));
						account.setAstate(acc_obj.getString("astate"));

						tv_name = (TextView) findViewById(R.id.information_name);
						tv_state = (TextView) findViewById(R.id.information_state);
						tv_aid = (TextView) findViewById(R.id.information_aid);
						tv_balance = (TextView) findViewById(R.id.information_balance);
						
						tv_name.setText(account.getCname());
						tv_state.setText(account.getAstate());
						tv_aid.setText(account.getAid());
						tv_balance.setText(account.getAbalance().toString());
						
						
						
					} catch (JSONException e1) {
						e1.printStackTrace();
					}							
			}//end case:1
		}
	}
};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.information);
		
		Bundle bundle = this.getIntent().getExtras();
		acc = (VAccountBean) bundle.get("a");
		
		//Toast.makeText(gotoinformationActivity.this, "hi,infomation", 0).show();
		
		
		new Thread(){
			public void run(){
				try {      
					String aid = acc.getAid();
					
        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mGetCus?aid="+aid);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setConnectTimeout(3000);
					conn.setDoInput(true);
					
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
					
					conn.connect();						
					//get the input stream
					InputStream inputstream = conn.getInputStream();
					String res = util_functions.readLine(inputstream);
					
					//Success
					Log.i("INFO","begin=1");
					Message msg = new Message();
					msg.what = 1;
					msg.obj = res;
					handler.sendMessage(msg);
        			
        			
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}.start();
		
		

		
	}
	
	public void backtoservices(View view){
		Intent intent = new Intent();
		intent.setClass(gotoinformationActivity.this,accountloginActivity.class);
		Bundle bundle=new Bundle();
	 	bundle.putSerializable("a", acc);
	 	intent.putExtras(bundle);
		startActivity(intent);
	}

}
