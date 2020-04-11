package longbowou.getnovissi.ussd

import android.accessibilityservice.AccessibilityService
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.romellfudi.ussdlibrary.USSDController.Companion.KEY_ERROR
import com.romellfudi.ussdlibrary.contains
import com.romellfudi.ussdlibrary.toLowerCase
import longbowou.getnovissi.ussd.MyUSSDController.Companion.KEY_LOGIN
import java.util.*

class MyUSSDServiceKT : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Companion.event = event

        Log.d(
            TAG, String.format(
                "onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [text] %s",
                event!!.eventType, event.className, event.packageName,
                event.eventTime, event.text
            )
        )

        instance = MyUSSDController.instance
        if (instance == null
            || !instance!!.isRunning!!
        ) {
            return
        }

        if (loginView(event) && notInputText(
                event
            )
        ) {
            // first view or logView, do nothing, pass / FIRST MESSAGE
            clickOnButton(
                event,
                0
            )
            instance!!.isRunning = false
            instance!!.callbackInvoke.over(
                event.text[0].toString()
            )
        } else if (problemView(event) || loginView(event)) {
            // deal down
            clickOnButton(
                event,
                1
            )
            instance!!.callbackInvoke.over(
                event.text[0].toString()
            )
        } else if (isUSSDWidget(event)) {
            // ready for work
            var response = ""
            for (text in event.text) {
                response += text
            }

            if (notInputText(
                    event
                )
            ) {
                // not more input panels / LAST MESSAGE
                // sent 'OK' button
                clickOnButton(
                    event,
                    0
                )
                instance!!.isRunning = false
                instance!!.callbackInvoke.over(
                    response
                )
            } else {
                // sent option 1
                if (instance!!.callbackMessage == null) {
                    instance!!.callbackInvoke.responseInvoke(response)
                    Log.d(TAG, "callbackInvoke")
                } else {
                    instance!!.callbackMessage!!.invoke(response)
//                    instance!!.callbackMessage = null
                    Log.d(TAG, "callbackMessage")
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
    private fun loginView(event: AccessibilityEvent): Boolean {
        return (isUSSDWidget(event)
                && instance!!.map!![KEY_LOGIN]!!.contains(event.text[0].toString()))
    }

    /**
     * The View has a problem message into USSD Widget
     *
     * @param event AccessibilityEvent
     * @return boolean USSD Widget has problem message
     */
    private fun problemView(event: AccessibilityEvent): Boolean {
        return (isUSSDWidget(event)
                && instance!!.map!![KEY_ERROR]!!
            .contains(event.text[0].toString()))
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

    /**
     * Active when SO interrupt the application
     */
    override fun onInterrupt() {
        Log.d(TAG, "onInterrupt")
    }

    /**
     * Configure accessibility server from Android Operative System
     */
    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "onServiceConnected")
    }

    companion object {

        private var instance: MyUSSDController? = null

        val TAG = MyUSSDServiceKT::class.java.simpleName

        private var event: AccessibilityEvent? = null

        /**
         * set text into input text at USSD widget
         *
         * @param event AccessibilityEvent
         * @param data  Any String
         */
        private fun setTextIntoField(event: AccessibilityEvent?, data: String?) {
            val ussdController =
                instance
            val arguments = Bundle()
            arguments.putCharSequence(
                AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, data
            )
            for (leaf in getLeaves(
                event
            )) {
                if (leaf.className == "android.widget.EditText" && !leaf.performAction(
                        AccessibilityNodeInfo.ACTION_SET_TEXT, arguments
                    )
                ) {
                    val clipboardManager = ussdController!!.context
                        .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboardManager.setPrimaryClip(ClipData.newPlainText("text", data))
                    leaf.performAction(AccessibilityNodeInfo.ACTION_PASTE)
                }
            }
        }

        private fun getLeaves(event: AccessibilityEvent?): List<AccessibilityNodeInfo> {
            val leaves: MutableList<AccessibilityNodeInfo> =
                ArrayList()
            if (event?.source != null) {
                getLeaves(
                    leaves,
                    event.source
                )
            }
            return leaves
        }

        private fun getLeaves(
            leaves: MutableList<AccessibilityNodeInfo>,
            node: AccessibilityNodeInfo
        ) {
            if (node.childCount == 0) {
                leaves.add(node)
                return
            }
            for (i in 0 until node.childCount) {
                getLeaves(
                    leaves,
                    node.getChild(i)
                )
            }
        }

        fun notInputText(event: AccessibilityEvent?): Boolean {
            var flag = true
            for (leaf in getLeaves(
                event
            )) {
                if (leaf.className == "android.widget.EditText") flag = false
            }
            return flag
        }

        /**
         * click a button using the index
         *
         * @param event AccessibilityEvent
         * @param index button's index
         */
        fun clickOnButton(event: AccessibilityEvent?, index: Int) {
            var count = -1
            for (leaf in getLeaves(
                event
            )) {
                if (leaf.className.toString().toLowerCase().contains("button")) {
                    count++
                    if (count == index) {
                        leaf.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    }
                }
            }
        }

        /**
         * Send whatever you want via USSD
         *
         * @param text any string
         */
        fun send(text: String?) {
            setTextIntoField(
                event,
                text
            )
            clickOnButton(
                event,
                1
            )
        }

        /**
         * Cancel USSD
         *
         */
        fun cancel() {
            clickOnButton(
                event,
                0
            )
        }
    }
}