package com.example.mymovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.data.model.ItemMovie
import com.squareup.picasso.Picasso

class MoviesAdapter(): RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

   private var listMovies: List<ItemMovie>? = null

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val moviewBanner: ImageView = itemView.findViewById(R.id.movie_poster_iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var movie = listMovies!![position]
        Picasso.get().load(movie.image).into(holder.moviewBanner)
    }

    override fun getItemCount(): Int {
       return listMovies!!.size
    }

    fun initMovieList(list: List<ItemMovie>){
        listMovies = list
        notifyDataSetChanged()
    }
}