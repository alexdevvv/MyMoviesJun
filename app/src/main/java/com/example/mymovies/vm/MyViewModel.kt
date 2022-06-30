package com.example.mymovies.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovies.TOKEN
import com.example.mymovies.data.FilmsDao
import com.example.mymovies.data.model.TopFilms
import com.example.mymovies.data.retrofit.ApiService
import com.example.mymovies.data.retrofit.RetrofitHelper
import com.example.mymovies.data.room.FilmsDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MyViewModel: ViewModel() {
    private val retrofitHelper = RetrofitHelper.getInstance().create(ApiService::class.java)
    private val db = FilmsDatabase.INSTANCE
    private val dao: FilmsDao =  db!!.filmDao()
    private val disposable = CompositeDisposable()

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

                    dao.deleteAll()
                    for (i in it.films){
                        dao.insert(i)
                    }
                }, {
                   dao.getFilms()
                })
        )
    }




    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

}