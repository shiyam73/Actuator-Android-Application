package com.example.actschedule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class OpenValve extends Activity {

	Button register;
	private boolean connected = false;
	String ip1 = "10.0.2.2";
    int port=10002;
    
    int existsint;
    int caughtAnExceptionOpeningInputStreamint ;
    int caughtAnExceptionOpeningOutputStreamint ;
    int resolvedNameToAddressint ;
  
    private MyAnimationDrawable myAnimDraw;
	ProgressThread progThread;
    ProgressDialog progDialog;
    Dialog pDialog;
    Button button1, button2;
	int typeBar;                        // Determines type progress bar: 0 = spinner, 1 = horizontal
	int delay = 40;                   // Milliseconds of delay in the update loop
	int maxBarValue = 20;      // Maximum value of horizontal progress bar
	InputStream nis; //Network Input Stream
    OutputStream nos;
    Bitmap mOriginalBitmap;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openvalve); 
        

        ip1 = (PreferenceConnector.readString(this,
				PreferenceConnector.IP, null));
        
        
        port = PreferenceConnector.readInteger(this,
				PreferenceConnector.PORT, 0);
        
        myAnimDraw = new MyAnimationDrawable();
        // Random r = new Random();
       //setContentView(myAnimDraw);
       // LayerDrawable ld = (LayerDrawable) getResources().getDrawable(R.drawable.progress_bar_states);
       // Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.pmeter);
         
        // Process button to start spinner progress dialog with anonymous inner class
        button1 = (Button) findViewById(R.id.Button01);
        button1.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
            	typeBar = 0;
            	Log.i("Progress activity", "Just clicked spinning prog");
                showDialog(typeBar);
            }
        }); 
        
        /*if (!connected) {
            //serverIpAddress = serverIp.getText().toString();
            if (!ip1.equals("")) {
                Thread cThread = new Thread(new ClientThread());
                cThread.start();
            }
        }*/
        
       /* int iW = 300;
        int iH = 200;
         //mOriginalBitmap = new Bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //Load image from resource
        mOriginalBitmap = BitmapFactory.decodeResource(getResources(), 
                R.drawable.mywatermeter2, options);
        //Scale to target size
        mOriginalBitmap = Bitmap.createScaledBitmap(mOriginalBitmap, iW, iH, true);*/
    }
    
   /* public Bitmap reduce(int icon)
    {
    	int iW = 300;
        int iH = 200;
         //mOriginalBitmap = new Bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //Load image from resource
        mOriginalBitmap = BitmapFactory.decodeResource(getResources(),icon, options);
        //Scale to target size
        mOriginalBitmap = Bitmap.createScaledBitmap(mOriginalBitmap, iW, iH, true);
        
        
        
        return mOriginalBitmap;
    }*/
    
    // Method to create a progress bar dialog of either spinner or horizontal type
    @Override
    protected Dialog onCreateDialog(int id) {
      switch(id) {
        case 0:    pDialog = new Dialog(this);
    	pDialog.setContentView(R.layout.custommsg);
        pDialog.setTitle("Turning on water.....");
        
    	TextView text = (TextView) pDialog.findViewById(R.id.text);
    	text.setText("Loading please wait.......");
    	progThread = new ProgressThread(handler);
        progThread.start();
        return pDialog;
    default:
        return null;
      }
    }
    
    // Handler on the main (UI) thread that will receive messages from the 
    // second thread and update the progress.
    
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
        	// Get the current value of the variable total from the message data
        	// and update the progress bar.
            int total = msg.getData().getInt("total");
            //pDialog.setProgress(total);
           
           
            
            if (total <= 0){
                dismissDialog(typeBar);
                 progThread.setState(ProgressThread.RESUME);
                 
                
                 
                 // new frames Dec 28 2012 BA
               //  Drawable drawable = new BitmapDrawable(reduce(R.drawable.mywatermeter2));
                 //Drawable drawable = new BitmapDrawable(mOriginalBitmap);
                 Drawable drawable = getResources().getDrawable(R.drawable.mywatermeter2);
             	myAnimDraw.addFrame(getResources().getDrawable(R.drawable.mywatermeter22), 2000);
             	myAnimDraw.run();
             	myAnimDraw.setDuration(216000);
             	
             	/*if (drawable instanceof BitmapDrawable) {
             	    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
             	    Bitmap bitmap = bitmapDrawable.getBitmap();
             	    bitmap.recycle();
             	}*/
             	
             	myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr10_export12), 2000);
             	myAnimDraw.run();
             	myAnimDraw.setDuration(216000);
             	
             	myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr20_export12), 2000);
                myAnimDraw.run();
             	myAnimDraw.setDuration(216000);
      
             	/*myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr30_export1), 2000);
             	myAnimDraw.run();             	
             	myAnimDraw.setDuration(216000);
             	myAnimDraw.setOneShot(false);*/
             	
             	
             	myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr40_export12), 2000);
             	myAnimDraw.run();             	
             	myAnimDraw.setDuration(216000);
             	myAnimDraw.setOneShot(false);
             	
             	myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr50_export12), 2000);
             	myAnimDraw.run();             	
             	myAnimDraw.setDuration(216000);
             	myAnimDraw.setOneShot(false);
             	
             	/*myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr60_export1), 2000);
             	myAnimDraw.run();             	
             	myAnimDraw.setDuration(216000);
             	myAnimDraw.setOneShot(false);*/
             	
             	myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr70_export12), 2000);
             	myAnimDraw.run();             	
             	myAnimDraw.setDuration(216000);
             	myAnimDraw.setOneShot(false);
             	
             	myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr80_export1), 2000);
             	myAnimDraw.run();             	
             	myAnimDraw.setDuration(216000);
             	myAnimDraw.setOneShot(false);
             	
             	/*myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr90_export1), 2000);
             	myAnimDraw.run();             	
             	myAnimDraw.setDuration(216000);
             	myAnimDraw.setOneShot(false);*/
             	
             	myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr100_export12), 2000);
             	myAnimDraw.run();             	
             	myAnimDraw.setDuration(216000);
             	myAnimDraw.setOneShot(false);
             	
             	myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr110_export12), 2000);
             	myAnimDraw.run();             	
             	myAnimDraw.setDuration(216000);
             	myAnimDraw.setOneShot(false);
             	
             	myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr120_export12), 2000);
             	myAnimDraw.run();             	
             	myAnimDraw.setDuration(216000);
             	myAnimDraw.setOneShot(false);
             	
             	myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr130_export12), 2000);
             	myAnimDraw.run();             	
             	myAnimDraw.setDuration(216000);
             	myAnimDraw.setOneShot(false);
             	
             	myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr140_export12), 2000);
             	myAnimDraw.run();             	
             	myAnimDraw.setDuration(216000);
             	myAnimDraw.setOneShot(false);
             	
             	myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr150_export12), 2000);
             	myAnimDraw.run();             	
             	myAnimDraw.setDuration(216000);
             	myAnimDraw.setOneShot(false);
             	
             	myAnimDraw.addFrame(getResources().getDrawable(R.drawable.watermeterr160_export12), 2000);
             	myAnimDraw.run();             	
             	myAnimDraw.setDuration(216000);
             	myAnimDraw.setOneShot(false);
             	
             	
             	
             	pDialog.setContentView(R.layout.customprogressdialog);
                pDialog.setTitle("Turning on water. Please wait.....");
                 
             	TextView text = (TextView) pDialog.findViewById(R.id.text);
             	text.setText("Loading.....!");
             	ImageView image = (ImageView) pDialog.findViewById(R.id.image);
             	image.setImageDrawable(myAnimDraw);
             	showDialog(0);
                
                  
            }
        }
    };
    
    // Inner class that performs progress calculations on a second thread.  Implement
    // the thread by subclassing Thread and overriding its run() method.  Also provide
    // a setState(state) method to stop the thread gracefully.
    
    private class ProgressThread extends Thread {	
    	
    	// Class constants defining state of the thread
    	final static int DONE = 0;
        final static int RUNNING = 1;
        final static int RESUME = 2 ;
        boolean exists=false;
        boolean caughtAnExceptionOpeningInputStream ;
        boolean caughtAnExceptionOpeningOutputStream ;
        boolean resolvedNameToAddress ;
        
        Socket socket;
        BufferedReader in;
        PrintWriter out;
          
        Handler mHandler;
        int mState;
        int total;
        int triedSocketStreamCnt = 0;
        // Constructor with an argument that specifies Handler on main thread
        // to which messages will be sent by this thread.
        
        ProgressThread(Handler h) {
            mHandler = h;
            exists=false;
            caughtAnExceptionOpeningInputStream=true;
            caughtAnExceptionOpeningOutputStream = true;
            resolvedNameToAddress = false;
            
            existsint=0;
            caughtAnExceptionOpeningInputStreamint=1;
            caughtAnExceptionOpeningOutputStreamint=1;
            resolvedNameToAddressint=0;

        }
        
        // Override the run() method that will be invoked automatically when 
        // the Thread starts.  Do the work required to update the progress bar on this
        // thread but send a message to the Handler on the main UI thread to actually
        // change the visual representation of the progress. In this example we count
        // the index total down to zero, so the horizontal progress bar will start full and
        // count down.
        
        @Override
        public void run() {
            mState = RUNNING;
            total = maxBarValue;
            
            while (mState == RUNNING || mState == RESUME) {
            	
            	System.out.println("Inside while");
            	// The method Thread.sleep throws an InterruptedException if Thread.interrupt() 
            	// were to be issued while thread is sleeping; the exception must be caught.
                try {
                	// Control speed of update (but precision of delay not guaranteed)
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Log.e("ERROR", "Thread was Interrupted");
                }
                
                // Send message (with current value of  total as data) to Handler on UI thread
                // so that it can update the progress bar.
               if (mState == RUNNING) {
            	   System.out.println("Inside if");
                Message msg = mHandler.obtainMessage();
                Bundle b = new Bundle();
                b.putInt("total", total);
                msg.setData(b);
                mHandler.sendMessage(msg);
                Log.i("Progress activity total" , String.valueOf(total));
                total--;      // Count down
               } else { // RESUME
            	  //Log.i("Progress activity","Here in resume");
            	  
            	   try {	
                  
            		   System.out.println("Inside elkse");
                     int receivedFromServer = 0 ;
                    
                      Log.i("Progress Activity","We got past nis read of 4096 bytes..may be we should find a way to get out if server is not sending anything");
                      connected = true;
                      
   	                  
   	                	  
                      socket = new Socket(ip1,port);
   	                  
   	               if(connected)
   	               {  
	                	
   	            	System.out.println("Inside connection");
	                         out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
	                                    .getOutputStream())), true);
	                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        
                       
	                          
	                            out.println("MESS");
	                       
	                        
	                            String line = in.readLine();
	                           
	        	               
	        	                receivedFromServer+=1;
	        	                System.out.println("Received "+line+"RS "+receivedFromServer);
	        	                int rec = Integer.parseInt(line);
	        	                myAnimDraw.setDuration(1);
	        	                
	        	                if (rec ==12) {
	                          	 
	        	                	System.out.println("Inside if if");
	                          	  myAnimDraw.setDuration(216000);
	                          	  out.close();
	                          	  in.close();
	                          	socket.close();
	                          	connected = false;
	                    		      dismissDialog(typeBar);
	                                progThread.setState(ProgressThread.DONE);
	                                Constants.open=1;
	                                runOnUiThread(new Runnable() {
	                     	         	  public void run()
	                     	         	  {
	                     	         		 
	                     	         		
	                     	         	    Toast.makeText(OpenValve.this, "Valve Opened", Toast.LENGTH_LONG).show();
	                     	         	   
	                     	         	    Intent intent = new Intent(OpenValve.this,MainActivity.class);
	                     	         		startActivity(intent);
	                     	         		onDestroy();
	                     	         		finish();
	                     	         	  }
	                     	         	});
	                          	  
	                            }
	                           
	        	                
	                       
	                         
	                }
                    
   	           
                    	 
                      }catch(Exception e){
                      	   Log.i("Progress Activity","We are here because we failed to read from the input stream. This happens because the other side (server/water turn off/on actuator) has nothing to send.Read input stream exception:"+e);
                      	   Log.i("Progress Activity","We simply log the error message (unable to obtain anything from the input stream) and cycle again");
                      	   Log.i("Progress Activity","During this time we take a break for about 3 seconds");
                      	   try {
                         	// Control speed of update (but precision of delay not guaranteed)
                             Thread.sleep(3000);
                           } catch (InterruptedException ee) {
                             Log.e("ERROR", "Thread was Interrupted II.."+ee);
                           }
                      }
                     } 
                    } 
                   } // sock stream may be closed when we reach this point
         
            
        
   
        
        // Set current state of thread (use state=ProgressThread.DONE to stop thread)
        public void setState(int state) {
            mState = state;
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
        super.onDestroy();
        System.out.println("In destroy open");
        
       button1.setOnClickListener(null);
       
       Drawable drawable = getResources().getDrawable(R.drawable.mywatermeter22);
       destroy(drawable);
       drawable = getResources().getDrawable(R.drawable.watermeterr10_export12);
       destroy(drawable);
       drawable = getResources().getDrawable(R.drawable.watermeterr20_export12);
       destroy(drawable);
       drawable = getResources().getDrawable(R.drawable.watermeterr40_export12);
       destroy(drawable);
       drawable = getResources().getDrawable(R.drawable.watermeterr50_export12);
       destroy(drawable);
       drawable = getResources().getDrawable(R.drawable.watermeterr70_export12);
       destroy(drawable);
       drawable = getResources().getDrawable(R.drawable.watermeterr80_export12);
       destroy(drawable);
       drawable = getResources().getDrawable(R.drawable.watermeterr100_export12);
       destroy(drawable);
       drawable = getResources().getDrawable(R.drawable.watermeterr110_export12);
       destroy(drawable);
       drawable = getResources().getDrawable(R.drawable.watermeterr120_export12);
       destroy(drawable);
       drawable = getResources().getDrawable(R.drawable.watermeterr130_export12);
       destroy(drawable);
       drawable = getResources().getDrawable(R.drawable.watermeterr140_export12);
       destroy(drawable);
       drawable = getResources().getDrawable(R.drawable.watermeterr150_export12);
       destroy(drawable);
       drawable = getResources().getDrawable(R.drawable.watermeterr160_export12);
       destroy(drawable);
       
        unbindDrawables(findViewById(R.id.openvalve));
        System.gc();
        Runtime.getRuntime().gc();
    }
     
    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
    
    public void destroy(Drawable drawable)
    {
    	if (drawable instanceof BitmapDrawable) {
     	    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
     	    Bitmap bitmap = bitmapDrawable.getBitmap();
     	    bitmap.recycle();
     	    bitmap=null;
     	}
    	
    }
}
