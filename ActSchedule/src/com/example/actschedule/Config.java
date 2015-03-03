package com.example.actschedule;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Config extends Activity {

	EditText ip,port,slp;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		init();
    }
    
    private void init() {
		ip = (EditText) findViewById(R.id.editTextName);
		port = (EditText) findViewById(R.id.editTextSurName);
		port.setInputType(InputType.TYPE_CLASS_NUMBER);
		slp = (EditText) findViewById(R.id.editSlp);
		slp.setInputType(InputType.TYPE_CLASS_NUMBER);
		
		readPerson();
	}

	public void save(View view) {
		String ipText = ip.getText().toString();
		String portText = port.getText().toString();
		String slpText = slp.getText().toString();
		
		if (ipText != null)
			PreferenceConnector.writeString(this, PreferenceConnector.IP,
					ipText);
		if (portText != null && !portText.equals(""))
			PreferenceConnector.writeInteger(this, PreferenceConnector.PORT,
					Integer.parseInt(portText));
		if (slpText != null && !slpText.equals(""))
			PreferenceConnector.writeInteger(this, PreferenceConnector.SLP,
					Integer.parseInt(slpText));
		
		Toast.makeText(Config.this, "Data Saved", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(Config.this,MainActivity.class);
		startActivity(intent);
	}

	public void reset(View view) {
		/* A better way to delete all is:
		 * PreferenceConnector.getEditor(this).clear().commit();
		 */
		PreferenceConnector.getEditor(this).remove(PreferenceConnector.IP)
				.commit();
		PreferenceConnector.getEditor(this).remove(PreferenceConnector.PORT)
				.commit();
		PreferenceConnector.getEditor(this).remove(PreferenceConnector.SLP)
		.commit();
		
		readPerson();
	}

	/*
	 * Read the data refer to saved person and visualize them into Edittexts
	 */
	private void readPerson() {
		ip.setText(PreferenceConnector.readString(this,
				PreferenceConnector.IP, null));
		
		String portPref = ""
				+ PreferenceConnector.readInteger(this,
						PreferenceConnector.PORT, 0);
		port.setText((portPref.equals("0")) ? null : portPref);
		String slpPref = ""
				+ PreferenceConnector.readInteger(this,
						PreferenceConnector.SLP, 0);
		slp.setText((slpPref.equals("0")) ? null : slpPref);
		
	}
}
