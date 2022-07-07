package com.dvm.dvmproject8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        button1.setOnClickListener {
            Toast.makeText(this, "Профиль", Toast.LENGTH_SHORT).show()
        }
        button2.setOnClickListener {
            Toast.makeText(this, "@Сегодня в кино", Toast.LENGTH_SHORT).show()
        }
        button3.setOnClickListener {
            Toast.makeText(this, "Буду смотреть", Toast.LENGTH_SHORT).show()
        }
        button4.setOnClickListener {
            Toast.makeText(this, "Загрузки", Toast.LENGTH_SHORT).show()
        }
        button5.setOnClickListener {
            Toast.makeText(this, "О приложении", Toast.LENGTH_SHORT).show()
        }*/
        val topAppBar = findViewById<MaterialToolbar>(R.id.settings)
        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        //val bottom_navigation: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottom_navigation.setOnNavigationItemSelectedListener {
        //bottom_navigation.OnItemSelectedListener {

            when (it.itemId) {
                R.id.favorites -> {
                    Toast.makeText(this, "Избранное", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.watch_later -> {
                    Toast.makeText(this, "Посмотреть позже", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.selections -> {
                    Toast.makeText(this, "Подборки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}