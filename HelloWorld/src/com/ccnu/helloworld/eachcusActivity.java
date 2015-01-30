package com.ccnu.helloworld;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.ccnu.bean.CustomerBean;
import com.ccnu.util.util_functions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class eachcusActivity extends Activity {
	private TextView tv_name,tv_sex,tv_cid,tv_addr,tv_tel,tv_career,tv_state;
	String adminaid = null;
	CustomerBean c;
	
	
public Handler handler = new Handler(){
		
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			switch(msg.what){
			case 1:{
				String r = (String) msg.obj;
				try {
					JSONObject obj = new JSONObject(r);
					String newtel = obj.getString("newtel");
					tv_tel.setText(newtel);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}//end case 1
			case 2:{
				String r = (String) msg.obj;
				try {
					JSONObject obj = new JSONObject(r);
					String newaddr = obj.getString("newaddr");
					tv_addr.setText(newaddr);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}//end case 2
			case 3:{
				String r = (String) msg.obj;
				try {
					JSONObject obj = new JSONObject(r);
					String newstate = obj.getString("newstate");
					tv_state.setText(newstate);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}//end case 3
				
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.each_cus);
		
		
		Bundle bundle = this.getIntent().getExtras();
		c = (CustomerBean) bundle.get("c");	
		adminaid = bundle.getString("adminaid");
		
		tv_name = (TextView) findViewById(R.id.each_cus_name);
		tv_sex = (TextView) findViewById(R.id.each_cus_sex);
		tv_cid = (TextView) findViewById(R.id.each_cus_cid);
		tv_addr = (TextView) findViewById(R.id.each_cus_addr);
		tv_tel = (TextView) findViewById(R.id.each_cus_tel);
		tv_career = (TextView) findViewById(R.id.each_cus_career);
		tv_state = (TextView) findViewById(R.id.each_cus_state);
		
		tv_name.setText(c.getCname());
		
		
		String sex = c.getCsex().toString();
		if(sex.equals("f")){
			tv_sex.setText("Female");
		}
		else{
			tv_sex.setText("Male");
		}		
		tv_cid.setText(c.getCid());
		tv_addr.setText(c.getCaddr());
		tv_tel.setText(c.getCtel());
		tv_career.setText(c.getCcareer());
		tv_state.setText(c.getCstate());
		
	}
	
	public void backtoadmin(View view){
		Intent intent = new Intent();
		intent.setClass(this, adminloginActivity.class);
		intent.putExtra("aid", adminaid);
		startActivity(intent);
	}
	
	public void edittel(View view){		
		final EditText et = new EditText(this);  
		
		new AlertDialog.Builder(this).setTitle("Please insert new tel number").setView(et).setPositiveButton("OK", new DialogInterface.OnClickListener() {  
	                    @Override  
	                    public void onClick(DialogInterface arg0, int arg1) {  
	                           
	                    	final String newtel = et.getText().toString();
	                        new Thread(){
	            				public void run(){
	            					try {       			
	            						
	            						String cur_addr = tv_addr.getText().toString();
	            						String cid = tv_cid.getText().toString();
	            						
	            	        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mUpdateCus?cid="+cid+"&newtel="+newtel+"&newaddr="+cur_addr);
	            						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            						conn.setConnectTimeout(3000);
	            						conn.setDoInput(true);
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
	            			Toast.makeText(eachcusActivity.this, "Update tel success!", 0).show();
	                    }  
	                }).setNegativeButton("Cancel", null).show();
	}
	
	public void editaddr(View view){		
		final EditText et = new EditText(this);  
		
		new AlertDialog.Builder(this).setTitle("Please insert new address").setView(et).setPositiveButton("OK", new DialogInterface.OnClickListener() {  
	                    @Override  
	                    public void onClick(DialogInterface arg0, int arg1) {  
	                           
	                    	final String newaddr = et.getText().toString();
	                        new Thread(){
	            				public void run(){
	            					try {       			
	            						
	            						String cur_tel = tv_tel.getText().toString();
	            						String cid = tv_cid.getText().toString();
	            						
	            	        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mUpdateCus?cid="+cid+"&newtel="+cur_tel+"&newaddr="+newaddr);
	            						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            						conn.setConnectTimeout(3000);
	            						conn.setDoInput(true);
	            						conn.connect();
	            						
	            						//get the input stream
	            						InputStream inputstream = conn.getInputStream();
	            						String res = util_functions.readLine(inputstream);
	            						
	            						//Success
	            						Log.i("INFO","begin=1");
	            						Message msg = new Message();
	            						msg.what = 2;
	            						msg.obj = res;
	            						handler.sendMessage(msg);
	            	        			
	            	        			
	            					} catch (MalformedURLException e) {
	            						e.printStackTrace();
	            					} catch (IOException e) {
	            						e.printStackTrace();
	            					}
	            					
	            				}
	            			}.start();
	            			Toast.makeText(eachcusActivity.this, "Update address success!", 0).show();
	                    }  
	                }).setNegativeButton("Cancel", null).show();
	}
	
	public void changestate(View view){		
		new Thread(){
			public void run(){
				try {       			
					String cid = tv_cid.getText().toString();
					String cstate = tv_state.getText().toString();
					
        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mUpdateCusstate?cid="+cid+"&cstate="+cstate);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(3000);
					conn.setDoInput(true);
					conn.connect();
					
					//get the input stream
					InputStream inputstream = conn.getInputStream();
					String res = util_functions.readLine(inputstream);
					
					//Success
					Log.i("INFO","begin=1");
					Message msg = new Message();
					msg.what = 3;
					msg.obj = res;
					handler.sendMessage(msg);
        			
        			
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}.start();
		Toast.makeText(eachcusActivity.this, "Update state success!", 0).show();
	}
	
	public void cus_accounts(View view){
		tv_cid = (TextView) findViewById(R.id.each_cus_cid);
		String cid = tv_cid.getText().toString();
		Intent intent = new Intent();
		intent.setClass(this, gotocusaccountsActivity.class);
		intent.putExtra("aid", adminaid);
		intent.putExtra("cid", cid);
		Bundle bundle = new Bundle();
		bundle.putSerializable("c", c);
		intent.putExtras(bundle);
		startActivity(intent);
	}

}
