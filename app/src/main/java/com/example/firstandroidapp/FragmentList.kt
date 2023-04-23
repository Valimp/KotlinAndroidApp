package com.example.firstandroidapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListFragment : Fragment() {
    
    // Equivalent du setContentView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(
            R.layout.fragment_list,
            container, false,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product1 = generateFakeProduct()
        val product2 = generateFakeProduct()
        val product3 = generateFakeProduct()
        val product4 = generateFakeProduct()
        val product5 = generateFakeProduct()


        val cardsList = listOf<Product>(product1, product2, product3, product4, product5)

        view.findViewById<RecyclerView>(R.id.list).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ListAdapter(cardsList, object : OnProductClickListener {
                override fun onProductClicked(barcode: String) {
                    // Naviguate to the fiche page
                    findNavController().navigate(
                        ListFragmentDirections.actionListFragmentToProductFragment(
                            barcode = barcode
                        )
                    )
                }
            })
        }
    }
}

class ListAdapter(
    private val listOfCards: List<Product>,
    val listener: OnProductClickListener,
) : RecyclerView.Adapter<PositionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        return PositionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.thumbnail, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        holder.updateCell(listOfCards[position])
    }

    override fun getItemCount(): Int {
        return listOfCards.size
    }

}

class PositionViewHolder(v: View, val listener: OnProductClickListener) : RecyclerView.ViewHolder(v) {

    private val placeHolderCard : AppCompatImageView = v.findViewById(R.id.placeholderImageCard)
    private val nameCard : TextView = v.findViewById(R.id.nameCard)
    private val brandCard : TextView = v.findViewById(R.id.brandCard)
    private val nutriscoreCard : TextView = v.findViewById(R.id.nutriscoreCard)
    private val calorieCard : TextView = v.findViewById(R.id.caloriesCard)

    fun updateCell(product: Product) {
        itemView.setOnClickListener {
            listener.onProductClicked(product.barcode)
        }

        Glide.with(itemView.context).load(product.thumbnail).into(placeHolderCard)
        nameCard.text = product.name
        brandCard.text = product.brand
        nutriscoreCard.text = "Nutriscore : " + product.nutriScore.label
        calorieCard.text = "XXX kCal/portion"
    }

}

interface OnProductClickListener {
    fun onProductClicked(barcode: String)
}