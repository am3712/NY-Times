package com.example.nytimes.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.nytimes.data.remote.Article
import com.example.nytimes.databinding.PopularFragmentBinding
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class PopularFragment : Fragment() {
    private val viewModel: PopularViewModel by viewModels()

    private var _binding: PopularFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopularFragmentBinding.inflate(inflater, container, false)

        //set adapter
        binding.articlesList.adapter = ArticleAdapter(object : ArticleListener {
            override fun onClick(article: Article) {
                Timber.i("Item clicked")
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setupObserver()
    }

    private fun setupObserver() {
        //observe no internet Connection or any error
        viewModel.error.observe(viewLifecycleOwner, {
            if (it != "OK") {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT)
                    .show()
                viewModel.clearErrorStatus();
            }
        })
    }

}