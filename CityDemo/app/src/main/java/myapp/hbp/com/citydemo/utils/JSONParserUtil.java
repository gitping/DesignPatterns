package myapp.hbp.com.citydemo.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import myapp.hbp.com.citydemo.model.RegionBean;

/**
 * 解析assets目录下的json数据
 * 
 * @author hbp.ping
 *
 */
public class JSONParserUtil {

	public List<RegionBean> getJSONParserResult(String JSONString, String key) {
		List<RegionBean> list = new ArrayList<RegionBean>();
		JsonObject result = new JsonParser().parse(JSONString).getAsJsonObject().getAsJsonObject(key);

		Iterator<?> iterator = result.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, JsonElement> entry = (Map.Entry<String, JsonElement>) iterator.next();
			RegionBean city = new RegionBean();
			city.setRegionName(entry.getValue().getAsString());
			city.setRegionCode(entry.getKey());
			list.add(city);
		}
		return list;
	}

}
