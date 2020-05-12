package com.example.myapplication.inventory

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.models.InventoryIngredient

class IngredientDialog: DialogFragment() {

    private lateinit var viewModel: InventoryViewModel

    private lateinit var etIngredientName: EditText
    private lateinit var etIngredientAmount: EditText
    private lateinit var etIngredientUnit: EditText
    private var ingredientId: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        val view: View = requireActivity().layoutInflater.inflate(R.layout.dialog_ingredient, null)

        viewModel = ViewModelProvider(requireActivity())[InventoryViewModel::class.java]
        builder.setView(view)

        initViews(view)

        val args = arguments
        args?.let {
            if (args.isEmpty) {
                createAddIngredientBuilder(builder)
            } else {
                etIngredientName.setText(args.getString("ingredientName"))
                etIngredientAmount.setText(args.getDouble("ingredientAmount").toString())
                etIngredientUnit.setText(args.getString("ingredientUnit"))
                ingredientId = args.get("ingredientId") as Int
                createEditIngredientBuilder(builder)
            }
        }

        return builder.create()
    }

    private fun initViews(view: View) {
        etIngredientName = view.findViewById(R.id.etIngredientName)
        etIngredientAmount = view.findViewById(R.id.etIngredientAmount)
        etIngredientUnit = view.findViewById(R.id.etIngredientUnit)
    }

    private fun createAddIngredientBuilder(builder: AlertDialog.Builder) {
        builder.setTitle("Add Ingredient")
            .setNegativeButton(
                "Cancel"
            ) { dialog: DialogInterface, _: Int ->
                Toast.makeText(context, "No Ingredient Added", Toast.LENGTH_SHORT).show()
                dialog.cancel()
            }
            .setPositiveButton(
                "Add Ingredient"
            ) { _: DialogInterface?, _: Int ->
                Toast.makeText(context, "Ingredient Added", Toast.LENGTH_SHORT).show()
                addIngredient()
            }
    }

    private fun addIngredient() {
        val ingredientName: String = etIngredientName.text.toString()
        val ingredientAmount: String = etIngredientAmount.text.toString()
        val ingredientUnit: String = etIngredientUnit.text.toString()
        if (ingredientName.trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(context, "Please enter an ingredient name", Toast.LENGTH_SHORT)
                .show()
            return
        }; if (ingredientAmount.trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(context, "Please enter an ingredient amount", Toast.LENGTH_SHORT)
                .show()
            return
        }; if (ingredientUnit.trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(context, "Please enter an ingredient unit", Toast.LENGTH_SHORT)
                .show()
            return
        }

        viewModel.insert(
            InventoryIngredient(
                0,
                ingredientName,
                ingredientAmount.toDouble(),
                ingredientUnit
            )
        )
    }

    private fun createEditIngredientBuilder(builder: AlertDialog.Builder) {
        builder.setTitle("Edit Ingredient")
            .setNegativeButton(
                "Cancel"
            ) { dialog: DialogInterface, _: Int ->
                Toast.makeText(context, "Edit Ingredient Cancelled", Toast.LENGTH_SHORT).show()
                dialog.cancel()
            }
            .setPositiveButton(
                "Edit Ingredient"
            ) { _: DialogInterface?, _: Int ->
                Toast.makeText(context, "Ingredient Edited", Toast.LENGTH_SHORT).show()
                editIngredient()
            }
    }

    private fun editIngredient() {
        val ingredientName: String = etIngredientName.text.toString()
        val ingredientAmount: String = etIngredientAmount.text.toString()
        val ingredientUnit: String = etIngredientUnit.text.toString()
        if (ingredientName.trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(context, "Please enter an ingredient name", Toast.LENGTH_SHORT)
                .show()
            return
        }; if (ingredientAmount.trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(context, "Please enter an ingredient amount", Toast.LENGTH_SHORT)
                .show()
            return
        }; if (ingredientUnit.trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(context, "Please enter an ingredient unit", Toast.LENGTH_SHORT)
                .show()
            return
        }

        viewModel.update(
            InventoryIngredient(
                ingredientId,
                ingredientName,
                ingredientAmount.toDouble(),
                ingredientUnit
            )
        )
    }

}
