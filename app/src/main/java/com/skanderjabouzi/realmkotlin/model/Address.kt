package com.skanderjabouzi.realmkotlin.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Address : RealmObject() {
    @PrimaryKey
    var name: String = ""
    var number: Int = 0
}