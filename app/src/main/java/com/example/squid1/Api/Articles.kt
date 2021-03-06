package com.example.squid1.Api

//Liste des valeurs envoyée par l'Api
data class Articles(

    val category: Category,
    val color: String,
    val description: String,
    val id: Int,
    val images: List<Image>,
    val name: String,
    val price: Long,
    val ratings: List<Any>,
    val stock: Int
)