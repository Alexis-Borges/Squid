package com.example.squid1.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.squid.R
import com.example.squid.databinding.FragmentProfileBinding
import com.example.squid1.Api.APIConfig
import com.example.squid1.Api.APIService
import com.example.squid1.Api.Product
import com.example.squid1.Api.user
import com.example.squid1.MainActivity
import com.example.squid1.ProductAdapter
import kotlinx.android.synthetic.main.fragment_blank.*
import retrofit2.Call
import retrofit2.Response


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            return inflater.inflate(R.layout.fragment_profile, container, false)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root


//        val tvPayment: TextView = binding.tvPayment
//        tvPayment.setOnClickListener {
//            val intent = Intent(context, PaymentActivity::class.java)
//            startActivity(intent)
//        }

        val logout: Button = binding.logout
        logout.setOnClickListener {
            logout()
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun logout() {
        user = null
    }
}


