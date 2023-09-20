package com.example.sbar_2

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.support.v7.widget.Toolbar

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var buttonDefault: Button? = null
    private var buttonCallAction: Button? = null
    private var buttonCustom: Button? = null
    private lateinit var sharedPreferences: SharedPreferences
    private var customButtonClickCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(this)

        // получаем корневой RelativeLayout в макете content_main.xml
        val contentMainLayout: View = findViewById(R.id.content_main_layout)
        buttonDefault = contentMainLayout.findViewById(R.id.button_default) as Button
        buttonCallAction = contentMainLayout.findViewById(R.id.button_call_action) as Button
        buttonCustom = contentMainLayout.findViewById(R.id.button_custom) as Button

        // вешаем слушатели
        buttonDefault!!.setOnClickListener(this)
        buttonCallAction!!.setOnClickListener(this)
        buttonCustom!!.setOnClickListener(this)

        // Инициализация SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        // Загрузка количества нажатий на кнопку custom из SharedPreferences
        customButtonClickCount = sharedPreferences.getInt("customButtonClickCount", 0)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_default -> {
                Snackbar.make(view, "Стандартный SnackBar", Snackbar.LENGTH_LONG).show()
            }
            R.id.button_call_action -> {
                val snackbar: Snackbar = Snackbar.make(view, "Вы изменили что-то", Snackbar.LENGTH_LONG)
                    .setAction("Вернуть как было?") {
                        Snackbar.make(view, "Все вернулось на свои места!", Snackbar.LENGTH_SHORT).show()
                    }
                snackbar.show()
            }
            R.id.button_custom -> {
                // Увеличиваем количество нажатий на кнопку custom и сохраняем в SharedPreferences
                customButtonClickCount++
                with(sharedPreferences.edit()) {
                    putInt("customButtonClickCount", customButtonClickCount)
                    apply()
                }

                // Виджет Snackbar с кастомной компоновкой
                val snackbar: Snackbar = Snackbar.make(view, "Повторите еще раз! (Нажато $customButtonClickCount раз)", Snackbar.LENGTH_LONG)
                    .setAction("Повторить") { }
                snackbar.setActionTextColor(Color.CYAN)

                val textView = snackbar.view.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView

                textView.setTextColor(Color.LTGRAY)
                snackbar.show()
            }
            R.id.fab -> {
                // Выводим количество нажатий на кнопку custom при нажатии на fab
                Snackbar.make(view, "Кнопка Custom нажата $customButtonClickCount раз", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}