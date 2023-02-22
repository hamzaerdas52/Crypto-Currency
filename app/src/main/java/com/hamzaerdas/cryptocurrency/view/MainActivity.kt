package com.hamzaerdas.cryptocurrency.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamzaerdas.cryptocurrency.adapter.CryptoAdapter
import com.hamzaerdas.cryptocurrency.databinding.ActivityMainBinding
import com.hamzaerdas.cryptocurrency.model.CryptoModel
import com.hamzaerdas.cryptocurrency.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), CryptoAdapter.Listener {

    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private lateinit var cryptoModels: ArrayList<CryptoModel>
    private lateinit var cryptoAdapter: CryptoAdapter
    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        cryptoModels = ArrayList<CryptoModel>()
        compositeDisposable = CompositeDisposable()
        loadData()

        binding.cryptoRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        //https://raw.githubusercontent.com/
        //atilsamancioglu/K21-JSONDataSet/master/crypto.json


    }

    private fun loadData(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        compositeDisposable.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))

        /*
        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object: Callback<List<CryptoModel>>{
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        cryptoModels = ArrayList(it)
                        for(cryptoModels: CryptoModel in cryptoModels){
                            println(cryptoModels.price)
                            println(cryptoModels.currency)
                        }
                        cryptoModels.let { result ->
                            cryptoAdapter = CryptoAdapter(result, this@MainActivity)
                            binding.cryptoRecyclerView.adapter = cryptoAdapter
                        }
                    }
                } else {
                  println("b")
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })
        */
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this, "Tıklandı: ${cryptoModel.currency}", Toast.LENGTH_LONG).show()
    }

    private fun handleResponse(cryptoList : List<CryptoModel>){
        cryptoModels = ArrayList(cryptoList)

        cryptoModels.let {
            cryptoAdapter = CryptoAdapter(it,this@MainActivity)
            binding.cryptoRecyclerView.adapter = cryptoAdapter
        }
    }
}































