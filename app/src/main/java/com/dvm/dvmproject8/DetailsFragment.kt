package com.dvm.dvmproject8

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.DataBindingUtil.setDefaultComponent
import androidx.fragment.app.Fragment
import com.dvm.dvmproject8.databinding.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_details.*
import com.dvm.dvmproject8.databinding.FragmentDetailsBinding
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private lateinit var fragbinding: FragmentDetailsBinding
    private lateinit var film: Film

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragbinding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return fragbinding.root//inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilmsDetails()
        fragbinding.detailsFabFavorites.setOnClickListener {
            if (!film.isInFavorites) {
                fragbinding.detailsFabFavorites.setImageResource(R.drawable.ic_round_favorite)
                film.isInFavorites = true
            } else {
                fragbinding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                film.isInFavorites = false
            }
        }
        fragbinding.detailsFabShare.setOnClickListener {
            //Создаем интент
            val intent = Intent()
            //Укзываем action с которым он запускается
            intent.action = Intent.ACTION_SEND
            //Кладем данные о нашем фильме
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this film: ${film.title} \n\n ${film.description}"
            )
            //УКазываем MIME тип, чтобы система знала, какое приложения предложить
            intent.type = "text/plain"
            //Запускаем наше активити
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
    }


    private fun setFilmsDetails() {

        //Получаем наш фильм из переданного бандла
        film = arguments?.get("film") as Film

        //Устанавливаем заголовок
        fragbinding.detailsToolbar.title = film.title
        //Устанавливаем картинку
        fragbinding.detailsPoster.setImageResource(film.poster)
        //Устанавливаем описание
        fragbinding.detailsDescription.text = film.description
        fragbinding.detailsFabFavorites.setImageResource(
            if (film.isInFavorites) R.drawable.ic_round_favorite
            else R.drawable.ic_baseline_favorite_border_24
        )
    }
}