package com.example.movieapp.fragments
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
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
        apiService=ApiClient.getRetrofit().create(ApiService::class.java)

        binding.edit.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                apiService.getSearchMovies(query = s.toString()).enqueue(object :
                    Callback<MainClass> {
                    override fun onResponse(call: Call<MainClass>, response: Response<MainClass>) {
                        if (response.isSuccessful){
                            val ooo=response.body()
                            searchAdapter= SearchAdapter(ooo!!.results,object:SearchAdapter.OnItemClickListener{
                                override fun onItemClick(id: Int) {
                                    getDetails(id)
                                }
                            })
                            binding.recView.adapter=searchAdapter
                        }
                    }
                    override fun onFailure(call: Call<MainClass>, t: Throwable) {

                    }
                })
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        binding.voice.setOnClickListener {
            val intent= Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speech to text")
            startActivityForResult(intent,1)
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, result: Intent?) {
        if(requestCode==1 && resultCode==RESULT_OK){
            val stringArrayListExtra = result?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
            binding.edit.setText(stringArrayListExtra[0])
        }
        super.onActivityResult(requestCode, resultCode, result)

    }
}