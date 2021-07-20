package com.example.movieapplication.ui.movie_details

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.movieapplication.R
import com.example.movieapplication.data.api.TheMovieDBInterface
import com.example.movieapplication.data.api.POSTER_BASE_URL
import com.example.movieapplication.data.api.TheMovieDBClient
import com.example.movieapplication.data.repository.NetworkState
import com.example.movieapplication.data.vo.TheMovieDetails
import com.example.movieapplication.databinding.ActivityMovieDetailsBinding
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

class MovieDetails : AppCompatActivity() {

    private lateinit var binding :  ActivityMovieDetailsBinding
    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId: Int = intent.getIntExtra("id",1)

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            binding.progressBar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.txtError.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })
    }
    @SuppressLint("SetTextI18n")
    fun bindUI(it: TheMovieDetails){
        binding.movieTitle.text = it.title
        binding.movieTagline.text = it.tagline
        binding.movieReleaseDate.text = it.releaseDate
        binding.movieRating.text = it.rating.toString()
        binding.movieRuntime.text = it.runtime.toString() + " minutes"
        binding.movieOverview.text = it.overview


        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Picasso.with(this)
                .load(moviePosterURL)
                .into(binding.ivMoviePoster);


    }


    private fun getViewModel(movieId:Int): MovieDetailsViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailsViewModel(movieRepository,movieId) as T
            }
        })[MovieDetailsViewModel::class.java]
    }
}