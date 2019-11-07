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
    private val dbVersion = 1

    private val createTableSql =
            "CREATE TABLE IF NOT EXISTS $dbTable ($colId INTEGER PRIMARY KEY, $colTitle TEXT, $colContent TEXT, $colLocationType INTEGER);"
    private var db: SQLiteDatabase? = null

    init {
        val dbHelper = DatabaseHelper(context)
        db = dbHelper.writableDatabase
    }

    fun insert(values: ContentValues): Long {
        return db!!.insert(dbTable, "", values)
    }

    fun queryAll(): Cursor {
        return db!!.rawQuery("select * from $dbTable", null)
    }

    fun delete(selection: String, selectionArgs: Array<String>): Int {
        return db!!.delete(dbTable, selection, selectionArgs)
    }

    fun deleteAll(): Int {
        return db!!.delete(dbTable, null, null)
    }

    fun update(values: ContentValues, selection: String, selectionargs: Array<String>): Int {

        return db!!.update(dbTable, values, selection, selectionargs)
    }

    fun fillDatabase():Long{
        val ID = insert(createCV(0, "Test 1", "This is a test", 0))
        insert(createCV(1, "Test 2", "This is a test", 1))

        return ID
    }

    private fun createCV(id: Int, title: String, content: String, locationType: Int): ContentValues {
        val aux = ContentValues()
        aux.put("Id", id)
        aux.put("Title", title)
        aux.put("Content", content)
        aux.put("LocationType", locationType)
        return aux
    }

    inner class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, dbName, null, dbVersion) {

        private var context: Context? = context

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(createTableSql)
            Toast.makeText(this.context, " database is created", Toast.LENGTH_LONG).show()
            fillDatabase()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table IF EXISTS $dbTable")
        }
    }
}