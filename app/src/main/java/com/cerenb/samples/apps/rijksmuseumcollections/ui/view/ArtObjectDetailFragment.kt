package com.cerenb.samples.apps.rijksmuseumcollections.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.cerenb.samples.apps.rijksmuseumcollections.R
import com.cerenb.samples.apps.rijksmuseumcollections.databinding.FragmentArtObjectDetailBinding
import com.cerenb.samples.apps.rijksmuseumcollections.domain.model.ArtObjectDetail
import com.cerenb.samples.apps.rijksmuseumcollections.ui.loadImage
import com.cerenb.samples.apps.rijksmuseumcollections.ui.viewmodel.ArtObjectDetailViewModel
import com.cerenb.samples.apps.rijksmuseumcollections.ui.viewmodel.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArtObjectDetailFragment : Fragment(R.layout.fragment_art_object_detail) {
    private var _binding: FragmentArtObjectDetailBinding? = null
    private val binding: FragmentArtObjectDetailBinding
        get() = requireNotNull(_binding)

    private val viewModel: ArtObjectDetailViewModel by viewModels()
    private val args: ArtObjectDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtObjectDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadArtObjectDetail(args.objectNumber).collectLatest {
                    when (it) {
                        UiState.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.artObjectDetailsGroup.isVisible = false
                        }
                        is UiState.ShowDetails -> {
                            binding.progressBar.isVisible = false
                            binding.artObjectDetailsGroup.isVisible = true
                            setupUi(it.artObjectDetail)
                        }
                        is UiState.Error -> {
                            Toast.makeText(
                                context,
                                getString(R.string.generic_error_message),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun setupUi(artObjectDetail: ArtObjectDetail) {
        artObjectDetail.imageUrl?.let { url ->
            binding.artImage.loadImage(
                url = url,
                context = binding.root.context
            )
        }
        binding.title.text = artObjectDetail.title
        binding.artistLabel.text = artObjectDetail.makerLine
        binding.subTitle.text = artObjectDetail.subTitle
        binding.description.text = artObjectDetail.description
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}