package com.yto.suixingouuser.util.download;

public interface HttpClientUtilIF {
	
	public void success(String jsonStr);
	
	public void onStart();
	
	public void loading(int curCount);
	
	public void fail(int code);

}
