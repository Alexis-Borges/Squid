package com.example.squid1.fragments


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.auth0.android.jwt.JWT
import com.example.squid.R
import com.example.squid.databinding.FragmentProfileBinding
import com.example.squid1.Login.AuthManagement
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val token = activity?.let { AuthManagement.getToken(it) }
        val jwt = token?.let { JWT (it) }
        val userId = jwt?.getClaim("id")?.asString()
        val userEmail = jwt?.getClaim("email")?.asString()
        val UserAdmin = jwt?.getClaim("isAdmin")?.asString()



        var cpmmande = view.findViewById<Button>(R.id.contacts)
        commande.setOnClickListener {

        }

        var contact = view.findViewById<Button>(R.id.contacts)
        contact.setOnClickListener {

        }

        var logout = view.findViewById<Button>(R.id.logout)
        logout.setOnClickListener {
            activity?.let { AuthManagement.disconnect(it) }
            restartApp()
        }

       return view
    }

    private fun restartApp() {
        val i = requireActivity().baseContext.packageManager
            .getLaunchIntentForPackage(requireActivity().baseContext.packageName)
        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
    }

}


