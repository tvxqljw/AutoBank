package com.ccnu.helloworld;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

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

import com.ccnu.bean.VAccountBean;
import com.ccnu.util.util_functions;

public class gotodepositActivity extends Activity{
	VAccountBean acc = null;
	private EditText depositmoney;
	
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
						
							//new AlertDialog.Builder(gotodepositActivity.this).setTitle("Deposit Success").setMessage("Hi,"+account.getCname()+", your have deposit "+money+". \nYour balance is "+account.getAbalance()).setPositiveButton("Confirm", null).show();		
							
							Intent intent =new Intent();
							intent.setClass(gotodepositActivity.this, gotoconfirmActivity.class);
							Bundle bundle=new Bundle();
						 	bundle.putSerializable("a", acc);
						 	intent.putExtras(bundle);
						 	String content = "Dear "+account.getCname()+",\n\nYou have deposited "+money+" RMB into account "+account.getAid()+"\n\nYour balance is "+account.getAbalance().toString();	
						 	String title = "Deposit Successfully!";	
							intent.putExtra("content", content);
							intent.putExtra("title", title);
							startActivity(intent);
								
						}else if(check == 0){
							//frozen
							new AlertDialog.Builder(gotodepositActivity.this).setTitle("ERROR").setMessage("Sorry,your account is frozen!").setPositiveButton("Confirm", null).show();		
						}
						else{
							new AlertDialog.Builder(gotodepositActivity.this).setTitle("ERROR").setMessage("Deposit Failed!").setPositiveButton("Confirm", null).show();	
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
		this.setContentView(R.layout.deposit);
		Bundle bundle = this.getIntent().getExtras();
		acc = (VAccountBean) bundle.get("a");
		//Toast.makeText(gotodepositActivity.this, "hi,deposit", 0).show();
	}
	
	public void depositSubmit(View view){
		//Verify
		depositmoney = (EditText) findViewById(R.id.depositmoney);
		String s_depositmoney = depositmoney.getText().toString();
		if(s_depositmoney==null || s_depositmoney ==" " || !util_functions.isPositiveInteger(s_depositmoney)){
			new AlertDialog.Builder(this).setTitle("Error").setMessage("Please insert correct number").setPositiveButton("Confirm", null).show();
			depositmoney.requestFocus();
			depositmoney.setText("");
		}
		else{
			
			new Thread(){
				public void run(){
					try {      
						
						String dmoney = depositmoney.getText().toString();
						String aid = acc.getAid();
						
	        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mDeposit?aid="+aid+"&dmoney="+dmoney);
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
			
			Log.i("Deposit","deposit money:"+s_depositmoney);
			//new AlertDialog.Builder(this).setTitle("Deposit").setMessage("You have deposit "+s_depositmoney+" RMB!").setPositiveButton("Confirm", null).show();
		}
		
	}	
	
	public void backtoservices(View view){
		Intent intent = new Intent();
		intent.setClass(gotodepositActivity.this,accountloginActivity.class);
		Bundle bundle=new Bundle();
	 	bundle.putSerializable("a", acc);
	 	intent.putExtras(bundle);
		startActivity(intent);
	}
	

}
