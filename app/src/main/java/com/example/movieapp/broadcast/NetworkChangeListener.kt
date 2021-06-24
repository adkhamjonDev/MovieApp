package com.example.movieapp.broadcast
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.movieapp.R
import com.example.movieapp.databinding.InternetDialogBinding
import com.example.movieapp.utils.InternetConnectionHelper
class NetworkChangeListener :BroadcastReceiver() {
    private lateinit var internetConnectionHelper:InternetConnectionHelper
    override fun onReceive(context: Context?, intent: Intent?) {
        internetConnectionHelper= InternetConnectionHelper(context!!)
        if(!internetConnectionHelper.isNetworkConnected()){
            val builder = AlertDialog.Builder(context)
            val view=LayoutInflater.from(context).inflate(R.layout.internet_dialog,null)
            builder.setView(view)
            val cardView=view.findViewById<CardView>(R.id.retry)
            val alertDialog = builder.create()
            alertDialog.show()
            alertDialog.setCancelable(true)
            cardView.setOnClickListener {
                alertDialog.dismiss()
                onReceive(context, intent)
            }
        }
    }

}