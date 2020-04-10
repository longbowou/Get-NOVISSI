package longbowou.getnovissi

import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.romellfudi.ussdlibrary.USSDController.Companion.KEY_LOGIN
import com.romellfudi.ussdlibrary.USSDController.Companion.instance
import com.romellfudi.ussdlibrary.USSDServiceKT

class MyUSSDServiceKT : USSDServiceKT() {

    private val TAG = USSDServiceKT::class.java.simpleName

    private var event: AccessibilityEvent? = null

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        this.event = event

        Log.d(TAG, "onAccessibilityEvent")

        Log.d(
            TAG, String.format(
                "onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [text] %s",
                event!!.eventType, event.className, event.packageName,
                event.eventTime, event.text
            )
        )

        if (instance == null
            || !instance!!.isRunning!!
        ) {
            return
        }

        if (LoginView(event) && notInputText(event)) {
            // first view or logView, do nothing, pass / FIRST MESSAGE
            clickOnButton(event, 0)
            instance!!.isRunning = false
            instance!!.callbackInvoke.over(
                event.text[0].toString()
            )
        } else if (problemView(event) || LoginView(event)) {
            // deal down
            clickOnButton(event, 1)
            instance!!.callbackInvoke.over(
                event.text[0].toString()
            )
        } else if (isUSSDWidget(event)) {
            // ready for work
            var response = ""
            for (text in event.text) {
                response += text
            }

            if (notInputText(event)) {
                // not more input panels / LAST MESSAGE
                // sent 'OK' button
                clickOnButton(event, 0)
                instance!!.isRunning = false
                instance!!.callbackInvoke.over(
                    response
                )
            } else {
                // sent option 1
                if (instance!!.callbackMessage == null) instance!!.callbackInvoke.responseInvoke(
                    response
                ) else {
                    instance!!.callbackMessage!!.invoke(
                        response
                    )
                    instance!!.callbackMessage =
                        null
                }
            }
        }
    }

    /**
     * The View has a login message into USSD Widget
     *
     * @param event AccessibilityEvent
     * @return boolean USSD Widget has login message
     */
    private fun LoginView(event: AccessibilityEvent): Boolean {
        return (isUSSDWidget(event)
                && instance!!.map!![KEY_LOGIN]!!.contains(event.text[0].toString()))
    }

    /**
     * The AccessibilityEvent is instance of USSD Widget class
     *
     * @param event AccessibilityEvent
     * @return boolean AccessibilityEvent is USSD
     */
    private fun isUSSDWidget(event: AccessibilityEvent): Boolean {
        return event.className == "amigo.app.AmigoAlertDialog" || event.className == "android.app.AlertDialog"
    }
}