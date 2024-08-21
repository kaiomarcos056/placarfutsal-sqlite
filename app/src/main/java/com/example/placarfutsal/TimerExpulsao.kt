package com.example.placarfutsal

import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView

// CLASSE CRIADA PARA CONTROLAR O TIME DE CADA TIME
// E NAO PRECISAR DUPLICAR O CODIGO PARA OS DOIS TIMES
class TimerExpulsao(
    private val initialTimeInMillis: Long,
    private val textView: TextView,
    private val button: Button
) {
    private var timer: CountDownTimer? = null
    private var timeRemaining: Long = initialTimeInMillis
    private var isRunning = false

    fun startTimer() {
        timer = object : CountDownTimer(timeRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                textView.text = String.format("%d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                textView.text = "2:00"
                timeRemaining = initialTimeInMillis
                isRunning = false
                button.text = "Start"
            }
        }.start()
        isRunning = true
        button.text = "Pause"
    }

    fun pauseTimer() {
        timer?.cancel()
        isRunning = false
        button.text = "Start"
    }

    fun toggleTimer() {
        if (isRunning) {
            pauseTimer()
        } else {
            startTimer()
        }
    }
}
