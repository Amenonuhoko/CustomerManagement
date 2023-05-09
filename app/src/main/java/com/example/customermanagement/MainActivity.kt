package com.example.customermanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DBHelper(this, null)

        val etId = findViewById<EditText>(R.id.etId)
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etMobile = findViewById<EditText>(R.id.etMobile)

        val btnReset = findViewById<Button>(R.id.btnReset)
        btnReset.setOnClickListener {
            db.deleteAll()

            db.addCustomer(1, "Alice", "alice@example.com", "555-1234")
            db.addCustomer(2, "Bob", "bob@example.com", "555-5678")
            db.addCustomer(3, "Charlie", "charlie@example.com", "555-9012")
            db.addCustomer(4, "Dave", "dave@example.com", "555-3456")
            db.addCustomer(5, "Eve", "eve@example.com", "555-7890")
        }

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val id = etId.text.toString().toInt()
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val mobile = etMobile.text.toString()

            db.addCustomer(id, name, email, mobile)

            Toast.makeText(this, "$name added to database", Toast.LENGTH_SHORT).show()

            etId.text.clear()
            etName.text.clear()
            etEmail.text.clear()
            etMobile.text.clear()
        }

        val btnSearch = findViewById<Button>(R.id.btnSearch)
        btnSearch.setOnClickListener {
            val name = etName.text.toString()

            val cursor = db.searchByName(name)

            val tvId = findViewById<TextView>(R.id.tvID)
            val tvName = findViewById<TextView>(R.id.tvName)
            val tvEmail = findViewById<TextView>(R.id.tvEmail)
            val tvMobile = findViewById<TextView>(R.id.tvMobile)

            cursor!!.moveToFirst()
            if (cursor!!.moveToFirst()) {
                tvId.text = cursor.getString(0) + "\n"
                tvName.text = cursor.getString(1) + "\n"
                tvEmail.text = cursor.getString(2) + "\n"
                tvMobile.text = cursor.getString(3) + "\n"
            }
            while (cursor.moveToNext()) {
                tvId.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID)) + "\n")
                tvName.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME)) + "\n")
                tvEmail.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.EMAIL)) + "\n")
                tvMobile.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.MOBILE)) + "\n")
            }
            cursor.close()
        }

        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        btnUpdate.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val mobile = etMobile.text.toString()

            val rows = db.updateCustomer(name, email, mobile)

            Toast.makeText(this, "$rows users updated", Toast.LENGTH_LONG).show()
        }

        val btnDelete = findViewById<Button>(R.id.btnDelete)
        btnDelete.setOnClickListener {
            val id = etId.text.toString().toInt()
            val rows = db.deleteCustomer(id)

            Toast.makeText(this,
                when (rows) {
                    0 -> "Nothing deleted"
                    1 -> "1 user deleted"
                    else -> "" // shouldn't happen
                },
                Toast.LENGTH_LONG).show()
        }

        val btnDisplay = findViewById<Button>(R.id.btnDisplay)
        btnDisplay.setOnClickListener {
            val cursor = db.displayAll()

            val tvId = findViewById<TextView>(R.id.tvID)
            val tvName = findViewById<TextView>(R.id.tvName)
            val tvEmail = findViewById<TextView>(R.id.tvEmail)
            val tvMobile = findViewById<TextView>(R.id.tvMobile)



            cursor!!.moveToFirst()
            if (cursor!!.moveToFirst()) {
                Log.d("DBHelper", cursor!!.getString(0))
                Log.d("DBHelper", cursor!!.getString(1))
                tvId.text = cursor.getString(0) + "\n"
                tvName.text = cursor.getString(1) + "\n"
                tvEmail.text = cursor.getString(2) + "\n"
                tvMobile.text = cursor.getString(3) + "\n"
            }
            while (cursor.moveToNext()) {
                tvId.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID)) + "\n")
                tvName.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME)) + "\n")
                tvEmail.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.EMAIL)) + "\n")
                tvMobile.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.MOBILE)) + "\n")
            }
            cursor.close()
        }
    }
}