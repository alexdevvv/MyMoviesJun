package com.example.mymovies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.data.model.Film
import com.example.mymovies.databinding.ActivityMainBinding
import com.example.mymovies.vm.MyViewModel

class MainActivity : AppCompatActivity(), MoviesAdapter.Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var vm: MyViewModel
    private lateinit var filmsAdapter: MoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this).get(MyViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.movie_rv)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        filmsAdapter = MoviesAdapter(this, this)
        recyclerView.adapter = filmsAdapter
        onBind()

//        val disposable = CompositeDisposable()

//        val retrofitHelper = RetrofitHelper.getInstance().create(ApiService::class.java)

//        disposable.add(
//            retrofitHelper.getTopFilms(1, TOKEN)
//                .subscribeOn(Schedulers.io()) //  Указываем в каком потоке получать данные
//                .observeOn(AndroidSchedulers.mainThread()) // Указываем что выводыить данные мы будем в основном програмном потоке
//                .subscribe({
//                    recyclerView.adapter = MoviesAdapter(it.films, this, this)
//                    Log.e("XXX", it.films[0].toString())
//                }, {
//                })
//        )

    }

    private fun onBind() {
        vm.getFilms().observe(this) {
            Log.e("RESUL_LIST", it.films.size.toString())
            filmsAdapter.initAdapter(it.films)

        }
    }

    override fun onClick(film: Film) {
        val intent = Intent(this, DetailMovieActivity::class.java)
        intent.putExtra(FILM_ID_KEY, film.filmId.toInt())
        intent.putExtra(FILM_NAME_KEY, film.nameRu)
        intent.putExtra(FILM_ORIGINAL_NAME_KEY, film.nameEn)
        intent.putExtra(FILM_RAITING_KEY, film.rating)
        intent.putExtra(RELEISE_DATE_KEY, film.year)
        intent.putExtra(FILM_DESCRIPTION_KEY, film.description)
        startActivity(intent)
    }

}