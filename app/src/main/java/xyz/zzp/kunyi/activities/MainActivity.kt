package xyz.zzp.kunyi.activities

import android.app.Activity
import android.app.job.JobParameters
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.LENGTH_SHORT
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.appinvite.AppInviteInvitation
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_job.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import xyz.zzp.kunyi.KunyiApp
import xyz.zzp.kunyi.R
import xyz.zzp.kunyi.R.id.*
import xyz.zzp.kunyi.adapters.JobsAdapter
import xyz.zzp.kunyi.components.SmartScrollListener
import xyz.zzp.kunyi.data.models.JobsModel
import xyz.zzp.kunyi.data.models.UserModel
import xyz.zzp.kunyi.data.vos.JobVO
import xyz.zzp.kunyi.events.FireBaseEvents
import xyz.zzp.kunyi.mvp.presenters.JobPresenter
import xyz.zzp.kunyi.mvp.views.JobsView
import java.io.File
import javax.xml.transform.Templates

open class MainActivity : BaseActivity(), JobsView {

    override fun lunchApplyPost() {
        if (UserModel.getInstance().isUserSignIn(applicationContext)) {
            val intent: Intent = ApplyActivity.newIntent(this)
            startActivity(intent)
        } else alertToSignIn()
    }

    override fun lunchDetail(postId: Long) {
        val intent: Intent = DetailActivity.newIntent(this, postId)
        startActivity(intent)
    }

    override fun displayErrorBV(errorMsg: String) {

    }

    val RC_INVITE_TO_APP : Int = 1237
    private lateinit var mJobAdapter: JobsAdapter
    private lateinit var mPresenter: JobPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mPresenter = ViewModelProviders.of(this).get(JobPresenter::class.java)
        mPresenter.initPresenter(this)

        swipeRefreshLayout.isRefreshing = true

        val mSmartScrollListener = SmartScrollListener(object : SmartScrollListener.OnSmartScrollListener {
            override fun onListEndReach() {
                Snackbar.make(rvJobsList, "List reached end.", LENGTH_SHORT).show()
            }
        })
        rvJobsList.addOnScrollListener(mSmartScrollListener)

        rvJobsList.setEmptyView(vpEmptyNews)
        rvJobsList.layoutManager = LinearLayoutManager(this)
        mJobAdapter = JobsAdapter(this, mPresenter)
        rvJobsList.adapter = mJobAdapter

        swipeRefreshLayout.setOnRefreshListener {
            object : SwipeRefreshLayout.OnRefreshListener {
                override fun onRefresh() {
                    mPresenter.onRefreshJobList()
                }

            }
        }

        fab.setOnClickListener { view ->
            if (UserModel.getInstance().isUserSignIn(applicationContext)) {
                var intent: Intent = AddJobActivity.newIntent(applicationContext)
                startActivity(intent)
            } else {
                alertToSignIn()
            }
        }

        iv_invite_to_app.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                sentInvitation()
            }

        })
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        mPresenter.onRefreshJobList()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_INVITE_TO_APP) {
            if (resultCode == Activity.RESULT_OK) {
                // Check how many invitations were sent and log.
                val ids = AppInviteInvitation.getInvitationIds(resultCode, data!!)
                Log.d(KunyiApp.TAG, "Invitations sent: " + ids.size)
                Snackbar.make(rvJobsList, "Invitations sent to " + ids.size + " friends", Snackbar.LENGTH_SHORT).show()
            } else {
                // Sending failed or it was canceled, show failure message to the user
                Log.d(KunyiApp.TAG, "Failed to send invitation.")
                Snackbar.make(rvJobsList, "Failed to send invitation.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    fun alertToSignIn() {
        Snackbar.make(rvJobsList, "You need to sign with Google", Snackbar.LENGTH_INDEFINITE)
                .setAction("Sign-In", object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        var intent: Intent = SignInActivity.newIntent(applicationContext)
                        startActivity(intent)
                    }

                }).show()
    }

    fun sentInvitation(){
        var intent : Intent = AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText("I want to join")
                .build()
        startActivityForResult(intent,RC_INVITE_TO_APP)
    }
    override fun tappedInterest(job: JobVO) {
        if (UserModel.getInstance().isUserSignIn(applicationContext)) {
            JobsModel.getINSTANCE().tapInterest(job)
        } else alertToSignIn()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadedJobs(event: FireBaseEvents.JobsLoadedEvent) {
        Log.i("Kunyi", "size : ${event.getJobs().size}")
        mJobAdapter.setNewData(event.getJobs() as MutableList<JobVO>)
        swipeRefreshLayout.isRefreshing = false
    }
}
