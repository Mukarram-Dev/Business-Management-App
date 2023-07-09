package com.mukarram.businessmanagementapp.DatabaseApp.ViewModelClasses

import android.app.Application
import androidx.lifecycle.*
import com.mukarram.businessmanagementapp.DatabaseApp.AppDatabase
import com.mukarram.businessmanagementapp.DatabaseApp.DaoFiles.ProductDao
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.Product
import com.mukarram.businessmanagementapp.DatabaseApp.UseCases.ProductUseCase
import com.mukarram.businessmanagementapp.DatabaseApp.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    savedStateHandle: SavedStateHandle,

): ViewModel()
{




}