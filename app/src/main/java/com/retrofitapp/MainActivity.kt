package com.retrofitapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Retrofit
import com.google.gson.Gson;
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory;
import java.lang.StringBuilder


const val BASE_URL= "https://jsonplaceholder.typicode.com/"
class MainActivity : AppCompatActivity() {
    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val recyclerview=findViewById<RecyclerView>(R.id.recyclerView_users)
        recyclerview.setHasFixedSize(true)
        linearLayoutManager= LinearLayoutManager(this)
        recyclerview.layoutManager= linearLayoutManager
        getMyData()


    }

    private fun getMyData() {
     val retrofitBuilder =Retrofit.Builder()
         .addConverterFactory(GsonConverterFactory.create())
         .baseUrl(BASE_URL)
         .build()
         .create(ApiInterface::class.java)

// val to get data from retrofit builder
        val retrofitData =retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>
            ) {
               val resposeBody= response.body()!!
                myAdapter=MyAdapter(baseContext,resposeBody)
                myAdapter.notifyDataSetChanged()
                val recyclerview=findViewById<RecyclerView>(R.id.recyclerView_users)
                recyclerview.adapter=myAdapter




               /*
                val mystringBuilder= StringBuilder()
                for( mydata in resposeBody){

                    mystringBuilder.append(mydata.id)
                    mystringBuilder.append("\n")
                }
                val txtid=findViewById<TextView>(R.id.txtId)
                txtid.text=mystringBuilder*/
            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                Log.d("MainActivity","OnFailure"+t.message)
            }
        })

    }


}