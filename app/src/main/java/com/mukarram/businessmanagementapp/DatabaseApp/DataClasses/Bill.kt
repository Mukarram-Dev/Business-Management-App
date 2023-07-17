    package com.mukarram.businessmanagementapp.DatabaseApp.DataClasses

    import androidx.room.ColumnInfo
    import androidx.room.Entity
    import androidx.room.ForeignKey
    import androidx.room.PrimaryKey

    @Entity(
        tableName = "bills",
        foreignKeys = [ForeignKey(entity = Customer::class, parentColumns = ["customerId"], childColumns = ["customerId"])]
    )
    data class Bill(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "billId")
        val id: Long?,
        val customerId: Long?,
        val date: String
    )

    class InvalidBillException(message: String): Exception(message)