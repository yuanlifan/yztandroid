package com.ylfcf.yzt.personcenter.itemfragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ylfcf.yzt.R;
import com.ylfcf.yzt.base.BaseViewPagerFragment;
import com.ylfcf.yzt.personcenter.adapter.PersonalOrderItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yjx on 2018/7/2.
 */
public class PersonalOrderItemFragment extends BaseViewPagerFragment {

    @Bind(R.id.recycler_view)
    RecyclerView      mRecyclerView;
    @Bind(R.id.easylayout)
    EasyRefreshLayout mEasylayout;

    private PersonalOrderItemAdapter mOrderItemAdapter;
    private List<String> mList = new ArrayList<>();
    private int          page  = 0;

    @Override
    protected void onInitPagerData() {

    }

    @Override
    protected void initData() {
        mEasylayout.setLoadMoreModel(LoadModel.ADVANCE_MODEL);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        if (mOrderItemAdapter == null)
            mOrderItemAdapter = new PersonalOrderItemAdapter(R.layout.item_order_form_list, mList);
        mOrderItemAdapter.openLoadAnimation();
        mOrderItemAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mOrderItemAdapter.setNotDoAnimationCount(3);//第一屏不显示动画
//        mOrderItemAdapter.onItemViewClick(this);
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                page++;
                //加载更多
                onDataMore();
            }

            @Override
            public void onRefreshing() {
                //下拉刷新
                onDataRefresh();
            }
        });
        mRecyclerView.setAdapter(mOrderItemAdapter);
        onDataRefresh();
    }

    private void onDataRefresh() {
        mList.clear();
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mOrderItemAdapter.notifyDataSetChanged();
        mEasylayout.refreshComplete();
    }

    private void onDataMore() {
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mOrderItemAdapter.notifyDataSetChanged();
        mEasylayout.loadMoreComplete();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_personal_order_form;
    }

}
