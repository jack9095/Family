//package com.maxxipoint.layoutmanager.widget;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import com.bumptech.glide.request.RequestOptions;
//import com.maxxipoint.layoutmanager.R;
//
//public class SimpleOverlayAdapter extends BaseOverlayPageAdapter {
//    private LayoutInflater mInflater;
//
//    public SimpleOverlayAdapter(Context context) {
//        super(context, new RequestOptions().error(R.mipmap.header_icon_1).placeholder(R.mipmap.header_icon_1));
//        mInflater = LayoutInflater.from(context);
//    }
//
//    public SimpleOverlayAdapter(Context context, RequestOptions imageOptions) {
//        super(context, imageOptions);
//        mInflater = LayoutInflater.from(context);
//    }
//
//    @Override
//    protected View itemView() {
//        return mInflater.inflate(R.layout.item_slide, null);
//    }
//}
