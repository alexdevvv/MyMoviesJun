package com.example.mymovies

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovies.data.model.ItemMovie
import com.example.mymovies.data.model.Movies
import com.example.mymovies.data.retrofit.MoviesApi
import com.example.mymovies.data.retrofit.RetrofitHelper
import com.example.mymovies.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var moviesAdapter: MoviesAdapter

    init {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val disposable = CompositeDisposable()


        val retrofitHelper = RetrofitHelper.getInstance().create(MoviesApi::class.java)

        binding.movieRv.layoutManager = LinearLayoutManager(this)
        moviesAdapter = MoviesAdapter()


        disposable.add(
            retrofitHelper.getTopMovies()
                .subscribeOn(Schedulers.io()) //  Указываем в каком потоке получать данные
                .observeOn(AndroidSchedulers.mainThread()) // Указываем что выводыить данные мы будем в основном програмном потоке
                .subscribe({
                    val listMovie = parseMovies(it)
                    moviesAdapter.initMovieList(listMovie)
                    Log.e("XXX", listMovie.size.toString())

                }, {

                })
        )
    }

    fun parseMovies(movies: Movies): List<ItemMovie> {
        val result = mutableListOf<ItemMovie>()
        for (i in movies.items) {
            result.add(i)
        }
        return result
    }
}