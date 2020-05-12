package com.example.myapplication.inventory

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.myapplication.R
import com.example.myapplication.inventory.util.InventoryAdapter
import com.example.myapplication.models.InventoryIngredient
import kotlinx.android.synthetic.main.fragment_inventory.*

class InventoryFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: InventoryViewModel
    private lateinit var adapter: InventoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)

        setHasOptionsMenu(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InventoryViewModel::class.java)

        initViews()

        viewModel.allIngredients.observe(viewLifecycleOwner, Observer { ingredients ->
            ingredients?.let { adapter.setIngredients(ingredients) }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.fabAddIngredient -> {
                showIngredientDialog()
            }
            else -> {

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.inventory_menu, menu)

        val searchView: SearchView = menu.findItem(R.id.searchInventory).actionView as SearchView

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
                viewModel.deleteAllIngredients()
                Toast.makeText(context, "All Ingredients Deleted", Toast.LENGTH_SHORT).show()
            }
            else -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViews() {
        rvIngredientList.layoutManager = LinearLayoutManager(context)
        rvIngredientList.setHasFixedSize(true)
        adapter = InventoryAdapter(context)
        adapter.setItemClickListener(object: InventoryAdapter.ItemClickListener {
            override fun onItemClick(inventoryIngredient: InventoryIngredient) {
                showIngredientDialog(inventoryIngredient)
            }

        })
        rvIngredientList.adapter = adapter
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
                viewModel.delete(adapter.getIngredient(position))
                rvIngredientList.adapter!!.notifyItemRemoved(position)
                Toast.makeText(context, "Ingredient Deleted", Toast.LENGTH_SHORT).show()
            }
        })
        itemTouchHelper.attachToRecyclerView(rvIngredientList)

        fabAddIngredient.setOnClickListener(this)
    }

    private fun showIngredientDialog(inventoryIngredient: InventoryIngredient? = null) {
        val args = Bundle()
        inventoryIngredient?.let {
            args.putString("ingredientName", it.name)
            args.putDouble("ingredientAmount", it.amount)
            args.putString("ingredientUnit", it.unit)
            args.putInt("ingredientId", it.id)
        }
        val dialog = IngredientDialog()
        dialog.arguments = args
        activity?.supportFragmentManager?.let { dialog.show(it, "IngredientDialog") }
    }



}
