package com.dvm.dvmproject8.view.rv_viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import com.dvm.remote_module.entity.ApiConstants
import com.dvm.dvmproject8.API
import com.dvm.remote_module.entity.ApiConstants
import com.dvm.dvmproject8.data.Entity.Film
import com.dvm.dvmproject8.databinding.FilmItemBinding
//import kotlinx.android.synthetic.main.film_item.view.*

//В конструктор класс передается layout, который мы создали(film_item.xml)
class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val filmItemBinding = FilmItemBinding.bind(itemView)
    //Привязываем View из layout к переменным
    private val title = filmItemBinding.title
    private val poster = filmItemBinding.poster
    private val description = filmItemBinding.description
    //Вот здесь мы находим в верстке наш прогресс бар для рейтинга
    private val ratingDonut = filmItemBinding.ratingDonut

    //В этом методе кладем данные из film в наши view
    fun bind(film: Film) {
        //Устанавливаем заголовок
        title.text = film.title
        //Устанавливаем постер
        //Указываем контейнер, в которм будет "жить" наша картинка
        Glide.with(itemView)
            //Загружаем сам ресурс
            .load(ApiConstants.IMAGES_URL + "w342" + film.poster)
            //Центруем изображение
            .centerCrop()
            //Указываем ImageView, куда будем загружать изображение
            .into(poster)
        //Устанавливаем описание
        description.text = film.description
        //устанавливаем рейтинг
        ratingDonut.setProgress((film.rating * 10).toInt())
    }
}