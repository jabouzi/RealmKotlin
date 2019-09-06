package com.skanderjabouzi.realmkotlin.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class AyaBookmark: RealmObject() {
    @PrimaryKey
    var id: String = ""
    var sura = 0
    var aya = 0

    fun generateId() {
        this.id = this.sura.toString() + "-" + this.aya.toString()
    }
}