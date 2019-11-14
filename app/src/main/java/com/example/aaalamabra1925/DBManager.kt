package com.example.aaalamabra1925

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

/*
This class uses SQLite API to connect with a database where all the interest points are stored.
 */
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

    // Inserts a given Interest Point from a 'ContentValues' object
    private fun insert(values: ContentValues): Long {
        return db!!.insert(dbTable, "", values)
    }
    // Returns an iterable cursor over the interest point with the given id.
    fun queryById(id: Int): Cursor {
        return db!!.rawQuery("select * from $dbTable where $colId = $id", null)
    }
    // Returns an iterable cursor over all interest points.
    fun queryAll(): Cursor {
        return db!!.rawQuery("select * from $dbTable", null)
    }
    // Returns an iterable cursor over all elements with the given location type.
    fun queryByLocationType(type: Int): Cursor {
        return db!!.rawQuery("select * from $dbTable where $colLocationType = $type", null)
    }

    // Deletes entries in the database. Unused in this project.
    private fun delete(selection: String, selectionArgs: Array<String>): Int {
        return db!!.delete(dbTable, selection, selectionArgs)
    }

    // Clears the database. Used for debugging.
    fun deleteAll(): Int {
        return db!!.delete(dbTable, null, null)
    }

    // Updates values in the database. Unused in this project.
    fun update(values: ContentValues, selection: String, selectionargs: Array<String>): Int {
        return db!!.update(dbTable, values, selection, selectionargs)
    }

    // Fills the database with the given interest points
    fun fillDatabase(): Long{
        val id = insert(createCV(1, "Outside 1", "o1 content", 0, 37.19729, -3.623131))
        insert(createCV(2, "Outside 2", "o2 content", 0,37.19731, -3.624033))
        insert(createCV(3, "Outside 3", "o3 content", 0, 37.19747, -3.624435))
        insert(createCV(4, "Outside 4", "o4 content", 0, 37.19755, -3.624337))
        insert(createCV(5, "Outside 5", "o5 content", 0, 37.19762, -3.624240))
        insert(createCV(6, "Test 1", "This is a test", 1, 100.0, 100.0))
        insert(createCV(7, "Test 2", "This is a test", 1,800.0,800.0))
        insert(createCV(8, "Test 3", "This is a test", 2,100.0 ,100.0))
        insert(createCV(9, "Test 4", "This is a test", 2,800.0 ,800.0))
        insert(createCV(10, "Puerta ciencias", "This is a test", 0,37.179537 ,-3.609248))
        insert(createCV(11, "Mecenas", "This is a test", 0,37.181097 ,-3.609929))
        insert(createCV(12, "Puerta parking", "This is a test", 0,37.178909 ,-3.610171))

        return id
    }

    // Creates a contentValues with the given data from an interest points.
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

    // Database helper class used to handle database creation and updates.
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