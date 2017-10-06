package com.binghui.binghuiliu.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by binghuiliu on 05/10/2017.
 */

public class Ingredient implements Parcelable {
    public String quantity;
    @SerializedName("ingredient")
    public String name;
    public String measure;

    public Ingredient() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.quantity);
        dest.writeString(this.name);
        dest.writeString(this.measure);
    }

    protected Ingredient(Parcel in) {
        this.quantity = in.readString();
        this.name = in.readString();
        this.measure = in.readString();
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
