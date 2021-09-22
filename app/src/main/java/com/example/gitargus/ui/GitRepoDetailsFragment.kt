package com.example.gitargus.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.gitargus.R

class GitRepoDetailsFragment : Fragment() {

    private lateinit var repoLink: String
    private lateinit var toolbarHandler: ToolbarHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)
        toolbarHandler = context as ToolbarHandler
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            arguments?.let {
                it.getString(REPO_LINK_EXTRA)?.let { link -> repoLink = link }
            }
        } else {
            savedInstanceState.getString(REPO_LINK_EXTRA)?.let { repoLink = it }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_git_repo_details, container, false)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView = view.findViewById<WebView>(R.id.repo_url_observer)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webView.webViewClient = RepoWebViewClient()
        webView.loadUrl(repoLink)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = toolbarHandler.handleItem(item)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(REPO_LINK_EXTRA, repoLink)
    }

    private inner class RepoWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) = false
    }

    companion object {
        const val TAG = "GitRepoDetailsFragment"
        const val REPO_LINK_EXTRA = "repo_link_extra"
        fun newInstance(repoUrl: String) = GitRepoDetailsFragment().also {
            it.arguments = bundleOf(REPO_LINK_EXTRA to repoUrl)
        }
    }
}