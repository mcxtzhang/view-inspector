package com.dianping.vi_lib.inspector;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxutong on 2018/4/28.
 */

public class TextViewAttributes implements IAttributes {
    TextView view;

    @Override
    public boolean accept(View view) {
        if (view instanceof TextView) {
            this.view = (TextView) view;
            return true;

        } else {
            return false;
        }

    }

    @Override
    public List<AttributeShowModel> attributes() {
        List<AttributeShowModel> result = new ArrayList<>();
        result.add(new AttributeShowModel() {
            @Override
            public String label() {
                return "text";
            }

            @Override
            public String editLabel() {
                return view.getText().toString();
            }

            @Override
            public void onAttributeChanged(String attribute) {
                view.setText(attribute);
            }
        });
        return result;
    }
}
