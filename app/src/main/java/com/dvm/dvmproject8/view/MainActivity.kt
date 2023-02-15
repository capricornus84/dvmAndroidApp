package com.dvm.dvmproject8.view

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat.animate
import androidx.fragment.app.Fragment
import com.dvm.dvmproject8.App
import com.dvm.dvmproject8.R
import com.dvm.dvmproject8.databinding.ActivityMainBinding
import com.dvm.dvmproject8.data.Entity.Film
import com.dvm.dvmproject8.receivers.ConnectionChecker
import com.dvm.dvmproject8.view.fragments.*
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

//import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    private lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()

        //Зупускаем фрагмент при старте
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null)
            .commit()
        receiver = ConnectionChecker()
        val filters = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_BATTERY_LOW)
        }
        registerReceiver(receiver, filters)

        if (!App.instance.isPromoShown) {
            //Получаем доступ к Remote Config
            val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
            //Устанавливаем настройки
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build()
            firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
            //Вызываем метод, которые получит данные с сервера и вешаем слушатель
            firebaseRemoteConfig.fetch()
                .addOnCompleteListener {
                    //Если все получилось успешно
                    if (it.isSuccessful) {
                        //активируем последний полученный конфиг с сервера
                        firebaseRemoteConfig.activate()
                        //Получаем ссылку
                        val filmLink = firebaseRemoteConfig.getString("film_link")
                        //Если поле не пустое
                        if (filmLink.isNotBlank()) {
                            //Ставим флаг что уже промо показали
                            App.instance.isPromoShown = true
                            //Включаем промо верстку
                            binding.promoViewGroup.apply {
                                //Делаем видимой
                                visibility = View.VISIBLE
                                //Анимируем появление
                                animate()
                                    .setDuration(1500)
                                    .alpha(1f)
                                    .start()
                                //Вызываем метод, который загрузит постер в ImageView
                                setLinkForPoster(filmLink)
                                //Кнопка, по нажатии на которую промо убереться(желательно сделать отдельную кнопку с крестиком)
                                watchButton.setOnClickListener {
                                    visibility = View.GONE
                                }
                            }
                        }
                    }
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    fun launchDetailsFragment(film: Film) {
        //Создаем "посылку"
        val bundle = Bundle()
        //Кладем наш фильм в "посылку"
        bundle.putParcelable("film", film)
        //Кладем фрагмент с деталями в перменную
        val fragment = DetailsFragment()
        //Прикрепляем нашу "посылку" к фрагменту
        fragment.arguments = bundle

        //Запускаем фрагмент
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun initNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
        //bottom_navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.home -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    //В первом параметре, если фрагмент не найден и метод вернул null, то с помощью
                    //элвиса мы вызываем создание нвого фрагмента
                    changeFragment( fragment?: HomeFragment(), tag)
                    true
                }
                R.id.favorites -> {
                    val tag = "favorites"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: FavoritesFragment(), tag)
                    true
                }
                R.id.watch_later -> {
                    val tag = "watch_later"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: WatchLaterFragment(), tag)
                    true
                }
                R.id.selections -> {
                    val tag = "selections"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: SelectionsFragment(), tag)
                    true
                }
                R.id.settings -> {
                    val tag = "settings"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment( fragment?: SettingsFragment(), tag)
                    true
                }
                else -> false
            }
        }

    }

    //Ищем фрагмент по тэгу, если он есть то возвращаем его, если нет - то null
    private fun checkFragmentExistence(tag: String): Fragment? = supportFragmentManager.findFragmentByTag(tag)

    private fun changeFragment(fragment: Fragment, tag: String) {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment, tag)
            .addToBackStack(null)
            .commit()
    }
}