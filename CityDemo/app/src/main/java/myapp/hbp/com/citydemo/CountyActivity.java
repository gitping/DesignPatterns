package myapp.hbp.com.citydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import myapp.hbp.com.citydemo.adapter.HotCityGridAdapter;
import myapp.hbp.com.citydemo.db.DBManager;
import myapp.hbp.com.citydemo.model.CityBean;
import myapp.hbp.com.citydemo.model.RegionBean;
import myapp.hbp.com.citydemo.view.WrapHeightGridView;

public class CountyActivity extends AppCompatActivity implements View.OnClickListener {

    public final static int RESULT_COUNTY_OK = 1000;

    private TextView tv_province_name, tv_city_name;
    private WrapHeightGridView gridview_county;
    private List<RegionBean> countyList = new ArrayList<>();
    private HotCityGridAdapter districtGridAdapter;
    private DBManager dbManager;
    private CityBean mCityBean;
    private boolean flag_hotcity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county);
        //初始化views
        tv_province_name = (TextView) findViewById(R.id.tv_province_name);
        tv_city_name = (TextView) findViewById(R.id.tv_city_name);
        gridview_county = (WrapHeightGridView) findViewById(R.id.gridview_county);

        //初始化dbManager
        dbManager = new DBManager(this);
        dbManager.copyDBFile();

        //获取是否是直辖市的标记
        flag_hotcity = getIntent().getBooleanExtra("hotcity", false);
        //获取上一个界面传过来的一级目录name和code
        mCityBean = (CityBean) getIntent().getSerializableExtra("cityBean");
        if (mCityBean == null) return;
        //上海市，北京市，天津市，重庆市等直辖市需做特殊处理
        if (flag_hotcity) {
            if (!TextUtils.isEmpty(mCityBean.getFirstName())) {
                //一级目录的name和code
                tv_province_name.setText(mCityBean.getFirstName());
            }

            List<RegionBean> districtList = dbManager.findByCodeDistricts(mCityBean.getFirstCode());
            if (districtList != null && districtList.size() > 0) {
                for (int i = 0; i < districtList.size(); i++) {
                    String byCode = districtList.get(i).getRegionCode();
                    List<RegionBean> blist = dbManager.findByCodeCountys(byCode);
                    if (blist != null && blist.size() > 0) {
                        countyList.addAll(blist);
                    }
                }
            }

        } else {
            if (!TextUtils.isEmpty(mCityBean.getFirstName())) {
                //一级目录的name和code
                tv_province_name.setText(mCityBean.getFirstName());
            }
            if (!TextUtils.isEmpty(mCityBean.getSecondeCode()) && !TextUtils.isEmpty(mCityBean.getSecondeName())) {
                //二级目录的name和code
                tv_city_name.setText(mCityBean.getSecondeName());

                //根据firstCode查询出它所在的城市或区信息
                countyList = dbManager.findByCodeCountys(mCityBean.getSecondeCode());
            }
        }

        //初始化adapter
        districtGridAdapter = new HotCityGridAdapter(this, countyList);
        gridview_county.setAdapter(districtGridAdapter);
        gridview_county.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RegionBean regionBean = countyList.get(i);
                if (regionBean == null) return;
                Intent intent = new Intent(CountyActivity.this, DistrictActivity.class);
                CityBean cityBean = new CityBean();
                cityBean.setFirstName(mCityBean.getFirstName());
                cityBean.setFirstCode(mCityBean.getFirstCode());
                cityBean.setSecondeName(mCityBean.getSecondeName());
                cityBean.setSecondeCode(mCityBean.getSecondeCode());
                if (!TextUtils.isEmpty(regionBean.getRegionCode())) {
                    cityBean.setThirdName(regionBean.getRegionName());
                    cityBean.setThirdCode(regionBean.getRegionCode());
                } else {
                    cityBean.setThirdName("");
                    cityBean.setThirdCode("");
                }
                intent.putExtra("cityBean", cityBean);
                setResult(RESULT_COUNTY_OK, intent);
                finish();

            }
        });


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
            CountyActivity.this.finish();
        }
    }
}
