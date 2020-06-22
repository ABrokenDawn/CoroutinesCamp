package com.hencoder.coroutinescamp

import android.os.Bundle
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    /*GlobalScope.launch {
      println("Coroutines Camp 1 ${Thread.currentThread().name}")
    }*/

    /*Thread {
      println("Coroutines Camp 2 ${Thread.currentThread().name}")
    }.start()

    thread {
      println("Coroutines Camp 3 ${Thread.currentThread().name}")
    }*/

    GlobalScope.launch(Dispatchers.Main) {
      ioCode1()
      uiCode1()
      ioCode2()
      uiCode2()
      ioCode3()
      uiCode3()
    }

    GlobalScope.launch {
      ioCode1()
    }

    classicIoCode1 {
      uiCode1()
    }

    classicIoCode1(false) {
      uiCode1()
    }

  }

  private val executor = ThreadPoolExecutor(5, 20, 1, TimeUnit.MINUTES, LinkedBlockingDeque())

  private fun classicIoCode1(uiThread: Boolean = true, block: () -> Unit) {
    executor.execute {
      println("Coroutines Camp classic io1 ${Thread.currentThread().name}")
      if (uiThread) {
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
      println("Coroutines Camp io1 ${Thread.currentThread().name}")
    }
  }

  private suspend fun ioCode2() {
    withContext(Dispatchers.IO) {
      println("Coroutines Camp io2 ${Thread.currentThread().name}")
    }
  }

  private suspend fun ioCode3() {
    withContext(Dispatchers.IO) {
      println("Coroutines Camp io3 ${Thread.currentThread().name}")
    }
  }

  private fun uiCode1() {
    println("Coroutines Camp ui1 ${Thread.currentThread().name}")
  }

  private fun uiCode2() {
    println("Coroutines Camp ui2 ${Thread.currentThread().name}")
  }

  private fun uiCode3() {
    println("Coroutines Camp ui3 ${Thread.currentThread().name}")
  }
}