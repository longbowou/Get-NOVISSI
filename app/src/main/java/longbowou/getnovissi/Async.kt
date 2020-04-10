package longbowou.getnovissi

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.romellfudi.ussdlibrary.USSDController

class Async(
    var context: Context,
    var map: java.util.HashMap<String, java.util.HashSet<String>>
) : AsyncTask<MutableMap<String, String>, Unit, Unit>() {
    var asyncInterface: AsyncInterface? = null
    var TAG = "Async"

    override fun doInBackground(vararg params: MutableMap<String, String>?) {
        val novissi = params[0]!!

        Log.d(TAG, "Processing novissi $novissi")

        val ussdApi = MyUSSDController.getInstance(context)
        ussdApi.callUSSDInvoke(
            "*855#",
            1,
            map,
            object : USSDController.CallbackInvoke {
                override fun responseInvoke(message: String) {
                    Log.d(TAG, "Step Zero response")
                    Log.d(TAG, message)
                    asyncInterface?.onUpdate("Step Zero response", message)

                    if (message.contains("Tapez 1 pour continuer")) {
                        ussdApi.send("1") { message_step_one ->
                            Log.d(TAG, "Step One response")
                            Log.d(TAG, message)
                            Log.d(TAG, message_step_one)
                            asyncInterface?.onUpdate("Step One response", message_step_one)

                            if (message_step_one.contains("1- S'inscrire au programme d'aide")) {
                                ussdApi.send("1") { message_step_two ->
                                    Log.d(TAG, "Step Two response")
                                    Log.d(TAG, message)
                                    Log.d(TAG, message_step_one)
                                    Log.d(TAG, message_step_two)
                                    asyncInterface?.onUpdate("Step Two response", message_step_two)

                                    if (message_step_two.contains("Veuillez saisir le numéro de la carte d'électeur")) {
                                        ussdApi.send(novissi.getValue("id_card")) { message_step_three ->
                                            Log.d(TAG, "Step Three response")
                                            Log.d(TAG, message)
                                            Log.d(TAG, message_step_one)
                                            Log.d(TAG, message_step_two)
                                            Log.d(TAG, message_step_three)
                                            asyncInterface?.onUpdate(
                                                "Step Three response",
                                                message_step_three
                                            )

                                            if (message_step_three.contains("nom")) {
                                                ussdApi.send(novissi.getValue("last_name")) { message_step_four ->
                                                    Log.d(
                                                        TAG,
                                                        "Step Four response"
                                                    )
                                                    Log.d(TAG, message_step_four)
                                                    asyncInterface?.onUpdate(
                                                        "Step Four response",
                                                        message_step_four
                                                    )

                                                    if (message_step_four.contains("prénom")) {
                                                        ussdApi.send(novissi.getValue("first_name")) { message_step_five ->
                                                            Log.d(
                                                                TAG,
                                                                "Step Five response"
                                                            )
                                                            Log.d(TAG, message_step_five)
                                                            asyncInterface?.onUpdate(
                                                                "Step Five response",
                                                                message_step_five
                                                            )

                                                            if (message_step_five.contains("date")) {
                                                                ussdApi.send(novissi.getValue("born_at")) { message_step_six ->
                                                                    Log.d(
                                                                        TAG,
                                                                        "Step Six response"
                                                                    )
                                                                    Log.d(TAG, message_step_six)
                                                                    asyncInterface?.onUpdate(
                                                                        "Step Six response",
                                                                        message_step_six
                                                                    )

                                                                    if (message_step_six.contains("mère")) {
                                                                        ussdApi.send(
                                                                            novissi.getValue(
                                                                                "mother"
                                                                            )
                                                                        ) { message_step_seven ->
                                                                            Log.d(
                                                                                TAG,
                                                                                "Step Seven response"
                                                                            )
                                                                            Log.d(
                                                                                TAG,
                                                                                message_step_seven
                                                                            )
                                                                            asyncInterface?.onUpdate(
                                                                                "Step Seven response",
                                                                                message_step_seven
                                                                            )

                                                                            if (message_step_seven.contains(
                                                                                    "TMONEY"
                                                                                )
                                                                            ) {
                                                                                ussdApi.send("2") { message_step_eight ->
                                                                                    Log.d(
                                                                                        TAG,
                                                                                        "Step Eight response"
                                                                                    )
                                                                                    Log.d(
                                                                                        TAG,
                                                                                        message_step_eight
                                                                                    )
                                                                                    asyncInterface?.onUpdate(
                                                                                        "Step Eight response",
                                                                                        message_step_eight
                                                                                    )

                                                                                    if (message_step_eight.contains(
                                                                                            "indicatif"
                                                                                        )
                                                                                    ) {
                                                                                        ussdApi.send(
                                                                                            novissi.getValue(
                                                                                                "phone_number"
                                                                                            )
                                                                                        ) { message_step_nine ->
                                                                                            Log.d(
                                                                                                TAG,
                                                                                                "Step Nine response"
                                                                                            )
                                                                                            Log.d(
                                                                                                TAG,
                                                                                                message_step_nine
                                                                                            )
                                                                                            asyncInterface?.onUpdate(
                                                                                                "Step Nine response",
                                                                                                message_step_nine
                                                                                            )
                                                                                            novissi["processed"] =
                                                                                                "Yes"
                                                                                            asyncInterface?.onProcessed(
                                                                                                novissi
                                                                                            )
                                                                                        }
                                                                                    } else {
                                                                                        Log.d(
                                                                                            TAG,
                                                                                            "On error Step Eight"
                                                                                        )
                                                                                        ussdApi.cancel()
                                                                                        asyncInterface?.onError(
                                                                                            novissi
                                                                                        )
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                Log.d(
                                                                                    TAG,
                                                                                    "On error Step Seven"
                                                                                )
                                                                                ussdApi.cancel()
                                                                                asyncInterface?.onError(
                                                                                    novissi
                                                                                )
                                                                            }
                                                                        }
                                                                    } else {
                                                                        Log.d(
                                                                            TAG,
                                                                            "On error Step Six"
                                                                        )
                                                                        ussdApi.cancel()
                                                                        asyncInterface?.onError(
                                                                            novissi
                                                                        )
                                                                    }
                                                                }
                                                            } else {
                                                                Log.d(TAG, "On error Step Five")
                                                                ussdApi.cancel()
                                                                asyncInterface?.onError(novissi)
                                                            }
                                                        }
                                                    } else {
                                                        Log.d(TAG, "On error Step Four")
                                                        ussdApi.cancel()
                                                        asyncInterface?.onError(novissi)
                                                    }
                                                }
                                            } else {
                                                Log.d(TAG, "On error Step Three")
                                                ussdApi.cancel()
                                                asyncInterface?.onError(novissi)
                                            }
                                        }
                                    } else {
                                        Log.d(TAG, "On error Step Two")
                                        ussdApi.cancel()
                                        asyncInterface?.onError(novissi)
                                    }
                                }
                            } else {
                                Log.d(TAG, "On error Step One")
                                ussdApi.cancel()
                                asyncInterface?.onError(novissi)
                            }
                        }
                    } else {
                        Log.d(TAG, "On error Step Zero")
                        ussdApi.cancel()
                        asyncInterface?.onError(novissi)
                    }
                }

                override fun over(message: String) {
                    Log.d(TAG, "On error response")
                    Log.d(TAG, message)
                    ussdApi.cancel()
                    asyncInterface?.onError(novissi)
                }
            })

    }

    interface AsyncInterface {
        fun onUpdate(step: String, message: String)
        fun onProcessed(novissi: MutableMap<String, String>)
        fun onError(novissi: MutableMap<String, String>)
    }
}