package myapp.hbp.com.citydemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import myapp.hbp.com.citydemo.CityPickerActivity;
import myapp.hbp.com.citydemo.DistrictActivity;
import myapp.hbp.com.citydemo.MainActivity;
import myapp.hbp.com.citydemo.R;
import myapp.hbp.com.citydemo.db.DBManager;
import myapp.hbp.com.citydemo.model.CityBean;
import myapp.hbp.com.citydemo.model.RegionBean;
import myapp.hbp.com.citydemo.utils.PinyinUtils;
import myapp.hbp.com.citydemo.view.WrapHeightGridView;

/**
 * @author huangbaiping
 */
public class CityListAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 3;

    private Context mContext;
    private LayoutInflater inflater;
    private List<RegionBean> mCities;
    private HashMap<String, Integer> letterIndexes;
    private String[] sections;
    private OnCityClickListener onCityClickListener;
    private CityBean locatedCity;
    private List<RegionBean> hotCityList;
    private DBManager dbManager;
    private int requestCode = -1;//startActivityForResult方法请求的requestCode，决定回传值的显示

    public CityListAdapter(Context mContext, List<RegionBean> mCities, List<RegionBean> hotCityList, int requestCode) {
        this.mContext = mContext;
        this.mCities = mCities;
        this.hotCityList = hotCityList;
        this.inflater = LayoutInflater.from(mContext);
        this.requestCode = requestCode;
        dbManager = new DBManager(mContext);
        if (mCities == null) {
            mCities = new ArrayList<>();
        }
        mCities.add(0, new RegionBean("定位", "0"));
        mCities.add(1, new RegionBean("热门", "1"));
        int size = mCities.size();
        letterIndexes = new HashMap<>();
        sections = new String[size];
        for (int index = 0; index < size; index++) {
            //当前城市拼音首字母
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(index).getPinyin());
            //上个首字母，如果不存在设为""
            String previousLetter = index >= 1 ? PinyinUtils.getFirstLetter(mCities.get(index - 1).getPinyin()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                letterIndexes.put(currentLetter, index);
                sections[index] = currentLetter;
            }
        }
    }

    /**
     * 更新定位状态
     */
    public void updateLocateState(CityBean city) {
        this.locatedCity = city;
        notifyDataSetChanged();
    }

    /**
     * 获取字母索引的位置
     *
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter) {
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public RegionBean getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        CityViewHolder holder;
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0: //定位
                view = inflater.inflate(R.layout.cp_view_locate_city, parent, false);
                ViewGroup container = (ViewGroup) view.findViewById(R.id.layout_locate);
                TextView tv_located_city = (TextView) view.findViewById(R.id.tv_located_city);
                if (locatedCity != null) {
                    tv_located_city.setText(locatedCity.getFirstName());
                }
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //返回定位城市
                        if (onCityClickListener != null) {
                            onCityClickListener.onCityClick(locatedCity);
                        }
                    }
                });
                break;
            case 1: //热门
                view = inflater.inflate(R.layout.cp_view_hot_city, parent, false);
                WrapHeightGridView gridView = (WrapHeightGridView) view.findViewById(R.id.gridview_hot_city);
                final HotCityGridAdapter hotCityGridAdapter = new HotCityGridAdapter(mContext, hotCityList);
                gridView.setAdapter(hotCityGridAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        /**
                         * 处理逻辑：
                         * 1.先判断当前选择的城市是否是特殊城市（上海市，北京市，天津市，重庆市），如果是需做特殊处理；
                         * 2.再判断当前选择的城市是否还有下一级的区域信息
                         * 有：需要跳转到区选择界面
                         *
                         * 没有：需判断该城市是否有上级目录，如果有需查询出他的上一级的name和code并回传上一个界面（如：广东省-东莞市）
                         */
                        //1.上海市，北京市，天津市，重庆市等直辖市需做特殊处理
                        String[] citys = new String[]{"北京市", "上海市", "天津市", "重庆市"};
                        List<String> hots = Arrays.asList(citys);
                        if (hots.contains(hotCityGridAdapter.getItem(position).getRegionName())) {
                            //存储省-市-区的name和code
                            CityBean cityBean = new CityBean();
                            cityBean.setFirstName(hotCityGridAdapter.getItem(position).getRegionName());
                            cityBean.setFirstCode(hotCityGridAdapter.getItem(position).getRegionCode());
                            cityBean.setSecondeName(hotCityGridAdapter.getItem(position).getRegionName());
                            cityBean.setSecondeCode(hotCityGridAdapter.getItem(position).getRegionCode());
                            cityBean.setThirdName("");
                            cityBean.setThirdCode("");
                            //TODO 运费查询到市级别直接返回
                            if (MainActivity.REQUEST_YUNFEI_CODE_1 == requestCode ||
                                    MainActivity.REQUEST_YUNFEI_CODE_2 == requestCode) {
                                if (onCityClickListener != null) {
                                    onCityClickListener.onCityClick(cityBean);
                                }
                            } else {
                                //跳转界面
                                Intent intent = new Intent(mContext, DistrictActivity.class);
                                intent.putExtra("cityBean", cityBean);
                                intent.putExtra("requestCode", requestCode);
                                //标记：该标记是为了处理特殊城市（上海市，北京市，天津市，重庆市）
                                intent.putExtra("hotcity", true);
                                ((CityPickerActivity) mContext).startActivityForResult(intent, requestCode);
                            }

                        } else {
                            String byCode = hotCityGridAdapter.getItem(position).getRegionCode();
                            //根据byCode查询出热门城市的上级的name和code值
                            List<RegionBean> provinceList = dbManager.findByCodeRegionBean(byCode);
                            RegionBean province = new RegionBean();
                            if (provinceList != null && provinceList.size() > 0) {
                                province = provinceList.get(0);
                            }
                            if (province == null) return;
                            CityBean cityBean = new CityBean();
                            cityBean.setFirstName(province.getRegionName());
                            cityBean.setFirstCode(province.getRegionCode());
                            cityBean.setSecondeName(hotCityGridAdapter.getItem(position).getRegionName());
                            cityBean.setSecondeCode(hotCityGridAdapter.getItem(position).getRegionCode());
                            cityBean.setThirdName("");
                            cityBean.setThirdCode("");

                            //2.再判断当前选择的城市是否还有下一级的区域信息
                            List<RegionBean> countyList = dbManager.findByCodeCountys(byCode);
                            if (countyList != null && countyList.size() > 0) {
                                if (MainActivity.REQUEST_YUNFEI_CODE_1 == requestCode ||
                                        MainActivity.REQUEST_YUNFEI_CODE_2 == requestCode) {
                                    //TODO 运费查询到市级别直接返回
                                    if (onCityClickListener != null) {
                                        onCityClickListener.onCityClick(cityBean);
                                    }
                                } else {
                                    //热门城市下有区或县,跳转到下级界面
                                    Intent intent = new Intent(mContext, DistrictActivity.class);
                                    intent.putExtra("cityBean", cityBean);
                                    intent.putExtra("requestCode", requestCode);
                                    //标记：是为了跳到下一个界面时，直接关闭掉
                                    intent.putExtra("flag_close", true);
                                    ((CityPickerActivity) mContext).startActivityForResult(intent, requestCode);
                                }
                            } else {
                                //热门城市下没有区或县（比如：广东省-东莞市），返回数据
                                if (onCityClickListener != null) {
                                    onCityClickListener.onCityClick(cityBean);
                                }
                            }
                        }
                    }
                });
                break;
            case 2: //所有
                if (view == null) {
                    view = inflater.inflate(R.layout.cp_item_city_listview, parent, false);
                    holder = new CityViewHolder();
                    holder.letter = (TextView) view.findViewById(R.id.tv_item_city_listview_letter);
                    holder.name = (TextView) view.findViewById(R.id.tv_item_city_listview_name);
                    view.setTag(holder);
                } else {
                    holder = (CityViewHolder) view.getTag();
                }
                if (position >= 1) {
                    final String city = mCities.get(position).getRegionName();
                    holder.name.setText(city);
                    String currentLetter = PinyinUtils.getFirstLetter(mCities.get(position).getPinyin());
                    String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(mCities.get(position - 1).getPinyin()) : "";
                    if (!TextUtils.equals(currentLetter, previousLetter)) {
                        holder.letter.setVisibility(View.VISIBLE);
                        holder.letter.setText(currentLetter);
                    } else {
                        holder.letter.setVisibility(View.GONE);
                    }
                    holder.name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /**
                             * 处理逻辑：
                             * 1.只有一级目录，直接返回（台湾省 ，香港 ，澳门，其它地区）
                             * 2.有两级目录，跳转到区选择界面（上海市，北京市，天津市，重庆市）
                             * 3.有三级目录，跳转到市选择界面
                             */
                            CityBean cityBean = null;
                            //1.台湾省 ，香港 ，澳门，其它地区,直接返回
                            String[] strs = new String[]{"710000", "810000", "820000", "000000"};
                            List<String> slist = Arrays.asList(strs);
                            String code = mCities.get(position).getRegionCode();
                            if (slist.contains(code)) {
                                //存储省-市-区的name和code
                                cityBean = new CityBean();
                                cityBean.setFirstName(mCities.get(position).getRegionName());
                                cityBean.setFirstCode(mCities.get(position).getRegionCode());
                                cityBean.setSecondeName(mCities.get(position).getRegionName());
                                cityBean.setSecondeCode(mCities.get(position).getRegionCode());
                                cityBean.setThirdName("");
                                cityBean.setThirdCode("");
                                //设置回调
                                if (onCityClickListener != null) {
                                    onCityClickListener.onCityClick(cityBean);
                                }
                            } else {
                                //2.上海市，北京市，天津市，重庆市等直辖市需做特殊处理
                                String[] citys = new String[]{"北京市", "上海市", "天津市", "重庆市"};
                                List<String> hots = Arrays.asList(citys);
                                if (hots.contains(mCities.get(position).getRegionName())) {
                                    /**
                                     * 1.是运费查询到市级，直接返回
                                     * 2.不是运费查询，跳转到二级目录
                                     */
                                    //存储省-市-区的name和code
                                    cityBean = new CityBean();
                                    cityBean.setFirstName(mCities.get(position).getRegionName());
                                    cityBean.setFirstCode(mCities.get(position).getRegionCode());
                                    cityBean.setSecondeName(mCities.get(position).getRegionName());
                                    cityBean.setSecondeCode(mCities.get(position).getRegionCode());
                                    cityBean.setThirdName("");
                                    cityBean.setThirdCode("");
                                    if (MainActivity.REQUEST_YUNFEI_CODE_1 == requestCode ||
                                            MainActivity.REQUEST_YUNFEI_CODE_2 == requestCode) {
                                        //TODO 运费查询到市级直接返回
                                        if (onCityClickListener != null) {
                                            onCityClickListener.onCityClick(cityBean);
                                        }
                                    } else {
                                        //跳转界面
                                        Intent intent = new Intent(mContext, DistrictActivity.class);
                                        intent.putExtra("cityBean", cityBean);
                                        intent.putExtra("requestCode", requestCode);
                                        //标记：该标记是为了处理特殊城市（上海市，北京市，天津市，重庆市）
                                        intent.putExtra("hotcity", true);
                                        ((CityPickerActivity) mContext).startActivityForResult(intent, requestCode);
                                    }

                                } else {
                                    //3.有三级目录的，需选择省-市-区
                                    /**
                                     * 跳转到二级目录...
                                     */
                                    //存储省-市-区的name和code
                                    cityBean = new CityBean();
                                    cityBean.setFirstName(mCities.get(position).getRegionName());
                                    cityBean.setFirstCode(mCities.get(position).getRegionCode());
                                    cityBean.setSecondeName("");
                                    cityBean.setSecondeCode("");
                                    cityBean.setThirdName("");
                                    cityBean.setThirdCode("");
                                    //跳转界面
                                    Intent intent = new Intent(mContext, DistrictActivity.class);
                                    intent.putExtra("cityBean", cityBean);
                                    intent.putExtra("requestCode", requestCode);
                                    ((CityPickerActivity) mContext).startActivityForResult(intent, requestCode);
                                }
                            }

                        }
                    });
                }
                break;
        }
        return view;
    }

    public static class CityViewHolder {
        TextView letter;
        TextView name;
    }

    public void setOnCityClickListener(OnCityClickListener listener) {
        this.onCityClickListener = listener;
    }

    public interface OnCityClickListener {
        void onCityClick(CityBean city);
    }

}
