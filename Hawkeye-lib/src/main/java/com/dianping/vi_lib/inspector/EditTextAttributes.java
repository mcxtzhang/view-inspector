package com.dianping.vi_lib.inspector;

import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxutong on 2018/5/9.
 */

public class EditTextAttributes implements IAttributes {
    EditText view;

    @Override
    public boolean accept(View view) {
        if (view instanceof EditText) {
            this.view = (EditText) view;
            return true;
        } else {
            return false;
        }

    }

    @Override
    public List<AttributeShowModel> attributes() {
        if (view == null) return null;
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
                return "hint";
            }

            @Override
            public String editLabel() {
                return view.getHint().toString();
            }

            @Override
            public void onAttributeChanged(String attribute) {
                view.setHint(attribute);
            }
        });
        return result;
    }
}
