package com.c.collectivefinanceapp.data.entities;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

@Entity(tableName = "expense_table")
public class Expense implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String source;

    private double amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Expense(String source, double amount) {
        this.source = source;
        this.amount = amount;
    }

    protected Expense(Parcel in) {
        id = in.readInt();
        source = in.readString();
        amount = in.readDouble();
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(source);
        parcel.writeDouble(amount);
    }
}
