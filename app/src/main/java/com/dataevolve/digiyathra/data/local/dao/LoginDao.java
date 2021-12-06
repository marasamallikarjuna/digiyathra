package com.dataevolve.digiyathra.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.dataevolve.digiyathra.data.local.entry.LoginEntity;

@Dao
public interface LoginDao {

    @Insert
    void insertEmployee(LoginEntity... loginEntities);

}
