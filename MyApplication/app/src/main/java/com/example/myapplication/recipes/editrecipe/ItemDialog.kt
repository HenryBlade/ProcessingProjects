package com.example.myapplication.recipes.editrecipe

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.models.InventoryIngredient
import com.example.myapplication.models.RecipeIngredient
import com.example.myapplication.models.Step

class ItemDialog: DialogFragment() {

    private lateinit var viewModel: EditRecipeViewModel

    private lateinit var etItemName: EditText
    private lateinit var tvItemNumber: TextView
    private lateinit var etItemAmount: EditText
    private lateinit var etItemUnit: EditText

    private var itemId: Int = 0
    private var itemType: String? = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        viewModel = ViewModelProvider(requireActivity())[EditRecipeViewModel::class.java]

        val args = arguments
        itemType = args!!.getString("itemType")

        var view: View? = null
        when (itemType) {
            "Ingredient" -> {
                view = requireActivity().layoutInflater.inflate(R.layout.dialog_ingredient, null)

                etItemName = view.findViewById(R.id.etIngredientName)
                etItemAmount = view.findViewById(R.id.etIngredientAmount)
                etItemUnit = view.findViewById(R.id.etIngredientUnit)

                etItemName.setText(args.getString("itemName"))
                etItemAmount.setText(args.getDouble("itemAmount").toString())
                etItemUnit.setText(args.getString("itemUnit"))

                createEditIngredientBuilder(builder)


            } "Step" -> {
                view = requireActivity().layoutInflater.inflate(R.layout.dialog_ingredient, null)

                etItemName = view.findViewById(R.id.etStepName)
                tvItemNumber = view.findViewById(R.id.tvStepNumber)

                etItemName.setText(args.getString("itemName"))
                tvItemNumber.text = args.getInt("itemNumber").toString()

                createEditStepBuilder(builder)

            } else -> {
                dismiss()
            }

        }

        itemId = args.getInt("itemId")

        builder.setView(view)

        return builder.create()
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
        val ingredientName: String = etItemName.text.toString()
        val ingredientAmount: String = etItemAmount.text.toString()
        val ingredientUnit: String = etItemUnit.text.toString()
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

        viewModel.updateIngredient(
            RecipeIngredient(
                itemId,
                ingredientName,
                ingredientAmount.toDouble(),
                ingredientUnit
            )
        )
    }

    private fun createEditStepBuilder(builder: AlertDialog.Builder) {
        builder.setTitle("Edit Step")
            .setNegativeButton(
                "Cancel"
            ) { dialog: DialogInterface, _: Int ->
                Toast.makeText(context, "Edit Step Cancelled", Toast.LENGTH_SHORT).show()
                dialog.cancel()
            }
            .setPositiveButton(
                "Edit Step"
            ) { _: DialogInterface?, _: Int ->
                Toast.makeText(context, "Step Edited", Toast.LENGTH_SHORT).show()
                editStep()
            }
    }

    private fun editStep() {
        val stepDescription: String = etItemName.text.toString()
        if (stepDescription.trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(context, "Please enter an step description", Toast.LENGTH_SHORT)
                .show()
            return
        }

        viewModel.updateStep(
            Step(
                stepDescription,
                itemId
            )
        )
    }

}
