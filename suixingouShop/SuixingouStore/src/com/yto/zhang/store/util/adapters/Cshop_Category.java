package com.yto.zhang.store.util.adapters;  
import android.widget.Adapter;
public class Cshop_Category {  
    private String mTitle;  
      
    private Adapter mAdapter;  
    public Cshop_Category(String title, Adapter adapter) {  
        mTitle = title;  
        mAdapter = adapter;  
    }  
      
    public void setTile(String title) {  
        mTitle = title;  
    }  
      
    public String getTitle() {  
        return mTitle;  
    }  
      
    public void setAdapter(Adapter adapter) {  
        mAdapter = adapter;  
    }  
      
    public Adapter getAdapter() {  
        return mAdapter;  
    }  
      
} 