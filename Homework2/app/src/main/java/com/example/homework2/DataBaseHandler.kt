package com.example.homework2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast



/*val DATABASE_NAME = "MyDB"
val TABLE_NAME = "User"
val COL_NAME = "name"
val COL_PICTURE = "picture"

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ImageData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}*/
/*class DataBaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_PICTURE + " PICTURE)";

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    /*fun insertData(user : User) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, user.name)
        cv.put(COL_PICTURE, user.picture)
        var result = db.insert(TABLE_NAME, null, cv)

        if (result == -1.toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }*/

}*/