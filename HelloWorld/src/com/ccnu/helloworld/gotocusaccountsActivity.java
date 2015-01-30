package com.ccnu.helloworld;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ccnu.bean.CustomerBean;
import com.ccnu.bean.VAccountBean;
import com.ccnu.helloworld.adminloginActivity.MyAdapter;
import com.ccnu.helloworld.adminloginActivity.MyAdapter_acc;
import com.ccnu.util.util_functions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class gotocusaccountsActivity extends Activity{

	//hander
	private ListView acc_lv; 
	List<VAccountBean> acc_list = null;
	private MyAdapter_acc adapter_acc;//self_defined adapter
	private TextView aid_tv;
	
	private String adminaid;
	private String cid;
	CustomerBean c;
	
	
public Handler handler = new Handler(){
		
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			switch(msg.what){
			case 1:{
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
			}//end case 1
			
			case 2:{
				String result = (String) msg.obj;
				Toast.makeText(gotocusaccountsActivity.this, "New account ("+result+")!", 0).show();
				//refresh
				new Thread(){
					public void run(){
						try {       			
		        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mGetAccByCid?cid="+cid);
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
			}//end case 2
				
			}
		}
		
	};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.cus_accounts);
		
		Bundle bundle = this.getIntent().getExtras();
		adminaid = bundle.getString("adminaid");
		cid = bundle.getString("cid");
		c = (CustomerBean) bundle.get("c");
		
		TextView top = (TextView) findViewById(R.id.cus_accounts_top);
		top.setText(c.getCname().toString());
		
		acc_lv = (ListView) findViewById(R.id.acc_lv);
		new Thread(){
			public void run(){
				try {       			
        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mGetAccByCid?cid="+cid);
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
		
		acc_lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Toast.makeText(gotocusaccountsActivity.this, acc_list.get(arg2).getAid()+" Details", 0).show();
				
				Intent acc_intent = new Intent();
				acc_intent.setClass(gotocusaccountsActivity.this, gotodetailActivity.class);
				
				VAccountBean a = acc_list.get(arg2);
				Bundle bundle = new Bundle();
				bundle.putSerializable("a", a);			
				acc_intent.putExtra("type", "cus");
				acc_intent.putExtra("adminaid", adminaid);
				bundle.putSerializable("c", c);
				acc_intent.putExtra("cid", cid);
				acc_intent.putExtras(bundle);
				startActivity(acc_intent);
			}
			
		});
		
		
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
			View view = View.inflate(gotocusaccountsActivity.this, R.layout.item_acc, null);
			
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
	
	
	public void backtocusaccounts(View view){
		Intent acc_intent = new Intent();
		acc_intent.setClass(gotocusaccountsActivity.this, eachcusActivity.class);		
		Bundle bundle = new Bundle();
		acc_intent.putExtra("adminaid", adminaid);
		bundle.putSerializable("c", c);
		acc_intent.putExtras(bundle);		
		startActivity(acc_intent);
	}
	
	public void addaccsubmit(View view){
		new Thread(){
			public void run(){
				try {       			
        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mAddacc?cid="+cid);
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

}
