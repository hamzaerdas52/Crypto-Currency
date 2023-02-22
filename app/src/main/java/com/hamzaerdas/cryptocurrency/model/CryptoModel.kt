package com.hamzaerdas.cryptocurrency.model

import com.google.gson.annotations.SerializedName

data class CryptoModel(
    // Java SerializedName kullanmak gerekir ama kotlin de çekilen verideki parametre
    // isimleri ile oluşturalan modeldeki parametre isimleri aynı olursa kullanmaya gerek yoktur.
    //@SerializedName("currency")
    val currency: String,
    val price: String
    )