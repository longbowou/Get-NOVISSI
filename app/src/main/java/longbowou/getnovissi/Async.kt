package longbowou.getnovissi

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.romellfudi.ussdlibrary.USSDController

class Async(
    var context: Context, var map: java.util.HashMap<String, java.util.HashSet<String>>
) : AsyncTask<MutableMap<String, String>, Unit, Unit>() {
    var asyncInterface: AsyncInterface? = null
    var TAG = "Async"

    override fun doInBackground(vararg params: MutableMap<String, String>?) {
        val novissi = params[0]!!

        Log.d(TAG, "Processing novissi $novissi")

        val ussdApi = MyUSSDController.getInstance(context)
        ussdApi.callUSSDInvoke("*855#", 0, map, object : USSDController.CallbackInvoke {
            override fun responseInvoke(message: String) {
                Log.d(TAG, "Step Zero Continue")
                Log.d(TAG, message)
                asyncInterface?.onUpdate("Step Zero Continue", message)

                if (message.contains("Tapez 1 pour continuer")) {
                    ussdApi.send("1") { message_step_one ->
                        Log.d(TAG, "Step One Continue")
                        Log.d(TAG, message_step_one)
                        asyncInterface?.onUpdate("Step One Continue", message_step_one)

                        if (message_step_one.contains("1- S'inscrire au programme d'aide")) {
                            ussdApi.send("1") { message_step_two ->
                                Log.d(TAG, "Step Two Register")
                                Log.d(TAG, message_step_two)
                                asyncInterface?.onUpdate("Step Two Register", message_step_two)

                                if (message_step_two.contains("Veuillez saisir le numéro de la carte d'électeur")) {
                                    asyncInterface?.onUpdate(
                                        "Step Two Sending Id card", message_step_two
                                    )
                                    ussdApi.send(novissi.getValue("id_card")) { message_step_three ->
                                        Log.d(TAG, "Step Three")
                                        Log.d(TAG, message_step_three)
                                        asyncInterface?.onUpdate(
                                            "Step Three", message_step_three
                                        )

                                        if (message_step_three.contains("Numéro de carte non valide ou déja enregistré")) {
                                            Log.d(TAG, "Novissi $novissi already bad id card")
                                            ussdApi.cancel()
                                            asyncInterface?.onProcessed(novissi)
                                            ussdApi.callbackMessage = null
                                        }

                                        if (message_step_three.contains("nom")) {
                                            asyncInterface?.onUpdate(
                                                "Step Three Sending Last Name", message_step_three
                                            )
                                            ussdApi.send(novissi.getValue("last_name")) { message_step_four ->
                                                Log.d(
                                                    TAG, "Step Four"
                                                )
                                                Log.d(TAG, message_step_four)
                                                asyncInterface?.onUpdate(
                                                    "Step Four", message_step_four
                                                )

                                                if (message_step_four.contains("nom ne correspond pas")) {
                                                    Log.d(
                                                        TAG, "Novissi $novissi bad last name"
                                                    )
                                                    ussdApi.cancel()
                                                    asyncInterface?.onProcessed(novissi)
                                                    ussdApi.callbackMessage = null
                                                }

                                                if (message_step_four.contains("prénoms")) {
                                                    asyncInterface?.onUpdate(
                                                        "Step Four Sending First Name",
                                                        message_step_four
                                                    )
                                                    ussdApi.send(novissi.getValue("first_name")) { message_step_five ->
                                                        Log.d(
                                                            TAG, "Step Five"
                                                        )
                                                        Log.d(TAG, message_step_five)
                                                        asyncInterface?.onUpdate(
                                                            "Step Five", message_step_five
                                                        )

                                                        if (message_step_five.contains("prénom ne correspond")) {
                                                            Log.d(
                                                                TAG,
                                                                "Novissi $novissi bad first name"
                                                            )
                                                            ussdApi.cancel()
                                                            asyncInterface?.onProcessed(novissi)
                                                            ussdApi.callbackMessage = null
                                                        }

                                                        if (message_step_five.contains("date")) {
                                                            asyncInterface?.onUpdate(
                                                                "Step Five Sending Born At",
                                                                message_step_five
                                                            )
                                                            ussdApi.send(novissi.getValue("born_at")) { message_step_six ->
                                                                Log.d(
                                                                    TAG, "Step Six"
                                                                )
                                                                Log.d(TAG, message_step_six)
                                                                asyncInterface?.onUpdate(
                                                                    "Step Six", message_step_six
                                                                )

                                                                if (message_step_six.contains("date ne correspond pas")) {
                                                                    Log.d(
                                                                        TAG,
                                                                        "Novissi $novissi bad born at"
                                                                    )
                                                                    ussdApi.cancel()
                                                                    asyncInterface?.onProcessed(
                                                                        novissi
                                                                    )
                                                                    ussdApi.callbackMessage = null
                                                                }

                                                                if (message_step_six.contains("mère")) {
                                                                    asyncInterface?.onUpdate(
                                                                        "Step Six Sending Mother Name",
                                                                        message_step_six
                                                                    )
                                                                    ussdApi.send(
                                                                        novissi.getValue(
                                                                            "mother"
                                                                        )
                                                                    ) { message_step_seven ->
                                                                        Log.d(
                                                                            TAG, "Step Seven"
                                                                        )
                                                                        Log.d(
                                                                            TAG, message_step_seven
                                                                        )
                                                                        asyncInterface?.onUpdate(
                                                                            "Step Seven",
                                                                            message_step_seven
                                                                        )

                                                                        if (message_step_seven.contains(
                                                                                "mère incorrect"
                                                                            )
                                                                        ) {
                                                                            Log.d(
                                                                                TAG,
                                                                                "Novissi $novissi bad mother name"
                                                                            )
                                                                            ussdApi.cancel()
                                                                            asyncInterface?.onProcessed(
                                                                                novissi
                                                                            )
                                                                            ussdApi.callbackMessage =
                                                                                null
                                                                        }

                                                                        if (message_step_seven.contains(
                                                                                "TMoney ou Flooz"
                                                                            )
                                                                        ) {
                                                                            asyncInterface?.onUpdate(
                                                                                "Step Seven TMoney ou Flooz",
                                                                                message_step_six
                                                                            )
                                                                            ussdApi.send("2") { message_step_eight ->
                                                                                Log.d(
                                                                                    TAG,
                                                                                    "Step Eight"
                                                                                )
                                                                                Log.d(
                                                                                    TAG,
                                                                                    message_step_eight
                                                                                )
                                                                                asyncInterface?.onUpdate(
                                                                                    "Step Eight",
                                                                                    message_step_eight
                                                                                )

                                                                                if (message_step_eight.contains(
                                                                                        "indicatif"
                                                                                    )
                                                                                ) {
                                                                                    asyncInterface?.onUpdate(
                                                                                        "Step Eight Sending Phone Number",
                                                                                        message_step_eight
                                                                                    )
                                                                                    ussdApi.send(
                                                                                        novissi.getValue(
                                                                                            "phone_number"
                                                                                        )
                                                                                    ) { message_step_nine ->
                                                                                        Log.d(
                                                                                            TAG,
                                                                                            "Step Nine End"
                                                                                        )
                                                                                        Log.d(
                                                                                            TAG,
                                                                                            message_step_nine
                                                                                        )
                                                                                        asyncInterface?.onUpdate(
                                                                                            "Step Nine End",
                                                                                            message_step_nine
                                                                                        )
                                                                                        novissi["processed"] =
                                                                                            "Yes"
                                                                                        ussdApi.callbackMessage =
                                                                                            null
                                                                                        ussdApi.cancel()
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
                                                                                    ussdApi.callbackMessage =
                                                                                        null
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
                                                                            ussdApi.callbackMessage =
                                                                                null
                                                                        }
                                                                    }
                                                                } else {
                                                                    Log.d(
                                                                        TAG, "On error Step Six"
                                                                    )
                                                                    ussdApi.cancel()
                                                                    asyncInterface?.onError(
                                                                        novissi
                                                                    )
                                                                    ussdApi.callbackMessage = null
                                                                }
                                                            }
                                                        } else {
                                                            Log.d(TAG, "On error Step Five")
                                                            ussdApi.cancel()
                                                            asyncInterface?.onError(novissi)
                                                            ussdApi.callbackMessage = null
                                                        }
                                                    }
                                                } else {
                                                    Log.d(TAG, "On error Step Four")
                                                    ussdApi.cancel()
                                                    asyncInterface?.onError(novissi)
                                                    ussdApi.callbackMessage = null
                                                }
                                            }
                                        } else {
                                            Log.d(TAG, "On error Step Three")
                                            ussdApi.cancel()
                                            asyncInterface?.onError(novissi)
                                            ussdApi.callbackMessage = null
                                        }
                                    }
                                } else {
                                    Log.d(TAG, "On error Step Two")
                                    ussdApi.cancel()
                                    asyncInterface?.onError(novissi)
                                    ussdApi.callbackMessage = null
                                }
                            }
                        } else {
                            Log.d(TAG, "On error Step One")
                            ussdApi.cancel()
                            asyncInterface?.onError(novissi)
                            ussdApi.callbackMessage = null
                        }
                    }
                } else {
                    Log.d(TAG, "On error Step Zero")
                    ussdApi.cancel()
                    asyncInterface?.onError(novissi)
                    ussdApi.callbackMessage = null
                }
            }

            override fun over(message: String) {
                Log.d(TAG, "On error ")
                Log.d(TAG, message)
                ussdApi.cancel()
                asyncInterface?.onError(novissi)
                ussdApi.callbackMessage = null
            }
        })
    }

    interface AsyncInterface {
        fun onUpdate(step: String, message: String)
        fun onProcessed(novissi: MutableMap<String, String>)
        fun onError(novissi: MutableMap<String, String>)
    }
}