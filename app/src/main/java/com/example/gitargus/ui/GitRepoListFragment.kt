package com.example.gitargus.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.domain.GitRepo
import com.example.gitargus.GitArgusApp
import com.example.gitargus.R
import com.example.gitargus.viewmodel.GitRepoListViewModel
import com.example.gitargus.viewmodel.ViewModelFactory
import javax.inject.Inject

class GitRepoListFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var onItemClickListener: OnItemClickListener

    private val viewModel: GitRepoListViewModel by activityViewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onItemClickListener = context as OnItemClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GitArgusApp.viewModelFactoryComponent.injectIntoGitRepoList(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_git_repo_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repoRecyclerView = view.findViewById<RecyclerView>(R.id.repo_recycler_view)
        repoRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadMoreGitRepos()
                }
            }
        })
        val adapter = GitRepoAdapter(this::onClick)
        repoRecyclerView.adapter = adapter

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            viewModel.loadReposAgain()
            repoRecyclerView.smoothScrollToPosition(0)
        }

        viewModel.gitRepos.observe(viewLifecycleOwner, { newList ->
            newList?.let { adapter.update(newList.toMutableList()) }
        })
    }

    private fun onClick(repo: GitRepo) = onItemClickListener.onClick(repo)

    interface OnItemClickListener {
        fun onClick(repo: GitRepo)
    }

    companion object {
        const val TAG = "GitRepoListFragment"
        fun newInstance() = GitRepoListFragment()
    }
}