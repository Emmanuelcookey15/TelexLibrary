package ng.hotels.telexlibraries

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_knowledge_base.*

class KnowledgeBase : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge_base)
        supportActionBar?.hide()

        remain_knowledge_base.setOnClickListener {
            onSNACK(it, "Already in selected page")
        }

        goto_open_ticket.setOnClickListener {
            finish()
        }

        walkthrough.setOnClickListener {
            onSNACK(it, "Feature not yet available")
        }

        billing.setOnClickListener {
            onSNACK(it, "Feature not yet available")
        }

        your_order.setOnClickListener {
            onSNACK(it, "Feature not yet available")
        }

        shipping.setOnClickListener {
            onSNACK(it, "Feature not yet available")
        }

    }



    private fun onSNACK(view: View, str: String){
        //Snackbar(view)
        val snackbar = Snackbar.make(view, str,
            Snackbar.LENGTH_LONG).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLUE)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.LTGRAY)
        val textView =
            snackbarView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.BLUE)
        textView.textSize = 28f
        snackbar.show()
    }
}
