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

import com.ccnu.util.util_functions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import android.app.AlertDialog; 
import android.app.Dialog; 

public class gotoadminloginActivity extends Activity{

	private EditText admin;
	private EditText adminpassword;
	
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
							boolean check = obj.getBoolean("check");
							
							if(check==true){
								//login success
								JSONObject acc = obj.getJSONObject("admin");
								String aid = acc.getString("aid");
								
									Toast.makeText(gotoadminloginActivity.this, "Welcome "+aid, 0).show();								
								 	Intent  intent = new Intent();
								 	intent.setClass(gotoadminloginActivity.this, adminloginActivity.class);
//								 	Bundle bundle=new Bundle();
//								 	bundle.putString("aid", aid);
								 	intent.putExtra("aid",aid);							 	
								 	startActivity(intent);
							}else{
							 	Log.e("ERROR","password error");
								 	new AlertDialog.Builder(gotoadminloginActivity.this).setTitle("ERROR").setMessage("Your password or adminId is error!").setPositiveButton("Confirm", null).show();
								 	adminpassword.requestFocus();
								 	adminpassword.setText("");
							}
							
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
		this.setContentView(R.layout.adminlogin);//jump into the page		
		 Log.i("INFO","gotoadminloginActivity runing");
		 
		 Log.i("INFO","adminloginActivity running");
			admin = (EditText) findViewById(R.id.adminId);
			adminpassword = (EditText) findViewById(R.id.adminpassword);
					      		
	}
	
	
	public void adminlogin(View view){
		
				
		new Thread(){
				public void run(){
					try {       			
	        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mAdminLogin");
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setDoOutput(true);
						conn.setConnectTimeout(3000);
						conn.setDoInput(true);
						
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
						
						conn.connect();
						
						DataOutputStream out = new DataOutputStream(conn.getOutputStream());
						//paramers
						String s_admin = admin.getText().toString();
						String s_adminpassword = adminpassword.getText().toString();
						String content = "aid="+URLEncoder.encode(s_admin,"UTF-8")+"&apassword="+URLEncoder.encode(s_adminpassword,"UTF-8")+"&atype=admin";
						out.writeBytes(content); 
						out.flush();
						out.close();
						
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
	
	public void backtohome(View view){
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
	}

}
