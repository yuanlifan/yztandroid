package com.ylfcf.yzt.base;


/**
 * @author guozhangyu  create by 2017/8/29 13:20
 * @Description
 */
public abstract class BaseViewPagerFragment extends BaseFragment {
    public boolean isInitFinish = false;//是否已初始化

    /**
     * 如果Fragment没有初始化，那么初始化Fragment
     */
    public void initPagerFragment() {
//        if (!isInitFinish) {
//            isInitFinish = true;
            onInitPagerData();
//        }
    }

    /**
     * 设置Framgment是否初始化
     *
     * @param bRefresh
     */
    public void setInitFinish(boolean bRefresh) {
        isInitFinish = bRefresh;
    }

    /**
     * 初始化Fragment抽象类
     */
    protected abstract void onInitPagerData();//初始化数据

}
