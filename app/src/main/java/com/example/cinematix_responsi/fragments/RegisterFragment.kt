package com.example.cinematix_responsi.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cinematix_responsi.R
import com.example.cinematix_responsi.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val TAG = "RegisterFragment"

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener(){
            val usn = binding.username.text.toString()
//            val intent = Intent(requireActivity(), RegisterResult::class.java)
//                .putExtra(EXTRA_USERNAME, usn)
//            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

}