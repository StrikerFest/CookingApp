package com.example.cookingapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookingapp.R;
import com.example.cookingapp.model.Ingredient;
import com.example.cookingapp.model.Instruction;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InstructionAdapter extends BaseAdapter {

	// Variable
	private Context context;
	private List<Instruction> listInstruction;
	private ArrayList<Instruction> arraylist;
	private TextView tvInstructionDescription;

	// Adapter constructor
	public InstructionAdapter(Context context, List<Instruction> listInstruction) {
		this.context = context;
		this.listInstruction = listInstruction;
		this.arraylist = new ArrayList<Instruction>();
		this.arraylist.addAll(listInstruction);
	}

	@Override
	public int getCount() {
		return listInstruction.size();
	}

	@Override
	public Object getItem(int position) {
		return listInstruction.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void remove(int position){
		listInstruction.remove(listInstruction.get(position));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
			convertView = LayoutInflater.from(context).inflate(R.layout.instruction_item,parent,false);

		// Find item by id
		tvInstructionDescription = convertView.findViewById(R.id.tvInstructionDescription);

		// Config strict mode to remove guard
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// Set data
		tvInstructionDescription.setText(listInstruction.get(position).getInstruction());

		notifyDataSetChanged();
		return convertView;

	}

}
