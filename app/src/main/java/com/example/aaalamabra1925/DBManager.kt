package com.example.aaalamabra1925

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DbManager(context: Context) {

    private val dbName = "Aaalamabra1925"
    private val dbTable = "InteresPoint"
    private val colId = "Id"
    private val colTitle = "Title"
    private val colContent = "Content"
    private val colLocationType = "LocationType"
    private val colLat = "Latitude"
    private val colLong = "Longitude"
    private val dbVersion = 1

    private val createTableSql =
            "CREATE TABLE IF NOT EXISTS $dbTable ($colId INTEGER PRIMARY KEY, " +
                    "$colTitle TEXT, " +
                    "$colContent TEXT, " +
                    "$colLocationType INTEGER, " +
                    "$colLat REAL, " +
                    "$colLong REAL);"
    private var db: SQLiteDatabase? = null

    init {
        val dbHelper = DatabaseHelper(context)
        db = dbHelper.writableDatabase
    }

    private fun insert(values: ContentValues): Long {
        return db!!.insert(dbTable, "", values)
    }

    fun queryById(id: Int): Cursor {
        return db!!.rawQuery("select * from $dbTable where $colId = $id", null)
    }

    fun queryAll(): Cursor {
        return db!!.rawQuery("select * from $dbTable", null)
    }

    // Returns a cursor over all elements with the given location type.
    fun queryByLocationType(type: Int): Cursor {
        return db!!.rawQuery("select * from $dbTable where $colLocationType = $type", null)
    }

    private fun delete(selection: String, selectionArgs: Array<String>): Int {
        return db!!.delete(dbTable, selection, selectionArgs)
    }

    fun deleteAll(): Int {
        return db!!.delete(dbTable, null, null)
    }

    fun update(values: ContentValues, selection: String, selectionargs: Array<String>): Int {

        return db!!.update(dbTable, values, selection, selectionargs)
    }

    fun fillDatabase(): Long{
        val id = insert(createCV(1, "Outside 1", "o1 content", 0, 37.197152, -3.624137))
        insert(createCV(2, "Outside 2", "o2 content", 0,37.29715, -3.624137))
        insert(createCV(3, "Outside 3", "o3 content", 0, 37.157152, -3.624137))
        insert(createCV(4, "Outside 4", "o4 content", 0, 37.317152, -3.624137))
        insert(createCV(5, "Outside 5", "o5 content", 0, 37.127152, -3.624137))
        insert(createCV(6, "Test 1", "This is a test", 1, 0.0, 0.0))
        insert(createCV(7, "Test 2", "This is a test", 1,0.0,0.0))
        insert(createCV(8, "Test 3", "This is a test", 1,0.0 ,0.0))
        insert(createCV(9, "Test 4", "This is a test", 1,0.0 ,0.0))
        
        return id
    }

    private fun createCV(id: Int, title: String, content: String, locationType: Int, latitude: Double, longitude: Double): ContentValues {
        val aux = ContentValues()
        aux.put("Id", id)
        aux.put("Title", title)
        aux.put("Content", content)
        aux.put("LocationType", locationType)
        aux.put("Latitude", latitude)
        aux.put("Longitude", longitude)
        return aux
    }

    inner class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, dbName, null, dbVersion) {

        private var context: Context? = context

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(createTableSql)
            Toast.makeText(this.context, " database is created", Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table IF EXISTS $dbTable")
            Toast.makeText(this.context, "Dropped table $dbTable", Toast.LENGTH_LONG).show()
        }
    }
}