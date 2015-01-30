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

public class gototransferActivity extends Activity{
	VAccountBean acc = null;
	private EditText transfermoney;
	private EditText oppositeaccount;

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
						int check = obj.getInt("check");
						
						if(check==1){
							//success
							JSONObject acc_obj = obj.getJSONObject("account");
							VAccountBean account = new VAccountBean();
							account.setAid(acc_obj.getString("aid"));
							account.setCname(acc_obj.getString("cname"));
							account.setAbalance((double)acc_obj.getInt("abalance"));							
							String money = obj.getString("money");
							String dcount = obj.getString("dcount");
							
							//new AlertDialog.Builder(gototransferActivity.this).setTitle("Draw Success").setMessage("Hi,"+account.getCname()+", your have draw "+money+". \nYour balance is "+account.getAbalance()).setPositiveButton("Confirm", null).show();		
						
							Intent intent =new Intent();
							intent.setClass(gototransferActivity.this, gotoconfirmActivity.class);
							Bundle bundle=new Bundle();
						 	bundle.putSerializable("a", acc);
						 	intent.putExtras(bundle);
						 	String content = "Dear "+account.getCname()+",\n\nYou have transfer "+money+" RMB to account "+dcount+"\n\nYour balance is "+account.getAbalance().toString();	
						 	String title = "Transfer Successfully!";	
							intent.putExtra("content", content);
							intent.putExtra("title", title);
							startActivity(intent);
						}else if(check == 0){
							//frozen
							new AlertDialog.Builder(gototransferActivity.this).setTitle("ERROR").setMessage("Sorry,your account is frozen!").setPositiveButton("Confirm", null).show();		
						}
						else if(check == -2){
							String dcount = obj.getString("dcount");
							new AlertDialog.Builder(gototransferActivity.this).setTitle("ERROR").setMessage("Sorry,the opposite account "+dcount+" is frozen!").setPositiveButton("Confirm", null).show();	
						}
						else if(check == -1){
							new AlertDialog.Builder(gototransferActivity.this).setTitle("ERROR").setMessage("Your money is not enough!").setPositiveButton("Confirm", null).show();	
						}
						else if(check == -3){
							new AlertDialog.Builder(gototransferActivity.this).setTitle("ERROR").setMessage("Opposite account is not exist!").setPositiveButton("Confirm", null).show();	
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
		this.setContentView(R.layout.transfer);
		Bundle bundle = this.getIntent().getExtras();
		acc = (VAccountBean) bundle.get("a");
		//Toast.makeText(gototransferActivity.this, "hi,transfer", 0).show();
	}

	public void transferSubmit(View view){
		transfermoney = (EditText) findViewById(R.id.transfermoney);
		oppositeaccount = (EditText) findViewById(R.id.oppositeaccount);
		String s_transfermoney = transfermoney.getText().toString();
		String s_oppositeaccount = oppositeaccount.getText().toString();
		//verify account
		if(s_oppositeaccount == null || s_oppositeaccount ==" "){
			new AlertDialog.Builder(this).setTitle("Error").setMessage("Please insert correct opposite account!").setPositiveButton("Confirm", null).show();
			oppositeaccount.requestFocus();
		}
		else if(s_transfermoney==null || s_transfermoney== " " ||!util_functions.isPositiveInteger(s_transfermoney)){
			new AlertDialog.Builder(this).setTitle("Error").setMessage("Please insert correct number").setPositiveButton("Confirm", null).show();
		}
		else{
			new Thread(){
				public void run(){
					try {      
						
						String s_transfermoney = transfermoney.getText().toString();
						String s_oppositeaccount = oppositeaccount.getText().toString();
						String aid = acc.getAid();
						
	        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mTransfer?aid="+aid+"&dmoney="+s_transfermoney+"&dcount="+s_oppositeaccount);
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
			transfermoney.requestFocus();
			transfermoney.setText("");
		}
	}
	
	public void backtoservices(View view){
		Intent intent = new Intent();
		intent.setClass(gototransferActivity.this,accountloginActivity.class);
		Bundle bundle=new Bundle();
	 	bundle.putSerializable("a", acc);
	 	intent.putExtras(bundle);
		startActivity(intent);
	}

}
