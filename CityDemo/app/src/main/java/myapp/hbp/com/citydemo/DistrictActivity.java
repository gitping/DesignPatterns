package myapp.hbp.com.citydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import myapp.hbp.com.citydemo.adapter.HotCityGridAdapter;
import myapp.hbp.com.citydemo.db.DBManager;
import myapp.hbp.com.citydemo.model.CityBean;
import myapp.hbp.com.citydemo.model.RegionBean;
import myapp.hbp.com.citydemo.view.WrapHeightGridView;

public class DistrictActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_province_name;
    private WrapHeightGridView gridview_district;
    private List<RegionBean> districtList;
    private HotCityGridAdapter districtGridAdapter;
    private DBManager dbManager;
    private CityBean mCityBean;
    private int requestCode = -1; //startActivityForResult方法请求的requestCode，决定回传值的显示
    public final static int RESULT_DIST_OK = 2000;
    public final static int RESULT_YUNFEI_OK = 2001;
    private boolean flag_hotcity = false;
    private boolean flag_close = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);
        //初始化views
        tv_province_name = (TextView) findViewById(R.id.tv_province_name);
        gridview_district = (WrapHeightGridView) findViewById(R.id.gridview_district);

        //初始化dbManager
        dbManager = new DBManager(this);
        dbManager.copyDBFile();

        //获取请求的code
        requestCode = getIntent().getIntExtra("requestCode", -1);
        //获取上一个界面传过来的一级目录name和code
        mCityBean = (CityBean) getIntent().getSerializableExtra("cityBean");
        //获取是否是直辖市的标记
        flag_hotcity = getIntent().getBooleanExtra("hotcity", false);
        //获取从热门城市界面传递过来的标记，作用：就是为了关闭本界面，跳转到下一个界面
        flag_close = getIntent().getBooleanExtra("flag_close", false);
        if (mCityBean == null) return;
        if (flag_close) {
            Intent data = new Intent(DistrictActivity.this, CountyActivity.class);
            data.putExtra("cityBean", mCityBean);
            data.putExtra("requestCode", requestCode);
            startActivityForResult(data, requestCode);
        } else {
            if (flag_hotcity) {
                //这里如果是直辖市，直接跳转到下一级目录
                Intent data = getIntent();
                data.setClass(DistrictActivity.this, CountyActivity.class);
                startActivityForResult(data, requestCode);
            } else {
                if (!TextUtils.isEmpty(mCityBean.getFirstCode()) && !TextUtils.isEmpty(mCityBean.getFirstName())) {
                    //一级目录的name和code
                    tv_province_name.setText(mCityBean.getFirstName());

                    //根据firstCode查询出它所在的城市或区信息
                    districtList = dbManager.findByCodeDistricts(mCityBean.getFirstCode());
                }
            }
        }

        //初始化adapter
        districtGridAdapter = new HotCityGridAdapter(this, districtList);
        gridview_district.setAdapter(districtGridAdapter);
        gridview_district.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //二级目录的name和code
                RegionBean regionBean = districtList.get(i);
                if (regionBean == null) return;
                CityBean cityBean = new CityBean();
                cityBean.setFirstName(mCityBean.getFirstName());
                cityBean.setFirstCode(mCityBean.getFirstCode());
                if (!TextUtils.isEmpty(regionBean.getRegionCode()) && !TextUtils.isEmpty(mCityBean.getFirstCode())) {
                    cityBean.setSecondeName(regionBean.getRegionName());
                    cityBean.setSecondeCode(regionBean.getRegionCode());
                } else {
                    cityBean.setSecondeName("");
                    cityBean.setSecondeCode("");
                }
                cityBean.setThirdName("");
                cityBean.setThirdCode("");
                if(MainActivity.REQUEST_YUNFEI_CODE_1 == requestCode ||
                        MainActivity.REQUEST_YUNFEI_CODE_2 == requestCode){
                    //TODO 运费查询到市级直接返回
                    Intent intent = new Intent(DistrictActivity.this, CityPickerActivity.class);
                    intent.putExtra("cityBean", cityBean);
                    setResult(RESULT_YUNFEI_OK, intent);
                    finish();
                } else {
                    //跳转到三级目录
                    Intent intent = new Intent(DistrictActivity.this, CountyActivity.class);
                    intent.putExtra("cityBean", cityBean);
                    startActivityForResult(intent, requestCode);
                }
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //注意：当标记为true时这里一定要记得关闭下
        if (flag_hotcity || flag_close) {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.back) {
            DistrictActivity.this.finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CountyActivity.RESULT_COUNTY_OK) {
            setResult(RESULT_DIST_OK, data);
            finish();
        }
    }

}
