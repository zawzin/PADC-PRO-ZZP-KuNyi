package xyz.zzp.kunyi.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_add_job.*
import kotlinx.android.synthetic.main.activity_apply.*
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_job.*
import xyz.zzp.kunyi.R
import xyz.zzp.kunyi.data.models.JobsModel

class AddJobActivity: BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            var intent: Intent = Intent(context,AddJobActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_job)

        if (supportActionBar != null) {
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        btnPostJob.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                if(TextUtils.isEmpty(etJobTitle.text)){

                }else if(TextUtils.isEmpty(etJobDesc.text)){

                }else if(TextUtils.isEmpty(etAmount.text)){

                }else if(TextUtils.isEmpty(etDuration.text)){

                }else if(TextUtils.isEmpty(etLocation.text)){

                }else{
                    JobsModel.getINSTANCE().addJob(etJobTitle.text.toString(),
                            etJobDesc.text.toString(),
                            Integer.parseInt(etAmount.text.toString()),
                            etDuration.text.toString(),
                            etLocation.text.toString())
                    onBackPressed()
                }
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