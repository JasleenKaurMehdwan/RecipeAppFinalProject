package project.st991587295.jasleen.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


import project.st991587295.jasleen.R
import project.st991587295.jasleen.model.Recipe



class RecipeAdapter(private val context: Context, private val RecipeList:ArrayList<Recipe>) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var imageView: ImageView = itemView.findViewById(R.id.recipeimage)
        var name : TextView = itemView.findViewById(R.id.recipename)
        var category: TextView = itemView.findViewById(R.id.recipecategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe : Recipe = RecipeList[position]
        if(recipe == null)
        {
            Toast.makeText(context, "null", Toast.LENGTH_SHORT).show()
        }
        Glide.with(context).load(recipe.image).into(holder.imageView)
        holder.name.text = recipe.name
        holder.category.text = recipe.category

    }

    override fun getItemCount(): Int {
        return RecipeList.size
    }
}