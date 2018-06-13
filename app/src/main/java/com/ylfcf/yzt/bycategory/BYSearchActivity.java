package com.ylfcf.yzt.bycategory;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ylfcf.yzt.R;
import com.ylfcf.yzt.appconfig.AppSpContact;
import com.ylfcf.yzt.base.BaseActivity;
import com.ylfcf.yzt.bycategory.adapter.BySearchTagAdapter;
import com.ylfcf.yzt.utils.SearchUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BYSearchActivity extends BaseActivity {

    @Bind(R.id.et_search)
    EditText      mEtSearch;
    @Bind(R.id.tv_search)
    TextView      mTvSearch;
    @Bind(R.id.tag_flow_layout1)
    TagFlowLayout mTagFlowLayout;
    @Bind(R.id.ll_history_record)
    LinearLayout  mLlHistoryRecord;
    @Bind(R.id.iv_clear_history)
    ImageView mIvClearHistory;


    private List<String> mHistory;
    private BySearchTagAdapter mBySearchTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bysearch);
        ButterKnife.bind(this);
        initHistory();
        initEditText();
    }

    private void initEditText() {
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(TextUtils.isEmpty(charSequence.toString())) {
                    //不显示清除文字图标
                }else {
                    //显示清除文字图标
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    private void initHistory() {
        mHistory = SearchUtils.readHistory(AppSpContact.SP_KEY_SEARCH_HISTORY);
        if(mHistory.size() > 0) {
            mLlHistoryRecord.setVisibility(View.VISIBLE);
            if(mBySearchTagAdapter == null) {
                mBySearchTagAdapter = new BySearchTagAdapter(mHistory, BYSearchActivity.this);
                mTagFlowLayout.setAdapter(mBySearchTagAdapter);
            }
            mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    Toast.makeText(BYSearchActivity.this, mHistory.get(position), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

        }else {
            mLlHistoryRecord.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.tv_search, R.id.iv_clear_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                //TODO 进行搜索，跳转列表页面
                SearchUtils.insertHistory(AppSpContact.SP_KEY_SEARCH_HISTORY, mEtSearch.getText().toString());

                finish();
                break;
            case R.id.iv_clear_history:
                SearchUtils.clearHistory(AppSpContact.SP_KEY_SEARCH_HISTORY);
                initHistory();
                break;
        }

    }

}
