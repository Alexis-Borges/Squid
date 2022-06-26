package com.example.squid1.Api

//Liste des valeurs envoyée par l'Api pour la liste de produits dans les favoris
data class FavItem(
    val article: Product,
    val id: Int,
)