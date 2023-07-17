package com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles


import androidx.room.*
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Bill
import kotlinx.coroutines.flow.Flow


@Dao
interface BillDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBill(bill: Bill) : Long

    @Query("SELECT * FROM bills")
     fun getAllBills(): Flow<List<Bill>>




    @Query("SELECT * FROM bills WHERE billId= :Id")
    suspend fun getBillById(Id: Long?) : Bill ?

    @Delete
    suspend fun clearBill(bill: Bill)


    // Add other necessary queries and operations
}
