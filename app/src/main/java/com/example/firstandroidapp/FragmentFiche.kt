package com.example.firstandroidapp

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class FicheFragment : Fragment() {


    // Equivalent du setContentView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(
            R.layout.fragment_fiche,
            container, false,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentProduct = (requireParentFragment().requireParentFragment() as ProductFragment).product

        Glide.with(this).load(currentProduct.thumbnail).into(view.findViewById(R.id.placeholderImage))

        view.findViewById<TextView>(R.id.name).setText(currentProduct.name)

        view.findViewById<TextView>(R.id.brand).setText(currentProduct.brand)

        view.findViewById<AppCompatImageView>(R.id.nutriscore).setImageResource(resources.getIdentifier(
            "nutriscore_${currentProduct.nutriScore.label.lowercase()}",
            "drawable",
            requireContext().packageName,
        ))

        view.findViewById<TextView>(R.id.barcode).applyBoldText("Code-Barre", currentProduct.barcode)

        view.findViewById<TextView>(R.id.quantity).applyBoldText(
            "Quantité",
            if (currentProduct.quantity == "") {
                "Inconnue"
            } else {
                currentProduct.quantity
            }
        )

        view.findViewById<TextView>(R.id.saleCountry).applyBoldText(
            "Vendu en",
            if (currentProduct.countries.isEmpty()) {
                "Aucun pays revendeur"
            } else {
                currentProduct.countries.joinToString(", ")
            }
        )

        view.findViewById<TextView>(R.id.ingrediant).applyBoldText(
            "Ingrédiants",
            if (currentProduct.ingredients.isEmpty()) {
                "Inconnus"
            } else {
                currentProduct.ingredients.joinToString(", ")
            }
        )

        view.findViewById<TextView>(R.id.allergens).applyBoldText(
            "Allergènes",
            if (currentProduct.allergens.isEmpty()) {
                "Aucun"
            } else {
                currentProduct.allergens.joinToString(", ")
            }
        )

        view.findViewById<TextView>(R.id.additives).applyBoldText(
            "Additifs",
            if (currentProduct.additives.isEmpty()) {
                "Aucun"
            } else {
                currentProduct.additives.joinToString(", ")
            }
        )
    }

    private fun TextView.applyBoldText(prefix: String, value: String) {
        val text = "$prefix : $value"
        val builder = SpannableStringBuilder(text)
        builder.setSpan(StyleSpan(Typeface.BOLD), 0, text.indexOf(":") + 1, 0)
        setText(builder)
    }

}

