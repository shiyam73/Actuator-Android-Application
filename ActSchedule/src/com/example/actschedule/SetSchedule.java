package com.example.actschedule;





import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SetSchedule extends Activity {
	private TextView mDateDisplay;
    private Button mPickDate;
    
    
    private TextView mDateDisplay1;
    private Button mPickDate1;
    
    private TextView mTimeDisplay;
    private Button mPickTime;
    
    
    private TextView mTimeDisplay1;
    private Button mPickTime1;
    
    private Button send;

    
    
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHr;
    private int mMin;
    
    
    
    String from="";
    String end="";
    String fromTime="";
    String endTime="";
    String ip="";
    String ip1 = "10.0.2.2";
    int port=10002;
    String line="";
    String actIdText;
        
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    
    
    private boolean connected = false;

    

    static final int DATE_DIALOG_ID = 0;
    static final int DATE_DIALOG_ID1 = 1;
    static final int TIME_DIALOG_ID = 2;
    static final int TIME_DIALOG_ID1 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setschedule);


        ip1 = (PreferenceConnector.readString(this,
				PreferenceConnector.IP, null));
        
        
        port = PreferenceConnector.readInteger(this,
				PreferenceConnector.PORT, 0);
        
        // capture our View elements
        mDateDisplay = (TextView) findViewById(R.id.fromDisplay);
        mPickDate = (Button) findViewById(R.id.fromDate);
        
        mDateDisplay1 = (TextView) findViewById(R.id.endDisplay);
        mPickDate1 = (Button) findViewById(R.id.endDate);
        
        mTimeDisplay = (TextView) findViewById(R.id.ftDisplay);
        mPickTime = (Button) findViewById(R.id.fromTime);
        
        mTimeDisplay1 = (TextView) findViewById(R.id.etDisplay);
        mPickTime1 = (Button) findViewById(R.id.endTime);
        
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(connectListener);
        
       
        
              
        // add a click listener to the date
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        mPickDate1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID1);
            }
        });
        
        //add a click listener to the time
        mPickTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
        
        mPickTime1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID1);
            }
        });
        
        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        //mDow = c.get(Calendar.DAY_OF_WEEK);
        
        //get the current time
        mHr = c.get(Calendar.HOUR_OF_DAY);
        mMin = c.get(Calendar.MINUTE);
        
        // display the current date
        updateDisplay();
        updateDisplay1();
        
        updateTimeDisplay();
        updateTimeDisplay1();
        
       
        
    }
    
    private OnClickListener connectListener = new OnClickListener() {

        public void onClick(View v) {
        	
        	
        	
            if (!connected) {
                //serverIpAddress = serverIp.getText().toString();
                if (!ip1.equals("")) {
                    Thread cThread = new Thread(new ClientThread());
                    cThread.start();
                }
            }
            
            }
      };
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        case DATE_DIALOG_ID1:
            return new DatePickerDialog(this,
                        mDateSetListener1,
                        mYear, mMonth, mDay);
        case TIME_DIALOG_ID:
            return new TimePickerDialog(this,
                        t,
                        mHr, mMin,true);
        case TIME_DIALOG_ID1:
            return new TimePickerDialog(this,
                        t1,
                        mHr, mMin,true);
        }
        return null;
    }
    
    // updates the date we display in the TextView
    private void updateDisplay() {
        mDateDisplay.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("-")
                    .append(mDay).append("-")
                    .append(mYear).append(" "));
        
        Calendar cal = new GregorianCalendar(mYear, mMonth, mDay);
        int mDow = cal.get(Calendar.DAY_OF_WEEK);
        if(mDow==-1)
        	mDow=0;

        
        from = mMonth + 1 +"-"+ mDay + "-" + mYear + "-" + mDow;
      
    }
    
    private void updateDisplay1() {
        mDateDisplay1.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("-")
                    .append(mDay).append("-")
                    .append(mYear).append(" "));
        
        Calendar cal = new GregorianCalendar(mYear, mMonth, mDay);
        int mDow = cal.get(Calendar.DAY_OF_WEEK)-1;
        if(mDow==-1)
        	mDow=0;
        end = mMonth + 1 +"-"+ mDay + "-" + mYear + "-" + mDow;
       
    }
    
    // updates the time we display in the TextView
    private void updateTimeDisplay() {
        mTimeDisplay.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mHr).append("-")
                    .append(mMin).append(" "));
        fromTime = mHr +"-"+ mMin;
      
    }
    
    private void updateTimeDisplay1() {
        mTimeDisplay1.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
            		.append(mHr).append("-")
            		.append(mMin).append(" "));
        endTime = mHr +"-"+ mMin;
       
    }
    
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
            
     private DatePickerDialog.OnDateSetListener mDateSetListener1 =
                    new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, 
                                              int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            updateDisplay1();
                        }
                    };
     
     // the callback received when the user "sets" the date in the dialog
                    private TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
                    	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    		mHr = hourOfDay;
                    		mMin = minute;
                    		updateTimeDisplay();
                    	}
                    	};
                            
                    	private TimePickerDialog.OnTimeSetListener t1 = new TimePickerDialog.OnTimeSetListener() {
                        	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        		mHr = hourOfDay;
                        		mMin = minute;
                        		updateTimeDisplay1();
                        	}
                        	};
                        
                    
                    public class ClientThread implements Runnable {
                    	
                    	String send="";
                    	
                    	
            	        public void run() {
            	            try {
            	            	
            	                Log.d("SETSCHEDULE", "C: Connecting...");
            	               
            	                Socket socket = new Socket(ip1,port);
            	               
            	                connected = true;
            	                System.out.println("From "+from+" End "+end+"  "+fromTime+"   "+endTime);
            	                
            	                send = "SCHEDULE|"+from + "-" + fromTime + "|" + end + "-" + endTime;
            	                
            	                System.out.println("FINAL "+send);
            	                
            	               if(connected)
            	                {
            	                	try {
            	                        Log.d("SETSCHEDULE", "C: Sending command.");
            	                       
            	                        
            	                        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket
            	                                    .getOutputStream())), true);
            	                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                        line = null;
                                       
                                      
            	                            
            	                            out.println(send);
            	                           
            	                            line = in.readLine();
            	                           
            	        	                System.out.println("Received "+line);
            	        	                
            	        	               
            	                            Log.d("SETSCHEDULE", "C: Sent.");
            	                            out.close();
            	                            in.close();
            	                            socket.close();
            	                    }
            	                	 
            	                	 catch (final SocketTimeoutException e) {
              	                        Log.e("SETSCHEDULE", e.toString());
              	                        runOnUiThread(new Runnable() {
                      	                	  public void run()
                      	                	  {
                      	                	    Toast.makeText(SetSchedule.this, "Error"+e, Toast.LENGTH_LONG).show();
                      	                	  }
                      	                	});
              	                    } 
            	                	catch (final Exception e) {
            	                        Log.e("SETSCHEDULE", "S: Error", e);
            	                        runOnUiThread(new Runnable() {
                  	                	  public void run()
                  	                	  {
                  	                	    Toast.makeText(SetSchedule.this, "Error"+e, Toast.LENGTH_LONG).show();
                  	                	  }
                  	                	});
            	                    }
            	                	
            	                	
            	                }
            	                
            	               
            	                
            	              
            	                
            	                runOnUiThread(new Runnable() {
            	                	  public void run()
            	                	  {
            	                		  connected= false;
            	                		  //Constants.set=1;
            	                	    Toast.makeText(SetSchedule.this, line, Toast.LENGTH_LONG).show();
            	                	
            	                	    Intent intent = new Intent(SetSchedule.this,MainActivity.class);
            	                		startActivity(intent);
            	                		finish();
            	                	  }
            	                	});
            	                
            	               
            	                Log.d("SETSCHEDULE", "C: Closed.");
            	            }
            	           
            	            catch (final Exception e) {
            	                Log.e("SETSCHEDULE", "C: Error", e);
            	                runOnUiThread(new Runnable() {
          	                	  public void run()
          	                	  {
          	                	    Toast.makeText(SetSchedule.this, "Error:"+e, Toast.LENGTH_LONG).show();
          	                	  }
          	                	});
            	                
            	            }
            	            
            	           
            	        }
            	    }
                    
   
    
                    
     

					
}

