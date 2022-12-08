package com.app.santasgifts.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.app.santasgifts.ItemUrl
import com.app.santasgifts.ItemUrlDao
import com.app.santasgifts.databinding.ActivityMainBinding
import com.app.santasgifts.view.Wv.WvActivity
import com.app.santasgifts.view.g.GActivity
import com.app.santasgifts.view.load.LoadingActivity
import com.app.santasgifts.workman.WM
import com.romainpiel.shimmer.Shimmer

class SpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val shimmer = Shimmer()
        shimmer.start(binding.shimmerTv)
    }

    override fun onResume() {
        super.onResume()
        wM()
    }


    private fun wM() {
        Thread.sleep(2000)
        val uploadWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<WM>()
                .build()
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(uploadWorkRequest.id)
            .observe(this, Observer { info ->
                if (info != null && info.state.isFinished) {
                    val isNot = info.outputData.getBoolean(
                        WM.KEY_RESULT,
                        false
                    )
                    if (isNot) {
                        val intet = Intent(this@SpActivity, LoadingActivity::class.java)
                        startActivity(intet)
                        finish()
                    } else {
                        val intet = Intent(this@SpActivity, GActivity::class.java)
                        startActivity(intet)
                        finish()
                    }
                }
            })
        WorkManager.getInstance(this).enqueue(uploadWorkRequest)
    }
}