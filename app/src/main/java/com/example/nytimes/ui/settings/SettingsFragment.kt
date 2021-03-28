package com.example.nytimes.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.addCallback
import androidx.preference.PreferenceFragmentCompat
import com.example.nytimes.MainActivity
import com.example.nytimes.R


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            //sol 1
//            findNavController()
//                .navigate(
//                    R.id.nav_graph,
//                    null,
//                    NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build()
//                )
            //sol 2
//            findNavController().graph.clear()
//            findNavController().navigate(R.id.nav_graph)

            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

}