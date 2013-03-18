package il.ac.shenkar.cadan;

import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Main extends Activity {
	private ImageView deleteIcon=null;
	private MyMarker currentMarker=null;
	private TextView textBox=null;
	private EditText editTextView=null;
	private OnClickListener markerListener=null;
	private List<MyMarker> markerList; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(this);
		//inflate the main view
		final RelativeLayout mainView = (RelativeLayout)inflater.inflate(R.layout.main, null) ;
		setContentView(mainView);
		//get the text box view
		textBox= (TextView) findViewById(R.id.textView1);
		//set him to be invisible
		textBox.setVisibility(View.INVISIBLE);
		final SurfaceView s=new SurfaceView(this);
		//find the delete icon ImageView
		deleteIcon=(ImageView) findViewById(R.id.delete_icon);
		//set him to be invisible
		deleteIcon.setVisibility(View.INVISIBLE);
		//add to the delete icon onClick listener
		deleteIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//hide the current marker- its the one we want to delete
				currentMarker.setVisibility(View.INVISIBLE);
				//remove him from the marker list
				markerList.remove(currentMarker);
				//set the current marker to be null
				currentMarker=null;
				//set the text box to be invisble
				textBox.setVisibility(View.INVISIBLE);
				//hide the edit text box
				editTextView.setVisibility(View.INVISIBLE);
				//hide the delete icon
				deleteIcon.setVisibility(View.INVISIBLE);
				
			}
		});
		//find the edit text view
		editTextView=(EditText) findViewById(R.id.edit_text);
		//set him to be invisible
		editTextView.setVisibility(View.INVISIBLE);
		//create a text watcher to the edit text view
		//this watcher will listen for any text change in the
		// edit text view and will update the current marker in the 
		//change of the text
		final TextWatcher tw=new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				String message=editTextView.getText().toString();
				//set the message on the current marker
				currentMarker.setMessage(message);
				//call to onClick of the marker listener
				markerListener.onClick(currentMarker);
				
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
		};
		//add the text watcher to the edit text 
		editTextView.addTextChangedListener(tw);
		//create the marke list
		markerList= new LinkedList<MyMarker>();
		//set onClic listener to marker 
		markerListener=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//the marker that was clicked
				MyMarker m=(MyMarker) v;
				//update the current marker to the one we chose
				currentMarker=m;
				//show the delete icon	
				deleteIcon.setVisibility(View.VISIBLE);
				//set the text that was set to the marker in the edit text box
				textBox.setText(m.getMessage());
				//crete layout parameter object to the textView of the marker 
				RelativeLayout.LayoutParams p= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
				//set the margin parameters in order to locate the textBox near the marke
				p.topMargin=((RelativeLayout.LayoutParams)m.getLayoutParams()).topMargin+75;
				p.leftMargin=((RelativeLayout.LayoutParams)m.getLayoutParams()).leftMargin;
				textBox.setLayoutParams(p);
				//show the edit text view
				editTextView.setVisibility(View.VISIBLE);
				//must do in in order to prevent loop
				editTextView.removeTextChangedListener(tw);
				//clear the text in 
				editTextView.setText("");
				int messageLength=m.getMessage().length();
			/*	if(messageLength%8==0&&messageLength!=0)
				{
					m.setMessage(m.getMessage()+'\n');
				}
				*/
				//set the updated  message
				editTextView.append(m.getMessage());
				//show the text box
				textBox.setVisibility(View.VISIBLE);
				//add the text watcher again
				editTextView.addTextChangedListener(tw);
			}
		};
		GestureDetector.OnGestureListener gs=new GestureDetector.SimpleOnGestureListener()
		{

			@Override
			public boolean onDoubleTap(MotionEvent e) {
				// TODO Auto-generated method stub
				return super.onDoubleTap(e);
			}

			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				super.onLongPress(e);
				float x,y;
				x=e.getRawX();
				y=e.getRawY();
				
				MyMarker m=new MyMarker(Main.this);
				//m.setxLocation(x);
				//m.setY(y);
				//m.setLayoutParams(params)
				
				int width = 80;
				int height = 80;
			
				
				RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(width, height);
				int x1,x2,y1,y2;
				x2=(int)e.getX()+mainView.getTop();
				y2=(int)e.getY()+mainView.getLeft();
				x1=(int) e.getX();
				m.setPadding(0,0,0,0);
				y1=(int) e.getY();
				rlp.leftMargin=x2-50;
				rlp.topMargin=y2-50;
				m.setVisibility(View.VISIBLE);
				m.setLayoutParams(rlp);
				mainView.addView(m);
				m.setOnClickListener(markerListener);
				currentMarker=m;
				editTextView.setText("");
				editTextView.setVisibility(View.VISIBLE);
				markerListener.onClick(m);
				//textBox.setVisibility(View.VISIBLE);
				//textBox.setText("("+x1+","+y1+")"+"-"+"("+x2+","+y2+")");
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				//return super.onSingleTapUp(e);
				editTextView.setVisibility(View.INVISIBLE);
				return true;
			}
			
		};
		final GestureDetector gd= new GestureDetector(Main.this,gs);
		mainView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{ 
				gd.onTouchEvent(event);
				return true;
			}
		});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
