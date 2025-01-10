package eu.tutorials.mywishlistapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.mywishlistapp.data.Wish
import eu.tutorials.mywishlistapp.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(
    private val wishRepository: WishRepository = Graph.wishRepository
): ViewModel() {
    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")

    fun onWishTitleChange(newTitle: String)  {
        wishTitleState = newTitle
    }

    fun onWishDescriptionChange(newDescription: String) {
        wishDescriptionState = newDescription
    }

    /* promise that the variable will be initialized before actually being used(async process)
     * basically to avoid null check process
     */
    lateinit var getAllWishes: Flow<List<Wish>>

    init {
        viewModelScope.launch {
            getAllWishes = wishRepository.getWishes()
        }
    }

    fun updateWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO){
            wishRepository.updateAWish(wish)
        }
    }
    fun addWish(wish: Wish) {
        // Optimization of Input - Output, efficient management of threads
        viewModelScope.launch(Dispatchers.IO){
            wishRepository.addAWish(wish)
        }
    }

    fun getAWishById(id:Long): Flow<Wish> {
        return wishRepository.getAWishById(id)
    }

    fun deleteWish(wish: Wish) {
        viewModelScope.launch(Dispatchers.IO){
            wishRepository.deleteAWish(wish)
        }
    }

}