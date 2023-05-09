package com.example.customermanagement

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "smtbiz"
        private const val DB_VERSION = 1
        const val TABLE_NAME = "customer"
        const val ID = "id"
        const val NAME = "name"
        const val EMAIL = "email"
        const val MOBILE = "mobile"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = (
                "CREATE TABLE $TABLE_NAME (" +
                        "$ID INT PRIMARY KEY," +
                        "$NAME TEXT," +
                        "$EMAIL TEXT," +
                        "$MOBILE TEXT" +
                        ")"
                )
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addCustomer(id: Int, name: String, email: String, mobile:String) {
        val values = ContentValues()

        val db = this.writableDatabase

        db.beginTransaction()
        try {
            values.put(ID, id)
            values.put(NAME, name)
            values.put(EMAIL, email)
            values.put(MOBILE, mobile)
            db.insert(TABLE_NAME, null, values)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        db.close()
    }

    fun searchByName(name: String): Cursor? {
        val db = this.readableDatabase

        val cursor: Cursor
        db.beginTransaction()
        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $NAME =?", arrayOf(name))
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }

        return cursor
    }

    fun updateCustomer(name: String, email: String, mobile: String): Int {
        val values = ContentValues()

        val db = this.writableDatabase

        db.beginTransaction()
        var rows = 0
        try {
            values.put(EMAIL, email)
            values.put(MOBILE, mobile)
            rows = db.update(TABLE_NAME, values, "name=?", arrayOf(name))
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        db.close()
        return rows
    }

    fun deleteCustomer(id: Int): Int {
        val db = this.writableDatabase

        db.beginTransaction()
        var rows = 0
        try {
            rows = db.delete(TABLE_NAME, "id=?", arrayOf(id.toString()))
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        db.close()
        return rows
    }

    fun displayAll(): Cursor? {
        val db = this.readableDatabase
        var cursor: Cursor? = null
        db.beginTransaction()
        try {
            cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }

        return cursor
    }


    fun deleteAll(): Int {
        val db = this.writableDatabase

        db.beginTransaction()
        var rows = 0
        try {
            rows = db.delete(TABLE_NAME,null, null)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        db.close()
        return rows
    }
}