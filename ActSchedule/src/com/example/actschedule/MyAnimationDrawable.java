package com.example.actschedule;
import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;
import android.graphics.drawable.Drawable;
//import android.graphics.drawable.LayerDrawable;

import android.util.Log;


public class MyAnimationDrawable extends AnimationDrawable {

	static final int numberOfFrames = 14 ;
	private volatile int duration;//its volatile because another thread will update its value
	private int currentFrame;
	private volatile int [] durationRa = new int[numberOfFrames];

	public MyAnimationDrawable() {
	    currentFrame = -1;
	}

	@Override
	public void run() {

	    int n = getNumberOfFrames();
	    currentFrame++;
	    Log.i("Progress activity","Value of current frame "+String.valueOf(currentFrame));
	    if (currentFrame >= numberOfFrames-1) {
	        currentFrame = 0;
	    }

	    selectDrawable(currentFrame);
	    scheduleSelf(this, SystemClock.uptimeMillis() +  durationRa[currentFrame]);
	    
	}

	public void setDuration(int duration)
	{
	    this.durationRa[currentFrame] =	 duration ;
	    //we have to do the following or the next frame will be displayed after the old duration
	    unscheduleSelf(this);
	    selectDrawable(currentFrame);
	    scheduleSelf(this, SystemClock.uptimeMillis()+this.durationRa[currentFrame]);
	}

}
