package com.example.mymovies

import android.content.Intent
import android.os.Bundle
import android.service.controls.templates.RangeTemplate
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.data.model.Film
import com.example.mymovies.data.model.TopFilms
import com.example.mymovies.data.retrofit.ApiService
import com.example.mymovies.data.retrofit.RetrofitHelper
import com.example.mymovies.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity(), MoviesAdapter.Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.movie_rv)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val disposable = CompositeDisposable()

        val retrofitHelper = RetrofitHelper.getInstance().create(ApiService::class.java)

        disposable.add(
            retrofitHelper.getTopFilms(1, TOKEN)
                .subscribeOn(Schedulers.io()) //  Указываем в каком потоке получать данные
                .observeOn(AndroidSchedulers.mainThread()) // Указываем что выводыить данные мы будем в основном програмном потоке
                .subscribe({
                    recyclerView.adapter = MoviesAdapter(it.films, this, this)
                    Log.e("XXX", it.films[0].toString())
                }, {
                })
        )
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