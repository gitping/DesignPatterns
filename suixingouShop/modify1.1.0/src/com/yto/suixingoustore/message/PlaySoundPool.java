package com.yto.suixingoustore.message;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;

public class PlaySoundPool {
	private Context context;
	// 音效的音量
	int streamVolume;
	// 定义SoundPool 对象
	private SoundPool soundPool;
	// 定义HASH表
	private HashMap<Integer, Integer> soundPoolMap;

	private PlaySoundPool(Context context) {
		this.context = context;
	}

	private static PlaySoundPool playsp;

	public static PlaySoundPool getInstance() {
		if (playsp == null) {
			playsp = new PlaySoundPool(FrameApplication.context);
			playsp.initSounds();
		}
		return playsp;
	}

	private void initSounds() {
		// 初始化soundPool 对象,第一个参数是允许有多少个声音流同时播放,第2个参数是声音类型,第三个参数是声音的品质
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
		// 初始化HASH表
		soundPoolMap = new HashMap<Integer, Integer>();
		// 获得声音设备和设备音量
		AudioManager mgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//		streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		loadSfx(R.raw.haveorder, 1);
		loadSfx(R.raw.cancelorder, 2);
		loadSfx(R.raw.ordersuccess, 3);
		loadSfx(R.raw.reminder, 4);//催单 
	}

	private void loadSfx(int raw, int ID) {
		// 把资源中的音效加载到指定的ID(播放的时候就对应到这个ID播放就行了)
		soundPoolMap.put(ID, soundPool.load(context, raw, 1));
	}

	public void play(int sound, int uLoop) {
		soundPool.play(soundPoolMap.get(sound), 1, 1, 1, uLoop, 1f);
	}

	public void playCirculation(int sound, int uLoop) {
		PlaySoundPool.stop();
		start(sound, uLoop);
	}

	private static PlayThread playTH;

	private void start(int sound, int uLoop) {
		if (playTH == null)
			playTH = new PlayThread(sound, uLoop);
		if (!playTH.isAlive()) {
			playTH.start();
		}
	}

	public static void stop() {
		if (playTH != null) {
			playTH.stopPlay();
			playTH = null;
		}
	}

	private class PlayThread extends Thread {
		private boolean mRunning = true;
		int sound;
		int uLoop;

		public PlayThread(int sound, int uLoop) {
			this.sound = sound;
			this.uLoop = uLoop;
		}

		public void stopPlay() {
			mRunning = false;
		}

		@Override
		public void run() {
			super.run();
			int i=1;
			while (uLoop >= i) {
				i++;
				if (mRunning == false) {
					break;
				}
				play(sound, 0);
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
