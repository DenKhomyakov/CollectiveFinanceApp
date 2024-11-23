package com.c.collectivefinanceapp.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

@Entity(tableName = "accumulation_table")
public class Accumulation implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private double amount;

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

    public Accumulation(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    protected Accumulation(Parcel in) {
        id = in.readInt();
        name = in.readString();
        amount = in.readDouble();
    }

    public static final Creator<Accumulation> CREATOR = new Creator<Accumulation>() {
        @Override
        public Accumulation createFromParcel(Parcel in) {
            return new Accumulation(in);
        }

        @Override
        public Accumulation[] newArray(int size) {
            return new Accumulation[size];
        }
    };

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
