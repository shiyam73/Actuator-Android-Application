package com.example.actschedule;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button schedule;
	Button open;
	Button close;
	Button dt;
	Button wep;
	Button config;
	TextView state;
	String st;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        schedule = (Button)findViewById(R.id.button1);
        open = (Button)findViewById(R.id.button2);
        close = (Button)findViewById(R.id.button3);
        dt = (Button)findViewById(R.id.button5);
        wep = (Button)findViewById(R.id.button4);
        config = (Button)findViewById(R.id.button6);
        state = (TextView)findViewById(R.id.textView2);
        
       /* if( (PreferenceConnector.readString(this,PreferenceConnector.WEPKEY, null))==null)
        {
        			schedule.setEnabled(false);
        			open.setEnabled(false);
        			close.setEnabled(false);
        			dt.setEnabled(false);
        }
        else
        {
			schedule.setEnabled(true);
			open.setEnabled(true);
			close.setEnabled(true);
			dt.setEnabled(true);
		}*/
        
        st = (String) state.getText();
        
        wep.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this,SetWep.class);
				startActivity(i);
			}
		});
        
        schedule.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this,SetSchedule.class);
				startActivity(i);
				
			}
		});
        
        open.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this,OpenValve.class);
				startActivity(i);
			}
		});
        
        close.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this,CloseValve.class);
				startActivity(i);
			}
		});
        
        dt.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this,SyncDate.class);
				startActivity(i);
			}
		});
        
        config.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this,Config.class);
				startActivity(i);
			}
		});
        
        if(st!=null)
        {
        	if(st.equalsIgnoreCase("valve open"))
        		open.setVisibility(View.INVISIBLE);
        	else if(st.equalsIgnoreCase("valve close"))
        		close.setVisibility(View.INVISIBLE);
        	else
        	{
        		open.setVisibility(View.VISIBLE);
        		close.setVisibility(View.VISIBLE);
        	}
        }
        else
        {
        	Toast.makeText(MainActivity.this, "State is none", Toast.LENGTH_LONG).show();
        }
        if(Constants.open==1)
        {
        	PreferenceConnector.writeInteger(this, PreferenceConnector.OPEN, 1);
        	Constants.open=0;
        }
        
        if(Constants.close==1)
        {
        	PreferenceConnector.writeInteger(this, PreferenceConnector.CLOSE, 1);
        	PreferenceConnector.writeInteger(this, PreferenceConnector.OPEN, 0);
        	Constants.close=0;
        }
        
        if(PreferenceConnector.readInteger(this, PreferenceConnector.OPEN, 0)==1)
        {
        	state.setText("Valve Open");
        	PreferenceConnector.writeInteger(this, PreferenceConnector.CLOSE, 0);
        	open.setVisibility(View.INVISIBLE);
        	
        }
        else if(PreferenceConnector.readInteger(this, PreferenceConnector.CLOSE, 0)==1)
        {
        	state.setText("Valve Close");
        	close.setVisibility(View.INVISIBLE);
        	open.setVisibility(View.VISIBLE);
        }
        else
        {
        	state.setText("None");
        }
        
    }
    
    @Override
   	public boolean onKeyDown(int keyCode, KeyEvent event)
       {
   		if(keyCode == KeyEvent.KEYCODE_BACK)
   		{
   			onDestroy();
   			finish();
   			return true;
   		}
   		return false;
   	}
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	open.setOnClickListener(null);
    	close.setOnClickListener(null);
    	
    }

}
