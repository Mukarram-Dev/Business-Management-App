package com.mukarram.businessmanagementapp.Presentaion.create_bill

import com.mukarram.businessmanagementapp.Presentaion.add_product.AddProductEvents

sealed class CreateBillEvent {

    data class EnteredCustName(val value: String) : CreateBillEvent()
    data class EnteredCustPhone(val value: String) : CreateBillEvent()
    data class EnteredCustAddress(val value: String) : CreateBillEvent()

    data class SelectedBillDate(val value: String) : CreateBillEvent()

    data class TotalBill(val value: String) : CreateBillEvent()



}