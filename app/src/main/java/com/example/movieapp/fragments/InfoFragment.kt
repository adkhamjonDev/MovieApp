package com.example.movieapp.fragments
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.adapters.CompanyItemAdapter
import com.example.movieapp.adapters.ImageItemAdapter
import com.example.movieapp.adapters.SimilarAdapter
import com.example.movieapp.databinding.FragmentInfoBinding
import com.example.movieapp.models.Images.ImageModel
import com.example.movieapp.models.Details.MovieDetails
import com.example.movieapp.models.Similar.SimilarClass
import com.example.movieapp.retrofit.ApiClient
import com.example.movieapp.retrofit.ApiService
import com.mig35.carousellayoutmanager.CarouselLayoutManager
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.mig35.carousellayoutmanager.CenterScrollListener
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InfoFragment : Fragment(),PopupMenu.OnMenuItemClickListener  {
    private lateinit var binding: FragmentInfoBinding
    private  var movieDetails: MovieDetails?=null
    private lateinit var apiService: ApiService
    private lateinit var imageItemAdapter: ImageItemAdapter
    private lateinit var companyItemAdapter: CompanyItemAdapter
    private lateinit var similarAdapter: SimilarAdapter
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentInfoBinding.inflate(inflater, container, false)
        apiService= ApiClient.getRetrofit().create(ApiService::class.java)
        if(arguments!=null){
            movieDetails=arguments?.getSerializable("data")as MovieDetails
        }
        setInfo()
        setImages()
        setSimilar()
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.more.setOnClickListener {
            val popup = PopupMenu(context,it)
            popup.setOnMenuItemClickListener(this)
            popup.inflate(R.menu.menu_popup)
            popup.show()
        }
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.share->{

            }
            else->{
            return false
            }
        }
        return false
    }
    private fun setInfo(){
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${movieDetails?.poster_path}")
            .into(binding.icon)
        binding.name.text=movieDetails?.title
        binding.tittle.text=movieDetails?.title
        val rate=movieDetails?.vote_average as Double
        val n=(rate*5.0)/10
        binding.ratingBar.rating=n.toFloat()
        binding.rateNumber.text=movieDetails?.vote_average.toString()

        val stringBuilder=StringBuilder()
        movieDetails!!.production_countries.forEach { item->
            stringBuilder.append(item.name+", ")
        }
        var str=stringBuilder.toString()
        str=str.substring(0,str.length-2)
        binding.country.text=str
        binding.year.text=movieDetails?.release_date
        val stringBuilder1=StringBuilder()
        movieDetails!!.genres.forEach { item->
            stringBuilder1.append(item.name+", ")
        }
        var str2=stringBuilder1.toString()
        str2=str2.substring(0,str2.length-2)
        binding.genre.text=str2
        binding.time.text="${movieDetails?.runtime} minutes"
        val stringBuilder2=StringBuilder()
        movieDetails!!.spoken_languages.forEach { item->
            stringBuilder2.append(item.name+", ")
        }
        var str3=stringBuilder2.toString()
        str3=str3.substring(0,str3.length-2)
        binding.language.text=str3
        companyItemAdapter=CompanyItemAdapter(movieDetails!!.production_companies)
        binding.recViewCompanies.adapter=companyItemAdapter
        binding.overview.text=movieDetails!!.overview
    }
    private fun setImages(){
        apiService.getMovieImages(movie_id = movieDetails!!.id).enqueue(object : Callback<ImageModel> {
            override fun onResponse(call: Call<ImageModel>, response: Response<ImageModel>) {
                if (response.isSuccessful){
                    val ooo=response.body()
                    imageItemAdapter= ImageItemAdapter(ooo!!.backdrops)
                    val manager=CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL,true)
                    binding.imageRecView.layoutManager=manager
                    binding.imageRecView.setHasFixedSize(true)
                    manager.maxVisibleItems=3
                    manager.setPostLayoutListener(CarouselZoomPostLayoutListener())
                    binding.imageRecView.adapter = imageItemAdapter
                    binding.imageRecView.addOnScrollListener(CenterScrollListener())
                }
            }
            override fun onFailure(call: Call<ImageModel>, t: Throwable) {

            }
        })
    }
    private fun setSimilar(){
        apiService.getSimilar(movie_id = movieDetails!!.id).enqueue(object : Callback<SimilarClass> {
            override fun onResponse(call: Call<SimilarClass>, response: Response<SimilarClass>) {
                if (response.isSuccessful){
                    val ooo=response.body()
                    similarAdapter= SimilarAdapter(ooo!!.results,object:SimilarAdapter.OnItemClickListener{
                        override fun onItemClick(id: Int) {
                            getDetails(id)
                        }
                    })
                    binding.recViewSimilar.adapter=similarAdapter
                }
            }
            override fun onFailure(call: Call<SimilarClass>, t: Throwable) {

            }
        })
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