package com.example.myapplication.recipes.editrecipe

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.myapplication.R
import com.example.myapplication.models.Recipe
import com.example.myapplication.models.RecipeIngredient
import com.example.myapplication.models.Step
import com.example.myapplication.recipes.editrecipe.util.RecipeIngredientAdapter
import com.example.myapplication.recipes.editrecipe.util.RecipeStepsAdapter
import kotlinx.android.synthetic.main.fragment_edit_recipe.*
import kotlinx.android.synthetic.main.fragment_inventory.rvIngredientList

class EditRecipeFragment : Fragment() {

    private lateinit var viewModel: EditRecipeViewModel
    private lateinit var ingredientsAdapter: RecipeIngredientAdapter
    private lateinit var stepsAdapter: RecipeStepsAdapter

    private var isNew = false
    private var recipeId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_edit_recipe, container, false)

        setHasOptionsMenu(true)

        val args = arguments
        args?.let {
            isNew = args.getBoolean("isNew")
            recipeId = args.getInt("recipeId")
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = EditRecipeViewModel(requireActivity().application, recipeId)
//        viewModel = ViewModelProvider(this).get(EditRecipeViewModel::class.java)

        initViews()

        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.edit_recipe_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.deleteAllIngredients -> {
                viewModel.deleteAllIngredients()
                Toast.makeText(context, "All Ingredients Deleted", Toast.LENGTH_SHORT).show()
            }
            R.id.deleteAllSteps -> {
                viewModel.deleteAllSteps()
                Toast.makeText(context, "All Steps Deleted", Toast.LENGTH_SHORT).show()
            }
            R.id.saveRecipe -> {
                val recipeTitle: String = etRecipeTitle.text.toString()
                val recipeDesc: String = etRecipeDesc.text.toString()
                if (recipeTitle.trim { it <= ' ' }.isEmpty()) {
                    Toast.makeText(context, "Please enter an ingredient name", Toast.LENGTH_SHORT)
                        .show()
                    return false
                }; if (recipeDesc.trim { it <= ' ' }.isEmpty()) {
                    Toast.makeText(context, "Please enter an ingredient amount", Toast.LENGTH_SHORT)
                        .show()
                    return false
                }

                if (isNew) {
                    val recipe = Recipe(0, etRecipeTitle.text.toString(), etRecipeDesc.text.toString())
                    viewModel.allIngredients.value?.let {
                        recipe.ingredientList = it as MutableList<RecipeIngredient>
                    }; viewModel.allSteps.value?. let {
                        recipe.stepList = it as MutableList<Step>
                    }

                    viewModel.insert(recipe)

                }; else {
                    val recipe: Recipe = viewModel.getRecipe().value!!
                    recipe.ingredientList = viewModel.allIngredients.value as MutableList<RecipeIngredient>
                    recipe.stepList = viewModel.allSteps.value as MutableList<Step>
                    viewModel.update(recipe)
                }
                Navigation.findNavController(requireView()).navigate(R.id.action_editRecipeFragment_to_recipesFragment)
            }
            android.R.id.home -> {
                if (isNew)
                    Toast.makeText(context, "No recipe created", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(context, "Recipe edit cancelled", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViews() {
        if (!isNew) {
//            var r = viewModel.getRecipe().value
            etRecipeTitle.setText(viewModel.getRecipe().value!!.title)
            etRecipeDesc.setText(viewModel.getRecipe().value!!.description)
            tvRecipeId.setText(viewModel.getRecipe().value!!.id)
        }

        rvIngredientList.layoutManager = LinearLayoutManager(context)
        rvIngredientList.setHasFixedSize(true)
        ingredientsAdapter = RecipeIngredientAdapter(context)
        ingredientsAdapter.setItemClickListener(object: RecipeIngredientAdapter.ItemClickListener {
            override fun onItemClick(ingredient: RecipeIngredient) {
                showEditItemDialog(ingredient = ingredient)
            }

        })
        rvIngredientList.adapter = ingredientsAdapter
        val ingredientItemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteIngredient(ingredientsAdapter.getIngredient(position))
                rvIngredientList.adapter!!.notifyItemRemoved(position)
                Toast.makeText(context, "Ingredient Deleted", Toast.LENGTH_SHORT).show()
            }
        })
        ingredientItemTouchHelper.attachToRecyclerView(rvIngredientList)

        rvStepList.layoutManager = LinearLayoutManager(context)
        rvStepList.setHasFixedSize(true)
        stepsAdapter = RecipeStepsAdapter(context)
        stepsAdapter.setItemClickListener(object: RecipeStepsAdapter.ItemClickListener {
            override fun onItemClick(step: Step) {
                showEditItemDialog(step = step)
            }

        })
        rvStepList.adapter = stepsAdapter
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteStep(stepsAdapter.getStep(position))
                rvStepList.adapter!!.notifyItemRemoved(position)
                Toast.makeText(context, "Step Deleted", Toast.LENGTH_SHORT).show()
            }
        })
        itemTouchHelper.attachToRecyclerView(rvStepList)
    }

    fun showEditItemDialog(step: Step? = null, ingredient: RecipeIngredient? = null) {
        val args = Bundle()
        step?.let {
            args.putString("itemName", step.description)
            args.putInt("itemNumber", step.number)
            args.putInt("itemId", step.number)
        }
        ingredient?.let {
            args.putString("itemName", ingredient.name)
            args.putDouble("itemAmount", ingredient.amount)
            args.putString("itemUnit", ingredient.unit)
            args.putInt("itemId", ingredient.id)
        }

        val dialog = ItemDialog()
        dialog.arguments = args
        activity?.supportFragmentManager?.let { dialog.show(it, "ItemDialog") }
    }

}
