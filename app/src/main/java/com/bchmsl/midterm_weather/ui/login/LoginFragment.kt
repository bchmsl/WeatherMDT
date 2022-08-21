package com.bchmsl.midterm_weather.ui.login

import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bchmsl.midterm_weather.R
import com.bchmsl.midterm_weather.databinding.FragmentLoginBinding
import com.bchmsl.midterm_weather.ui.base.BaseFragment
import com.bchmsl.midterm_weather.ui.signup.signupfirst.isValidEmail
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    // firebaseAuth
    private lateinit var firebaseAuth : FirebaseAuth
    private var email = ""
    private var password = ""

    override fun start() {
        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        binding.ibtnNext.setOnClickListener {
            //get data
            email = binding.tilEmail.editText?.text.toString()
            password = binding.tilPassword.editText?.text.toString()
            //validate data
            if(!isValidEmail(binding.tilEmail)) {}
            else {
                //data is validated, begin login
                firebaseLogin()
            }
        }
        binding.tvSignUp.setOnClickListener {
            goToSingUpFra()
        }
    }

    private fun firebaseLogin() {
        //show progress bar
        binding.pbLogin.visibility = View.VISIBLE
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //login success

                //hide progress bar
                hideProgressBar()

                //get user info
                val firebaseUser  = firebaseAuth.currentUser
                val email = firebaseUser!!.email
//                Toast.makeText(this, "Logged in as $email", Toast.LENGTH_SHORT).show()

                //go to LoggedIn/profile Fragment
                goToMainFra()


            }
            .addOnFailureListener{ e->
                //login failed

                //hide progress bar
                hideProgressBar()
                Snackbar.make(binding.root, "Login failed due to ${e.message}", Snackbar.LENGTH_SHORT)
                    .setTextMaxLines(1)
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.regular_red))
                    .show()

            }
    }

    private fun hideProgressBar() {
        binding.pbLogin.visibility = View.GONE
    }

    private fun goToSingUpFra(){
        findNavController().navigate(LoginFragmentDirections.actionLogInFragmentToSignUpFragment())
    }

    private fun goToMainFra(){
        findNavController().navigate(LoginFragmentDirections.actionLogInFragmentToMainFragment())
    }

}