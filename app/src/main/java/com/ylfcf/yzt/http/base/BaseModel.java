package com.ylfcf.yzt.http.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author yangjinxin  create by 2017/8/22 19:41
 * @Description
 */
public class BaseModel implements Parcelable {

    /**
     * time : 2018-06-21 13:35:09
     * error_id : 0
     * error :
     */

    private String time;
    private int    error_id;
    private String error;

    public BaseModel() {

    }

    protected BaseModel(Parcel in) {
        time = in.readString();
        error_id = in.readInt();
        error = in.readString();
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getError_id() {
        return error_id;
    }

    public void setError_id(int error_id) {
        this.error_id = error_id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(time);
        parcel.writeInt(error_id);
        parcel.writeString(error);
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "time='" + time + '\'' +
                ", error_id=" + error_id +
                ", error='" + error + '\'' +
                '}';
    }

}
