package com.example.newzzapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// adapter use to bind data from the view to recycler view//
class NewsAdapter( private val listner:NewsItemClicked) : RecyclerView.Adapter<NewsViewHolder>() {

    private val items : ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item , parent , false)
        val viewHolder = NewsViewHolder(view)
        // layout inflater changes xl to view ;
        view.setOnClickListener{
            listner.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItems = items[position]
        holder.titleView.text = currentItems.title
        holder.author.text = currentItems.author
        Glide.with(holder.itemView.context).load(currentItems.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews: ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }

}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){  // view holder is used by adapter to inflate the data //
    val titleView : TextView = itemView.findViewById(R.id.title)
    val image : ImageView = itemView.findViewById(R.id.image)
    val author : TextView = itemView.findViewById(R.id.author)
}

interface NewsItemClicked {
    fun onItemClicked(item:News)
}