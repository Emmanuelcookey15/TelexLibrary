package ng.hotels.telexlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ng.hotels.telexlibraries.TelexManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val t = TelexManager(this)
            .setOrganization("mark-essien-inc-b-447199")
            .setTechnicalTeam("management-team")
            .setGeneralTeam("hotels.ng-customer-support")
            .setSalesTeam("corporate-sales")
            .setFrameLayout(R.id.frame_layout)
            .initializeAsFragment()


    }


    fun changeFragmentViews(fragment: Fragment)  {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
            .commitNow()

    }

}
