package com.example.mymovies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.example.mymovies.data.model.Film

class MoviesAdapter(
                    private val context: Context,
                    private val listener: Listener): RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    private  var listMovies: List<Film> = listOf()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val moviewBanner: ImageView = itemView.findViewById(R.id.movie_poster_iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var film = listMovies[position]
        Glide
            .with(context)
            .load(film.posterUrl)
            .apply(RequestOptions()
                .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round))
            .transition(withCrossFade())
            .into(holder.moviewBanner)

        holder.itemView.setOnClickListener{
            listener.onClick(film)
        }
    }


    fun initAdapter(listMovies: List<Film>){
        this.listMovies = listMovies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return listMovies.size
    }

    interface Listener{
        fun onClick(film: Film)
    }


}