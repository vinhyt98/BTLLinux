package com.example.calculator;

import java.util.ArrayList;

//import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
//import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
//import android.widget.Toast;
import process.Process;

public class MainActivity extends Activity implements OnClickListener {
	
	private ArrayList<Button> list;
	private ArrayList<String> kytu, bodem;
	private TextView pt, kq;
	private final int MAX = 10;
	private int curr;
	private LinearLayout gHeight,hg;
	private Boolean canDel=true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		anhXa();
		for(int i=0;i<list.size();i++) {
			list.get(i).setOnClickListener(this);
		}

	}
	
	private void anhXa() {
		list  = new ArrayList<Button>();
		kytu = new ArrayList<String>();
		bodem = new ArrayList<String>();
		
		for(int i=0;i<10;i++) {
			kytu.add(i+"");
		}
		kytu.add(".");
		kytu.add("+");
		kytu.add("-");
		kytu.add("*");
		kytu.add("/");
		kytu.add("(");
		kytu.add(")");
		
		//0->9
		list.add((Button)findViewById(R.id.bt0));
		list.add((Button)findViewById(R.id.bt1));
		list.add((Button)findViewById(R.id.bt2));
		list.add((Button)findViewById(R.id.bt3));
		list.add((Button)findViewById(R.id.bt4));
		list.add((Button)findViewById(R.id.bt5));
		list.add((Button)findViewById(R.id.bt6));
		list.add((Button)findViewById(R.id.bt7));
		list.add((Button)findViewById(R.id.bt8));
		list.add((Button)findViewById(R.id.bt9));
		
		//10->16
		list.add((Button)findViewById(R.id.btph));
		list.add((Button)findViewById(R.id.btco));
		list.add((Button)findViewById(R.id.bttr));
		list.add((Button)findViewById(R.id.btnh));
		list.add((Button)findViewById(R.id.btch));
		list.add((Button)findViewById(R.id.btngt));
		list.add((Button)findViewById(R.id.btngp));
		//17-21
		list.add((Button)findViewById(R.id.btdel));
		list.add((Button)findViewById(R.id.btcan));
		list.add((Button)findViewById(R.id.btba));
		list.add((Button)findViewById(R.id.btlen));
		list.add((Button)findViewById(R.id.btxuong));

		pt = (TextView) findViewById(R.id.pt);
		kq = (TextView) findViewById(R.id.kq);
		
		hg = (LinearLayout) findViewById(R.id.hg);
		DisplayMetrics d = hg.getResources().getDisplayMetrics();
		int height = (int) (d.heightPixels*(20.0/100)*(55.0/100));
		for(int i=0;i<list.size();i++) {
			list.get(i).setHeight(height);
		}

	}

	@Override
	public void onClick(View view) {
		switch (list.indexOf((Button) view)) {
			case 17: //del
				String text = pt.getText().toString();
				if(text.length()==0) break;
				pt.setText(text.substring(0, text.length()-1));
				curr = bodem.size();
				break;
			case 18://can
				pt.setText("");
				kq.setText("");
				curr=bodem.size();
				break;
			case 19://bang
				Process pro = new Process(); 
				ArrayList temp;
				if(bodem.size()==10) {
					bodem.remove(0);
				}
				bodem.add(pt.getText().toString());
				String s = pt.getText().toString();
				temp = pro.getKQ(s);
				if(!(Boolean)temp.get(0)) {
					kq.setText("LOI PHEP TINH!!!");
				}else {
					kq.setText((Double)temp.get(1)+"");
				}
				curr = bodem.size()-1;
				canDel = true;
				break;
			case 20:
				if(bodem.size()==0||curr==0) break;
				pt.setText(bodem.get(curr-1));
				if(curr!=1) curr--;
				break;
			case 21:
				if(bodem.size()==0 || curr > bodem.size()) break;
				pt.setText(bodem.get(curr-1));
				if(curr<bodem.size()) curr++;
				break;
			default:
				if(canDel) {
					pt.setText("");
				}
				canDel = false;
				curr=bodem.size();
				pt.append(kytu.get(list.indexOf(view)));
		}
	}
	
	private double tinh (String s) {
		double kq = 0;
		
		
		
		return kq;
	}
}







