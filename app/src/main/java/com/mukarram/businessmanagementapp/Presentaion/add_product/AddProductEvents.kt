package com.mukarram.businessmanagementapp.Presentaion.add_product

import androidx.compose.ui.focus.FocusState

sealed class AddProductEvents{

    data class EnteredProdName(val value : String ) : AddProductEvents()


    data class EnteredProdQty(val value : String ) : AddProductEvents()


    data class EnteredProdPrice(val value : String ) : AddProductEvents()


    data class SelectProdType(val value : String ) : AddProductEvents()


    object saveProduct : AddProductEvents()
}
