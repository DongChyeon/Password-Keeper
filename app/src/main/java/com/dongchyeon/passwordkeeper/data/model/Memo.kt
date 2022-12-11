package com.dongchyeon.passwordkeeper.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Memos")
data class Memo(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "uid") var uid: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "memo") var memo: String
) {
    constructor(title: String, category: String, uid: String, password: String, memo: String) :
            this(0, title, category, uid, password, memo)
}