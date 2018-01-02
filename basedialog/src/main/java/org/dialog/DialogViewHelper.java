package org.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * description：
 * <p/>
 * Created by TIAN FENG on 2017/10/23
 * QQ：27674569
 * Email: 27674569@qq.com
 * Version：1.0
 */
class DialogViewHelper {

    private View mContentView = null;

    private Dialog mDialog;


    // 防止泄露
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper(Context context, int layoutResId, Dialog dialog) {
        this();
        mDialog  = dialog;
        mContentView = LayoutInflater.from(context).inflate(layoutResId, null);
    }


    public DialogViewHelper() {
        mViews = new SparseArray<>();
    }

    /**
     * 设置布局View
     *
     * @param contentView
     */
    public void setContentView(View contentView) {
        this.mContentView = contentView;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        // 每次都 findViewById   减少findViewById的次数
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(text);
        }
    }

    public <T extends View> T getView(int viewId) {
        WeakReference<View> viewReference = mViews.get(viewId);
        // 侧漏的问题
        View view = null;
        if (viewReference != null) {
            view = viewReference.get();
        }

        if (view == null) {
            view = mContentView.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId, new WeakReference<>(view));
            }
        }
        return (T) view;
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param listener
     */
    public void setOnclickListener(int viewId, final AlertDialog.DialogClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v,mDialog);
                }
            });
        }
    }

    /**
     * 获取ContentView
     *
     * @return
     */
    public View getContentView() {
        return mContentView;
    }
}
