package longbowou.getnovissi.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import longbowou.getnovissi.R
import longbowou.getnovissi.fragments.NovissisFragment
import longbowou.getnovissi.fragments.ProcessingFragment
import longbowou.getnovissi.ussd.MyUSSDController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        MyUSSDController.verifyAccesibilityAccess(this)

        val processingFragment =
            ProcessingFragment()

        val novissisFragment =
            NovissisFragment()

        val viewPagerAdapter =
            ViewPagerAdapter(
                supportFragmentManager,
                listOf(processingFragment, novissisFragment)
            )
        view_pager.adapter = viewPagerAdapter

        fab.setOnClickListener {
            processingFragment.start()
        }

        fab_add.setOnClickListener {
            startActivity(Intent(this, NovissiActivity::class.java))
        }

        fab_stop.setOnClickListener {
            processingFragment.stop()
        }

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CALL_PHONE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    1
                )

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    fun hideFloatingActionButtons() {
        if (fab.visibility == View.VISIBLE) {
            fab.hide()
        }

        if (fab_stop.visibility == View.VISIBLE) {
            fab_stop.hide()
        }
    }

    fun showFloatingActionButtons() {
        if (fab.visibility != View.VISIBLE) {
            fab.show()
        }

        if (fab_stop.visibility != View.VISIBLE) {
            fab_stop.show()
        }
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
            R.id.action_about -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    class ViewPagerAdapter(fm: FragmentManager, private var fragments: List<Fragment>) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int = fragments.count()

        override fun getItem(position: Int): Fragment = fragments[position]
    }
}
