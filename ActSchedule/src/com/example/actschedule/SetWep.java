package com.example.actschedule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetWep extends Activity {

	Button send;
	EditText wep;
	String wepkey ="";
	private boolean connected = false;
	String ip1 = "10.0.2.2";
    int port=10002;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setwep);
        
        send = (Button)findViewById(R.id.button1);
        wep = (EditText) findViewById(R.id.editTextName);
        
        send.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				wepkey = wep.getText().toString();
				check();
				
				
			}
		});
        

        ip1 = (PreferenceConnector.readString(this,
				PreferenceConnector.IP, null));
        
        
        port = PreferenceConnector.readInteger(this,
				PreferenceConnector.PORT, 0);
        
        
    }
    
    public void check()
    {
	    if (wepkey != null)
	    {
			PreferenceConnector.writeString(this, PreferenceConnector.WEPKEY,
					wepkey);
			
			/*Toast.makeText(SetWep.this, "Data Saved", Toast.LENGTH_LONG).show();
			startActivity(new Intent(SetWep.this,MainActivity.class));*/
			
			 if (!connected) {
	             
	             if (!ip1.equals("")) {
	                 Thread cThread = new Thread(new ClientThread());
	                 cThread.start();
	             }
	         }
	    }
    }
    
     public class ClientThread implements Runnable {
      	
      	String send="";
      	String line;
	        public void run() {
	            try {
	            	
	                Log.d("SETWEP", "C: Connecting...");
	               
	                Socket socket = new Socket(ip1,port);
	               
	                connected = true;
	                
	                send = "KEY|"+wepkey;
	                
	                System.out.println("WEPKEY "+send);
	                
	                
	               if(connected)
	                {
	                	try {
	                        Log.d("SETWEP", "C: Sending command.");
	                       
	                        
	                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
	                                    .getOutputStream())), true);
	                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                          
	                      
	                            out.println(send);
	                         
	                           
	                            line = in.readLine();
	                           
	        	                System.out.println("Received "+line);
	        	               
	        	               
	        	                
	        	              
	                            Log.d("SETWEP", "C: Sent.");
	                            out.close();
	                            in.close();
	                            socket.close();
	                    }
	                	 
	                	 catch (final SocketTimeoutException e) {
	                        Log.e("SETWEP", e.toString());
	                        runOnUiThread(new Runnable() {
        	                	  public void run()
        	                	  {
        	                	    Toast.makeText(SetWep.this, "Error"+e, Toast.LENGTH_LONG).show();
        	                	  }
        	                	});
	                    } 
	                	catch (final Exception e) {
	                        Log.e("SETWEP", "S: Error", e);
	                        runOnUiThread(new Runnable() {
    	                	  public void run()
    	                	  {
    	                	    Toast.makeText(SetWep.this, "Error"+e, Toast.LENGTH_LONG).show();
    	                	  }
    	                	});
	                    }
	                	
	                	
	                }
	                
	               
	                
	              
	                
	                runOnUiThread(new Runnable() {
	                	  public void run()
	                	  {
	                		  connected= false;
	                		 
	                	    Toast.makeText(SetWep.this, line, Toast.LENGTH_LONG).show();
	                	   
	                	    Intent intent = new Intent(SetWep.this,MainActivity.class);
	                		startActivity(intent);
	                		finish();
	                	  }
	                	});
	                
	               
	                Log.d("REGISTER", "C: Closed.");
	            }
	           
	            catch (final Exception e) {
	                Log.e("SETWEP", "C: Error", e);
	                runOnUiThread(new Runnable() {
                	  public void run()
                	  {
                	    Toast.makeText(SetWep.this, "Error:"+e, Toast.LENGTH_LONG).show();
                	  }
                	});
	                
	            }
	            
	           
	        }
	    }
}
