package com.odhiambodevelopers.rxkotlin

import android.content.Context
import android.content.Intent
import android.database.Observable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.odhiambodevelopers.rxkotlin.database.DetailsAdapter
import com.odhiambodevelopers.rxkotlin.database.DetailsDao
import com.odhiambodevelopers.rxkotlin.database.DetailsDatabase
import com.odhiambodevelopers.rxkotlin.database.DetailsDatabase.Companion.getInstance
import com.odhiambodevelopers.rxkotlin.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var detailsDatabase: DetailsDatabase
    private lateinit var detailsDao: DetailsDao
    private val adapter by lazy { DetailsAdapter() }
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailsDatabase = getInstance(applicationContext)
        detailsDao = detailsDatabase.detailsDao

        // displaying data
        val display = dataDisplay(this).subscribe()
        compositeDisposable.add(display)

        binding.btnFab.setOnClickListener {
            val intent = Intent(this, AddDetailActivity::class.java)
            startActivity(intent)
        }

    }

    // setting data to the recyclerview
    private fun dataDisplay(context: Context): Flowable<List<Long>> {
        return Maybe.fromAction<List<Long>>(){

            //creating and submiting list to the recyclerview
            val myList = detailsDao.getAllDetails()
            adapter.submitList(myList)
            binding.detailsRecycler.adapter = adapter

        }.toFlowable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnComplete {
                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
            }
            .doOnError {
                Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show()
            }
    }

}