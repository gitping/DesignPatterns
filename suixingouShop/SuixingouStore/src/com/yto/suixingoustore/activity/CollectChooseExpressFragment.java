package com.yto.suixingoustore.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.yto.suixingoustore.FrameApplication;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.util.pinyinsort.PinyinComparator;
import com.yto.suixingouuser.util.android.SuixingouDatabaseHelper;
import com.yto.zhang.store.util.adapters.GridViewAdapter;
import com.yto.zhang.store.util.adapters.SortAdapter;
import com.yto.zhang.util.modle.ExpressBean;

/**
 * 代收 选择快递公司fragment
 */
public class CollectChooseExpressFragment extends Fragment {
	
	private GridView gridview;
	private ListView listview;
	SuixingouDatabaseHelper dbhelper;
//	private CharacterParser		characterParser;
	private PinyinComparator	pinyinComparator;
	private Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_collect_choose, container, false);
		context=FrameApplication.context;
//		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		gridview=(GridView)view.findViewById(R.id.gridview);
		listview=(ListView)view.findViewById(R.id.collect_listview);
		dbhelper=SuixingouDatabaseHelper.getInstance();
		List<ExpressBean> list=dbhelper.getExpressNameList();
		if (list.size() != 0) {
			List<ExpressBean> list1=new ArrayList<ExpressBean>();
			for (int i = 0; i < 8; i++) {
				list1.add(list.get(i));
			}
			Log.i("info", "dblist"+list.size());
			GridViewAdapter adapter=new GridViewAdapter(context, list1, 1);
			gridview.setAdapter(adapter);
			
			//下面进行的拼音排序
			list = filledData(list);
			Collections.sort(list, pinyinComparator);
			SortAdapter madapter = new SortAdapter(context, list, 1);
			listview.setAdapter(madapter);
		}else {
			Toast.makeText(getActivity(), "您手机配置太低！不能保证该功能正常使用！", Toast.LENGTH_SHORT).show();
			getActivity().finish();
		}
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		List<ExpressBean> list=dbhelper.getExpressNameList();
		List<ExpressBean> list1=new ArrayList<ExpressBean>();
		for (int i = 0; i < 8; i++) {
			list1.add(list.get(i));
		}
		GridViewAdapter adapter=new GridViewAdapter(context, list1, 1);
		adapter.notifyDataSetChanged();
	}
	
	
	public  String converterToSpell(String chines){          
        String pinyinName = "";  
        char[] nameChar = chines.toCharArray();  
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        for (int i = 0; i < nameChar.length; i++) {  
            if (nameChar[i] > 128) {  
                try {  
                    pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];  
                } catch (BadHanyuPinyinOutputFormatCombination e) {  
                    e.printStackTrace();  
                }  
            }else{  
                pinyinName += nameChar[i];  
            }  
        }  
        return pinyinName;  
    }  
	
	
	private List<ExpressBean> filledData(List<ExpressBean> mSortList) {
		List<ExpressBean> sList = new ArrayList<ExpressBean>();

		for (int i = 0; i < mSortList.size(); i++) {
			ExpressBean sortModel =mSortList.get(i);
//			sortModel.setImgSrc(imgData[i]);
//			sortModel.setName(date[i]);
			// 汉字转换成拼音
			String pinyin = converterToSpell(sortModel.getExName());
			Log.i("return", pinyin+i +mSortList.get(i).getExCode());
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			sList.add(sortModel);
		}
		return sList;

	}

}
