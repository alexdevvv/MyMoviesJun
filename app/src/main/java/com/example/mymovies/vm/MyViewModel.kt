package com.example.mymovies.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovies.MoviesAdapter
import com.example.mymovies.TOKEN
import com.example.mymovies.data.model.TopFilms
import com.example.mymovies.data.retrofit.ApiService
import com.example.mymovies.data.retrofit.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MyViewModel: ViewModel() {
    val retrofitHelper = RetrofitHelper.getInstance().create(ApiService::class.java)
    val disposable = CompositeDisposable()

    private val filmsLiveData:  MutableLiveData<TopFilms> by lazy {
        MutableLiveData<TopFilms>()
            .also {
            getFilmsFromServer()
        }
    }

    fun getFilms(): LiveData<TopFilms>{
        return filmsLiveData
    }



    private fun getFilmsFromServer() {
        disposable.add(
          retrofitHelper.getTopFilms(1, TOKEN)
                .subscribeOn(Schedulers.io()) //  Указываем в каком потоке получать данные
                .observeOn(AndroidSchedulers.mainThread()) // Указываем что выводыить данные мы будем в основном програмном потоке
                .subscribe({
                    filmsLiveData.postValue(it)
                    Log.e("XXX", it.films[0].toString())
                }, {
                })
        )
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

}