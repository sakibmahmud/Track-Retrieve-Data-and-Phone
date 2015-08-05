package com.kamrul.gps;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Delinfo extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delinfo);
		
		TextView tv = (TextView) findViewById(R.id.textView1);
		
		String di = "Write \"new folder\" (without quote) to delete the folder(or file) " +
				"named as \"new folder\" " +
				"in memory card.\n\nWrite \"new folder/info.txt\" (without quote) " +
				"to delete the file named as \"info.txt\" which is saved in \"new folder\" in memory card.\n\n" +
			    "If you want to delete any file you have specify its format(extention).\n\n" +
				"For example: if you want to delete a file which is located in MemoryCard, in " +
				"folder1\\folder2\\document1.docx then you should write: \n\n" +
				"\"folder1\\folder2\\document1.docx\"\n\n(Dont put the quotation mark)" +
				"\n\nWarning! \nOnce you delete, you cannt get it back.";
		
		tv.setText(di);
		
		
	}}