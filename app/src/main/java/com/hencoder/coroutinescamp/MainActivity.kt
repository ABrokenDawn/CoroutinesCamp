package com.hencoder.coroutinescamp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.hencoder.coroutinescamp.arch.RengViewModel
import com.hencoder.coroutinescamp.model.Repo
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.*
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    /*GlobalScope.launch {
      println("Coroutines Camp 1 ${Thread.currentThread().name}")
    }

    Thread {
      println("Coroutines Camp 2 ${Thread.currentThread().name}")
    }.start()

    thread {
      println("Coroutines Camp 3 ${Thread.currentThread().name}")
    }*/

    /*thread {
      ioCode1()
      runOnUiThread {
        uiCode1()
        thread {
          ioCode2()
          runOnUiThread {
            uiCode2()
            thread {
              ioCode3()
              runOnUiThread {
                ioCode3()
              }
            }
          }
        }
      }
    }*/

    /*lifecycleScope.launch(Dispatchers.Main) {
      ioCode1()
      uiCode1()
      ioCode2()
      uiCode2()
      ioCode3()
      uiCode3()
    }

    lifecycleScope.launch(Dispatchers.Main) {
      ioCode1()
      uiCode1()
      ioCode2()
      uiCode2()
      ioCode3()
      uiCode3()
    }*/

    lifecycleScope.launch(Dispatchers.Main) {
      delay(1000)
      textView.text = "1000 了！"
    }

    thread {
      Thread.sleep(1000)
      runOnUiThread {
        textView.text = "1000 了！"
      }
    }

    /*thread {
      println("Coroutines Camp classic 1 ${Thread.currentThread().name}")
      classicIoCode1(block = ::uiCode1)
      println("Coroutines Camp classic 3 ${Thread.currentThread().name}")
    }

    ioCode1()*/

    /*val model: RengViewModel by viewModels()
    model.repos.observe(this, Observer { repos ->
      textView.text = repos[0].name
    })*/

    /*GlobalScope.launch(Dispatchers.Main) {
      ioCode1()
      uiCode1()
    }*/

    /*val retrofit = Retrofit.Builder()
      .baseUrl("https://api.github.com/")
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
      .build()
    val api = retrofit.create(Api::class.java)*/
    /*api.listRepos("rengwuxian")
      .enqueue(object : Callback<List<Repo>?> {
        override fun onFailure(call: Call<List<Repo>?>, t: Throwable) {

        }

        override fun onResponse(call: Call<List<Repo>?>, response: Response<List<Repo>?>) {
          textView.text = response.body()?.get(0)?.name
        }
      })*/

    /*GlobalScope.launch(Dispatchers.Main) {
      val one = async { api.listReposKt("rengwuxian") }
      val two = async { api.listReposKt("rengwuxian") }
      val same = one.await()[0].name == two.await()[0].name
      textView.text = "$same same"
    }*/

    /*Single.zip<List<Repo>, List<Repo>, Boolean>(api.listReposRx("rengwuxian"),
      api.listReposRx("rengwuxian"),
      BiFunction { list1, list2 -> list1[0].name == list2[0].name })
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(object : SingleObserver<Boolean?> {
        override fun onSuccess(t: Boolean?) {
          textView.text = t.toString()
        }

        override fun onSubscribe(d: Disposable) {
          disposable.add(d)
        }

        override fun onError(e: Throwable?) {
        }
      })*/
    (::classicIoCode1)(true, {})
    (::classicIoCode1).invoke(true, {})
    classicIoCode1(true, {})
  }

  private fun classicIoCode1(toUiThread: Boolean = true, block: () -> Unit) {
    val executor = ThreadPoolExecutor(5, 20, 1, TimeUnit.MINUTES, LinkedBlockingQueue(1000))
    executor.execute {
      Thread.sleep(1000)
      println("Coroutines Camp classic io1 ${Thread.currentThread().name}")
      if (toUiThread) {
        runOnUiThread {
          block()
        }
      } else {
        block()
      }
    }
  }

  private suspend fun ioCode1() {
    withContext(Dispatchers.IO) {
      Thread.sleep(3000)
      println("Coroutines Camp io1 ${Thread.currentThread().name}")
    }
  }

  private suspend fun ioCode2() {
    withContext(Dispatchers.IO) {
      Thread.sleep(1000)
      println("Coroutines Camp io2 ${Thread.currentThread().name}")
    }
  }

  private suspend fun ioCode3() {
    withContext(Dispatchers.IO) {
      Thread.sleep(1000)
      println("Coroutines Camp io3 ${Thread.currentThread().name}")
    }
  }

  private fun uiCode1() {
//    println("Coroutines Camp ui1 ${Thread.currentThread().name}")
    textView.text = "heiheihei"
  }

  private fun uiCode2() {
    println("Coroutines Camp ui2 ${Thread.currentThread().name}")
  }

  private fun uiCode3() {
    println("Coroutines Camp ui3 ${Thread.currentThread().name}")
  }
}