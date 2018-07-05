package com.ylfcf.yzt.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ylfcf.yzt.MainApp;
import com.ylfcf.yzt.http.base.BaseModel;
import com.ylfcf.yzt.utils.HttpResultGsonDeserializer;
import com.ylfcf.yzt.utils.ToastHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *@author yjx on 2017/7/14.
 */

public class HttpManager {
    private static HttpManager         sHttpManager;
    private final  OkHttpClient        mOkHttpClient;
    private final  Gson                mGson;
    private        Handler             mHandler;
    private        Map<String, String> params ;//用来封装请求参数

    private HttpManager() {
        mOkHttpClient = new OkHttpClient();
        //设置超时时间
        mOkHttpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        //设置读取超时
        mOkHttpClient.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        //设置写超时
        mOkHttpClient.newBuilder().writeTimeout(10, TimeUnit.SECONDS);

//        mGson = new Gson();
        mGson = new GsonBuilder()
                .registerTypeAdapter(BaseModel.class, new HttpResultGsonDeserializer())
                .create();
        //创建handler用来将数据操作，设置到主线程中
        mHandler = new Handler(Looper.getMainLooper());
        params = new HashMap<>();

    }

    //懒汉式单例
    static HttpManager getHttpManager() {

        if (sHttpManager == null) {
            synchronized (HttpManager.class) {
                if (sHttpManager == null) {
                    sHttpManager = new HttpManager();
                }
            }
        }

        return sHttpManager;
    }


    public void get(String url, MyCallBack myCallBack) {
        Request request = buildRequest(url, REQUEST_TYPE.GET);
        doRequest(request, myCallBack);

    }

    void post(String url, MyCallBack myCallBack) {
        Request request = buildRequest(url, REQUEST_TYPE.POST);
        doRequest(request, myCallBack);
    }

    HttpManager addParam(String key, String value) {
        params.put(key, value);

        return sHttpManager;
    }

    private enum REQUEST_TYPE {
        GET, POST
    }


    private Request buildRequest(String url, REQUEST_TYPE type) {
        Request.Builder builder = new Request.Builder();
        if (type == REQUEST_TYPE.GET) {
            url = doUrl(url);
            builder.get();
        } else {
            builder.post(buildRequestBody());
        }
        params.clear();
        builder.url(url);
        return builder.build();
    }

    //用来拼接带有参数的get方式的地址
    private String doUrl(String url) {
        if (params == null || params.size() == 0) {
            return url;
        }

        //如果有参数，动态拼接，&k=v
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append("&" + entry.getKey() + "=" + entry.getValue());
        }
        return sb.toString();
    }

    //创建出post方式的请求体对象
    private RequestBody buildRequestBody() {
        FormBody.Builder builder = new FormBody.Builder();
        //遍历map，并设置请求参数
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if(!entry.getValue().equals("")) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }


    //请求代码
    private void doRequest(final Request request, final MyCallBack myCallBack) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailure(call, e, myCallBack);
//                params.clear();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                params.clear();
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    sendSuccess(call, json, myCallBack);
                } else {
                    sendFailure(call,null,myCallBack);
                }
            }
        });
    }

    private void sendSuccess(final Call call, final String json, final MyCallBack myCallBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //gson 解析成String是不可以的
                if (myCallBack.type == String.class) {
                    myCallBack.onSuccess(call, json);
                } else {
                    Log.e("返回json数据",json);
                    Object o = null;
                    try {
                        o = mGson.fromJson(json, myCallBack.type);
                        myCallBack.onSuccess(call, o);
                    }catch(Exception e) {
                        ToastHelper.showAlert(MainApp.getContext(), "数据解析异常");
                    }
                }
            }
        });

    }

    private void sendFailure(final Call call, final IOException e, final MyCallBack myCallBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                myCallBack.onFailure(call, e);
            }
        });
    }

    public void removeAllMessage() {
        if(mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

}
