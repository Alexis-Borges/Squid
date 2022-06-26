package com.example.squid1

class Utilities {

    companion object {

        fun isValidMail(email: String): Boolean { //verifier le champ Email des formulaires
            return !email.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

    }

}