package com.example.myapplication.inventory.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.InventoryIngredient
import kotlinx.android.synthetic.main.item_ingredient.view.*
import java.util.*
import kotlin.collections.ArrayList

class InventoryAdapter internal constructor(
    context: Context?
): RecyclerView.Adapter<InventoryAdapter.IngredientHolder>(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var ingredients: MutableList<InventoryIngredient> = arrayListOf()
    private var ingredientsFull: MutableList<InventoryIngredient> = arrayListOf()

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
        fun onItemClick(inventoryIngredient: InventoryIngredient)
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

    internal fun setIngredients(inventoryIngredients: List<InventoryIngredient>) {
        this.ingredients = inventoryIngredients as MutableList<InventoryIngredient>
        this.ingredientsFull = ArrayList(inventoryIngredients)
        notifyDataSetChanged()
    }

    override fun getItemCount() = ingredients.size

    fun getIngredient(position: Int): InventoryIngredient = ingredients[position]

    override fun getFilter(): Filter {
        return ingredientFilter
    }

    private var ingredientFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<InventoryIngredient>()

            constraint?.let {
                if (it.isEmpty()) {
                    filteredList.addAll(ingredientsFull)
                } else {
                    val filterPattern: String = constraint.toString().toLowerCase(Locale.ROOT).trim()

                    for (ingredient in ingredientsFull) {
                        if (ingredient.name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                            filteredList.add(ingredient)
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
                ingredients.clear()
                val elements: List<InventoryIngredient> = results.values as List<InventoryIngredient>
                ingredients.addAll(elements)
                notifyDataSetChanged()
            }
        }

    }

}