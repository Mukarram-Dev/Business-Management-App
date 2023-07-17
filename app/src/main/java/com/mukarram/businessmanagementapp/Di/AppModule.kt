package com.mukarram.businessmanagementapp.Di

import android.app.Application
import com.mukarram.businessmanagementapp.DatabaseApp.AppDatabase
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseBill.AddBill
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseBill.BillUseCase
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseBill.GetAllBills
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseBill.GetBillsById
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseCustomer.AddCustomer
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseCustomer.CustomerUseCases
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseCustomer.GetAllCustomers
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseCustomer.GetCustomerById
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProduct.*
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProductBill.AddProductBill
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProductBill.GetProductBillById
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.UseCaseProductBill.ProductBillUseCase
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return AppDatabase.getDatabase(app.applicationContext)
    }

    //for product

    @Provides
    @Singleton
    fun provideProductRepository(db: AppDatabase): ProductRepository {
        return ProductRepositoryImpl(db.productDao())
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: ProductRepository): ProductUseCase {
        return ProductUseCase(
            getAllProducts = GetAllProducts(repository),
            getProductById = GetProductById(repository),
            addProduct = AddProduct(repository),
            deleteProduct = DeleteProduct(repository),
            updateProduct = UpdateProduct(repository)
        )
    }

    //for bills
    @Provides
    @Singleton
    fun provideBillRepository(db: AppDatabase): BillRepository {
        return BillRepositoryImpl(db.billDao())
    }

    @Provides
    @Singleton
    fun provideBillUseCase(repository: BillRepository): BillUseCase {
        return BillUseCase(
            getAllBills = GetAllBills(repository),
            addBill = AddBill(repository),
            getBillsById = GetBillsById(repository)
        )
    }

    //for Customer
    @Provides
    @Singleton
    fun provideCutomerRepository(db: AppDatabase): CustomerRepository {
        return CustomRepositoryImpl(db.customerDao())
    }

    @Provides
    @Singleton
    fun provideCustomerUseCase(repository: CustomerRepository): CustomerUseCases {
        return CustomerUseCases(
            addCustomer = AddCustomer(repository),
            getCustomerById = GetCustomerById(repository),
            getAllCustomers = GetAllCustomers(repository)

            )
    }

    //for product sale
    @Provides
    @Singleton
    fun provideProductBillRepository(db: AppDatabase): ProductBillRepository {
        return ProductBillReposImpl(db.productBillDao())
    }

    @Provides
    @Singleton
    fun provideProductBillUseCase(repository: ProductBillRepository): ProductBillUseCase {
        return ProductBillUseCase(
            addProductBill = AddProductBill(repository),
            getProductBillById = GetProductBillById(repository)

            )
    }


}