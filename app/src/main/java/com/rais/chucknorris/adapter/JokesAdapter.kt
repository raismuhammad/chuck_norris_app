package com.rais.chucknorris.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rais.chucknorris.data.remote.response.Jokes
import com.rais.chucknorris.databinding.JokesItemBinding

class JokesAdapter : RecyclerView.Adapter<JokesAdapter.JokesViewHolder>() {

    var jokesList = ArrayList<Jokes>()

    fun setJokesList(jokes: List<Jokes>) {
        this.jokesList.clear()
        this.jokesList = jokes as ArrayList<Jokes>
        notifyDataSetChanged()
    }

    class JokesViewHolder(val binding: JokesItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(jokes: Jokes) {
            with(itemView) {
                binding.tvJokes.text = jokes.value
                binding.tvCreatedAt.text = jokes.created_at
                binding.lvItem.setOnClickListener {
                    val url = jokes.url
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JokesViewHolder{
        return JokesViewHolder(JokesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) {
        holder.bind(jokesList[position])
    }

    override fun getItemCount(): Int {
        return jokesList.size
    }
}