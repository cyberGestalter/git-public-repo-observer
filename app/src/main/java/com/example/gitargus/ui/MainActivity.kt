package com.example.gitargus.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.domain.GitRepo
import com.example.gitargus.R

class MainActivity : AppCompatActivity(), GitRepoListFragment.OnItemClickListener, ToolbarHandler {

    lateinit var repoLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            openFragment(GitRepoListFragment.newInstance(), GitRepoListFragment.TAG)
        } else {
            savedInstanceState.getString(REPO_LINK)?.let {
                repoLink = it
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.share_link -> {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, repoLink)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
                true
            } else {
                false
            }
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(REPO_LINK, repoLink)
    }

    override fun onClick(repo: GitRepo) {
        repoLink = repo.htmlUrl
        openFragment(GitRepoDetailsFragment.newInstance(repo.htmlUrl), GitRepoDetailsFragment.TAG)
    }

    override fun handleItem(item: MenuItem): Boolean {
        return onOptionsItemSelected(item)
    }

    private fun openFragment(fragment: Fragment, tag: String) {
        val fragmentCount = supportFragmentManager.fragments.size
        supportFragmentManager.beginTransaction().run {
            replace(R.id.fragment_container, fragment, tag)
            if (fragmentCount != 0) {
                addToBackStack(tag)
            }
            commit()
        }
    }

    companion object {
        const val REPO_LINK = "repo_link"
    }
}