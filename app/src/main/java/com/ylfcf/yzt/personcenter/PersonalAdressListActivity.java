package com.ylfcf.yzt.personcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ylfcf.yzt.R;
import com.ylfcf.yzt.base.BaseActivity;
import com.ylfcf.yzt.personcenter.adapter.PersonalAdressListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalAdressListActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {


    @Bind(R.id.tv_title)
    TextView          mTvTitle;
    @Bind(R.id.recycler_view)
    RecyclerView      mRecyclerView;
    @Bind(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @Bind(R.id.ll_bottom)
    LinearLayout      mLl_bottom;
    @Bind(R.id.tv_finish)
    TextView          mTv_finish;

    private PersonalAdressListAdapter mAdressListAdapter;
    private List<String> mList = new ArrayList<>();
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_adress_list);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        mTvTitle.setText("管理收货地址");
//        initEmpty();
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mList.add("1");
        mEasylayout.setLoadMoreModel(LoadModel.ADVANCE_MODEL);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (mAdressListAdapter == null)
            mAdressListAdapter = new PersonalAdressListAdapter(R.layout.item_adress_list, mList);
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
        mRecyclerView.setAdapter(mAdressListAdapter);
        onDataRefresh();
        mAdressListAdapter.setOnItemClickListener(this);
    }

    private void onDataRefresh() {
        mEasylayout.refreshComplete();
    }

    private void onDataMore() {
        mList.add("1");
        mAdressListAdapter.notifyDataSetChanged();
        mEasylayout.loadMoreComplete();
    }


    @OnClick({R.id.rl_toptitlebar_back, R.id.tv_editor, R.id.tv_add_address, R.id.tv_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_toptitlebar_back:
                finish();
                break;

            case R.id.tv_editor:
                mLl_bottom.setVisibility(View.GONE);
                mTv_finish.setVisibility(View.VISIBLE);
                mAdressListAdapter.initEditor();

                break;
            case  R.id.tv_finish:
                mLl_bottom.setVisibility(View.VISIBLE);
                mTv_finish.setVisibility(View.GONE);
                mAdressListAdapter.closeEditor();

                break;

            case R.id.tv_add_address:
//                mList.add("6");
//                mAdressListAdapter.notifyDataSetChanged();
                startActivity(new Intent(PersonalAdressListActivity.this, PersonalAdressActivity.class));
                break;
        }

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(new Intent(PersonalAdressListActivity.this, PersonalAdressActivity.class));
    }

}
