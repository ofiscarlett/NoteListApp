package com.example.notelistapp.model

import android.icu.text.CaseMap.Title
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Parcelize
data class Note(
    //create database standard
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    //save two things
    val noteTitle: String,
    val noteDesc:String,
): Parcelable
