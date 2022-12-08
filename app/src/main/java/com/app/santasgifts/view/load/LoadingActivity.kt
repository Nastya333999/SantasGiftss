package com.app.santasgifts.view.load

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.santasgifts.databinding.ActivityLoadingBinding
import com.app.santasgifts.view.Wv.WvActivity
import com.romainpiel.shimmer.Shimmer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoadingBinding
    private val viewModel: LoadingViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val shimmer = Shimmer()
        shimmer.start(binding.shimmerTv)

        viewModel.init(this)

        viewModel._data.observe(this) { url ->
            if (url.isEmpty())
                return@observe

            val intent = Intent(this, WvActivity::class.java)
            intent.putExtra("web_url", url)
            Log.e("URL", "$url")
            startActivity(intent)
            finish()
        }
    }
}