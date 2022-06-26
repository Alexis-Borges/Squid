package com.example.squid1.Api

//Liste des valeurs envoyer par l'Api
data class Cartitem(
    val article: Articles,
    val id: Int,
    val quantity: Long
)