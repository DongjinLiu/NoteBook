package com.example.jin.notebook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jin on 2017/7/19.
 */

public class NoteAdapter extends ArrayAdapter<Note> {

    private int resourceId;

    public NoteAdapter(Context context, int textViewResourceId, List<Note> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Note note=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView noteName=(TextView)view.findViewById(R.id.note_name);
        TextView noteText=(TextView)view.findViewById(R.id.note_text);
        noteName.setText(note.getName());
        noteText.setText(note.getText());
        return view;
    }
}
