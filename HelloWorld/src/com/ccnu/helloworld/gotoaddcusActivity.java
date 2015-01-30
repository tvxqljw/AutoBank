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

import com.ccnu.bean.CustomerBean;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class gotoaddcusActivity extends Activity{
	
	private EditText cname;
	private RadioGroup csexG;
	private RadioButton csex;
	private EditText ctel;
	private EditText caddr;
	private EditText cid;
	private EditText ccareer;
	
	String adminaid = null;
	CustomerBean c;
	
	public Handler handler = new Handler(){
		
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			switch(msg.what){
			case 1:
				String r = (String) msg.obj;
				try {
					
					JSONObject obj = new JSONObject(r);
					boolean check = obj.getBoolean("check");
					
					if(check==true){
						//exist
						String gcid = obj.getString("cid");
						new AlertDialog.Builder(gotoaddcusActivity.this).setTitle("ERROR").setMessage("Customer "+gcid+" is exist!" ).setPositiveButton("Confirm", null).show();	
						cid.requestFocus();
						cid.setText("");
					}
					else{
										
						Toast.makeText(gotoaddcusActivity.this, "add success!", 0).show();
						JSONObject jobj = obj.getJSONObject("customer");
						CustomerBean cus = new CustomerBean();
						cus.setCname(jobj.getString("cname"));
						cus.setCid(jobj.getString("cid"));
						cus.setCaddr(jobj.getString("caddr"));
						cus.setCcareer(jobj.getString("ccareer"));
						cus.setCsex(jobj.getString("csex"));
						cus.setCtel(jobj.getString("ctel"));
						cus.setCstate(jobj.getString("cstate"));
						
						Log.i("--------------------------------",cus.getCname());
						
						Intent intent = new Intent();
						intent.setClass(gotoaddcusActivity.this,eachcusActivity.class );
						intent.putExtra("adminaid", adminaid);
						Bundle bundle = new Bundle();
						bundle.putSerializable("c", cus);
						intent.putExtras(bundle);
						startActivity(intent);
					}
					
					
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
		
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.addcus);
		Bundle bundle = this.getIntent().getExtras();		
		adminaid = bundle.getString("adminaid");
		
		
		cname = (EditText) findViewById(R.id.addcus_nameev);		
		ctel = (EditText) findViewById(R.id.addcus_telev);
		caddr = (EditText) findViewById(R.id.addcus_addrev);
		cid = (EditText) findViewById(R.id.addcus_identityev);
		ccareer = (EditText) findViewById(R.id.addcus_careerev);
		csexG = (RadioGroup) findViewById(R.id.addcus_sexgroup);
		csex = (RadioButton)findViewById(csexG.getCheckedRadioButtonId());
		
	}
	
	public void exitaddcus(View view){
		Intent intent = new Intent();
		intent.setClass(this, adminloginActivity.class);
		intent.putExtra("aid", adminaid);
		startActivity(intent);
	}
	
	
	public void addcusSubmit(View view){
		//click to add

		String s_cname = cname.getText().toString();		
		String s_ctel = ctel.getText().toString();
		String s_caddr = caddr.getText().toString();
		String s_cid = cid.getText().toString();		
		String s_ccareer = ccareer.getText().toString();
		String s_csex = csex.getText().toString();
		
		//verify
	
		if(s_cname.trim()==null || s_cname.trim()=="" || s_cname.trim().equals(null) ||s_cname.trim().equals("")){
			new AlertDialog.Builder(this).setTitle("Error").setMessage("Name can not be empty!").setPositiveButton("Confirm", null).show();
			cname.requestFocus();
			cname.setText("");
		}else if(s_ctel.trim()==null || s_ctel.trim()=="" ||s_ctel.trim().equals(null) ||s_ctel.trim().equals("")){
			new AlertDialog.Builder(this).setTitle("Error").setMessage("Tel can not be empty!").setPositiveButton("Confirm", null).show();
			ctel.requestFocus();
			ctel.setText("");
		}
		else if(s_caddr.trim()==null || s_caddr.trim()=="" || s_caddr.trim().equals(null) || s_caddr.trim().equals("")){
			new AlertDialog.Builder(this).setTitle("Error").setMessage("Address can not be empty!").setPositiveButton("Confirm", null).show();
			caddr.requestFocus();
			caddr.setText("");
		}
		else if(s_cid.trim()==null || s_cid.trim()=="" || s_cid.trim().equals(null) || s_cid.trim().equals("")){
			new AlertDialog.Builder(this).setTitle("Error").setMessage("Identity can not be empty!").setPositiveButton("Confirm", null).show();
			cid.requestFocus();
			cid.setText("");
		}
		else if(s_ccareer.trim()==null || s_ccareer.trim()==""|| s_ccareer.trim().equals(null)|| s_ccareer.trim().equals("")){
			new AlertDialog.Builder(this).setTitle("Error").setMessage("Career can not be empty!").setPositiveButton("Confirm", null).show();
			ccareer.requestFocus();
			ccareer.setText("");
		}
		else{
			//ok
			Log.i("INFO","infomation:"+s_cname+" "+s_csex+" "+s_ctel+" "+s_caddr+" "+s_cid+" "+s_ccareer);
			
			new Thread(){
				public void run(){
					try {       			
	        			Log.i("AddCus","");
	        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mAddCus");
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setDoOutput(true);
						conn.setConnectTimeout(3000);
						conn.setDoInput(true);
						
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
						
						conn.connect();
						
						DataOutputStream out = new DataOutputStream(conn.getOutputStream());
						String content = "cname="+URLEncoder.encode(cname.getText().toString(),"UTF-8")+"&csex="+URLEncoder.encode(csex.getText().toString(),"UTF-8")+"&ctel="+URLEncoder.encode(ctel.getText().toString(),"UTF-8")+"&caddr="+URLEncoder.encode(caddr.getText().toString(),"UTF-8")+"&ccareer="+URLEncoder.encode(ccareer.getText().toString(),"UTF-8")+"&cid="+URLEncoder.encode(cid.getText().toString(),"UTF-8");
						Log.i("PARAMER",content);
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
		
		
	}
}