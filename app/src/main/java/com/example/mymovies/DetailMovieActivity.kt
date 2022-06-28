package com.example.mymovies

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mymovies.data.retrofit.ApiService
import com.example.mymovies.data.retrofit.RetrofitHelper
import com.example.mymovies.databinding.ActivityDetailMovieBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

const val FILM_ID_KEY = "film_id"
const val FILM_NAME_KEY = "film_name"
const val FILM_ORIGINAL_NAME_KEY = "film_original_name"
const val FILM_RAITING_KEY = "film_raiting"
const val RELEISE_DATE_KEY = "releise_date_key"
const val FILM_DESCRIPTION_KEY = "film_description_key"

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMovieBinding
    var disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitHelper = RetrofitHelper.getInstance().create(ApiService::class.java)
        val filmId = getMyIntent()

        disposable.add(
            retrofitHelper.getFilmById(filmId, TOKEN)
                .subscribeOn(Schedulers.io()) //  Указываем в каком потоке получать данные
                .observeOn(AndroidSchedulers.mainThread()) // Указываем что выводыить данные мы будем в основном програмном потоке
                .subscribe({
                    with(binding) {
                        getImage(it.posterUrl, movieImg)
                        filmNameValueTv.text = intent.getStringExtra(FILM_NAME_KEY)
                        originalFilmNameValueTv.text = intent.getStringExtra(FILM_ORIGINAL_NAME_KEY)
                        filmRatingValueTv.text = intent.getStringExtra(FILM_RAITING_KEY)
                        releaseDateValueTv.text = intent.getStringExtra(RELEISE_DATE_KEY)
                        releaseDateValueTv.text = intent.getStringExtra(RELEISE_DATE_KEY)
                        filmDescriptionValueTv.text = intent.getStringExtra(FILM_DESCRIPTION_KEY)
                    }
                }, {
                    Log.e("XXX", it.message.toString())
                })
        )
    }

    private fun getImage(imageURL: String, poster: ImageView) {
        Glide
            .with(this)
            .load(imageURL)
            .into(poster)
    }

    private fun getMyIntent(): Int {
        val filmID = intent.getIntExtra(FILM_ID_KEY, -1)
        return filmID
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

}
