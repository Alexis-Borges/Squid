package com.example.squid1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.squid.databinding.FragmentBookmarkBinding
import com.example.squid1.ProductAdapter
import com.example.squid1.Api.listProductFavourite


class BookmarkFragment : Fragment() {
    private var _binding: FragmentBookmarkBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerViewBookmark.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter= context?.let { ProductAdapter(it, listProductFavourite) }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}