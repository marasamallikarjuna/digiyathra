package com.dataevolve.digiyathra.data.local.entry;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "login_table")
public class LoginEntity {
    @Override
    public String toString() {
        return "Login{" +
                "mKey='" + mKey + '\'' +
                '}';
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pin")
    private String mKey;

    public LoginEntity(@NonNull String key) {
        this.mKey = key;
    }

    @NonNull
    public String getKey() {
        return this.mKey;
    }
}