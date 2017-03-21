package myapp.hbp.com.citydemo.model;

import java.io.Serializable;

/**
 * author zaaach on 2016/1/26.
 */
public class RegionBean implements Serializable {

    private String regionName;
    private String regionCode;
    private String pinyin;

    public RegionBean() {
    }

    public RegionBean(String name, String pinyin) {
        this.regionName = name;
        this.pinyin = pinyin;
    }

    public RegionBean(String regionName, String regionCode, String pinyin) {
        this.regionName = regionName;
        this.regionCode = regionCode;
        this.pinyin = pinyin;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
