package xyz.zzp.kunyi.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import xyz.zzp.kunyi.R
import xyz.zzp.kunyi.data.models.UserModel

class SignInActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener {
    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    companion object {
        protected var mGoogleApiClient : GoogleApiClient? = null
        protected val RC_GOOGLE_SIGN_IN = 1236

        fun newIntent(context: Context): Intent {
            var intent: Intent = Intent(context, SignInActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("505880949189-0s9su8nu1uujndp0msqitm0qshkioiee.apps.googleusercontent.com")
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(applicationContext)
                .enableAutoManage(this /*FragmentActivity*/, this /*OnConnectionFailedListener*/)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build()

        btnSignInGoogle.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                signInWithGoogle()
            }

        })

        btnRegister.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                var intent: Intent = RegisterActivity.newIntent(applicationContext)
                startActivity(intent)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN){
            var result: GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            processGoogleSignInResult(result)
        }
    }

    private fun signInWithGoogle(){
        var signInIntent: Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
    }

    private fun processGoogleSignInResult(signInResult: GoogleSignInResult) {
        if (signInResult.isSuccess) {
            // Google Sign-In was successful, authenticate with Firebase
            var account: GoogleSignInAccount = signInResult.signInAccount!!
            UserModel.getInstance().authenticateUserWithGoogleAccount(this,account,
                    object: UserModel.SignInWithGoogleAccountDelegate{
                        override fun onSuccessSignIn(signInAccount: GoogleSignInAccount) {
                            Toast.makeText(applicationContext,"Good Job!", Toast.LENGTH_SHORT).show()
                            onBackPressed()
                        }

                        override fun onFailureSignIn(msg: String) {
                            Snackbar.make(rvJobsList,"Your Google sign-in failed", Snackbar.LENGTH_LONG).show()
                        }

                    })
        } else {
            // Google Sign-In failed
            Snackbar.make(rvJobsList, "Your Google sign-in failed.", Snackbar.LENGTH_LONG).show()
        }
    }
}