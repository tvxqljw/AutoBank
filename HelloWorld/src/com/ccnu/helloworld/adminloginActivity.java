package com.ccnu.helloworld;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.ccnu.bean.CustomerBean;
import com.ccnu.bean.VAccountBean;
import com.ccnu.util.util_functions;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;

public class adminloginActivity extends Activity{
	//hander
	private ListView cus_lv; 
	private ListView acc_lv; 
	List<CustomerBean> cus_list = null;
	List<VAccountBean> acc_list = null;
	private MyAdapter adapter;//self_defined adapter
	private MyAdapter_acc adapter_acc;//self_defined adapter
	private TextView aid_tv;
	private EditText cussearch;
	private EditText accsearch;
	
	String newstate;
	
	private String aid;//admin aid
	
	
	//each
	private TextView cid;
	

	public Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			switch(msg.what){
			case 1:{
				String json_res = (String) msg.obj;
				//list is customerBean
					cus_list = new ArrayList<CustomerBean>();
					//json parse
					try {
					JSONArray array = new JSONArray(json_res);
					for(int i=0; i<array.length();i++){
						JSONObject obj = array.getJSONObject(i);
						CustomerBean cus = new CustomerBean();
						cus.setCname(obj.getString("cname"));
						cus.setCaddr(obj.getString("caddr"));
						cus.setCtel(obj.getString("ctel"));
						cus.setCcareer(obj.getString("ccareer"));
						cus.setCsex(obj.getString("csex"));
						cus.setCid(obj.getString("cid"));
						cus.setCstate(obj.getString("cstate"));
						Log.i("Cname",cus.getCname());
						cus_list.add(cus);
					}
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
						
						adapter = new MyAdapter();
						cus_lv.setAdapter(adapter);
						break;
			}//end case:1
			case 2:{
				String json_res = (String) msg.obj;
				//list is customerBean
				acc_list = new ArrayList<VAccountBean>();
				//json parse
				try {
				JSONArray array = new JSONArray(json_res);
				for(int i=0; i<array.length();i++){
					JSONObject obj = array.getJSONObject(i);
					VAccountBean acc = new VAccountBean();
					acc.setAbalance((double) obj.getInt("abalance"));
					acc.setAstate(obj.getString("astate"));
					acc.setAid(obj.getString("aid"));
					acc.setCname(obj.getString("cname"));
					acc_list.add(acc);
				}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
						
						adapter_acc = new MyAdapter_acc();
						acc_lv.setAdapter(adapter_acc);
						break;
			}//end case 2
			
			case 3:{
				String json_res = (String) msg.obj;
				
				try {
					JSONObject result = new JSONObject(json_res);
					boolean check = result.getBoolean("check");
					if(check==true){
						//search success
						cus_list = new ArrayList<CustomerBean>();
						JSONObject obj = result.getJSONObject("customer");
						CustomerBean cus = new CustomerBean();
						cus.setCname(obj.getString("cname"));
						cus.setCaddr(obj.getString("caddr"));
						cus.setCtel(obj.getString("ctel"));
						cus.setCcareer(obj.getString("ccareer"));
						cus.setCsex(obj.getString("csex"));
						cus.setCid(obj.getString("cid"));
						cus.setCstate(obj.getString("cstate"));
						Log.i("Cname",cus.getCname());
						cus_list.add(cus);
						adapter = new MyAdapter();
						cus_lv.setAdapter(adapter);
						Toast.makeText(adminloginActivity.this, "Search finished!", 0).show();
					}
					else{
						//cid not exist
						Toast.makeText(adminloginActivity.this, "Identity is not exist!", 0).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

				break;
			}//end case 3
			
			case 4:{
				String json_res = (String) msg.obj;
				
				try {
					JSONObject result = new JSONObject(json_res);
					boolean check = result.getBoolean("check");
					if(check==true){
						//search success
						acc_list = new ArrayList<VAccountBean>();
						JSONObject obj = result.getJSONObject("account");
						VAccountBean acc = new VAccountBean();
						acc.setAbalance((double) obj.getInt("abalance"));
						acc.setAstate(obj.getString("astate"));
						acc.setAid(obj.getString("aid"));
						acc.setCname(obj.getString("cname"));
						acc_list.add(acc);
						adapter_acc = new MyAdapter_acc();
						acc_lv.setAdapter(adapter_acc);
						Toast.makeText(adminloginActivity.this, "Search finished!", 0).show();
					}
					else{
						//cid not exist
						Toast.makeText(adminloginActivity.this, "Account Id is not exist!", 0).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
					
			}//end case 4
			
			case 5:{
				String r = (String) msg.obj;
				try {
					JSONObject obj = new JSONObject(r);
					String news = obj.getString("newstate");
					newstate = news;
					
					new Thread(){
						public void run(){
							try {       			
			        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mGetallacc");
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
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}//end case 5
				
			
				}
			}
		};
		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);			
		//Toast.makeText(adminloginActivity.this, "Successfully Login!", 0).show();
		this.setContentView(R.layout.adminindex);
		
		//set top aid 
				Bundle bundle = this.getIntent().getExtras();  
				aid = bundle.getString("aid");
				aid_tv = (TextView) findViewById(R.id.adminindex_top);
				aid_tv.setText(aid);
				
		
		  //deal wth tabhost    
	      TabHost tabHost=(TabHost)findViewById(R.id.tabHost);
	      tabHost.setup();
	      
	      LayoutInflater inflater =LayoutInflater.from(this);
	      inflater.inflate(R.layout.managecus, 
	      		tabHost.getTabContentView(), true);
	      
	      inflater.inflate(R.layout.manageacc, 
	      		tabHost.getTabContentView(), true);

	      TabSpec spec1=tabHost.newTabSpec("MC");
	      spec1.setContent(R.id.managecusPage);
	      spec1.setIndicator("Manage Customer");

	      TabSpec spec2=tabHost.newTabSpec("MS");
	      spec2.setIndicator("Manage Account");//指标
	      spec2.setContent(R.id.manageaccPage);//对应的xmlR.layout


	      tabHost.addTab(spec1);
	      tabHost.addTab(spec2);
	      
			//Customer get the data from server
			cus_lv = (ListView) findViewById(R.id.cus_lv);
			new Thread(){
				public void run(){
					try {       			
	        			Log.i("INFO","begin=");
	        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mGetallcus");
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
			
			
			
			
			
			//Account get the data from server
			acc_lv = (ListView) findViewById(R.id.acc_lv);
			new Thread(){
				public void run(){
					try {       			
	        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mGetallacc");
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
			
			
			
			//click each cus
			cus_lv.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					Toast.makeText(adminloginActivity.this, cus_list.get(arg2).getCname(), 0).show();
					
					Intent intent = new Intent();
					intent.setClass(adminloginActivity.this, eachcusActivity.class);
					//value
					CustomerBean c = cus_list.get(arg2);
					Bundle bundle = new Bundle();
					bundle.putSerializable("c", c);
					intent.putExtras(bundle);
					intent.putExtra("adminaid", aid);
					startActivity(intent);
					
					
				}});
			
			acc_lv.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Toast.makeText(adminloginActivity.this, acc_list.get(arg2).getAid()+" Details", 0).show();
					
					Intent acc_intent = new Intent();
					acc_intent.setClass(adminloginActivity.this, gotodetailActivity.class);
					
					VAccountBean a = acc_list.get(arg2);
					Bundle bundle = new Bundle();
					bundle.putSerializable("a", a);
					acc_intent.putExtras(bundle);
					acc_intent.putExtra("type", "admin");
					acc_intent.putExtra("adminaid", aid);
					
					startActivity(acc_intent);
				}
				
			});
			
			
			//long press to change account state
			acc_lv.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					final String cur_state = acc_list.get(arg2).getAstate();
					final String cur_aid = acc_list.get(arg2).getAid();
					
					
									
					new AlertDialog.Builder(adminloginActivity.this).setTitle("Do you want to change state?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {  
	                    @Override  
	                    public void onClick(DialogInterface arg0, int arg1) {  	                            	
	                    	
	                    	new Thread(){
	            				public void run(){
	            					try {       			
	            	        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mUpdateAcc?aid="+cur_aid+"&astate="+cur_state);
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
	            						msg.what = 5;
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
	                }).setNegativeButton("No", null).show();
					//update
        			acc_list.get(arg2).setAstate(newstate);
        			//adapter_acc.notifyDataSetChanged();
        			Toast.makeText(adminloginActivity.this, "Update state success!", 0).show();
					
					
					return true;
				}
			});
			
      
	}
	
	

	
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			//get list size
			Log.i("Size",""+cus_list.size());
			return cus_list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {			
			//put json data into each item with style
			Log.i("Position",""+position);
			View view = View.inflate(adminloginActivity.this, R.layout.item_cus, null);
			
			TextView tv_cname = (TextView) view.findViewById(R.id.c_name);
			TextView tv_cid = (TextView) view.findViewById(R.id.c_cid);
			TextView tv_cstate = (TextView) view.findViewById(R.id.c_state);
			
			CustomerBean customer = cus_list.get(position);
			
			if(cus_list.get(position).getCstate().equals("off")){
				//off
				tv_cstate.setBackgroundResource(R.drawable.cstate_off);		
				tv_cstate.setTextAppearance(getBaseContext(), R.style.custv_state_off);
			}
						
			tv_cname.setText(customer.getCname());
			tv_cid.setText(customer.getCid());
			tv_cstate.setText(customer.getCstate());

			
			

			
			return view;
			
		}	
		
	}
	
	class MyAdapter_acc extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return acc_list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			View view = View.inflate(adminloginActivity.this, R.layout.item_acc, null);
			
			TextView tv_aname = (TextView) view.findViewById(R.id.a_name);
			TextView tv_aid = (TextView) view.findViewById(R.id.a_aid);
			TextView tv_astate = (TextView) view.findViewById(R.id.a_state);
			TextView tv_abalance = (TextView) view.findViewById(R.id.a_balance);
			
			VAccountBean account = acc_list.get(position);
			if(account.getAstate().equals("frozen")){
				//frozen
				tv_astate.setBackgroundResource(R.drawable.astate_frozen);
				tv_astate.setTextAppearance(getBaseContext(), R.style.acctv_state_frozen);
			}
			
			tv_aname.setText(account.getCname());
			tv_aid.setText(account.getAid());
			tv_astate.setText(account.getAstate());
			tv_abalance.setText(account.getAbalance().toString());
			
			return view;
		}
		
	}
	
	
	public void gotoaddcus(View view){
		Intent intent = new Intent();
		intent.setClass(this, gotoaddcusActivity.class);
		intent.putExtra("adminaid", aid);
		startActivity(intent);
	}

	public void backtologin(View view){
		Intent intent = new Intent();
		intent.setClass(this, gotoadminloginActivity.class);
		startActivity(intent);
	}
	
	public void refresh(View view){
		new Thread(){
			public void run(){
				try {       			
        			Log.i("INFO","begin=");
        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mGetallcus");
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
		
		new Thread(){
			public void run(){
				try {       			
        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mGetallacc");
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
	}
	
	public void cussearchSubmit(View view){
		cussearch =(EditText) findViewById(R.id.cussearchev);
		final String search = cussearch.getText().toString();
		if(search==null ||search==""){
			new AlertDialog.Builder(adminloginActivity.this).setTitle("ERROR").setMessage("identity can not be empty!" ).setPositiveButton("Confirm", null).show();	
			cussearch.requestFocus();
		}
		else{
			//goto search
			new Thread(){
				public void run(){
					try {       			
	        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mCusSearch?cid="+search);
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
		}
	}
	
	public void accsearchSubmit(View view){
		accsearch = (EditText) findViewById(R.id.accsearchev);
		final String search = accsearch.getText().toString();
		if(search==null ||search==""){
			new AlertDialog.Builder(adminloginActivity.this).setTitle("ERROR").setMessage("Account Id can not be empty!" ).setPositiveButton("Confirm", null).show();	
			accsearch.requestFocus();
		}
		else{
			//goto search
			new Thread(){
				public void run(){
					try {       			
	        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mAccSearch?aid="+search);
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
						msg.what = 4;
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
