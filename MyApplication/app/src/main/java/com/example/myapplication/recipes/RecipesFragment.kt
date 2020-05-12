package com.example.myapplication.recipes

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Recipe
import com.example.myapplication.recipes.util.RecipesAdapter
import kotlinx.android.synthetic.main.fragment_recipes.*

class RecipesFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: RecipesViewModel
    private lateinit var adapter: RecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =  inflater.inflate(R.layout.fragment_recipes, container, false)

        setHasOptionsMenu(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecipesViewModel::class.java)

        view?.let { initViews(it) }

        viewModel.allRecipes.observe(viewLifecycleOwner, Observer { recipes ->
            recipes?.let { adapter.setRecipes(recipes) }
        })

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.fabAddRecipe -> {
                openEditRecipeFragment(v)
            }
            else -> {

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.recipes_menu, menu)

        val searchView: SearchView = menu.findItem(R.id.searchRecipes).actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.deleteAllIngredients -> {
                viewModel.deleteAllRecipes()
                Toast.makeText(context, "All Recipes Deleted", Toast.LENGTH_SHORT).show()
            }
            else -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViews(view: View) {
        rvRecipeList.layoutManager = LinearLayoutManager(context)
        rvRecipeList.setHasFixedSize(true)
        adapter = RecipesAdapter(context)
        adapter.setItemClickListener(object: RecipesAdapter.ItemClickListener {
            override fun onItemClick(recipe: Recipe) {
                openEditRecipeFragment(view, recipe)
            }

        })
        rvRecipeList.adapter = adapter
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
                viewModel.delete(adapter.getRecipe(position))
                rvRecipeList.adapter!!.notifyItemRemoved(position)
                Toast.makeText(context, "Recipe Deleted", Toast.LENGTH_SHORT).show()
            }
        })
        itemTouchHelper.attachToRecyclerView(rvRecipeList)

        fabAddRecipe.setOnClickListener(this)
    }

    fun openEditRecipeFragment(view: View, recipe: Recipe? = null) {
        val args = Bundle()
        recipe?.let {
            args.putBoolean("isNew", false)
            args.putInt("recipeId", recipe.id)
        } ?: run {
            args.putBoolean("isNew", true)
            args.putInt("recipeId", 0)
        }
        Navigation.findNavController(view).navigate(R.id.action_recipesFragment_to_editRecipeFragment, args)
    }


}
