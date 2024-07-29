package com.group4.edusurveyor.module.auth.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.group4.edusurveyor.R
import com.group4.edusurveyor.databinding.LoginActivityBinding
import com.group4.edusurveyor.module.auth.model.UserRecordModel
import com.group4.edusurveyor.module.auth.viewmodel.LoginViewModel
import com.group4.edusurveyor.module.map.view.MapsActivity
import com.group4.edusurveyor.repository.local.helper.UserDatabase
import kotlinx.coroutines.launch
import java.security.SecureRandom

class LoginActivity: AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding
    private var viewModel = LoginViewModel(UserDatabase(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)

        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var nonceBytes = ByteArray(40)
        SecureRandom().nextBytes(nonceBytes)
        var nonce = Base64.encodeToString(nonceBytes,  Base64.URL_SAFE)

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(resources.getString(R.string.client_id))
            .setAutoSelectEnabled(true)
            .setNonce(nonce)
            .setFilterByAuthorizedAccounts(false)
        .build()

        binding.contentLogin.btnLogin.setOnClickListener {
            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val context = this

            lifecycleScope.launch {
                try {
                    val credentialManager = CredentialManager.create(context)
                    val result = credentialManager.getCredential(
                        request = request,
                        context = context,
                    )
                    handleSignIn(result)
                } catch (e: GetCredentialException) {
                    handleFailure(e)
                }
            }
        }
    }

    fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        val credential = result.credential

        when (credential) {

            // Passkey credential
            is PublicKeyCredential -> {
                // Share responseJson such as a GetCredentialResponse on your server to
                // validate and authenticate
                val responseJson = credential.authenticationResponseJson
            }

            // Password credential
            is PasswordCredential -> {
                // Send ID and password to your server to validate and authenticate.
                val username = credential.id
                val password = credential.password
            }

            // GoogleIdToken credential
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract id to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        Log.d("LoginActivity", "${googleIdTokenCredential.id}")
                        viewModel.insertNewUser(
                            UserRecordModel(
                                0,
                                googleIdTokenCredential.id,
                                googleIdTokenCredential.displayName.toString(),
                                googleIdTokenCredential.idToken,
                                viewModel.getCurrentDateTime(),
                                viewModel.getCurrentDateTime()
                            )
                        )
                        val intent = Intent(this, MapsActivity::class.java)
                        startActivity(intent)
                        finish()
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e("LoginActivity:", "Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e("LoginActivity:", "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e("LoginActivity:", "Unexpected type of credential")
            }
        }
    }

    fun handleFailure(e: GetCredentialException) {
        Log.e("LoginActivity:", "handleFailure", e)
    }
}