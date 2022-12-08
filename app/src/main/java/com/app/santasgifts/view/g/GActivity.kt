package com.app.santasgifts.view.g

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.get
import com.app.santasgifts.data.FlipState
import com.app.santasgifts.R
import com.app.santasgifts.databinding.ActivityGactivityBinding
import com.wajahatkarim3.easyflipview.EasyFlipView


class GActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGactivityBinding
    private lateinit var textView: TextView
    private val viewModel: GViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mainEasyFlipView = findViewById<ImageView>(R.id.iv_main)
        textView = findViewById<TextView>(R.id.textView)

        viewModel.startGame()
        startTimer()

        binding.btnSpin.setOnClickListener {
            viewModel.startGame()
            startTimer()
        }

        viewModel.mainItem.observe(this) {
            mainEasyFlipView.setImageResource(it.resId)
        }

        viewModel.winText.observe(this) {
            textView.text = it
        }

        viewModel.listGameItems.observe(this) {
            it.forEachIndexed { index, cardItem ->
                val viewItem = binding.gridCards[index]

                val easyFlipViews = viewItem.findViewById<EasyFlipView>(R.id.easy_flip)

                easyFlipViews.setOnFlipListener { easyFlipView, newCurrentSide ->
                    if (newCurrentSide == EasyFlipView.FlipState.BACK_SIDE)
                        viewModel.itemFlipped(cardItem)
                }

                easyFlipViews.findViewById<ImageView>(R.id.iv_front_side)
                    .setImageResource(cardItem.resId)

                if (cardItem.flipState == FlipState.INVISIBLE)
                    easyFlipViews.hide()
                else {
                    easyFlipViews.show()

                    if (easyFlipViews.currentFlipState == EasyFlipView.FlipState.BACK_SIDE && cardItem.flipState == FlipState.FRONT ||
                        easyFlipViews.currentFlipState == EasyFlipView.FlipState.FRONT_SIDE && cardItem.flipState == FlipState.BACK
                    )
                        easyFlipViews.flipTheView()

                }
            }

        }
    }

    private fun startTimer() {
        object : CountDownTimer(30000, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                if (viewModel.winText.value == "You are win") {
                    cancel()
                } else {
                    textView.text = "You have: " + millisUntilFinished / 1000 + " seconds to win!!!"

                }
            }


            override fun onFinish() {
                textView.text = "Time is over!"
            }
        }.start()
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}