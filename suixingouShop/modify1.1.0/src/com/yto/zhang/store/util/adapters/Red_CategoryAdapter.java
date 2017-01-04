package com.yto.zhang.store.util.adapters;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import com.yto.suixingouuser.util.MyUtils;
public abstract class Red_CategoryAdapter extends BaseAdapter {
	Context context;
    private List<Cshop_Category> categories = new ArrayList<Cshop_Category>();  
    public void addCategory(String title, Adapter adapter,Context context) {  
        categories.add(new Cshop_Category(title, adapter));
        this.context=context;
    }  
    
    /**
     * 移除掉指定的adapter
     * @param cid
     */
    public void removeCateGory(List<Cshop_Category> catego)
    {
    	categories=catego;
    	notifyDataSetChanged();
    }
    public void removeCateGory(List<Cshop_Category> catego,String title)
    {
    	Cshop_Category category=null;
    	if (catego!=null) {
        	categories=catego;
        	notifyDataSetChanged();
		}else
		{
			for (Cshop_Category myShopAdapter : categories) {
				if (myShopAdapter.getTitle().equals(title)) {
					//说明两个adapter一样
					category=myShopAdapter;
					
				}
			}
			categories.remove(category);
			notifyDataSetChanged();
		}

    }
    @Override  
    public int getCount() {  
        int total = 0;  
          
        for (Cshop_Category category : categories) {  
            total += category.getAdapter().getCount() + 1;  
        }  
          
        return total;  
    }  
    @Override  
    public Object getItem(int position) {  
        for (Cshop_Category category : categories) {  
            if (position == 0) {  
                return category;  
            }  
              
            int size = category.getAdapter().getCount() + 1;  
            if (position < size) {  
                return category.getAdapter().getItem(position-1);  
            }  
            position -= size;  
        }  
          
        return null;  
    }  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
      
    public int getViewTypeCount() {  
        int total = 1;  
          
        for (Cshop_Category category : categories) {  
            total += category.getAdapter().getViewTypeCount();  
        }  
          
        return total;  
    }  
    
    public int getItemViewType(int position) {  
        int typeOffset = 1;  
          
        for (Cshop_Category category : categories) {  
            if (position == 0) {  
                return 0;  
            }  
              
            int size = category.getAdapter().getCount() + 1;  
            if (position < size) {  
                return typeOffset + category.getAdapter().getItemViewType(position - 1);  
            }  
            position -= size;  
              
            typeOffset += category.getAdapter().getViewTypeCount();  
        }  
          
        return -1;  
    }  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {
    	MyUtils.logd("position="+position);
        int categoryIndex = 0;  
        for (Cshop_Category category : categories) {  
            if (position == 0) {  
            	//标题view
//            	 Intent intent2=new Intent(MyBrcastAction.CHANGECOLOR);
//            	 intent2.putExtra("cid", categoryIndex);
//            	 context.sendBroadcast(intent2);
                return getTitleView(category.getTitle(), categoryIndex,convertView, parent); 
            }  
            int size = category.getAdapter().getCount()+1;  
            if (position < size) {
            	//内容view
                return category.getAdapter().getView(position - 1, convertView, parent);
            }  
            position -= size;  
            categoryIndex++;  
        }  
          
        return null;  
    }  
      
    protected abstract View getTitleView(String caption,int index,View convertView,ViewGroup parent);  
      
} 