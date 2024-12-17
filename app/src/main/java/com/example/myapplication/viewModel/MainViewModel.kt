package com.example.myapplication.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Model.SliderModel
import com.example.myapplication.Model.ItemsModel
import com.google.firebase.database.*

class MainViewModel : ViewModel() {

    // Firebase Database Reference
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    // LiveData to hold banners, categories, and recommended items
    private val _banners = MutableLiveData<List<SliderModel>>()
    private val _category = MutableLiveData<MutableList<CategoryModel>>()
    private val _recommended = MutableLiveData<MutableList<ItemsModel>>()

    val banners: LiveData<List<SliderModel>> get() = _banners
    val category: LiveData<MutableList<CategoryModel>> get() = _category
    val recommended: LiveData<MutableList<ItemsModel>> get() = _recommended

    /**
     * Load recommended items from Firebase Database
     */
    fun loadFiltered(id: String) {
        val ref = firebaseDatabase.getReference("Items")

        // Convert id to Int and ensure compatibility with Firebase
        val categoryIdInt = id.toIntOrNull()
        if (categoryIdInt != null) {
            val query: Query = ref.orderByChild("categoryId").equalTo(categoryIdInt.toDouble())

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val lists = mutableListOf<ItemsModel>()

                    // Log the query result size for debugging
                    Log.d("MainViewModel", "Snapshot children count: ${snapshot.childrenCount}")

                    for (childSnapshot in snapshot.children) {
                        val list = childSnapshot.getValue(ItemsModel::class.java)
                        if (list != null) {
                            Log.d("MainViewModel", "Item found: ${list.title}, CategoryId: ${list.categoryId}")
                            lists.add(list)
                        }
                    }

                    // Update LiveData
                    _recommended.value = lists
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MainViewModel", "Database error: ${error.message}")
                }
            })
        } else {
            Log.e("MainViewModel", "Invalid categoryId: $id")
        }
    }


    fun loadRecommended() {
        val Ref = firebaseDatabase.getReference("Items")
        val query: Query = Ref.orderByChild("showRecommended").equalTo(true)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()

                // Iterate over the snapshot children to extract ItemsModel objects
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemsModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }

                // Update the LiveData with the retrieved recommended items
                _recommended.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                // Log any database errors
                Log.e("MainViewModel", "Database error: ${error.message}")
            }
        })
    }
    /**
     * Load categories from Firebase Database
     */
    fun loadCategory() {
        val ref = firebaseDatabase.getReference("Category")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<CategoryModel>()

                // Iterate over the snapshot children to extract CategoryModel objects
                for (childSnapshot in snapshot.children) {
                    val category = childSnapshot.getValue(CategoryModel::class.java)
                    if (category != null) {
                        lists.add(category)
                    }
                }

                // Update the LiveData with the retrieved categories
                _category.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                // Log any database errors
                Log.e("MainViewModel", "Database error: ${error.message}")
            }
        })
    }

    /**
     * Load banners from Firebase Database
     */
    fun loadBanners() {
        val ref = firebaseDatabase.getReference("Banner")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>()

                // Iterate over the snapshot children to extract SliderModel objects
                for (childSnapshot in snapshot.children) {
                    val banner = childSnapshot.getValue(SliderModel::class.java)
                    if (banner != null) {
                        lists.add(banner)
                    }
                }

                // Update the LiveData with the retrieved banners
                _banners.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                // Log any database errors
                Log.e("MainViewModel", "Database error: ${error.message}")
            }
        })
    }
}
