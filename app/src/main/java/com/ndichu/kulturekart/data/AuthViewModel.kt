package com.ndichu.kulturekart.data


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.ndichu.kulturekart.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject


class AuthViewModel @Inject constructor(): ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference



    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> get() = _currentUser

    private val _loginStateAndRole = MutableStateFlow(Pair(false, ""))
    val loginStateAndRole: StateFlow<Pair<Boolean, String>> get() = _loginStateAndRole


    init {
        val firebaseUser = auth.currentUser
        if (firebaseUser != null) {
            viewModelScope.launch { // Use viewModelScope
                fetchUserRole(firebaseUser.uid)
            }
        }
    }


    private suspend fun fetchUserRole(uid: String) {
        try {
            val snapshot = database.child("users").child(uid).get().await() // Use await()
            val email = snapshot.child("email").value?.toString() ?: ""
            val role = snapshot.child("role").value?.toString() ?: "buyer"
            _currentUser.value = User(uid, email, role)
            _loginStateAndRole.value = Pair(true, role)
        } catch (e: Exception) {
            // Handle the error appropriately, e.g., log it
            println("Error fetching user role: ${e.message}")
            // Consider setting a default state or showing an error message to the user
            _loginStateAndRole.value = Pair(false, "") // Or some error state
        }
    }


    fun register(email: String, password: String, role: String, callback: (User?, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val uid = it.user?.uid ?: return@addOnSuccessListener
                val user = User(uid, email, role)

                // Save to Realtime Database
                database.child("users").child(uid).setValue(user)
                    .addOnSuccessListener {
                        _currentUser.value = user
                        _loginStateAndRole.value = Pair(true, role)
                        callback(user, "Registration successful")
                    }
                    .addOnFailureListener { e ->
                        callback(null, e.message ?: "Failed to save user data")
                    }
            }
            .addOnFailureListener { e ->
                callback(null, e.message ?: "Registration failed")
            }
    }



    fun login(email: String, password: String, callback: (User?, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val uid = it.user?.uid ?: return@addOnSuccessListener
                val userRef = database.child("users").child(uid)

                userRef.get().addOnSuccessListener { snapshot ->
                    var role = snapshot.child("role").value?.toString()

                    // If role is missing, set a default
                    if (role == null) {
                        role = "buyer" // or "seller" as your default
                        userRef.child("role").setValue(role)
                    }

                    val user = User(uid, email, role)
                    _currentUser.value = user
                    _loginStateAndRole.value = Pair(true, role)
                    callback(user, "Login successful")
                }.addOnFailureListener { e ->
                    callback(null, e.message ?: "Failed to fetch user data")
                }
            }
            .addOnFailureListener { e ->
                callback(null, e.message ?: "Login failed")
            }
    }



    fun switchRole(newRole: String, callback: (Boolean) -> Unit) {
        val user = auth.currentUser ?: return
        database.child("users").child(user.uid).child("role").setValue(newRole)
            .addOnSuccessListener {
                _currentUser.value = _currentUser.value?.copy(role = newRole)
                _loginStateAndRole.value = Pair(true, newRole)
                callback(true)
            }
            .addOnFailureListener { callback(false) }
    }

    fun logout() {
        auth.signOut()
        _currentUser.value = null
        _loginStateAndRole.value = Pair(false, "")
    }
}

//
//import androidx.lifecycle.ViewModel
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.FirebaseDatabase
//import com.ndichu.kulturekart.model.User
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//
//
//class AuthViewModel : ViewModel() {
//
//    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
//    private val database = FirebaseDatabase.getInstance().reference
//
//    private val _currentUser = MutableStateFlow<User?>(null)
//    val currentUser: StateFlow<User?> get() = _currentUser
//
//    private val _loginStateAndRole = MutableStateFlow(Pair(false, ""))
//    val loginStateAndRole: StateFlow<Pair<Boolean, String>> get() = _loginStateAndRole
//
//    init {
//        val firebaseUser = auth.currentUser
//        if (firebaseUser != null) {
//            fetchUserRole(firebaseUser.uid)
//        }
//    }
//
//    private fun fetchUserRole(uid: String) {
//        database.child("users").child(uid).get().addOnSuccessListener { snapshot ->
//            val email = snapshot.child("email").value?.toString() ?: ""
//            val role = snapshot.child("role").value?.toString() ?: "buyer"
//            _currentUser.value = User(uid, email, role)
//            _loginStateAndRole.value = Pair(true, role)
//        }
//    }
//
//    fun register(email: String, password: String, role: String, callback: (Boolean, String) -> Unit) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnSuccessListener {
//                val uid = it.user?.uid ?: return@addOnSuccessListener
//                val user = User(uid, email, role)
//                database.child("users").child(uid).setValue(user)
//                    .addOnSuccessListener {
//                        _currentUser.value = user
//                        _loginStateAndRole.value = Pair(true, role)
//                        callback(true, "Registered successfully")
//                    }
//                    .addOnFailureListener { e ->
//                        callback(false, e.message ?: "Registration failed")
//                    }
//            }
//            .addOnFailureListener { e ->
//                callback(false, e.message ?: "Registration failed")
//            }
//    }
//
//    fun login(email: String, password: String, callback: (User?, String) -> Unit) {
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnSuccessListener { authResult ->
//                val firebaseUser = authResult.user
//                val uid = firebaseUser?.uid
//                if (uid == null) {
//                    callback(null, "User ID is null")
//                    return@addOnSuccessListener
//                }
//
//                database.child("users").child(uid).get()
//                    .addOnSuccessListener { snapshot ->
//                        val role = snapshot.child("role").value?.toString() ?: "buyer"
//                        val safeEmail = firebaseUser.email ?: email
//                        val user = User(uid, safeEmail, role)
//                        _currentUser.value = user
//                        _loginStateAndRole.value = Pair(true, role)
//                        callback(user, "Login successful")
//                    }
//                    .addOnFailureListener { e ->
//                        callback(null, e.message ?: "Failed to fetch user data")
//                    }
//            }
//            .addOnFailureListener { e ->
//                callback(null, e.message ?: "Login failed")
//            }
//    }
//
//
////    fun login(email: String, password: String, callback: (User?, String) -> Unit) {
////        auth.signInWithEmailAndPassword(email, password)
////            .addOnSuccessListener {
////                val uid = it.user?.uid ?: return@addOnSuccessListener
////                database.child("users").child(uid).get()
////                    .addOnSuccessListener { snapshot ->
////                        val role = snapshot.child("role").value?.toString() ?: "buyer"
////                        val user = User(uid, email, role)
////                        _currentUser.value = user
////                        _loginStateAndRole.value = Pair(true, role)
////                        callback(user, "Login successful")
////                    }
////                    .addOnFailureListener { e ->
////                        callback(null, e.message ?: "Failed to fetch user data")
////                    }
////            }
////            .addOnFailureListener { e ->
////                callback(null, e.message ?: "Login failed")
////            }
////    }
//
//    fun switchRole(newRole: String, callback: (Boolean) -> Unit) {
//        val user = auth.currentUser ?: return
//        database.child("users").child(user.uid).child("role").setValue(newRole)
//            .addOnSuccessListener {
//                _currentUser.value = _currentUser.value?.copy(role = newRole)
//                _loginStateAndRole.value = Pair(true, newRole)
//                callback(true)
//            }
//            .addOnFailureListener { callback(false) }
//    }
//
//    fun logout() {
//        auth.signOut()
//        _currentUser.value = null
//        _loginStateAndRole.value = Pair(false, "")
//    }
//}
//
//
