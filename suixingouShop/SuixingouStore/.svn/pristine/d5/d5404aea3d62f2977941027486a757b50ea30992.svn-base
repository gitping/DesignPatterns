package com.yto.suixingoustore.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yto.suixingoustore.FBaseActivity;
import com.yto.suixingoustore.R;

/**
 * 扶持基金说明
 * @author admin
 *
 */
public class SupportFundsActivity extends FBaseActivity {
	private TextView topmiddle;
	private Button topright;

	@Override
	protected void init() {

	}

	@Override
	protected void setupView() {
		setContentView(R.layout.activity_supportfunds_lay);
		topmiddle = (TextView) findViewById(R.id.text_topmiddle);
		topmiddle.setText("扶持基金说明");
		topright=(Button)findViewById(R.id.but_topright);
		topright.setVisibility(View.VISIBLE);
		topright.setBackgroundDrawable(getResources().getDrawable(R.drawable.financial_topright));
		topright.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SupportFundsActivity.this, MakeMoneyActivity.class));
			}
		});
		
	}

}
