package com.example.ecommerceapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.model.UserProfile
import com.example.ecommerceapp.repositories.FireStoreRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class AuthViewModel
@Inject constructor(private val auth : FirebaseAuth)  : ViewModel() {

    //can only be subclassed in the same file
    //represents restricted hierarchy of types
    //defining finite state or n/w responses
    //no need else for all the state is defined
    sealed class  AuthState{
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val user: String) : AuthState()
        data class Error(val msg : String)  : AuthState()
    }

    private val _authState = MutableStateFlow<AuthState>(
        if(auth.currentUser !=null) AuthState.Success(auth.currentUser!!.uid)
        else AuthState.Idle
    )

    val authState : StateFlow<AuthState> = _authState

    val isLoggedIn : Boolean
        get() = authState.value is AuthState.Success

    //getting current user

    val currentUser = auth.currentUser?.let {
        firebaseUser ->
        UserProfile(uid = firebaseUser.uid, name = firebaseUser.displayName ?:"User",
            email = firebaseUser.email ?:"")
    }

    //Login function
    fun login(email : String, password  :String){
        //auth state is loading
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(email, password)
                    .await() // used for converting firebase task into a kotlin function

                //result.user is not null , we are signing the user
                //if the result is null and there is no user with the email and password,
                // a safe check for calling the error function
                Log.i("Malvika", "result =${result.user!!.email}")
                result.user ?.let {
                    _authState.value = AuthState.Success(it.uid)
                } ?: run{
                    _authState.value = AuthState.Error("Login Failed")
                }
            }catch (e  : Exception){
                _authState.value = AuthState.Error(e.message.toString())
            }
        }
    }


    fun signUp(name : String, email: String, password: String){
        Log.i("Malvika", "In signup screen")
        viewModelScope.launch {
            try{
                _authState.value = AuthState.Loading
                auth.createUserWithEmailAndPassword(email, password).await()
                Log.i("Malvika", "In signup screen + $auth")
                _authState.value = AuthState.Success("Signup success")

            }catch (e  : Exception){
                _authState.value = AuthState.Error(e.message.toString() ?: "Signup Failed")
            }
        }
    }

    fun signOut(){
        auth.signOut()
        _authState.value  = AuthState.Idle
    }

}