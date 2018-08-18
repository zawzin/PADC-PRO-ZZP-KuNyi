package xyz.zzp.kunyi.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_detail.*
import xyz.zzp.kunyi.R
import xyz.zzp.kunyi.data.models.JobsModel
import xyz.zzp.kunyi.data.vos.JobVO


class DetailActivity : BaseActivity(){

    companion object {
        fun newIntent(context: Context,postId : Long): Intent{
            val intent : Intent = Intent(context,DetailActivity::class.java)
            intent.putExtra("post_id",postId)
            return intent
        }
        private var mJob : JobVO = JobVO()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.title = null
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        var postId: Long = intent.getLongExtra("post_id",0)

        displayDetail(JobsModel.getINSTANCE().loadJobById(postId))

        btnApply.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                var intent: Intent = ApplyActivity.newIntent(applicationContext)
                startActivity(intent)
            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun displayDetail(job : JobVO){
        tvJobTitle.text = job.shortDesc
        tvPayment.text = job.offerAmount!!.amount.toString()
        tvPaymentDuration.text = " "+job.offerAmount!!.duration.toString()
        tvLocation.text = job.location
        tvPhoneNo.text = job.phoneNo
        tvJobDesc.text = job.fullDesc

        var str : String = ""
        for (jobReq in job.requiredSkill!!){
            str = jobReq.skillName.toString() + ", " +str
        }
        tvRequirement.text = str
    }
}