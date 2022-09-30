package com.example.project3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//import com.codepath.bestmovieslistapp.R.id


class BestMoviesRecyclerViewAdapter(
    private val movies: List<BestMovies>,
    private val mListener: OnListFragmentInteractionListener?
)
    : RecyclerView.Adapter<BestMoviesRecyclerViewAdapter.MovieViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_info, parent, false)
        return MovieViewHolder(view)
    }


    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: BestMovies? = null
        val mMovieTitle: TextView = mView.findViewById<View>(R.id.movie_title) as TextView
        val mMovieimage: ImageView = mView.findViewById<View>(R.id.movie_Image) as ImageView
        val mMoviedesc: TextView = mView.findViewById<View>(R.id.movie_description) as TextView

        override fun toString(): String {
            return mMovieTitle.toString()
        }
    }

    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    override fun onBindViewHolder(viewholder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        val url = "https://image.tmdb.org/t/p/w500/"
        movie.movieImageUrl = url.plus(movie.movieImageUrl)

        viewholder.mItem = movie
        viewholder.mMovieTitle.text = movie.title
        viewholder.mMoviedesc.text = movie.overview


        Glide.with(viewholder.mView)
            .load(movie.movieImageUrl)
            .centerInside()
            .into(viewholder.mMovieimage)


    }

    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return movies.size
    }
}