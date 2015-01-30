package com.ccnu.helloworld;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
import android.widget.EditText;
import android.widget.Toast;

public class gotoresetpasswordActivity extends Activity{
	VAccountBean acc = null;
	private EditText oldpassword;
	private EditText newpassword;
	private EditText comfirmnewpassword;

	
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
							//success									
							//new AlertDialog.Builder(gotoresetpasswordActivity.this).setTitle("Reset password successfully!").setMessage("You have reseted the new password successfully,please relogin with the new password!").setPositiveButton("Confirm", null).show();
							Toast.makeText(gotoresetpasswordActivity.this, "Reset password successfully,please relogin with the new password!", 0).show();
							Intent returnlogin = new Intent();
							returnlogin.setClass(gotoresetpasswordActivity.this, gotoaccountloginActivity.class);
							startActivity(returnlogin);
						}
						else{
							new AlertDialog.Builder(gotoresetpasswordActivity.this).setTitle("Error").setMessage("Your old password is error!").setPositiveButton("Confirm", null).show();
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
		this.setContentView(R.layout.resetpassword);
		Bundle bundle = this.getIntent().getExtras();
		acc = (VAccountBean) bundle.get("a");
		//Toast.makeText(gotoresetpasswordActivity.this, "hi,resetpassword", 0).show();
		
	}
	
	public void resetpasswordSubmit(View view){
		oldpassword = (EditText) findViewById(R.id.oldpwd);
		newpassword = (EditText) findViewById(R.id.newpwd);
		comfirmnewpassword = (EditText) findViewById(R.id.confirmnewpwd);
		
		String s_oldpassword = oldpassword.getText().toString();
		String s_newpassword = newpassword.getText().toString();
		String s_comfirmnewpassword = comfirmnewpassword.getText().toString();
		
		//verify old pwd
		if(s_oldpassword==null|| s_oldpassword==" "){
			new AlertDialog.Builder(this).setTitle("Error").setMessage("Please insert old password").setPositiveButton("Confirm", null).show();
			//focus
			oldpassword.requestFocus();
			oldpassword.setText("");		
		}
		else if(s_newpassword ==null||s_newpassword==" "){
			//verify new password
			new AlertDialog.Builder(this).setTitle("Error").setMessage("Please insert new password!").setPositiveButton("Confirm", null).show();
			newpassword.requestFocus();
			newpassword.setText("");   
			
											
		}
		else if(s_comfirmnewpassword==null||s_comfirmnewpassword==" "|| !s_comfirmnewpassword.equals(s_newpassword)){
			new AlertDialog.Builder(this).setTitle("Error").setMessage("Confirm password is different to new password!").setPositiveButton("Confirm", null).show();
			comfirmnewpassword.requestFocus();		
			comfirmnewpassword.setText("");					

		}
		else{
			//success
			new Thread(){
				public void run(){
					try {      
						
						String op = oldpassword.getText().toString();
						String np = newpassword.getText().toString();
						String aid = acc.getAid();
						
	        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mResetpwd?aid="+aid+"&oldpwd="+op+"&newpwd="+np);
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
	}
	
	public void backtoservices(View view){
		Intent intent = new Intent();
		intent.setClass(gotoresetpasswordActivity.this,accountloginActivity.class);
		Bundle bundle=new Bundle();
	 	bundle.putSerializable("a", acc);
	 	intent.putExtras(bundle);
		startActivity(intent);
	}

}
