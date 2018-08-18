package xyz.zzp.kunyi.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import xyz.zzp.kunyi.R

class AddInfoActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            var intent : Intent = Intent(context,AddInfoActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_infromation)
    }
}