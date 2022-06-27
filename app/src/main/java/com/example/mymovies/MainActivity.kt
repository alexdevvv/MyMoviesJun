package com.example.mymovies

import android.os.Bundle
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
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        val disposable = CompositeDisposable()

        val retrofitHelper = RetrofitHelper.getInstance().create(ApiService::class.java)

        disposable.add(
            retrofitHelper.getTopFilms(1, TOKEN)
                .subscribeOn(Schedulers.io()) //  Указываем в каком потоке получать данные
                .observeOn(AndroidSchedulers.mainThread()) // Указываем что выводыить данные мы будем в основном програмном потоке
                .subscribe({
                    recyclerView.adapter = MoviesAdapter(it.films, this, this)
                }, {

                })
        )
    }

    override fun onClick(film: Film) {
    }

}