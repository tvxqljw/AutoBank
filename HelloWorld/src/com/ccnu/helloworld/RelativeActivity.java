package com.ccnu.helloworld;

import android.app.Activity;
import android.os.Bundle;

public class RelativeActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.person);
	}

}
