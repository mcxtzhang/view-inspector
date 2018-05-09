package com.dianping.vi_lib.inspector;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.dianping.vi_lib.Hawkeye;

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
        result.add(new AttributeShowModel() {
            @Override
            public String label() {
                return "textColor";
            }

            @Override
            public String editLabel() {
                int textColor = (view).getCurrentTextColor();
                return "#" + Integer.toHexString(textColor);
            }

            @Override
            public void onAttributeChanged(String attribute) {
                try {
                    int color = Color.parseColor(attribute);
                    view.setTextColor(color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        result.add(new AttributeShowModel() {
            @Override
            public String label() {
                return "textSize";
            }

            @Override
            public String editLabel() {
                float textSize = ((TextView) view).getTextSize();
                return toSp(textSize) + "";
            }

            @Override
            public void onAttributeChanged(String attribute) {
                if (!TextUtils.isEmpty(attribute)) {
                    view.setTextSize(Float.parseFloat(attribute));
                }
            }
        });
        return result;
    }

    public int toSp(float px) {
        float scaledDensity = Hawkeye.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scaledDensity);
    }
}
