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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class gotodrawActivity extends Activity{
	VAccountBean acc = null;
	private EditText drawmoney;

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
							//new AlertDialog.Builder(gotodrawActivity.this).setTitle("Draw Success").setMessage("Hi,"+account.getCname()+", your have draw "+money+". \nYour balance is "+account.getAbalance()).setPositiveButton("Confirm", null).show();		
						
							Intent intent =new Intent();
							intent.setClass(gotodrawActivity.this, gotoconfirmActivity.class);
							Bundle bundle=new Bundle();
						 	bundle.putSerializable("a", acc);
						 	intent.putExtras(bundle);
						 	String content = "Dear "+account.getCname()+",\n\nYou have draw "+money+" RMB from account "+account.getAid()+"\n\nYour balance is "+account.getAbalance().toString();	
						 	String title = "Draw Successfully!";	
							intent.putExtra("content", content);
							intent.putExtra("title", title);
							startActivity(intent);
							
						}else if(check == 0){
							//frozen
							new AlertDialog.Builder(gotodrawActivity.this).setTitle("ERROR").setMessage("Sorry,your account is frozen!").setPositiveButton("Confirm", null).show();			
						}
						else if(check == -1){
							new AlertDialog.Builder(gotodrawActivity.this).setTitle("ERROR").setMessage("Your money is not enough!").setPositiveButton("Confirm", null).show();	
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
		this.setContentView(R.layout.draw);
		Bundle bundle = this.getIntent().getExtras();
		acc = (VAccountBean) bundle.get("a");
		//Toast.makeText(gotodrawActivity.this, "hi,draw", 0).show();
	}

	public void drawSubmit(View view){
		drawmoney = (EditText) findViewById(R.id.drawmoney);	
		String s_drawmoney = drawmoney.getText().toString();
		
		Button drawbutton = (Button)view;
	    final String buttonText = drawbutton.getText().toString();
		
		if(buttonText.equals("Draw")){
			//user-defined
			if(s_drawmoney==null || s_drawmoney ==" " || !util_functions.isPositiveInteger(s_drawmoney)){
				new AlertDialog.Builder(this).setTitle("Error").setMessage("Please insert correct number").setPositiveButton("Confirm", null).show();
				drawmoney.requestFocus();
				drawmoney.setText("");
			}
			else{
				new Thread(){
					public void run(){
						try {      
							
							String dmoney = drawmoney.getText().toString();
							String aid = acc.getAid();
							
		        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mDraw?aid="+aid+"&dmoney="+dmoney);
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
		else{
			//fixed number
			new Thread(){
				public void run(){
					try {      
						String aid = acc.getAid();
						
	        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mDraw?aid="+aid+"&dmoney="+buttonText);
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
		intent.setClass(gotodrawActivity.this,accountloginActivity.class);
		Bundle bundle=new Bundle();
	 	bundle.putSerializable("a", acc);
	 	intent.putExtras(bundle);
		startActivity(intent);
	}

}
