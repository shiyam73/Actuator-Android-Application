package com.example.actschedule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SyncDate extends Activity {

	String send;
	Button sync;
	private boolean connected = false;
	String ip1 = "10.0.2.2";
    int port=10002;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syncdate);
        
        sync = (Button)findViewById(R.id.button1);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
 	   //get current date time with Date()
 	   Date date = new Date();
 	   send = dateFormat.format(date);
        
        sync.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				if (!connected) {
		             
		             if (!ip1.equals("")) {
		                 Thread cThread = new Thread(new ClientThread());
		                 cThread.start();
		             }
		         }
			}
		});
        

        ip1 = (PreferenceConnector.readString(this,
				PreferenceConnector.IP, null));
        
        
        port = PreferenceConnector.readInteger(this,
				PreferenceConnector.PORT, 0);
        
   
    }
    
public class ClientThread implements Runnable {
      	
      
      	String line;
	        public void run() {
	            try {
	            	
	                Log.d("SYNC DATE", "C: Connecting...");
	               
	                Socket socket = new Socket(ip1,port);
	               
	                connected = true;
	                
	                send = "DATE|"+send;
	                
	                System.out.println("SYNC DATE"+send);
	                
	                
	               if(connected)
	                {
	                	try {
	                        Log.d("SYNC DATE", "C: Sending command.");
	                       
	                        
	                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
	                                    .getOutputStream())), true);
	                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                          
	                      
	                            out.println(send);
	                         
	                           
	                            line = in.readLine();
	                           
	        	                System.out.println("Received "+line);
	        	               
	        	               
	        	                
	        	              
	                            Log.d("SYNC DATE", "C: Sent.");
	                            out.close();
	                            in.close();
	                            socket.close();
	                    }
	                	 
	                	 catch (final SocketTimeoutException e) {
	                        Log.e("SYNC DATE", e.toString());
	                        runOnUiThread(new Runnable() {
        	                	  public void run()
        	                	  {
        	                	    Toast.makeText(SyncDate.this, "Error"+e, Toast.LENGTH_LONG).show();
        	                	  }
        	                	});
	                    } 
	                	catch (final Exception e) {
	                        Log.e("SYNC DATE", "S: Error", e);
	                        runOnUiThread(new Runnable() {
    	                	  public void run()
    	                	  {
    	                	    Toast.makeText(SyncDate.this, "Error"+e, Toast.LENGTH_LONG).show();
    	                	  }
    	                	});
	                    }
	                	
	                	
	                }
	                
	               
	                
	              
	                
	                runOnUiThread(new Runnable() {
	                	  public void run()
	                	  {
	                		  connected= false;
	                		 
	                	    Toast.makeText(SyncDate.this, line, Toast.LENGTH_LONG).show();
	                	   
	                	    Intent intent = new Intent(SyncDate.this,MainActivity.class);
	                		startActivity(intent);
	                		finish();
	                	  }
	                	});
	                
	               
	                Log.d("SYNC DATE", "C: Closed.");
	            }
	           
	            catch (final Exception e) {
	                Log.e("SYNC DATE", "C: Error", e);
	                runOnUiThread(new Runnable() {
                	  public void run()
                	  {
                	    Toast.makeText(SyncDate.this, "Error:"+e, Toast.LENGTH_LONG).show();
                	  }
                	});
	                
	            }
	            
	           
	        }
	    }
}
