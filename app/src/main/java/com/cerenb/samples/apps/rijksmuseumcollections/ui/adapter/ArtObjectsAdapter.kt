package com.cerenb.samples.apps.rijksmuseumcollections.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cerenb.samples.apps.rijksmuseumcollections.databinding.ItemArtObjectBinding
import com.cerenb.samples.apps.rijksmuseumcollections.databinding.ItemArtObjectsSectionHeaderBinding
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObject
import com.cerenb.samples.apps.rijksmuseumcollections.ui.loadImage
import com.cerenb.samples.apps.rijksmuseumcollections.ui.model.ArtObjectListItem
import com.cerenb.samples.apps.rijksmuseumcollections.ui.view.ArtObjectsFragmentDirections

class ArtObjectsAdapter :
    PagingDataAdapter<ArtObjectListItem, RecyclerView.ViewHolder>(ArtObjectsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> HeaderViewHolder(
                ItemArtObjectsSectionHeaderBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            VIEW_TYPE_ART_OBJECT_ITEM -> ArtObjectViewHolder(
                ItemArtObjectBinding.inflate(layoutInflater, parent, false)
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ArtObjectListItem.ArtObjectHeader -> VIEW_TYPE_HEADER
            else -> VIEW_TYPE_ART_OBJECT_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val artObjectListItem = getItem(position)) {
            is ArtObjectListItem.ArtObjectHeader ->
                (holder as HeaderViewHolder).bind(artObjectListItem.artistName)
            is ArtObjectListItem.ArtObjectItem ->
                (holder as ArtObjectViewHolder).bind(artObjectListItem.artObject)
            else -> throw IllegalArgumentException()
        }
    }

    class ArtObjectViewHolder(
        private val binding: ItemArtObjectBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artObject: ArtObject) {
            artObject.imageUrl?.let { url ->
                binding.artImage.loadImage(
                    url = url,
                    context = binding.root.context
                )
            }
            binding.title.text = artObject.title
            binding.longTitle.text = artObject.longTitle
            binding.root.setOnClickListener {
                val direction =
                    ArtObjectsFragmentDirections.actionArtObjectsFragmentToArtObjectDetailFragment(
                        objectNumber = artObject.objectNumber
                    )
                it.findNavController().navigate(direction)
            }
        }
    }

    class HeaderViewHolder(
        private val binding: ItemArtObjectsSectionHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(header: String) {
            binding.header.text = header
        }
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ART_OBJECT_ITEM = 1
    }
}

class ArtObjectsDiffCallback : DiffUtil.ItemCallback<ArtObjectListItem>() {

    override fun areItemsTheSame(oldItem: ArtObjectListItem, newItem: ArtObjectListItem): Boolean {
        return if (oldItem is ArtObjectListItem.ArtObjectItem && newItem is ArtObjectListItem.ArtObjectItem) {
            oldItem.artObject.objectNumber == newItem.artObject.objectNumber
        } else if (oldItem is ArtObjectListItem.ArtObjectHeader && newItem is ArtObjectListItem.ArtObjectHeader) {
            oldItem.artistName == newItem.artistName
        } else {
            oldItem == newItem
        }
    }

    override fun areContentsTheSame(
        oldItem: ArtObjectListItem,
        newItem: ArtObjectListItem
    ): Boolean {
        return oldItem == newItem
    }
}
