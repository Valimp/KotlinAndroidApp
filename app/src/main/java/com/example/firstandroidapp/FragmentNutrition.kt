package com.example.firstandroidapp

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment


class NutritionFragment : Fragment() {


    // Equivalent du setContentView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(
            R.layout.fragment_nutrition,
            container, false,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DrawableCompat.setTintList(view.findViewById<View>(R.id.roundedLipid).background, ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), R.color.nutrient_level_low)))
        DrawableCompat.setTintList(view.findViewById<View>(R.id.roundedAcid).background, ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.nutrient_level_low)))
        DrawableCompat.setTintList(view.findViewById<View>(R.id.roundedSugar).background, ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.nutrient_level_moderate)))
        DrawableCompat.setTintList(view.findViewById<View>(R.id.roundedSalt).background, ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.nutrient_level_high)))

    }

}