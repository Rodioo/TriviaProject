package com.example.triviaproject.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.triviaproject.R
import com.example.triviaproject.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        val launcher = createSignInLauncher()

        binding.signInButton.setOnClickListener{
            googleSignIn(launcher)
        }

        return binding.root
    }

    private fun googleSignIn(launcher: ActivityResultLauncher<Intent>) {
        auth = FirebaseAuth.getInstance()

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        val signInIntent = googleSignInClient.signInIntent

        launcher.launch(signInIntent)
    }

    private fun createSignInLauncher(): ActivityResultLauncher<Intent> {
        val launcher = this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResult(task)
            }
        }
        return launcher
    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account = task.result
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener{
                    if (it.isSuccessful) {
                        Log.i("loginFragment", account.displayName.toString())
                        view?.findNavController()?.navigate(LoginFragmentDirections.actionLoginFragmentToCategoriesFragment())
                    } else {
                        Toast.makeText(this.context, "Login Failed: ${it.exception.toString()}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            Toast.makeText(this.context, "Login Failed: ${task.exception.toString()}", Toast.LENGTH_LONG).show()
        }
    }
}


