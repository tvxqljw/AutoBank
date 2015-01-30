package com.ccnu.helloworld;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ccnu.bean.CustomerBean;
import com.ccnu.bean.DetailBean;
import com.ccnu.bean.VAccountBean;
import com.ccnu.helloworld.adminloginActivity.MyAdapter;
import com.ccnu.util.util_functions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class gotodetailActivity extends Activity{
	VAccountBean acc = null;
	String adminaid = null;
	String aid;
	String cid;
	CustomerBean c;
	private String type=null;
	private TextView tv_top;
	private ListView detail_lv;
	List<DetailBean> detail_list = null;
	
	private EditText starttimeev; 
	private EditText endtimeev; 
	
	private MyAdapter adapter;//self_defined adapter
	
	public Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			switch(msg.what){
			case 1:{
				String json_res = (String) msg.obj;
				//list is customerBean
					detail_list = new ArrayList<DetailBean>();
					//json parse
					try {
					JSONArray array = new JSONArray(json_res);
					for(int i=0; i<array.length();i++){
						Log.i("Length:",""+array.length());
						JSONObject obj = array.getJSONObject(i);
						DetailBean detail = new DetailBean();
						detail.setAid(obj.getString("aid"));
						detail.setDbalance((double)obj.getInt("dbalance"));
						detail.setDcount(obj.getString("dcount"));
						detail.setDdate(obj.getString("ddate"));
						detail.setDmoney((double)obj.getInt("dmoney"));
						detail.setDtype(obj.getString("dtype"));
						detail_list.add(detail);
				
						
						Log.i("Balance",detail.getDbalance().toString());
					}
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
						
						adapter = new MyAdapter();
						detail_lv.setAdapter(adapter);
						
			}//end case:1
		}
	}
};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		this.setContentView(R.layout.detail);
		
		//Toast.makeText(gotodetailActivity.this, "hi,detail", 0).show();
		
		Bundle bundle = this.getIntent().getExtras();		
		VAccountBean account = (VAccountBean) bundle.get("a");
		acc = (VAccountBean) bundle.get("a");
		type = bundle.getString("type");
		adminaid = bundle.getString("adminaid");
		cid = bundle.getString("cid");
		c = (CustomerBean) bundle.get("c");
				
		//Toast.makeText(gotodetailActivity.this, "HI "+account.getAid(), 0).show();
		
		tv_top = (TextView) findViewById(R.id.top_detail);
		
		
		aid = account.getAid().toString();
		
		tv_top.setText(account.getCname()+"\n("+account.getAid()+")");
		
		//load detail list
		detail_lv = (ListView) findViewById(R.id.detail_lv);
		new Thread(){
			public void run(){
				try {       			
        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mGetdetail");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setConnectTimeout(3000);
					conn.setDoInput(true);
					
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
					
					conn.connect();
					
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					String content = "aid="+URLEncoder.encode(aid,"UTF-8");
					out.writeBytes(content); 
					out.flush();
					out.close();
					
					//get the input stream
					InputStream inputstream = conn.getInputStream();
					String res = util_functions.readLine(inputstream);
										
					//Success
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

	
	
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return detail_list.size();
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
			View view = View.inflate(gotodetailActivity.this, R.layout.item_detail, null);
			
			TextView tv_dmoney = (TextView) view.findViewById(R.id.d_money);
			TextView tv_dcount = (TextView) view.findViewById(R.id.d_dcount);
			TextView tv_balance = (TextView) view.findViewById(R.id.d_banlance);
			TextView tv_type = (TextView) view.findViewById(R.id.d_type);
			TextView tv_date= (TextView) view.findViewById(R.id.d_date);
			
			DetailBean d = detail_list.get(position);
			Log.i("TYPE",d.getDtype().toString());

			String count = d.getDcount().toString();
			if(count.equals(d.getAid().toString())){
				tv_dcount.setText("--");
				
			}else{
				tv_dcount.setText(d.getDcount().toString());
			}
			
			
			tv_type.setText(d.getDtype().toString());
			tv_date.setText(d.getDdate().toString());
			tv_balance.setText(d.getDbalance().toString());		
			String type = d.getDtype().toString();
			if(type.equals("draw")||type.equals("transOut")){
				tv_dmoney.setText("-"+d.getDmoney().toString());
			}
			else{
				tv_dmoney.setText(d.getDmoney().toString());
			}			
			return view;
		}

		
		
	}
	
	public void backtoservices(View view){
		Intent intent = new Intent();		
		if(type.equals("customer")){
			intent.setClass(gotodetailActivity.this,accountloginActivity.class);
			Bundle bundle=new Bundle();
		 	bundle.putSerializable("a", acc);
		 	intent.putExtras(bundle);
		}
		else if(type.equals("admin")){
			//admin		
			intent.setClass(gotodetailActivity.this,adminloginActivity.class);
		 	intent.putExtra("aid",adminaid);
			
		}
		else{
			//admin_cus
			intent.setClass(gotodetailActivity.this,gotocusaccountsActivity.class);
		 	intent.putExtra("cid",cid);
		 	Bundle bundle=new Bundle();
		 	intent.putExtra("aid",adminaid);
		 	bundle.putSerializable("c", c);
		 	intent.putExtras(bundle);
		}
		startActivity(intent);
	}
	
	public void detailsearchSubmit(View view){
		starttimeev = (EditText) findViewById(R.id.st_ev);
		endtimeev =(EditText) findViewById(R.id.ed_ev);
		new Thread(){
			public void run(){
				try {       			
					
					String st = starttimeev.getText().toString();
					String et = endtimeev.getText().toString();
					
        			URL url = new URL("http://192.168.0.84:8080/AutoBank/mGetdetail?aid="+aid+"&starttime="+st+"&endtime="+et);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setConnectTimeout(3000);
					conn.setDoInput(true);
					
					conn.connect();
					

					
					//get the input stream
					InputStream inputstream = conn.getInputStream();
					String res = util_functions.readLine(inputstream);
										
					//Success
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
		
		Toast.makeText(this, "Search finised!",0).show();
	}
	
}
