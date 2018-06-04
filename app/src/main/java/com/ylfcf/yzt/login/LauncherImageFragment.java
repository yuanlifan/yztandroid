package com.ylfcf.yzt.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.ylfcf.yzt.R;
import com.ylfcf.yzt.base.BaseViewPagerFragment;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * @author guozhangyu  create by 2017/8/29 13:30
 * @Description
 */
public class LauncherImageFragment extends BaseViewPagerFragment {
    @Bind(R.id.img_index)
    ImageView imgIndex;
    @Bind(R.id.iv_enter)
    ImageView ivEnter;

    private int mPosition;

    public LauncherImageFragment() {

    }

    public static LauncherImageFragment newInstance(int position) {
        LauncherImageFragment fragment = new LauncherImageFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onInitPagerData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mPosition = getArguments().getInt("position", 0);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_launcher_image;
    }

    @Override
    protected void initData() {
        setUpViewComponent();
    }

    private void setUpViewComponent() {
        switch (mPosition) {
            case 2:
                imgIndex.setImageResource(R.drawable.app_index_page_2);
                ivEnter.setVisibility(View.GONE);
                break;
            case 3:
                imgIndex.setImageResource(R.drawable.app_index_page_3);
                ivEnter.setVisibility(View.GONE);
                break;
            case 4:
                imgIndex.setImageResource(R.drawable.app_index_page_4);
                ivEnter.setVisibility(View.GONE);
                break;
            case 5:
                imgIndex.setImageResource(R.drawable.app_index_page_5);
                ivEnter.setVisibility(View.VISIBLE);
                break;
            default:
                imgIndex.setImageResource(R.drawable.app_index_page_1);
                ivEnter.setVisibility(View.GONE);
                break;
        }
    }

//    @OnClick(R.id.iv_enter)
//    void onHomeScreen() {
//        if (mPosition == 5) {
//            startActivity(new Intent(mContext, LoadingNextActivity.class));
//            getActivity().finish();
//        }
//    }
}
