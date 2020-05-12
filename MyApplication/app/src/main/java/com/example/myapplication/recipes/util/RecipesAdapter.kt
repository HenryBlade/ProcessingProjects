package com.example.myapplication.recipes.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Recipe
import kotlinx.android.synthetic.main.item_recipe.view.*
import java.util.*
import kotlin.collections.ArrayList

class RecipesAdapter internal constructor(
        context: Context?
    ): RecyclerView.Adapter<RecipesAdapter.RecipeHolder>(),
        Filterable {

        private val inflater: LayoutInflater = LayoutInflater.from(context)
        private var recipes: MutableList<Recipe> = arrayListOf()
        private var recipesFull: MutableList<Recipe> = arrayListOf()

        private lateinit var listener: ItemClickListener

        inner class RecipeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val recipeTitle: TextView = itemView.tvRecipeTitle
            val recipeDesc: TextView = itemView.tvRecipeDesc

            init {
                itemView.setOnClickListener {
                    listener.onItemClick(recipes[adapterPosition])
                }
            }

        }

        interface ItemClickListener {
            fun onItemClick(recipe: Recipe)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
            return RecipeHolder(inflater.inflate(R.layout.item_recipe, parent, false))
        }

        override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
            val current = recipes[position]
            holder.recipeTitle.text = current.title
            holder.recipeDesc.text = current.description
        }

        fun setItemClickListener(listener: ItemClickListener) {
            this.listener = listener
        }

        internal fun setRecipes(recipes: List<Recipe>) {
            this.recipes = recipes as MutableList<Recipe>
            this.recipesFull = ArrayList(recipes)
            notifyDataSetChanged()
        }

        override fun getItemCount() = recipes.size

        fun getRecipe(position: Int): Recipe = recipes[position]

        override fun getFilter(): Filter {
            return recipeFilter
        }

        private var recipeFilter: Filter = object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = ArrayList<Recipe>()

                constraint?.let {
                    if (it.isEmpty()) {
                        filteredList.addAll(recipesFull)
                    } else {
                        val filterPattern: String = constraint.toString().toLowerCase(Locale.ROOT).trim()

                        for (recipe in recipesFull) {
                            if (recipe.title.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                                filteredList.add(recipe)
                            }
                        }
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.let {
                    recipes.clear()
                    val elements: List<Recipe> = results.values as List<Recipe>
                    recipes.addAll(elements)
                    notifyDataSetChanged()
                }
            }

        }
}