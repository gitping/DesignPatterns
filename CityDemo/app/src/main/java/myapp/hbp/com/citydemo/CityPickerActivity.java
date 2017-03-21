package myapp.hbp.com.citydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myapp.hbp.com.citydemo.adapter.CityListAdapter;
import myapp.hbp.com.citydemo.adapter.ResultListAdapter;
import myapp.hbp.com.citydemo.db.DBManager;
import myapp.hbp.com.citydemo.model.CityBean;
import myapp.hbp.com.citydemo.model.RegionBean;
import myapp.hbp.com.citydemo.utils.FileUtil;
import myapp.hbp.com.citydemo.utils.JSONParserUtil;
import myapp.hbp.com.citydemo.view.SideLetterBar;


/**
 * @author huangbaiping
 */
public class CityPickerActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_PICKED_CITY = "cityBean";

    private ListView mListView;
    private ListView mResultListView;
    private SideLetterBar mLetterBar;
    private EditText searchBox;
    private ImageView clearBtn;
    private ImageView backBtn;
    private ViewGroup emptyView;

    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<RegionBean> mAllCities;
    private DBManager dbManager;
    private CityBean locationCity = null;
    private List<RegionBean> hotCityList;

    private int requestCode = -1; //startActivityForResult方法请求的requestCode，决定回传值的显示
    public final static int RESULT_CITY_OK = 3000;
    public final static int RESULT_YUNFEI_OK = 3001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_picker);

        //热门城市数据
        getData();
        requestCode = getIntent().getIntExtra("requestCode", -1);

        initData();
        initView();
        //初始化定位
        initLocation();
    }

    /**
     * 获取热门数据
     */
    private void getData() {
        hotCityList = new ArrayList<>();
        try {
            // 热门城市
            // 解析assets目录下的数据
            JSONParserUtil parser = new JSONParserUtil();
            // 读取json数据
            String area_str = FileUtil.readAssets(getApplicationContext(), "area.json");
            // 热门城市数据
            hotCityList = parser.getJSONParserResult(area_str, "area");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLocation() {
        locationCity = new CityBean();
        locationCity.setFirstName("上海市");
        locationCity.setFirstCode("310000");
        locationCity.setSecondeName("上海市");
        locationCity.setSecondeCode("310000");
        locationCity.setThirdName("长宁区");
        locationCity.setThirdCode("310105");
        if (locationCity != null) {
            mCityAdapter.updateLocateState(locationCity);
        }
    }

    private void initData() {
        dbManager = new DBManager(this);
        dbManager.copyDBFile();
        mAllCities = dbManager.getProvinces();
        mCityAdapter = new CityListAdapter(this, mAllCities, hotCityList, requestCode);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(CityBean city) {
                back(city);
            }
        });
        mResultAdapter = new ResultListAdapter(this, null);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview_all_city);
        mListView.setAdapter(mCityAdapter);

        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });

        //设置搜索框
        searchBox = (EditText) findViewById(R.id.et_search);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    List<RegionBean> result = dbManager.searchProvinces(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });

        emptyView = (ViewGroup) findViewById(R.id.empty_view);
        mResultListView = (ListView) findViewById(R.id.listview_search_result);
        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RegionBean regionBean = mResultAdapter.getItem(position);
                if (regionBean != null) {
                    //上海市，北京市，天津市，重庆市等直辖市需做特殊处理
                    String[] citys = new String[]{"北京市", "上海市", "天津市", "重庆市"};
                    List<String> hots = Arrays.asList(citys);
                    if (hots.contains(regionBean.getRegionName())) {
                        CityBean cityBean = new CityBean();
                        cityBean.setFirstName(regionBean.getRegionName());
                        cityBean.setFirstCode(regionBean.getRegionCode());
                        cityBean.setSecondeName(regionBean.getRegionName());
                        cityBean.setSecondeCode(regionBean.getRegionCode());
                        cityBean.setThirdName("");
                        cityBean.setThirdCode("");
                        //TODO 运费查询到市级别直接返回
                        if (MainActivity.REQUEST_YUNFEI_CODE_1 == requestCode ||
                                MainActivity.REQUEST_YUNFEI_CODE_2 == requestCode) {
                            back(cityBean);
                        } else {
                            //跳转界面
                            Intent intent = new Intent(CityPickerActivity.this, DistrictActivity.class);
                            intent.putExtra("cityBean", cityBean);
                            intent.putExtra("requestCode", requestCode);
                            //标记：该标记是为了处理特殊城市（上海市，北京市，天津市，重庆市）
                            intent.putExtra("hotcity", true);
                            startActivityForResult(intent, requestCode);
                        }
                    } else {
                        //存储省-市-区的name和code
                        CityBean cityBean = new CityBean();
                        cityBean.setFirstName(regionBean.getRegionName());
                        cityBean.setFirstCode(regionBean.getRegionCode());
                        cityBean.setSecondeName("");
                        cityBean.setSecondeCode("");
                        cityBean.setThirdName("");
                        cityBean.setThirdCode("");
                        //跳转界面
                        Intent intent = new Intent(CityPickerActivity.this, DistrictActivity.class);
                        intent.putExtra("cityBean", cityBean);
                        intent.putExtra("requestCode", requestCode);
                        startActivityForResult(intent, requestCode);
                    }
                }
            }
        });

        clearBtn = (ImageView) findViewById(R.id.iv_search_clear);
        backBtn = (ImageView) findViewById(R.id.back);

        clearBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    private void back(CityBean city) {
        Intent data = new Intent();
        data.putExtra(KEY_PICKED_CITY, city);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_search_clear) {
            searchBox.setText("");
            clearBtn.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            mResultListView.setVisibility(View.GONE);
        } else if (i == R.id.back) {
            finish();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == DistrictActivity.RESULT_DIST_OK) {
            setResult(RESULT_CITY_OK, data);
            finish();
        } else if (resultCode == DistrictActivity.RESULT_YUNFEI_OK) {
            setResult(RESULT_YUNFEI_OK, data);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
