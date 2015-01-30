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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class gotoaccountloginActivity extends Activity{

	
	private EditText account;
	private EditText accountpassword;
	

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
								JSONObject acc = obj.getJSONObject("account");
								VAccountBean account = new VAccountBean();
								account.setAid(acc.getString("aid"));
								account.setCname(acc.getString("cname"));
								account.setAbalance((double)acc.getInt("abalance"));
								account.setAdate(acc.getString("adate"));
								account.setAstate(acc.getString("astate"));
								account.setCid(acc.getString("cid"));
								account.setType(acc.getString("type"));
								
									Toast.makeText(gotoaccountloginActivity.this, "Welcome "+account.getCname(), 0).show();								
								 	Intent  intent = new Intent();
								 	intent.setClass(gotoaccountloginActivity.this, accountloginActivity.class);
								 	Bundle bundle=new Bundle();
								 	bundle.putSerializable("a", account);
								 	intent.putExtras(bundle);							 	
								 	startActivity(intent);
							}else{
								 	Log.e("ERROR","password error");
								 	new AlertDialog.Builder(gotoaccountloginActivity.this).setTitle("ERROR").setMessage("Your password or adminId is error!").setPositiveButton("Confirm", null).show();			
								 	accountpassword.requestFocus();
								 	accountpassword.setText("");
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
		this.setContentView(R.layout.accountlogin);
		Log.i("INFO","gotoaccountloginActivity runing");
			account = (EditText) findViewById(R.id.accountId);
			accountpassword = (EditText) findViewById(R.id.accountpassword);
	}
	
	public void accountlogin(View view){		
		new Thread(){
				public void run(){
					try {       			
	        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mAccountLogin");
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setDoOutput(true);
						conn.setConnectTimeout(3000);
						conn.setDoInput(true);
						
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
						
						conn.connect();
						
						DataOutputStream out = new DataOutputStream(conn.getOutputStream());
						//paramers
						String s_account = account.getText().toString();
						String s_accountpassword = accountpassword.getText().toString();
						String content = "aid="+URLEncoder.encode(s_account,"UTF-8")+"&apassword="+URLEncoder.encode(s_accountpassword,"UTF-8");
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
