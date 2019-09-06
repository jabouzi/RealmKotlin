/*
 * Copyright 2014 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skanderjabouzi.realmkotlin

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

import io.realm.Realm
import io.realm.RealmConfiguration
import com.skanderjabouzi.realmkotlin.model.Migration
import com.skanderjabouzi.realmkotlin.model.Person

/*
** This example demonstrates how you can migrate your data through different updates
** of your models.
*/
class MigrationExampleActivity : Activity() {

    private var rootLayout: LinearLayout? = null
    private var realm: Realm? = null
    private var realmVersion = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realm_migration_example)

        rootLayout = findViewById<LinearLayout>(R.id.container)
        rootLayout!!.removeAllViews()

        // 3 versions of the databases for testing. Normally you would only have one.
//        copyBundledRealmFile(this.resources.openRawResource(R.raw.default0), "default0.realm")
//        copyBundledRealmFile(this.resources.openRawResource(R.raw.default1), "default1.realm")
//        copyBundledRealmFile(this.resources.openRawResource(R.raw.default2), "default2.realm")

        // When you create a RealmConfiguration you can specify the version of the schema.
        // If the schema does not have that version a RealmMigrationNeededException will be thrown.
//        val config0 = RealmConfiguration.Builder()
//            .name("default0.realm")
//            .schemaVersion(realmVersion.toLong())
//            .build()
//
//        // You can then manually call Realm.migrateRealm().
//        try {
//            Realm.migrateRealm(config0, Migration())
//        } catch (ignored: FileNotFoundException) {
//            // If the Realm file doesn't exist, just ignore.
//        }
//
//        realm = Realm.getInstance(config0)
//        showStatus("Default0")
//        showStatus(realm)
//        realm!!.close()

        // Or you can add the migration code to the configuration. This will run the migration code without throwing
        // a RealmMigrationNeededException.
        val config1 = RealmConfiguration.Builder()
            .name("default.realm")
            .schemaVersion(realmVersion.toLong())
            .migration(Migration())
            .build()

        realm = Realm.getInstance(config1) // Automatically run migration if needed
        showStatus("Default")
        showStatus(realm)
        realm!!.close()

//        // or you can set .deleteRealmIfMigrationNeeded() if you don't want to bother with migrations.
//        // WARNING: This will delete all data in the Realm though.
//        val config2 = RealmConfiguration.Builder()
//            .name("default2.realm")
//            .schemaVersion(realmVersion.toLong())
//            .deleteRealmIfMigrationNeeded()
//            .build()
//
//        realm = Realm.getInstance(config2)
//        showStatus("default2")
//        showStatus(realm)
//        realm!!.close()
    }

//    private fun copyBundledRealmFile(inputStream: InputStream, outFileName: String): String? {
//        try {
//            val file = File(this.filesDir, outFileName)
//            val outputStream = FileOutputStream(file)
//            val buf = ByteArray(1024)
//            var bytesRead: Int
//            do {
//
//                bytesRead = inputStream.read(buf)
//
//                if (bytesRead <= 0)
//
//                    break
//
//            } while (true)
//            outputStream.close()
//            return file.absolutePath
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//        return null
//    }

    private fun realmString(realm: Realm): String {
        val stringBuilder = StringBuilder()
        for (person in realm.where(Person::class.java).findAll()) {
            stringBuilder.append(person.toString()).append("\n")
        }

        return if (stringBuilder.length == 0) "<data was deleted>" else stringBuilder.toString()
    }

    private fun showStatus(realm: Realm?) {
        showStatus(realmString(realm!!))
    }

    private fun showStatus(txt: String) {
        Log.i(TAG, txt)
        val tv = TextView(this)
        tv.text = txt
        rootLayout!!.addView(tv)
    }

    companion object {
        val TAG = MigrationExampleActivity::class.java.name
    }
}
