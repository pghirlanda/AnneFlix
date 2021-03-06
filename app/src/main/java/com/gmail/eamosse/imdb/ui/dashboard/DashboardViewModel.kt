package com.gmail.eamosse.imdb.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.eamosse.idbdata.api.response.VideoResponse
import com.gmail.eamosse.idbdata.data.DetailMovie
import com.gmail.eamosse.idbdata.data.PopularMovies
import com.gmail.eamosse.idbdata.data.Video
import com.gmail.eamosse.idbdata.local.entities.FavoriteEntity
import com.gmail.eamosse.idbdata.repository.MovieRepository
import com.gmail.eamosse.idbdata.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _popularmovies: MutableLiveData<List<PopularMovies>> = MutableLiveData()
    val popularmovies: LiveData<List<PopularMovies>>
        get() = _popularmovies

    private val _video: MutableLiveData<List<Video>> = MutableLiveData()
    val video: LiveData<List<Video>>
        get() = _video

    private val _topratedmovies: MutableLiveData<List<PopularMovies>> = MutableLiveData()
    val topratedmovies: LiveData<List<PopularMovies>>
        get() = _topratedmovies

    private val _upcomingmovies: MutableLiveData<List<PopularMovies>> = MutableLiveData()
    val upcomingmovies: LiveData<List<PopularMovies>>
        get() = _upcomingmovies

    private val _moviedetail: MutableLiveData<DetailMovie> = MutableLiveData()
    val movieDetail: LiveData<DetailMovie>
        get() = _moviedetail

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String>
        get() = _error

    private val _similarmovies: MutableLiveData<List<PopularMovies>> = MutableLiveData()
    val similarmovies: LiveData<List<PopularMovies>>
        get() = _similarmovies

    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getPopularMovies()) {
                is Result.Succes -> {
                    _popularmovies.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getTopRatedMovies()) {
                is Result.Succes -> {
                    _topratedmovies.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    fun getUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getUpcomingMovies()) {
                is Result.Succes -> {
                    _upcomingmovies.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    fun getVideo(Id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getVideo(Id)) {
                is Result.Succes -> {
                    _video.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    fun getDetailMovie(Id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getDetailMovie(Id)) {
                is Result.Succes -> {
                    _moviedetail.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }

    fun addToFavorite(movie: DetailMovie) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addToFavorite(
                FavoriteEntity(
                    movie.id.toString(),
                    movie.backdrop_path,
                    movie.title,
                    movie.overview,
                    movie.poster_path,
                    movie.video,
                    movie.date
                )
            )
        }
    }

    suspend fun checkMovie(id: String) = repository.checkMovie(id)

    fun removeFromFavorite(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeFromFavorite(id)
        }
    }

    fun getSimilarMovies(Id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getSimilarMovies(Id)) {
                is Result.Succes -> {
                    _similarmovies.postValue(result.data)
                }
                is Result.Error -> {
                    _error.postValue(result.message)
                }
            }
        }
    }
}
