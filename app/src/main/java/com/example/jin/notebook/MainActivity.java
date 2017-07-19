package com.example.jin.notebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity
 * 包含创建新笔记按钮
 * 搜索笔记功能
 * 显示所有笔记
 */
public class MainActivity extends AppCompatActivity {

    Button mButtonCreate;
    EditText mEditTextKeyword;
    Button mButtonSearch;
    Button mButtonCancle;
    ListView listView;

    ArrayList<Note> displayList=new ArrayList<>();//在ListView中显示的List
    ArrayList<Note> noteList=new ArrayList<>();//所有笔记组成的List
    ArrayList<Note> searchList=new ArrayList<>();//符合搜索条件的笔记组成的List
    String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate");

        Connector.getDatabase();//创建数据库


        mButtonCreate=(Button)findViewById(R.id.button_create);
        mEditTextKeyword=(EditText)findViewById(R.id.textview_keyword);
        mButtonSearch=(Button)findViewById(R.id.button_search);
        mButtonCancle=(Button)findViewById(R.id.button_cancel);
        listView=(ListView)findViewById(R.id.list_view);

        mEditTextKeyword.setText("");//清空搜索框

        //创建一条新笔记
        mButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CreateNoteActivity.class);
                startActivity(intent);
            }
        });

        readSql();//遍历数据库
        refreshList();//刷新显示List

        //搜索
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWord=mEditTextKeyword.getText().toString();//获取搜索关键字

                //清空列表
                displayList.clear();
                searchList.clear();

                mButtonCancle.setVisibility(View.VISIBLE);

                //查数据库
                readSql();
                for(Note note:noteList){
                    if (note.getName().indexOf(keyWord)!=-1 ||note.getText().indexOf(keyWord)!=-1){
                        searchList.add(note);//存入搜索结果
                        displayList.add(note);//添加到显示列表
                        Log.d(TAG, "searchName: "+note.getName());
                    }
                }
                //将搜索结果显示到ListView
                noteList.clear();
                noteList=(ArrayList)searchList.clone();
                refreshList();
            }
        });

        mButtonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清空列表
                displayList.clear();
                searchList.clear();

                mButtonCancle.setVisibility(View.GONE);

                //查数据库
                readSql();
                mEditTextKeyword.setText("");
                refreshList();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note=noteList.get(position);

                Intent intent=new Intent(MainActivity.this,ReadNoteActivity.class);
                Log.d(TAG, "onItemClick: ClickName"+note.getName());
                Log.d(TAG, "onItemClick: ClickText"+note.getText());
                intent.putExtra("NoteName", note.getName());
                intent.putExtra("NoteText",note.getText());
                startActivity(intent);
            }
        });
    }

    /**
     * 遍历数据库
     */
    private void readSql(){
        noteList.clear();
        List<Note> notes= DataSupport.findAll(Note.class);
        for(Note note:notes){
            noteList.add(note);
        }
    }

    /**
     * 遍历noteList列表
     * 将所有note的name置入nameList
     * 将nameList显示在ListView中
     */
    private void refreshList(){
        displayList.clear();
        for (Note item:noteList){
            Note note=new Note();
            note.setName(item.getName());
            if (item.getText().length()>41){
                note.setText(item.getText().substring(0,41)+"...");
            }else{
                note.setText(item.getText());
            }
            displayList.add(note);
        }
        NoteAdapter adapter=new NoteAdapter(MainActivity.this,R.layout.note_item,displayList);
        listView.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        mEditTextKeyword.setText("");
        mButtonCancle.setVisibility(View.GONE);
        readSql();
        refreshList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
        mEditTextKeyword.setText("");
        mButtonCancle.setVisibility(View.GONE);
        readSql();
        refreshList();
    }
}
