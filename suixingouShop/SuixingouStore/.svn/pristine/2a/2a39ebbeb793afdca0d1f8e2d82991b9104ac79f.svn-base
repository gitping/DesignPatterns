package com.yto.suixingoustore.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;

/**
 * 财务管理fragment
 */
public class FinancialManagementActivity extends FBaseActivity {
    private TextView fincailpay, accountdetail, texttopmiddle;
    private Button topright, gotobind;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private TextView bottom1, bottom2;


    @Override
    protected void init() {

    }

    @Override
    protected void setupView() {
        setContentView(R.layout.activity_finacialmanagement_lay);
        fincailpay = (TextView) findViewById(R.id.fincail);
        bottom1 = (TextView) findViewById(R.id.bottom_one);
        bottom2 = (TextView) findViewById(R.id.bottom_two);
        accountdetail = (TextView) findViewById(R.id.detailaccount);
        texttopmiddle = (TextView) findViewById(R.id.text_topmiddle);
        texttopmiddle.setText("财务管理");
        topright = (Button) findViewById(R.id.but_topright);
        topright.setVisibility(View.VISIBLE);
        topright.setBackgroundDrawable(getResources().getDrawable(R.drawable.financial_topright));
        gotobind = (Button) findViewById(R.id.mbindingcard);
        MenuClick cli = new MenuClick();
        topright.setOnClickListener(cli);
        fincailpay.setOnClickListener(cli);
        accountdetail.setOnClickListener(cli);

        FragmentFincial mfragment = new FragmentFincial();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_lay, mfragment);
        ft.commit();
    }

    class MenuClick implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fincail:
                    accountdetail.setTextColor(getResources().getColor(R.color.black));
//				bottom2.setBackgroundColor(getResources().getColor(R.color.white));
                    bottom2.setVisibility(View.GONE);
                    bottom1.setVisibility(View.VISIBLE);
                    fincailpay.setTextColor(getResources().getColor(R.color.finacialbottom));
                    bottom1.setBackgroundColor(getResources().getColor(R.color.finacialbottom));

                    FragmentFincial mfragment = new FragmentFincial();
                    fm = getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_lay, mfragment);
                    ft.commit();
                    break;
                case R.id.detailaccount:
                    bottom2.setVisibility(View.VISIBLE);
                    bottom1.setVisibility(View.GONE);
                    accountdetail.setTextColor(getResources().getColor(R.color.finacialbottom));
                    bottom2.setBackgroundColor(getResources().getColor(R.color.finacialbottom));
                    fincailpay.setTextColor(getResources().getColor(R.color.black));
//				bottom1.setBackgroundColor(getResources().getColor(R.color.white));
                    FragmentDetailAccount fragment = new FragmentDetailAccount();
                    fm = getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_lay, fragment);
                    ft.commit();
                    break;
                case R.id.but_topright:
                    startActivity(new Intent(FinancialManagementActivity.this, MakeMoneyActivity.class));
                    break;
            }
        }
    }

    public void bindCard(View v) {
        startActivity(new Intent(FinancialManagementActivity.this, StoreMyBackAccountActivity.class));
    }

}
