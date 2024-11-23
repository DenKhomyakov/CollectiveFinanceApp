package com.c.collectivefinanceapp.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

@Entity(tableName = "regular_payment_table")
public class RegularPayment implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private double amount;

    protected RegularPayment(Parcel in) {
        id = in.readInt();
        name = in.readString();
        amount = in.readDouble();
    }

    public static final Creator<RegularPayment> CREATOR = new Creator<RegularPayment>() {
        @Override
        public RegularPayment createFromParcel(Parcel in) {
            return new RegularPayment(in);
        }

        @Override
        public RegularPayment[] newArray(int size) {
            return new RegularPayment[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public RegularPayment(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(amount);
    }
}
