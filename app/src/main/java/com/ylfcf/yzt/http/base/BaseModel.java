package com.ylfcf.yzt.http.base;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * @author yangjinxin  create by 2017/8/22 19:41
 * @Description
 */
public class BaseModel implements Parcelable {
    public String message;//描述信息
    public int    code;

    public static final Creator<BaseModel> CREATOR = new Creator<BaseModel>() {
        @Override
        public BaseModel createFromParcel(Parcel in) {
            return new BaseModel(in);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public BaseModel() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeInt(this.code);
    }

    protected BaseModel(Parcel in) {
        this.message = in.readString();
        this.code = in.readInt();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BaseModel{");
        sb.append("message='").append(message).append('\'');
        sb.append(", code=").append(code);
        sb.append('}');
        return sb.toString();
    }
}
