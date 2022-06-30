package com.cerenb.samples.apps.rijksmuseumcollections.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.cerenb.samples.apps.rijksmuseumcollections.R
import com.cerenb.samples.apps.rijksmuseumcollections.databinding.FragmentArtObjectsBinding
import com.cerenb.samples.apps.rijksmuseumcollections.ui.adapter.ArtObjectLoadStateAdapter
import com.cerenb.samples.apps.rijksmuseumcollections.ui.adapter.ArtObjectsAdapter
import com.cerenb.samples.apps.rijksmuseumcollections.ui.viewmodel.ArtObjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArtObjectsFragment : Fragment(R.layout.fragment_art_objects) {

    private var _binding: FragmentArtObjectsBinding? = null
    private val binding: FragmentArtObjectsBinding
        get() = requireNotNull(_binding)

    private val viewModel: ArtObjectViewModel by viewModels()
    private val artObjectsAdapter by lazy { ArtObjectsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtObjectsBinding.inflate(inflater, container, false)

        binding.artObjectList.adapter = artObjectsAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupUi()
    }

    private fun setupListeners() {
        binding.swipeRefresh.setOnRefreshListener { artObjectsAdapter.refresh() }
    }

    private fun setupUi() {
        binding.artObjectList.adapter = artObjectsAdapter.withLoadStateHeaderAndFooter(
            header = ArtObjectLoadStateAdapter(artObjectsAdapter),
            footer = ArtObjectLoadStateAdapter(artObjectsAdapter)
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                artObjectsAdapter.loadStateFlow.collect { loadStates ->
                    binding.swipeRefresh.isRefreshing =
                        loadStates.mediator?.refresh is LoadState.Loading
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getArtObjects().collectLatest { artObjects ->
                    artObjectsAdapter.submitData(artObjects)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}