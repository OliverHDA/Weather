package ru.oliverhd.weather.db.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"city", "temperature", "date"})})
public class SearchHistory {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "temperature")
    public String temperature;

    @ColumnInfo(name = "date")
    public String date;
}

