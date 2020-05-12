package com.example.myapplication.recipes.editrecipe.util;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Step
import kotlinx.android.synthetic.main.item_recipe_step.view.*

class RecipeStepsAdapter internal constructor(
        context: Context?
): RecyclerView.Adapter<RecipeStepsAdapter.StepHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var steps: MutableList<Step> = arrayListOf()

    private lateinit var listener: ItemClickListener


    inner class StepHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stepDescription: TextView = itemView.tvStepDescription
        val stepNumber: TextView = itemView.tvStepNumber

        init {
            itemView.setOnClickListener {
                listener.onItemClick(steps[adapterPosition])
            }
        }

    }

    interface ItemClickListener {
        fun onItemClick(step: Step)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHolder {
        return StepHolder(inflater.inflate(R.layout.item_ingredient, parent, false))
    }

    override fun onBindViewHolder(holder: StepHolder, position: Int) {
        val current = steps[position]
        holder.stepDescription.text = current.description
        holder.stepNumber.text = current.number.toString()
    }

    override fun getItemCount(): Int = steps.size

    internal fun setSteps(steps: List<Step>) {
        this.steps = steps as MutableList<Step>
        notifyDataSetChanged()
    }

    fun getStep(position: Int): Step = steps[position]

    fun setItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

}
