package myapp.hbp.com.citydemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import myapp.hbp.com.citydemo.R;
import myapp.hbp.com.citydemo.model.RegionBean;

/**
 * author zaaach on 2016/1/26.
 */
public class HotCityGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<RegionBean> mCities;

    public HotCityGridAdapter(Context context,List<RegionBean> mCities) {
        this.mContext = context;
        this.mCities = mCities;
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
    public View getView(int position, View view, ViewGroup parent) {
        HotCityViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.cp_item_hot_city_gridview, parent, false);
            holder = new HotCityViewHolder();
            holder.name = (TextView) view.findViewById(R.id.tv_hot_city_name);
            view.setTag(holder);
        } else {
            holder = (HotCityViewHolder) view.getTag();
        }
        holder.name.setText(mCities.get(position).getRegionName());
        return view;
    }

    public static class HotCityViewHolder {
        TextView name;
    }
}
