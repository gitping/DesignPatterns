package com.yto.suixingoustore.activity.express;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.baidu.mobstat.StatService;
import com.frame.view.gridview.NoScrollGridView;
import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;
import com.yto.suixingoustore.util.pinyinsort.PinyinComparator;
import com.yto.suixingouuser.util.android.SuixingouDatabaseHelper;
import com.yto.zhang.store.util.adapters.GridViewAdapter;
import com.yto.zhang.store.util.adapters.SortAdapter;
import com.yto.zhang.util.modle.ExpressBean;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class SignInCompanyChooseActivity extends FBaseActivity {
	private NoScrollGridView gridview;
	private NoScrollGridView listview;
	private TextView mTitleText;
	private PinyinComparator	pinyinComparator;
	private List<ExpressBean> list;
	private List<ExpressBean> list1;
	private List<ExpressBean> list2;

	@Override
	protected void init() {
	}
	
	@Override
	protected void setupView() {
		setContentView(R.layout.express_company_chooseac);
		
		mTitleText = (TextView) findViewById(R.id.text_topmiddle);
	
		mTitleText.setText("选择快递公司");
	
		pinyinComparator = new PinyinComparator();
		gridview=(NoScrollGridView)findViewById(R.id.gridview);
		listview=(NoScrollGridView)findViewById(R.id.collect_listview);
		list=SuixingouDatabaseHelper.getInstance().getExpressNameList();
		if (list.size() != 0) {
			list1=new ArrayList<ExpressBean>();
			for (int i = 0; i < 8; i++) {
				list1.add(list.get(i));
			}
			Log.i("info", "dblist"+list.size());
			GridViewAdapter adapter=new GridViewAdapter(this, list1, 2);
			gridview.setAdapter(adapter);
			gridview.setOnItemClickListener(new setItemOnclick(0));
			
			//下面进行的拼音排序
			list = filledData(list);
			Collections.sort(list, pinyinComparator);
			list2 = getList(list);
			SortAdapter madapter = new SortAdapter(this, list2, 2);
			listview.setAdapter(madapter);
			listview.setOnItemClickListener(new setItemOnclick(1));
		}else {
			Toast.makeText(this, "您手机配置太低！不能保证该功能正常使用！", Toast.LENGTH_SHORT).show();
			finish();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StatService.onPageStart(this, "选择快件所属公司");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		StatService.onPageEnd(this, "选择快件所属公司");
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
	
	/**
	 * 下半部分gridview需要按首字母排序，在第一个字母之前插入几个空的ExpressBean对象，让分类首字母能到下一排的第一位显示
	 */
	private List<ExpressBean> getList(List<ExpressBean> list){
		int upitem = 0;
		List<ExpressBean> mlist = new ArrayList<ExpressBean>();
		for(int i=0;i<list.size();i++){
			int section = getSectionForPosition(i);
			int firstitem = getPositionForSection(section);
			if(upitem == firstitem){
				mlist.add(list.get(i));
			}else{
				int add = 4 - mlist.size()%4;
				for(int j=0;j<add;j++){
					ExpressBean eb = new ExpressBean();
					eb.setSortLetters(",");
					mlist.add(eb);
				}
				mlist.add(list.get(i));
			}
			upitem = firstitem;
		}
		return mlist;		
	}
	
	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}
	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < list.size(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if(firstChar == section) {
				return i;
			}
		}

		return -1;
	}
	
	class setItemOnclick implements OnItemClickListener{
		public int type;
		public setItemOnclick(int type){
			this.type = type;
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String ExCode, ExPic, ExName;
			if(type == 0){
				ExCode = list1.get(position).getExCode();
				ExPic = list1.get(position).getExPic();
				ExName = list1.get(position).getExName();
			}else{
				ExCode = list2.get(position).getExCode();
				ExPic = list2.get(position).getExPic();
				ExName = list2.get(position).getExName();
			}

			Intent it = new Intent();
			it.putExtra("chooseExName", ExName);
			it.putExtra("chooseExCode", ExCode);
			it.putExtra("chooseExPic", ExPic);
			setResult(101, it);
			finish();
		}	
	}
}
