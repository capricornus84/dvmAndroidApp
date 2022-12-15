package com.dvm.dvmproject8.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dvm.dvmproject8.view.rv_adapters.FilmListRecyclerAdapter
import com.dvm.dvmproject8.view.MainActivity
//import com.dvm.dvmproject8.TopSpacingItemDecoration
import com.dvm.dvmproject8.databinding.FragmentFavoritesBinding
import com.dvm.dvmproject8.data.Entity.Film
import com.dvm.dvmproject8.utils.AnimationHelper
//import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : Fragment() {

    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var favFragBinding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("onCreateView fav")
        // Inflate the layout for this fragment
        favFragBinding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return favFragBinding.root
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            //Получаем список при транзакции фрагмента
            val favoritesList: List<Film> = emptyList()
            println("onViewCreated fav1")
            AnimationHelper.performFragmentCircularRevealAnimation(favFragBinding.favoritesFragmentRoot, requireActivity(),2)
            println("onViewCreated fav2")
            favFragBinding.favoritesRecycler.apply {
                filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })
                //Присваиваем адаптер
                adapter = filmsAdapter
                //Присвои layoutmanager
                layoutManager = LinearLayoutManager(requireContext())
                //Применяем декоратор для отступов
                val decorator = TopSpacingItemDecoration(8)
                addItemDecoration(decorator)
            }
            //Кладем нашу БД в RV
            filmsAdapter.addItems(favoritesList)
        }

}