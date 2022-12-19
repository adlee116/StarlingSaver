package com.starling.presentation.home

data class AccountListItem(
    val name: String,
    val defaultCategory: String,
    val uid: String,
    val onClick: (uid: String, default: String) -> Unit
)