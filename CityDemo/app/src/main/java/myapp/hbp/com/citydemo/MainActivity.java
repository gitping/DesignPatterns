package myapp.hbp.com.citydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import myapp.hbp.com.citydemo.model.CityBean;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView receive_city_name_tv, sender_city_name_tv, yunfei_city_name_tv;
    private Button btn_receive_city, btn_sender_city, btn_yunfei_city;

    public static final int REQUEST_YUNFEI_CODE_1 = 100;
    public static final int REQUEST_YUNFEI_CODE_2 = 101;
    private static final int REQUEST_RECEIVE_CODE = 200;
    private static final int REQUEST_SENDER_CODE = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initEvents();

    }

    //初始化views
    private void initViews() {
        receive_city_name_tv = (TextView) findViewById(R.id.receive_city_name_tv);
        sender_city_name_tv = (TextView) findViewById(R.id.sender_city_name_tv);
        yunfei_city_name_tv = (TextView) findViewById(R.id.yunfei_city_name_tv);
        btn_receive_city = (Button) findViewById(R.id.btn_receive_city);
        btn_sender_city = (Button) findViewById(R.id.btn_sender_city);
        btn_yunfei_city = (Button) findViewById(R.id.btn_yunfei_city);
    }

    //事件处理
    private void initEvents() {
        btn_receive_city.setOnClickListener(this);
        btn_sender_city.setOnClickListener(this);
        btn_yunfei_city.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, CityPickerActivity.class);
        switch (view.getId()) {
            case R.id.btn_receive_city:
                intent.putExtra("requestCode", REQUEST_RECEIVE_CODE);
                startActivityForResult(intent, REQUEST_RECEIVE_CODE);
                break;
            case R.id.btn_sender_city:
                intent.putExtra("requestCode", REQUEST_SENDER_CODE);
                startActivityForResult(intent, REQUEST_SENDER_CODE);
                break;
            case R.id.btn_yunfei_city:
                intent.putExtra("requestCode", REQUEST_YUNFEI_CODE_1);
                startActivityForResult(intent, REQUEST_YUNFEI_CODE_1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ||
                resultCode == CityPickerActivity.RESULT_CITY_OK ||
                resultCode == CityPickerActivity.RESULT_YUNFEI_OK) {
            switch (requestCode) {
                case REQUEST_RECEIVE_CODE:
                    if (data != null) {
                        CityBean city = (CityBean) data.getSerializableExtra(CityPickerActivity.KEY_PICKED_CITY);
                        receive_city_name_tv.setText(city.getFirstName() + "-" + city.getFirstCode() + "\n" +
                                city.getSecondeName() + "-" + city.getSecondeCode() + "\n" +
                                city.getThirdName() + "-" + city.getThirdCode());
                    }
                    break;
                case REQUEST_SENDER_CODE:
                    if (data != null) {
                        CityBean city = (CityBean) data.getSerializableExtra(CityPickerActivity.KEY_PICKED_CITY);
                        sender_city_name_tv.setText(city.getFirstName() + "-" + city.getFirstCode() + "\n" +
                                city.getSecondeName() + "-" + city.getSecondeCode() + "\n" +
                                city.getThirdName() + "-" + city.getThirdCode());
                    }
                    break;
                case REQUEST_YUNFEI_CODE_1:
                    if (data != null) {
                        CityBean city = (CityBean) data.getSerializableExtra(CityPickerActivity.KEY_PICKED_CITY);
                        yunfei_city_name_tv.setText(city.getFirstName() + "-" + city.getFirstCode() + "\n" +
                                city.getSecondeName() + "-" + city.getSecondeCode() + "\n" +
                                city.getThirdName() + "-" + city.getThirdCode());
                    }
                    break;
                case REQUEST_YUNFEI_CODE_2:
                    if (data != null) {
                        CityBean city = (CityBean) data.getSerializableExtra(CityPickerActivity.KEY_PICKED_CITY);
                        yunfei_city_name_tv.setText(city.getFirstName() + "-" + city.getFirstCode() + "\n" +
                                city.getSecondeName() + "-" + city.getSecondeCode() + "\n" +
                                city.getThirdName() + "-" + city.getThirdCode());
                    }
                    break;
            }
        }
    }
}
