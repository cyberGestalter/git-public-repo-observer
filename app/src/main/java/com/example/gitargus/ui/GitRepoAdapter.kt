package com.example.gitargus.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.GitRepo
import com.example.gitargus.R

class GitRepoAdapter(private val onClick: (GitRepo) -> Unit) :
    RecyclerView.Adapter<GitRepoAdapter.GitRepoViewHolder>() {

    private var itemList = mutableListOf<GitRepo>()

    inner class GitRepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val repoPhoto = itemView.findViewById<ImageView>(R.id.repo_photo)
        private val repoId = itemView.findViewById<TextView>(R.id.repo_id)
        private val repoName = itemView.findViewById<TextView>(R.id.repo_name)
        private val repoOwner = itemView.findViewById<TextView>(R.id.repo_owner)
        private val repoDescription = itemView.findViewById<TextView>(R.id.repo_description)

        fun bind(repo: GitRepo, onClick: (GitRepo) -> Unit) {
            itemView.setOnClickListener {
                onClick(repo)
            }
            Glide.with(itemView.context)
                .load(repo.owner.ownerPhoto)
                .circleCrop()
                .into(repoPhoto)

            repoId.text = "${repo.id}"
            repoName.text = repo.name
            repoOwner.text = repo.owner.ownerName
            repoDescription.text = repo.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitRepoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.git_repo_item, parent, false)
        return GitRepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GitRepoViewHolder, position: Int) =
        holder.bind(itemList[position], onClick)

    override fun getItemCount(): Int = itemList.size

    fun update(items: MutableList<GitRepo>) {
        val diffResult = DiffUtil.calculateDiff(
            GitRepoDiffUtil(itemList, items), false
        )
        itemList.clear()
        itemList.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }
}

class GitRepoDiffUtil(
    private val oldList: MutableList<GitRepo>,
    private val newList: MutableList<GitRepo>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

}