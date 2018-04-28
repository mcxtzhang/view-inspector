package com.dianping.vi_lib.inspector;

import android.view.View;

import java.util.List;

/**
 * Created by zhangxutong on 2018/4/28.
 */

public interface IAttributes {
    boolean accept(View view);

    List<AttributeShowModel> attributes();
}
