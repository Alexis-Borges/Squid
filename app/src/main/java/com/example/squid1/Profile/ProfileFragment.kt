package com.example.squid1.Profile


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.auth0.android.jwt.JWT
import com.example.squid.R
import com.example.squid.databinding.FragmentProfileBinding
import com.example.squid1.Login.AuthManagement


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

        var infoUser =  view.findViewById<Button>(R.id.infoUser) //Redirection vers la page information utilisateur
        infoUser.setOnClickListener {
            val intent = Intent(activity, infoUserActivity::class.java)
            startActivityForResult(intent, 1)
        }
            //TODO afficher les commandes passées
//        var commande = view.findViewById<Button>(R.id.commande)
//        commande.setOnClickListener {
//            val intent = Intent(this, ::class.java)
//            startActivityForResult(intent, 1)
//        }
//
        var contact = view.findViewById<Button>(R.id.contacts) //Redirection vers la page formulaire de contact
        contact.setOnClickListener {
            val intent = Intent(activity, ContactActivity::class.java)
            startActivityForResult(intent, 1)
        }

        var logout = view.findViewById<Button>(R.id.logout) //permet la deconnexion et la supression du token d'authentification
        logout.setOnClickListener {
            activity?.let { AuthManagement.disconnect(it) }
            restartApp()
        }

       return view
    }

    private fun restartApp() { //Redémarrage de l'application lors de la déconnexion
        val i = requireActivity().baseContext.packageManager
            .getLaunchIntentForPackage(requireActivity().baseContext.packageName)
        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
    }

}


