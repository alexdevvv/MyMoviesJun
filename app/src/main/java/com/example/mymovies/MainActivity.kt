package com.example.mymovies

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mymovies.data.retrofit.MoviesApi
import com.example.mymovies.data.retrofit.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val disposable = CompositeDisposable()

        val retrofitHelper = RetrofitHelper.getInstance().create(MoviesApi::class.java)

        disposable.add(
            retrofitHelper.getTopMovies()
                .subscribeOn(Schedulers.io()) //  Указываем в каком потоке получать данные
                .observeOn(AndroidSchedulers.mainThread()) // Указываем что выводыить данные мы будем в основном програмном потоке
                .subscribe({
                        Log.e("XXX", it.items.size.toString())
                }, {

                })
        )


    }
}