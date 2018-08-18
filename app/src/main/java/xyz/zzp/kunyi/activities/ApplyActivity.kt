package xyz.zzp.kunyi.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_apply.*
import xyz.zzp.kunyi.R

class ApplyActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            var intent: Intent = Intent(context,ApplyActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply)

        if (supportActionBar != null) {
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        btnSend.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                onBackPressed()
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}