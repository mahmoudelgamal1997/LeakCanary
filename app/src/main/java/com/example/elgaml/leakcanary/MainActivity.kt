package com.example.elgaml.leakcanary

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var myAsync:MyAsync
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            myAsync=  MyAsync(this)
            myAsync.execute()
        }
    }

    @SuppressLint("StaticFieldLeak")
    fun startAsyncWork() {
        // This runnable is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the thread finishes (e.g. rotation),
        // the activity instance will leak.
        val work = Runnable { // Do some slow work in background
            SystemClock.sleep(5000)
        }
        Thread(work).start()
    }

    class MyAsync :AsyncTask<Void,Void,Void>{
         var context:Context
         constructor(context: Context){
             this.context=context
         }
        override fun doInBackground(vararg p0: Void?): Void? {

            var bitmap=BitmapFactory.decodeResource(context.resources,R.drawable.ic_launcher_background)
            try {
            Thread.sleep(5000)
            }catch (ex:Exception){
                Log.e("ErrorSleep",ex.message)
            }
                return null
            }

    }

    override fun onDestroy() {
        myAsync.cancel(true)
        super.onDestroy()
        Log.e("onDestroyed","onDestroyed")
    }
}
