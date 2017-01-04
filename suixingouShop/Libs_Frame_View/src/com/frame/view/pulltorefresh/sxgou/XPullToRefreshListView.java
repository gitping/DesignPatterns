package com.frame.view.pulltorefresh.sxgou;

import com.frame.view.R;
import com.frame.view.pulltorefresh.PullToRefreshBase;
import com.frame.view.pulltorefresh.PullToRefreshListView;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 自定义PullToRefreshListView
 * 1.添加刷新和加载更多的监听
 * 2.加载更多更改为滑动到底部自动加载更多，不需要上拉
 * 3.底部加载更多的点击事件-加载更多
 * @author ShenHua
 * 2015年8月13日下午2:06:44
 */
public class XPullToRefreshListView extends PullToRefreshListView{

	private LayoutInflater mInflater;
	private View viewFooter;
	private LoadDateListener loadDateListener;
	private boolean isLoadMore;
	private int sleepTime = 500;
	private ProgressBar sxgfooter_pb;
	private TextView sxgfooter_tv;
	private boolean isLoading;//是否在加载或者刷新的标记，不能同时刷新加载，或者多次滑到底部的多次加载
	public XPullToRefreshListView(Context context) {
		super(context);
		initListView(context);
	}

	public XPullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initListView(context);
	}

	public XPullToRefreshListView(Context context, Mode mode) {
		super(context, mode);
		initListView(context);
	}

	public XPullToRefreshListView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
		initListView(context);
	}
	
	/**
	 * 设置加载数据监听
	 */
	public void setLoadDateListener(LoadDateListener l) {
		loadDateListener = l;
	}
	
	/**
	 * 是否支持加载更多
	 * 设置支持时添加listview的footer
	 * 设置不支持时删除listview的footer
	 */
	public void isLoadMore(boolean isLoadMore){
		this.isLoadMore = isLoadMore;
		if(viewFooter != null&&!isLoadMore){
			removeViewFooter();
		}
		if(isLoadMore){
			if(viewFooter != null){
				removeViewFooter();
			}
			addSXGFooter();
		}
	}
	
	/**
	 * 设置刷新和加载更多时的等待时间，默认为500ms
	 * @param sleepTime
	 */
	public void setSleepTime(int sleepTime){
		this.sleepTime = sleepTime;
	}

	/**
	 * 设置footer布局
	 * @param resouce
	 */
	public void addViewFooter(int layout){
		viewFooter = mInflater.inflate(layout, null);
		getRefreshableView().addFooterView(viewFooter);
		//底部布局点击的监听事件
		viewFooter.setOnClickListener(new OnClickListenerImple());
		viewFooter.setClickable(false);
	}
	
	/**
	 * 设置SXGfooter布局
	 */
	public void addSXGFooter(){
		addViewFooter(R.layout.pull_to_refresh_sxgoufooter);
		sxgfooter_pb = (ProgressBar) viewFooter.findViewById(R.id.sxgfooter_pb);
		sxgfooter_tv = (TextView) viewFooter.findViewById(R.id.sxgfooter_tv);
	}
	
	/**
	 * 设置footer失败显示
	 */
	public void setFooterFail(){
		if(sxgfooter_pb != null){
			sxgfooter_pb.setVisibility(View.GONE);
		}
		if(sxgfooter_tv != null){
			sxgfooter_tv.setText("加载失败");
		}
		if(viewFooter != null){
			viewFooter.setClickable(true);
		}
	}
	
	/**
	 * 设置footer加载中显示
	 */
	public void setFooterInit(){
		if(sxgfooter_pb != null){
			sxgfooter_pb.setVisibility(View.VISIBLE);
		}
		if(sxgfooter_tv != null){
			sxgfooter_tv.setText("加载中...");
		}
	}
	
	/**
	 * 移除底部footer
	 */
	public void removeViewFooter(){
		getRefreshableView().removeFooterView(viewFooter);
	}

	/**
	 * 初始化PullToRefreshListView的一些基本设定
	 * @param context
	 */
	private void initListView(Context context){
		setMode(Mode.PULL_DOWN_TO_REFRESH);
		
		//添加底的布局
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		//滚动监听
		setOnScrollListener(new OnScrollListenerImple());
		
		//刷新监听
		setOnRefreshListener(new OnRefreshListenerImple());
	}
	
	/**
	 * 控制多次连续点击或多次滑到底部的操作
	 */
	private long lastClickTime;
    public synchronized boolean isFastClick() {
        long time = System.currentTimeMillis();   
        if ( time - lastClickTime < 1000) {   
            return true;   
        }   
        lastClickTime = time;   
        return false;   
    }
	
	/**
	 * 刷新的异步处理
	 */
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		public boolean isFooter;
		//重构传入上拉还是下拉
		public GetDataTask(boolean isFooter){
			this.isFooter = isFooter;
		}
		
		@Override
		protected String[] doInBackground(Void... params) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			if(isFooter){
				//加载更多
				if(loadDateListener != null){
            		loadDateListener.onLoadMore();
            	}
			}else{
				//刷新
				if(loadDateListener != null){
	        		loadDateListener.onRefresh();
	        	}
			}
		}
	}
	
	/**
	 * 滚动到底部的监听
	 */
	private class OnScrollListenerImple implements OnScrollListener{ 
        @Override
        public void onScroll(AbsListView listView, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
        }
        
        @Override
        public void onScrollStateChanged(AbsListView listview, int scrollState) {
        	switch (scrollState) {
            // 当不滚动时
            case OnScrollListener.SCROLL_STATE_IDLE:
                // 判断滚动到底部
	            if (listview.getLastVisiblePosition() == (listview.getCount() - 1)) {
	            	//加载更多
	            	if(loadDateListener != null&&isLoadMore&&!isFastClick()){
	            		//loadDateListener.onLoadMore();
	            		new GetDataTask(true).execute();
	            	}
	            }
	            break;
        	}
        }
    }
	
	/**
	 * 刷新的监听
	 */
	private class OnRefreshListenerImple implements OnRefreshListener<ListView>{
		@Override
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			//设置刷新请求
			if (refreshView.isFooterShowed()) {
				//加载更多
	        	new GetDataTask(true).execute();
			}else{
				//刷新
				new GetDataTask(false).execute();
			}
    	}
    }
	
	/**
	 * 加载更多view的点击事件
	 */
	private class OnClickListenerImple implements OnClickListener{
		@Override
		public void onClick(View v) {
			//加载更多
        	if(loadDateListener != null&&!isFastClick()){
        		//loadDateListener.onLoadMore();
        		new GetDataTask(true).execute();
        	}
		}	
	}
	
	/**
	 * 刷新跟加载更多的监听接口
	 */
	public interface LoadDateListener{
		
		public void onRefresh();
		
		public void onLoadMore();
	}
}
