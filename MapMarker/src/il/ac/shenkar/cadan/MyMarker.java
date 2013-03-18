package il.ac.shenkar.cadan;

import android.content.Context;
import android.widget.ImageView;

public class MyMarker extends ImageView {

	private float xLocation;
	private float yLocation;
	private String message;
	public MyMarker(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setImageResource(R.drawable.small_marker);
	}
	public float getxLocation() {
		return xLocation;
	}
	public void setxLocation(float xLocation) {
		this.xLocation = xLocation;
	}
	public float getyLocation() {
		return yLocation;
	}
	public void setyLocation(float yLocation) {
		this.yLocation = yLocation;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
