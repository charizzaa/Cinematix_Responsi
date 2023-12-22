package com.example.cinematix_responsi.fragments

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.cinematix_responsi.AdminMainHome
import com.example.cinematix_responsi.NotifReceiver
import com.example.cinematix_responsi.R
import com.example.cinematix_responsi.UserMainMenu
import com.example.cinematix_responsi.databinding.FragmentLoginBinding
import com.example.cinematix_responsi.databinding.FragmentRegisterBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private val channelId = "CINEMATIX"
    private val notifId = 1989
    private lateinit var notifManager: NotificationManager
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        notifManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val email: TextInputEditText = binding.username
        val password: TextInputEditText = binding.password
        val loginBtn : Button = binding.loginbtn
        
        // Check if user credentials are saved in SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val savedEmail = sharedPreferences.getString("email", null)
        val savedPassword = sharedPreferences.getString("password", null)
        val editor = sharedPreferences.edit()

        loginBtn.setOnClickListener {
            if (email.text.toString().isEmpty()) {
                Toast.makeText(requireActivity(), "Please Fill the Email!", Toast.LENGTH_SHORT).show()
            }
            if (password.text.toString().isEmpty()) {
                Toast.makeText(requireActivity(), "Please Fill the Email!", Toast.LENGTH_SHORT).show()
            }

            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(requireActivity()) { taskk ->
                    val currentUser = auth.currentUser
                    if (taskk.isSuccessful) {
                        if (currentUser != null) {
                            // Save user credentials in SharedPreferences
                            // Check if the user is an admin based on their email address
                            if (currentUser.email == "admincaca@gmail.com") {
                                // User is an admin, redirect to Admin activity
                                editor.putString("email", email.text.toString())
                                editor.putString("password", password.text.toString())
                                editor.apply()
                                startActivity(Intent(requireActivity(), AdminMainHome::class.java))

                                showNotification()
                            } else {
                                // User is not an admin, redirect to User activity
                                startActivity(Intent(requireActivity(), UserMainMenu::class.java))

                                showNotification()
                            }
                        }
                    } else {
                        Toast.makeText(requireActivity(), "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
        return binding.root
    }

    private fun showNotification() {
        val currentUser = Firebase.auth.currentUser
        val email = currentUser?.email

        val builder = NotificationCompat.Builder(requireContext(), "Login_notification")
            .setContentTitle("Login Status")
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentText("$email Berhasil login")
            .setAutoCancel(true)

        val notifChannel = NotificationChannel(
            channelId,
            "Notif",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        with(notifManager) {
            createNotificationChannel(notifChannel)
            notify(0, builder.build())
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}