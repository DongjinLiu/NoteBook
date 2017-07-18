package com.example.jin.notebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * CreateNoteActivity
 *
 * 该活动用来创建并保存一条新的笔记
 *
 * Author:jin
 * Date:2017/6/9
 */
public class CreateNoteActivity extends AppCompatActivity {

    Button mButtonSave;
    EditText mEditTextName;
    EditText mEditTextText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        mButtonSave=(Button)findViewById(R.id.button_save);
        mEditTextName=(EditText)findViewById(R.id.edit_name);
        mEditTextText=(EditText)findViewById(R.id.edit_text);

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note=new Note();
                note.setName(mEditTextName.getText().toString());
                note.setText(mEditTextText.getText().toString());
                note.save();//将笔记保存至数据库
                finish();
            }
        });
    }
}
