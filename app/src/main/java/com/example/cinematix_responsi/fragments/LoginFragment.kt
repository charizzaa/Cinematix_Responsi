package com.example.cinematix_responsi.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cinematix_responsi.R
import com.example.cinematix_responsi.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.loginbtn.setOnClickListener{
            val enteredUsername = binding.username.text.toString()
            val enteredPassword = binding.password.text.toString()

            if (enteredUsername == "Caca" && enteredPassword == "503754") {
//                val intentRegister = Intent(requireActivity(), LoginResult::class.java)
//                intentRegister.putExtra(EXTRA_USERNAME, enteredUsername)
//                startActivity(intentRegister)
            } else {
                val toastMessage = "Username atau password salah"
                Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

}