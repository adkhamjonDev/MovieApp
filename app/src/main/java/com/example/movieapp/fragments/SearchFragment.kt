package com.example.movieapp.fragments
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.adapters.SearchAdapter
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.models.Details.MovieDetails
import com.example.movieapp.models.MainClass
import com.example.movieapp.models.MovieClass
import com.example.movieapp.retrofit.ApiClient
import com.example.movieapp.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var apiService: ApiService
    private lateinit var searchAdapter: SearchAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentSearchBinding.inflate(inflater, container, false)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }
    private fun getDetails(id:Int){
        apiService.getMovieDetails(movie_id = id).enqueue(object :
            Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                if (response.isSuccessful){
                    val ooo=response.body()
                    val bundle= bundleOf("data" to ooo)
                    findNavController().navigate(R.id.infoFragment,bundle)
                }
            }
            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {

            }
        })
    }
}