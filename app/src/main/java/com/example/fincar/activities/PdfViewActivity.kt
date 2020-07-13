package com.example.fincar.activities

import android.net.http.SslError
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.fincar.R
import com.example.fincar.app.Tools
import com.github.barteksc.pdfviewer.PDFView
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest
import java.io.File

const val EXTRA_PDF_URL = "extra-pdf-url"
class PdfViewActivity : AppCompatActivity() {

    private lateinit var pdfView: PDFView
    private lateinit var loadingRootLayout: FrameLayout
    private lateinit var loadingAnimationView: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)

        pdfView = findViewById(R.id.pdfView)
        loadingRootLayout = findViewById(R.id.loadingRootLayout)
        loadingAnimationView = findViewById(R.id.loadingAnimationView)

        val web = findViewById<WebView>(R.id.web)

        web.setDownloadListener { url, _, _, _, _ ->

            pdfView.visibility = View.VISIBLE
            web.visibility = View.GONE
            loadPdfFromUrl(url)

        }

        web.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                handler.proceed()
            }
        }

        val bookUrl = intent.getStringExtra(EXTRA_PDF_URL)
        d("bookUrl", bookUrl)
        web.loadUrl(bookUrl)
    }

    private fun loadPdfFromUrl(url: String) {
        Tools.startLoadingAnimation(loadingRootLayout, loadingAnimationView)
        FileLoader.with(this)
            .load(url)
            .fromDirectory("PDFFiles", FileLoader.DIR_EXTERNAL_PUBLIC)
            .asFile(object : FileRequestListener<File> {
                override fun onLoad(request: FileLoadRequest?, response: FileResponse<File>?) {
                    val pdfFile = response?.body

                    pdfView.fromFile(pdfFile)
                        .password(null)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .enableDoubletap(true)
                        .load()

                    Tools.cancelLoadingAnimation(loadingRootLayout, loadingAnimationView)

                }

                override fun onError(request: FileLoadRequest?, t: Throwable?) {
                    Toast.makeText(this@PdfViewActivity, t!!.message, Toast.LENGTH_SHORT).show()
                }

            })
    }
}
