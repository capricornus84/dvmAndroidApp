package com.dvm.dvmproject8.view.fragments

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dvm.dvmproject8.databinding.FragmentHomeBinding
import com.dvm.dvmproject8.data.Entity.Film
import com.dvm.dvmproject8.utils.AnimationHelper
import com.dvm.dvmproject8.utils.AutoDisposable
import com.dvm.dvmproject8.utils.addTo
import com.dvm.dvmproject8.view.MainActivity
import com.dvm.dvmproject8.view.rv_adapters.FilmListRecyclerAdapter
import com.dvm.dvmproject8.viewmodel.HomeFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers

import java.util.*
import java.util.concurrent.TimeUnit
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy


class HomeFragment : Fragment() {
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    //private lateinit var adapter: FilmListRecyclerAdapter
    //private lateinit var layoutManager: LinearLayoutManager
    private lateinit var homeFragBinding: FragmentHomeBinding

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }
    private val autoDisposable = AutoDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        autoDisposable.bindTo(lifecycle)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeFragBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return homeFragBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(homeFragBinding.homeFragmentRoot, requireActivity(), 1)

        initSearchView()
        initPullToRefresh()
        //находим наш RV
        initRecyckler()
        //Кладем нашу БД в RV
        //filmsAdapter.addItems(filmsDataBase)
        viewModel.filmsListData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                filmsAdapter.addItems(list)
            }.addTo(autoDisposable)
        viewModel.showProgressBar
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                homeFragBinding.progressBar.isVisible = it
            }.addTo(autoDisposable)
    }

    private fun initPullToRefresh() {
        //Вешаем слушатель, чтобы вызвался pull to refresh
        homeFragBinding.pullToRefresh.setOnRefreshListener {
            //Чистим адаптер(items нужно будет сделать паблик или создать для этого публичный метод)
            filmsAdapter.items.clear()
            //Делаем новый запрос фильмов на сервер
            viewModel.getFilms()
            //Убираем крутящиеся колечко
            homeFragBinding.pullToRefresh.isRefreshing = false
        }
    }

    private fun initSearchView() {
        homeFragBinding.searchView.setOnClickListener {

            homeFragBinding.searchView.isIconified = false
        }

        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            //Вешаем слушатель на клавиатуру
            homeFragBinding.searchView.setOnQueryTextListener(object :
            //Вызывается на ввод символов
                SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    filmsAdapter.items.clear()
                    subscriber.onNext(newText)
                    return false
                }
                //Вызывается по нажатию кнопки "Поиск"
                override fun onQueryTextSubmit(query: String): Boolean {
                    subscriber.onNext(query)
                    return false
                }
            })
        })
            .subscribeOn(Schedulers.io())
            .map {
                it.toLowerCase(Locale.getDefault()).trim()
            }
            .debounce(800, TimeUnit.MILLISECONDS)
            .filter {
                //Если в поиске пустое поле, возвращаем список фильмов по умолчанию
                viewModel.getFilms()
                it.isNotBlank()
            }
            .flatMap {
                viewModel.getSearchResult(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {
                    Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show()
                },
                onNext = {
                    filmsAdapter.addItems(it)
                }
            )
            .addTo(autoDisposable)
    }

    private fun initRecyckler() {
        homeFragBinding.mainRecycler.apply {
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
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
    }

}

class TopSpacingItemDecoration (private val paddingInDp: Int): RecyclerView.ItemDecoration() {
    private val Int.convertPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = paddingInDp.convertPx
        outRect.right = paddingInDp.convertPx
        outRect.left = paddingInDp.convertPx

    }
}
