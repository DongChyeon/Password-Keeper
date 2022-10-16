package com.dongchyeon.passwordkeeper.data.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Memos")
data class Memo(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "uid") var uid: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "memo") var memo: String
) : Parcelable {
    constructor(title: String, category: String, uid: String, password: String, memo: String) :
            this(0, title, category, uid, password, memo)
}