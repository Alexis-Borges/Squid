package com.example.squid1.Api

//Liste des valeurs envoyée par l'Api pour les produits dans le panier
data class Cartitem(
    val article: Articles,
    val id: Int,
    val quantity: Long
)