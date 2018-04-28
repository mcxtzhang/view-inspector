package com.dianping.vi_lib.inspector;

/**
 * Created by zhangxutong on 2018/4/28.
 */

public interface AttributeShowModel {
    String label();

    String editLabel();

    void onAttributeChanged(String attribute);
}
