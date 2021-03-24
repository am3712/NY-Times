package com.example.nytimes.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.nytimes.databinding.DetailsFragmentBinding

class DetailsFragment : Fragment() {
    private val args: DetailsFragmentArgs by navArgs()

    private val viewModel: DetailsViewModel by viewModels()

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        if (viewModel.article.value == null) {
            viewModel.setArticle(args.article)
        }

        binding.button.setOnClickListener { openWebPage(viewModel.article.value?.url!!) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }


}