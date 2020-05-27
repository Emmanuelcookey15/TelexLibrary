package ng.hotels.telexlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import ng.hotels.telexlibraries.SupportFragment
import ng.hotels.telexlibraries.TelexManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val t = TelexManager(this)
            .setDevelopersEmail("emmanuelcookey744@gmail.com")
            .setFrameLayout(R.id.frame_layout)
            .initializeAsFragment()


    }


    fun changeFragmentViews(fragment: Fragment)  {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
            .commitNow()

    }

}
