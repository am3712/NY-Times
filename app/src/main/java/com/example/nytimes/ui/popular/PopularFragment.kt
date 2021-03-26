package com.example.nytimes.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nytimes.data.remote.Article
import com.example.nytimes.databinding.PopularFragmentBinding
import com.google.android.material.snackbar.Snackbar

class PopularFragment : Fragment() {
    private val viewModel: PopularViewModel by viewModels()

    private var _binding: PopularFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopularFragmentBinding.inflate(inflater, container, false)

        //set adapter
        binding.articlesList.adapter = ArticleAdapter(object : ArticleListener {
            override fun onClick(article: Article) {
                findNavController().navigate(
                    PopularFragmentDirections.actionNavPopularToDetailsFragment(
                        article
                    )
                )
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
            if (it != "CLEAR") {
                //error
                if (it != "OK") {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT)
                        .show()
                    if (viewModel.articles.value.isNullOrEmpty())
                        binding.connectionError.visibility = View.VISIBLE
                    viewModel.clearErrorStatus()
                }
                // results retrieved
                else
                    binding.connectionError.visibility = View.GONE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}