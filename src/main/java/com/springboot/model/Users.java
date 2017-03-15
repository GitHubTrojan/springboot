package com.springboot.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Vincent on 2017/3/14.
 */
public class Users {
    public int uid;

    /**
     * By adding this annotation '@JSONField(serialize = false)', we can figure out whether the configuation of fastJsonMessageConverter is effective or not.
     * if it worked, the result we got at the page should not contain the uname field
     * before add: { "uid":1, "uname":"Vincent Wang" }, after add: { "uid":1 }
     */
    public String uname;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? "" : uname;
    }

    @Override
    public String toString() {
        return "Users{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                '}';
    }
}
