package com.ylfcf.yzt.home.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ylfcf.yzt.R;
import com.ylfcf.yzt.base.BaseFragment;
import com.ylfcf.yzt.home.adapter.YztShoppingTrolleyAdapter;
import com.ylfcf.yzt.personcenter.adapter.PersonalAdressListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class YztShoppingFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, YztShoppingTrolleyAdapter.OnChooseAllChangeListener {


    @Bind(R.id.recycler_view)
    RecyclerView      mRecyclerView;
    @Bind(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @Bind(R.id.cb_choose_all)
    CheckBox          mCbChooseAll;
    @Bind(R.id.tv_money)
    TextView          mTvMoney;

    private YztShoppingTrolleyAdapter mTrolleyAdapter;
    private List<String> mList = new ArrayList<>();
    private int          page  = 0;
    private boolean isOpen = true;

    @Override
    protected void initData() {
        mList.add("0");
        mList.add("0");
        mList.add("0");
        mList.add("0");
        mList.add("0");
        mList.add("0");
        mEasylayout.setLoadMoreModel(LoadModel.ADVANCE_MODEL);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        if (mTrolleyAdapter == null)
            mTrolleyAdapter = new YztShoppingTrolleyAdapter(R.layout.item_trolley_list, mList);
        mTrolleyAdapter.setOnChooseAllChangeListener(this);
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                page++;
//                onDataMore();
            }

            @Override
            public void onRefreshing() {
                onDataRefresh();
            }
        });
        mRecyclerView.setAdapter(mTrolleyAdapter);
        onDataRefresh();
        mTrolleyAdapter.setOnItemClickListener(this);
        mCbChooseAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isOpen) {
                    if(isChecked) {
                        mTrolleyAdapter.setAllChoose();
                    }else {
                        mTrolleyAdapter.setAllUnchoose();
                    }
                }else {
                    isOpen = true;
                }
            }
        });

    }

    private void onDataRefresh() {
        mEasylayout.refreshComplete();
    }

    private void onDataMore() {
        mList.add("0");
        mList.add("0");
        mList.add("0");
        mList.add("0");
        mTrolleyAdapter.notifyDataSetChanged();
        mEasylayout.loadMoreComplete();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_yzt_shopping;
    }

    @OnClick({R.id.tv_delete, R.id.tv_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_delete:

                break;
            case R.id.tv_buy:

                break;
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void isChooseAll(boolean isAllChoose) {
        if(isAllChoose) {
            mCbChooseAll.setChecked(true);
        }else {
            isOpen = false;
            mCbChooseAll.setChecked(false);
        }
    }
}
