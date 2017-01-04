package com.yto.zhang.util.modle;

/**
 * 获取店铺类型
 * @author ShenHua
 * 2015年5月22日下午4:13:40
 */
public class Dictionary {

    private Integer id;
    private Integer dicid;
    private String dicname;
    private Integer parentdicid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDicid() {
        return dicid;
    }

    public void setDicid(Integer dicid) {
        this.dicid = dicid;
    }

    public String getDicname() {
        return dicname;
    }

    public void setDicname(String dicname) {
        this.dicname = dicname;
    }

    public Integer getParentdicid() {
        return parentdicid;
    }

    public void setParentdicid(Integer parentdicid) {
        this.parentdicid = parentdicid;
    }
    
}
