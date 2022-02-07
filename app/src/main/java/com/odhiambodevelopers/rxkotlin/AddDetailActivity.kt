package com.odhiambodevelopers.rxkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.odhiambodevelopers.rxkotlin.database.DetailsDao
import com.odhiambodevelopers.rxkotlin.database.DetailsDatabase
import com.odhiambodevelopers.rxkotlin.database.DetailsEntity
import com.odhiambodevelopers.rxkotlin.databinding.ActivityAddDetailBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddDetailActivity : AppCompatActivity() {
    // Declare instances used
    private lateinit var binding: ActivityAddDetailBinding
    private lateinit var detailsDatabase: DetailsDatabase
    private lateinit var detailsDao: DetailsDao
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //creating database instance
        detailsDatabase = DetailsDatabase.getInstance(applicationContext)
        detailsDao = detailsDatabase.detailsDao

        // adding click listener to button
        binding.btnAdd.setOnClickListener {
            if (binding.edtName.text.toString().isEmpty()){
                binding.edtName.error = "Required"
                return@setOnClickListener
            } else if (binding.edtWeight.text.toString().isEmpty()){
                binding.edtWeight.error = "Required"
                return@setOnClickListener
            }
            else{

                // load subscription using disposable
                val loadDisposable = addingDetails(this).subscribe()
                compositeDisposable.add(loadDisposable)

                // shifting to the next activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun addingDetails(context: Context): Flowable<List<Long>> {
        return Maybe.fromAction<List<Long>>(){
            // creating database instance
            val database = DetailsDatabase.getInstance(context = context).detailsDao

            //Adding data using the data class
            val details = DetailsEntity(0,binding.edtName.text.toString(),binding.edtWeight.text.toString())
            //inserting data
            database.insertDetails(details)
        }.toFlowable() // using flowable to handle huge data emission
            .observeOn(AndroidSchedulers.mainThread())  //placing observer to the main thread
            .subscribeOn(Schedulers.io()) //subscribing to the io thread
            .doOnComplete {
                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show()
            }
            .doOnError {
                Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show()
            }
    }
}