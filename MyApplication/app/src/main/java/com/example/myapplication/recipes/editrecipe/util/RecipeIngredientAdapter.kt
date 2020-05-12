package com.example.myapplication.recipes.editrecipe.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.RecipeIngredient
import kotlinx.android.synthetic.main.item_ingredient.view.*

class RecipeIngredientAdapter internal constructor(
    context: Context?
): RecyclerView.Adapter<RecipeIngredientAdapter.IngredientHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var ingredients: MutableList<RecipeIngredient> = arrayListOf()

    private lateinit var listener: ItemClickListener


    inner class IngredientHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ingredientName: TextView = itemView.tvIngredientName
        val ingredientAmount: TextView = itemView.tvIngredientAmount
        val ingredientUnit: TextView = itemView.tvIngredientUnit

        init {
            itemView.setOnClickListener {
                listener.onItemClick(ingredients[adapterPosition])
            }
        }

    }

    interface ItemClickListener {
        fun onItemClick(ingredient: RecipeIngredient)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientHolder {
        return IngredientHolder(inflater.inflate(R.layout.item_ingredient, parent, false))
    }

    override fun onBindViewHolder(holder: IngredientHolder, position: Int) {
        val current = ingredients[position]
        holder.ingredientName.text = current.name
        holder.ingredientAmount.text = current.amount.toString()
        holder.ingredientUnit.text = current.unit
    }

    fun setItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    internal fun setIngredients(ingredients: List<RecipeIngredient>) {
        this.ingredients = ingredients as MutableList<RecipeIngredient>
        notifyDataSetChanged()
    }

    override fun getItemCount() = ingredients.size

    fun getIngredient(position: Int): RecipeIngredient = ingredients[position]

}