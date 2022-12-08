package com.app.santasgifts.view.Wv

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.app.santasgifts.App
import com.app.santasgifts.DaoSession
import com.app.santasgifts.ItemUrl
import com.app.santasgifts.R
import com.app.santasgifts.databinding.ActivityWvBinding
import com.app.santasgifts.view.g.GActivity
import com.google.android.material.snackbar.Snackbar

class WvActivity : AppCompatActivity() {
    private lateinit var immediateWebView: WebView
    private lateinit var binding: ActivityWvBinding
    private lateinit var valueCallback: ValueCallback<Array<Uri?>>

    val data = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        valueCallback.onReceiveValue(it.toTypedArray())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = resources.getColor(R.color.black, theme)

        immediateWebView = findViewById(R.id.immediate_web_view)
        CookieManager.getInstance().setAcceptThirdPartyCookies(immediateWebView, true)
        CookieManager.getInstance().setAcceptCookie(true)

        immediateWebView.loadUrl(intent.getStringExtra("web_url")!!)
        immediateWebView.settings.loadWithOverviewMode = false

        immediateWebView.settings.javaScriptEnabled = true
        immediateWebView.settings.domStorageEnabled = true

        immediateWebView.settings.userAgentString =
            immediateWebView.settings.userAgentString.replace("wv", "")


        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (immediateWebView.canGoBack()) {
                        immediateWebView.goBack()
                    } else {
//                        isEnabled = false
                    }
                }
            })


        immediateWebView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                Snackbar.make(view, error.description, Snackbar.LENGTH_LONG).show()
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                Log.e("onPageFinished", "url is $url")
                CookieManager.getInstance().flush()
                if ((PREFIX + BASE_URL) == url) {
                    val intent = Intent(this@WvActivity, GActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    val daoSession: DaoSession = (application as App).daoSession
                    val itemUrlDao = daoSession.itemUrlDao
                    val items = itemUrlDao.loadAll()

                    if (url.isNotEmpty() && !url.contains(BASE_URL) && items.isEmpty()) {
                        val itemUrl = ItemUrl()
                        itemUrl.url = url
                        itemUrlDao.save(itemUrl)
                    }
                }
            }
        }

        immediateWebView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri?>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                valueCallback = filePathCallback
                data.launch(IMAGE_MIME_TYPE)
                return true
            }

            @SuppressLint("SetJavaScriptEnabled")
            override fun onCreateWindow(
                view: WebView?, isDialog: Boolean,
                isUserGesture: Boolean, resultMsg: Message
            ): Boolean {
                val newWebView = WebView(this@WvActivity)
                newWebView.settings.javaScriptEnabled = true
                newWebView.webChromeClient = this
                newWebView.settings.javaScriptCanOpenWindowsAutomatically = true
                newWebView.settings.domStorageEnabled = true
                newWebView.settings.setSupportMultipleWindows(true)
                val transport = resultMsg.obj as WebView.WebViewTransport
                transport.webView = newWebView
                resultMsg.sendToTarget()
                return true
            }
        }
    }

    companion object {
        const val PREFIX = "https://"
        const val BASE_URL = "whitewater.agency/"
        private const val IMAGE_MIME_TYPE = "image/*"
    }
}