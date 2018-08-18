package xyz.zzp.kunyi.data.models

import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import java.lang.Exception

class UserModel private constructor(context: Context):BaseModel(context){

    companion object {
        private var INSTANCE : UserModel? = null

        private var mFirebaseAuth = FirebaseAuth.getInstance()
        private var mFirebaseUser = mFirebaseAuth.getCurrentUser()

        fun initUserModel(context: Context){
            INSTANCE = UserModel(context)
        }

        fun getInstance():UserModel{
            val i = INSTANCE
            return i!!
        }
    }

    fun isUserSignIn(context: Context) : Boolean{
        return context.getSharedPreferences("sign_in_user",Context.MODE_PRIVATE).getString("sign_in_user_id",null) != null
    }

    fun authenticateUserWithGoogleAccount(context: Context, signInAccount: GoogleSignInAccount,delegate: SignInWithGoogleAccountDelegate){
        var credential: AuthCredential = GoogleAuthProvider.getCredential(signInAccount.idToken,null)
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                    override fun onComplete(p0: Task<AuthResult>) {
                        if (!p0.isSuccessful) {
                            delegate.onFailureSignIn(p0.exception!!.message!!)
                        } else {
                            delegate.onSuccessSignIn(signInAccount)
                            var sharedPreferences: SharedPreferences = context.getSharedPreferences("sign_in_user", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("sign_in_user_id", signInAccount.displayName)
                            editor.apply()
                        }
                    }
                })
                .addOnFailureListener(object: OnFailureListener {
                    override fun onFailure(p0: Exception) {
                        delegate.onFailureSignIn(p0.message!!)
                    }

                })
    }

    interface SignInWithGoogleAccountDelegate {
        fun onSuccessSignIn(signInAccount: GoogleSignInAccount)

        fun onFailureSignIn(msg: String)
    }

    fun getUser(): FirebaseUser? {
       return mFirebaseUser
    }

}