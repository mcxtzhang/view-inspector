package com.dianping.vi_lib.suspend.full;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.widget.PopupWindowCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dianping.vi_lib.Hawkeye;
import com.dianping.vi_lib.inspector.AttributeShowModel;
import com.dianping.vi_lib.suspend.ViewUtils;

import java.util.List;


class AttributeViewerView extends FrameLayout {
    private int topGap = 0;
    private final Rect outRect = new Rect();
    private final Paint selectionPaint;

    private View currentView;
    private PopupWindow currentDetailWindow;

    AttributeViewerView(Context context) {
        super(context);

        outRect.left = 0;
        outRect.top = 0;
        outRect.right = ViewUtils.getScreenWidthPixels(context);
        outRect.bottom = ViewUtils.getScreenHeightPixels(context);

        selectionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectionPaint.setColor(Color.parseColor("#8078909C"));
        selectionPaint.setStyle(Paint.Style.FILL);

        setWillNotDraw(false);
        setClickable(false);

        //setBackgroundColor(Color.GREEN);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismissPopupIfNeeded();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(outRect, selectionPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            View touchTarget = findTarget(Hawkeye.getCurrentActivity().getWindow().getDecorView(), x, y);

            View newTarget;
            if (currentView == touchTarget) {
                newTarget = (View) touchTarget.getParent();
            } else {
                newTarget = touchTarget;
            }

            setTarget(newTarget);
            invalidate();
            return true;
        }

        return super.onTouchEvent(event);
    }

    private View findTarget(View root, float x, float y) {
        // we consider the "best target" to be the view width the smallest width / height
        // whose location on screen is within the given touch area.
        View bestTarget = root;
        if (root instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) root;
            int count = parent.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = parent.getChildAt(i);
                getScreenLocation(child, outRect);
                if (child.getVisibility() != VISIBLE) {
                    continue;
                }
                if (x >= outRect.left && x <= outRect.right && y >= outRect.top && y <= outRect.bottom) {
                    final View target = findTarget(child, x, y);
                    if (target.getWidth() <= bestTarget.getWidth() && target.getHeight() <= bestTarget.getHeight()) {
                        bestTarget = target;
                    }
                }
            }
        }
        return bestTarget;
    }

    private void setTarget(View view) {
        currentView = view;
        getScreenLocation(view, outRect);
        //getTargetReact(view, outRect);

        dismissPopupIfNeeded();

        currentDetailWindow = createDetailWindowForView(view);
        PopupWindowCompat.showAsDropDown(currentDetailWindow, view, 0, 0, Gravity.CENTER_HORIZONTAL);
    }

    private PopupWindow createDetailWindowForView(final View view) {
        final Context context = getContext();
        final int width = (int) (getMeasuredWidth() * (4f / 5));
        final int height = getMeasuredHeight() / 2;
//        final AttributeDetailView detailView = new AttributeDetailView(context);
//        detailView.setTarget(view);
        LinearLayout detailList = new LinearLayout(context);
        detailList.setOrientation(LinearLayout.VERTICAL);

/*        LinearLayout line = new LinearLayout(context);
        TextView detailView = new TextView(view.getContext());
        final EditText et = new EditText(context);

        if (view instanceof TextView) {
            detailView.setText("TextColors");
            int textColor = ((TextView) view).getCurrentTextColor();
            et.setText("#" + Integer.toHexString(textColor));
        } else {
            detailView.setText("fasdas");
        }
        line.addView(detailView);
        line.addView(et);
        detailList.addView(line);

        LinearLayout line2 = new LinearLayout(context);
        TextView detailView2 = new TextView(view.getContext());
        final EditText et2 = new EditText(context);

        if (view instanceof TextView) {
            detailView2.setText("TextSize");
            float textSize = ((TextView) view).getTextSize();
            et2.setText(toSp(textSize) + "");
        } else {
            detailView2.setText("fasdas");
        }
        line2.addView(detailView2);
        line2.addView(et2);
        detailList.addView(line2);


        LinearLayout line3 = new LinearLayout(context);
        TextView detailView3 = new TextView(view.getContext());
        final EditText et3 = new EditText(context);

        if (view instanceof TextView) {
            detailView3.setText("Text");
            et3.setText(((TextView) view).getText());
        } else {
            detailView3.setText("fasdas");
        }
        line3.addView(detailView3);
        line3.addView(et3);

        detailList.addView(line3);*/


        List<AttributeShowModel> attributesByView = Hawkeye.getAttributesByView(view);
        if (attributesByView!=null){
            for (final AttributeShowModel attributeShowModel : attributesByView) {
                LinearLayout line3 = new LinearLayout(context);
                TextView detailView3 = new TextView(view.getContext());
                final EditText et3 = new EditText(context);
                detailView3.setText(attributeShowModel.label());
                et3.setText(attributeShowModel.editLabel());
                et3.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        attributeShowModel.onAttributeChanged(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                line3.addView(detailView3);
                line3.addView(et3);

                detailList.addView(line3);
            }
        }


        final PopupWindow popupWindow = new PopupWindow(detailList, width, height);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#737373")));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
/*        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (view instanceof TextView) {
                    ((TextView) view).setTextColor(Color.parseColor(et.getText().toString()));
                    ((TextView) view).setTextSize(Float.parseFloat((et2.getText().toString())));
                    ((TextView) view).setText(((et3.getText().toString())));
                }
            }
        });*/
        return popupWindow;
    }

    public int toSp(float px) {
        float scaledDensity = Hawkeye.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scaledDensity);
    }

    void dismissPopupIfNeeded() {
        if (currentDetailWindow != null && currentDetailWindow.isShowing()) {
            currentDetailWindow.dismiss();
        }
    }

    private static final int[] OUT_LOCATION = new int[2];

    public void getScreenLocation(@NonNull View view, Rect rect) {
        view.getLocationOnScreen(OUT_LOCATION);

        rect.left = OUT_LOCATION[0];
        rect.top = OUT_LOCATION[1];
        rect.right = rect.left + view.getMeasuredWidth();
        rect.bottom = rect.top + view.getMeasuredHeight();
    }

/*    public void getTargetReact(View target, Rect rect) {
        if (topGap == 0) {
            int[] temp = new int[2];
            MainActivity.getActivity().getWindow().getDecorView().findViewById(android.R.id.content).getLocationInWindow(temp);
            topGap = temp[1];
        }
        target.getLocationOnScreen(OUT_LOCATION);
        rect.left = OUT_LOCATION[0];
        rect.top = OUT_LOCATION[1] - topGap;
        rect.right = rect.left + target.getMeasuredWidth();
        rect.bottom = rect.top + target.getMeasuredHeight();
    }*/
}