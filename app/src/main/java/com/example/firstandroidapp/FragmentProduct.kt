package com.example.firstandroidapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductFragment : Fragment() {

    lateinit var product: Product

    // Equivalent du setContentView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(
            R.layout.fragment_product,
            container, false,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val barcode = ProductFragmentArgs.fromBundle(requireArguments()).barcode
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)

        //Request :
        GlobalScope.launch(Dispatchers.Main) {
            // state.text = "Loading"
            progressBar.visibility = View.VISIBLE

            val currentProduct = withContext(Dispatchers.Default) {
                NetworkManager.getProduct(barcode)
                //NetworkManager.getProduct("5000159484695")
            }

            product =  currentProduct.toProduct()

            // state.text = "Success"
            val navHostFragment = NavHostFragment.create(
                R.navigation.details_nav
            )

            childFragmentManager.beginTransaction()
                .replace(
                    R.id.product_details_nav_host, navHostFragment
                ).commitAllowingStateLoss()

            navHostFragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onCreate(owner: LifecycleOwner) {
                    super.onCreate(owner)

                    NavigationUI.setupWithNavController(
                        view.findViewById<BottomNavigationView>(R.id.product_details_bottom_nav),
                        navHostFragment.navController
                    )
                }
            })

            progressBar.visibility = View.GONE


        }
    }
}