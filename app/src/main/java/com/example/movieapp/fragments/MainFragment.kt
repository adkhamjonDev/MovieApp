package com.example.movieapp.fragments
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapters.RvAdapter
import com.example.movieapp.databinding.FragmentMainBinding
import com.example.movieapp.databinding.TypeDialogBinding
import com.example.movieapp.models.MovieClass
import com.example.movieapp.models.Details.MovieDetails
import com.example.movieapp.models.numClass.TypeClass
import com.example.movieapp.retrofit.ApiClient
import com.example.movieapp.retrofit.ApiService
import com.example.movieapp.viewModel.MovieViewModel
import com.example.movieapp.viewModel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var rvAdapter: RvAdapter
    private var typeClass:TypeClass?=null
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var apiService: ApiService
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentMainBinding.inflate(inflater, container, false)
        apiService=ApiClient.getRetrofit().create(ApiService::class.java)
        movieViewModel= ViewModelProviders.of(
            this,
            ViewModelFactory(ApiClient.apiService,"")
        )[MovieViewModel::class.java]
        gridLayoutManager= GridLayoutManager(context,1)
        rvAdapter = RvAdapter(gridLayoutManager,object:RvAdapter.OnItemClickListener{
            override  fun itemClick(movieClass: MovieClass) {
                apiService.getMovieDetails(movie_id = movieClass.id).enqueue(object :
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
        })
        binding.recView.hasFixedSize()
        binding.recView.layoutManager=gridLayoutManager
        binding.recView.adapter = rvAdapter
        lifecycleScope.launch {
            movieViewModel.popular.collectLatest {
                rvAdapter.submitData(it)
            }
        }
        binding.linearView.setOnClickListener {
            binding.linearView.visibility=View.GONE
            binding.gridView.visibility=View.VISIBLE
            if (gridLayoutManager.spanCount == 1) {
                gridLayoutManager.spanCount = 3
                rvAdapter.notifyItemRangeChanged(0, rvAdapter.itemCount);
            }
        }
        binding.gridView.setOnClickListener {
            binding.gridView.visibility=View.GONE
            binding.linearView.visibility=View.VISIBLE
            if (gridLayoutManager.spanCount == 3) {
                gridLayoutManager.spanCount = 1
                rvAdapter.notifyItemRangeChanged(0, rvAdapter.itemCount);
            }
        }
        binding.search.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
        binding.showDialog.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            val binding1= TypeDialogBinding.inflate(inflater, null, false)
            builder.setView(binding1.root)
            val alertDialog=builder.create()
            binding1.popular.setOnClickListener {
                binding.tittle.text="Popular"
                lifecycleScope.launch {
                    movieViewModel.popular.collectLatest {
                        rvAdapter.submitData(it)
                    }
                }
                alertDialog.dismiss()
            }
            binding1.top.setOnClickListener {
                binding.tittle.text="Top Rated"
                lifecycleScope.launch {
                    movieViewModel.topRated.collectLatest {
                        rvAdapter.submitData(it)
                    }
                }
                alertDialog.dismiss()
            }
            binding1.upcoming.setOnClickListener {
                binding.tittle.text="Upcoming"
                lifecycleScope.launch {
                    movieViewModel.upcoming.collectLatest {
                        rvAdapter.submitData(it)
                    }
                }
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
        return binding.root
    }
}