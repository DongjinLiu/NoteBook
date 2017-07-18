package com.example.jin.notebook;

import org.litepal.crud.DataSupport;

/**
 * Created by jin on 2017/5/19.
 *
 * Note数据库类
 * 用来创建数据库
 */

public class Note extends DataSupport{

    private String name;

    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
