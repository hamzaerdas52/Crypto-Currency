package com.hamzaerdas.cryptocurrency.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hamzaerdas.cryptocurrency.databinding.ActivityMainBinding
import com.hamzaerdas.cryptocurrency.model.CryptoModel
import com.hamzaerdas.cryptocurrency.service.CryptoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var binding: ActivityMainBinding
private val BASE_URL = "https://raw.githubusercontent.com/"
private var cryptoModels: ArrayList<CryptoModel>? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //https://raw.githubusercontent.com/
        //atilsamancioglu/K21-JSONDataSet/master/crypto.json

        loadData()
    }

    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object: Callback<List<CryptoModel>>{
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if(response.isSuccessful){
                    response.body()?.let { result ->
                        cryptoModels = ArrayList(result)

                        for(cryptoModel: CryptoModel in cryptoModels!!){
                            println(cryptoModel.currency)
                            println(cryptoModel.price)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}































