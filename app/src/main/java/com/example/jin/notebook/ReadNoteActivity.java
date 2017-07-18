package com.example.jin.notebook;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.litepal.crud.DataSupport;

/**
 * ReadNoteActivity
 * 查看修改笔记
 *
 * Author:jin
 * Date:2017/6/9
 */
public class ReadNoteActivity extends AppCompatActivity {

    EditText mEditTextName;
    EditText mEditTextText;
    String noteName;
    String noteText;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_note);

        final String TAG="ReadNoteActivity";

        mEditTextName=(EditText)findViewById(R.id.edit_name);
        mEditTextText=(EditText)findViewById(R.id.edit_text);
        mEditTextName.setEnabled(false);
        mEditTextText.setEnabled(false);

        refreshMessage();//根据Intent携带的信息更新UI里面的信息

        final Button mButtonEdit=(Button)findViewById(R.id.button_edit);
        final Button mButtonSave=(Button)findViewById(R.id.button_save);
        final Button mButtonCancel=(Button)findViewById(R.id.button_cancel);
        final Button mButtonDelete=(Button)findViewById(R.id.button_delete);
        final Button mButtonSaveAs=(Button)findViewById(R.id.button_saveas);

        //编辑该笔记
        mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //设置为可编辑状态
                mEditTextName.setEnabled(true);
                mEditTextText.setEnabled(true);

                //显示/隐藏按钮
                mButtonEdit.setVisibility(View.GONE);
                mButtonDelete.setVisibility(View.GONE);
                mButtonSave.setVisibility(View.VISIBLE);
                mButtonCancel.setVisibility(View.VISIBLE);
                mButtonSaveAs.setVisibility(View.VISIBLE);
            }
        });

        //删除该笔记
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(Note.class,"name = ? and text=?", noteName,noteText);
                finish();//结束该Activity
            }
        });

        //保存编辑后的笔记
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //从数据库中删除该笔记
                DataSupport.deleteAll(Note.class,"name = ? and text=?", noteName,noteText);

                Note note=new Note();
                Log.d(TAG, "Name: "+mEditTextName.getText().toString());
                Log.d(TAG, "Text: "+mEditTextText.getText().toString());
                note.setName(mEditTextName.getText().toString());
                note.setText(mEditTextText.getText().toString());
                note.save();//重新保存在数据库中
                finish();
            }
        });

        //另存为一条新的笔记
        mButtonSaveAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note=new Note();
                Log.d(TAG, "Name: "+mEditTextName.getText().toString());
                Log.d(TAG, "Text: "+mEditTextText.getText().toString());
                note.setName(mEditTextName.getText().toString());
                note.setText(mEditTextText.getText().toString());
                note.save();//存入数据库
                finish();
            }
        });

        //取消编辑该笔记
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //设置编辑框为不可编辑状态
                mEditTextName.setEnabled(false);
                mEditTextText.setEnabled(false);

                //恢复未修改的笔记信息
                mEditTextName.setText(noteName);
                mEditTextText.setText(noteText);

                //显示/隐藏按钮
                mButtonEdit.setVisibility(View.VISIBLE);
                mButtonCancel.setVisibility(View.VISIBLE);
                mButtonSave.setVisibility(View.GONE);
                mButtonSaveAs.setVisibility(View.GONE);
                mButtonCancel.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 根据intent携带的信息更新Text
     */
    private void refreshMessage(){
        intent=getIntent();
        noteName=intent.getStringExtra("NoteName");
        noteText=intent.getStringExtra("NoteText");
        mEditTextName.setText(noteName);
        mEditTextText.setText(noteText);
    }
}
