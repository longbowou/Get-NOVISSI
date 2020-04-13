package longbowou.getnovissi

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.romellfudi.ussdlibrary.USSDController
import longbowou.getnovissi.ussd.MyUSSDController
import longbowou.getnovissi.ussd.MyUSSDServiceKT

class ProcessNovissiAsyncTask(
    private var context: Context,
    private var map: java.util.HashMap<String, java.util.HashSet<String>>
) : AsyncTask<MutableMap<String, String>, Unit, Unit>() {
    var asyncInterface: AsyncInterface? = null
    lateinit var ussdController: MyUSSDController

    override fun doInBackground(vararg params: MutableMap<String, String>?) {
        val novissi = params[0]!!

        Log.d(TAG, "Initializing $novissi")
        fireUpdate("Initializing", "$novissi", true)

        ussdController = MyUSSDController.getInstance(context, true)
        ussdController.callUSSDInvoke(
            "*855#",
            simSlot,
            map,
            object : USSDController.CallbackInvoke {
                override fun responseInvoke(message: String) {
                    if (!message.contains("Tapez 1 pour continuer")) {
                        fireError(novissi, "On error Step Zero", message)
                        return
                    }
                    fireUpdate("Step Zero Continue", message)

                    ussdController.send("1") { message_step_one ->
                        if (!message_step_one.contains("1- S'inscrire au programme d'aide")) {
                            fireError(novissi, "On error Step One", message_step_one)
                            return@send
                        }
                        fireUpdate("Step One Continue", message_step_one)

                        ussdController.send("1") { message_step_two ->
                            if (!message_step_two.contains("Veuillez saisir le numéro de la carte d'électeur")) {
                                fireError(novissi, "On error Step Two", message_step_two)
                                return@send
                            } else {
                                fireUpdate("Step Two Register", message_step_two)
                            }

                            asyncInterface?.onUpdate("Step Two Sending Id card")
                            ussdController.send(novissi.getValue("id_card")) { message_step_three ->
                                if (message_step_three.contains("Numéro de carte non valide ou déja enregistré")) {
                                    logError(
                                        novissi,
                                        "id_card",
                                        "bad id card",
                                        "Step Three bad id card",
                                        message_step_three
                                    )
                                    return@send
                                }

                                if (!message_step_three.contains("nom")) {
                                    fireError(novissi, "On error Step Three", message_step_three)
                                    return@send
                                }
                                fireUpdate("Step Three", message_step_three)

                                asyncInterface?.onUpdate("Step Three Sending Last Name")
                                ussdController.send(novissi.getValue("last_name")) { message_step_four ->
                                    if (message_step_four.contains("nom ne correspond pas")) {
                                        logError(
                                            novissi,
                                            "last_name",
                                            "bad last name",
                                            "Step Four bad last name",
                                            message_step_four
                                        )
                                        return@send
                                    }

                                    if (!message_step_four.contains("prénoms")) {
                                        fireError(novissi, "On error Step Four", message_step_four)
                                        return@send
                                    }
                                    fireUpdate("Step Four", message_step_four)

                                    asyncInterface?.onUpdate("Step Four Sending First Name")
                                    ussdController.send(novissi.getValue("first_name")) { message_step_five ->
                                        if (message_step_five.contains("prénom ne correspond")) {
                                            logError(
                                                novissi,
                                                "first_name",
                                                "bad first name",
                                                "Step Five bad first name",
                                                message_step_five
                                            )
                                            return@send
                                        }

                                        if (!message_step_five.contains("date")) {
                                            fireError(
                                                novissi,
                                                "On error Step Five",
                                                message_step_five
                                            )
                                            return@send
                                        }
                                        fireUpdate("Step Five", message_step_five)

                                        asyncInterface?.onUpdate("Step Five Sending Born At")
                                        ussdController.send(novissi.getValue("born_at")) { message_step_six ->
                                            if (message_step_six.contains("date ne correspond pas")) {
                                                logError(
                                                    novissi,
                                                    "born_at",
                                                    "bad born at date",
                                                    "Step Six bad born at date",
                                                    message_step_six
                                                )
                                                return@send
                                            }

                                            if (!message_step_six.contains("mère")) {
                                                fireError(
                                                    novissi,
                                                    "On error Step Six",
                                                    message_step_six
                                                )
                                                return@send
                                            }
                                            fireUpdate("Step Six", message_step_six)

                                            asyncInterface?.onUpdate("Step Six Sending Mother Name")
                                            ussdController.send(novissi.getValue("mother")) { message_step_seven ->
                                                if (message_step_seven.contains("mère incorrect")) {
                                                    logError(
                                                        novissi,
                                                        "mother",
                                                        "bad mother name",
                                                        "Step Seven bad mother name",
                                                        message_step_seven
                                                    )
                                                    return@send
                                                }

                                                if (!message_step_seven.contains("TMoney ou Flooz")) {
                                                    fireError(
                                                        novissi,
                                                        "On error Step Seven",
                                                        message_step_seven
                                                    )
                                                    return@send
                                                }
                                                fireUpdate("Step Seven", message_step_seven)

                                                asyncInterface?.onUpdate("Step Seven TMoney ou Flooz")
                                                ussdController.send("2") { message_step_eight ->
                                                    if (!message_step_eight.contains("indicatif")) {
                                                        fireError(
                                                            novissi,
                                                            "On error Step Eight",
                                                            message_step_eight
                                                        )
                                                        return@send
                                                    }
                                                    fireUpdate("Step Eight", message_step_eight)

                                                    asyncInterface?.onUpdate("Step Eight Sending Phone Number")
                                                    ussdController.send(novissi.getValue("phone_number")) { message_step_nine ->
                                                        val step = "Step Nine"
                                                        if (message_step_nine.contains("Désolé, ce numéro a atteint la limite")) {
                                                            logError(
                                                                novissi,
                                                                "phone_number",
                                                                "phone number has reach limit",
                                                                "Step Nine phone number has reach limit",
                                                                message_step_nine
                                                            )
                                                            return@send
                                                        }
                                                        fireUpdate(step, message_step_nine)

                                                        novissi["processed"] = "Yes"
                                                        ussdController.cancel()
                                                        ussdController.callbackMessage = null
                                                        asyncInterface?.onProcessed(
                                                            novissi,
                                                            step,
                                                            message_step_nine
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                override fun over(message: String) {
                    fireError(novissi, "On error", message)
                }
            })
    }

    private fun fireError(novissi: MutableMap<String, String>, level: String, message: String) {
        Log.d(TAG, level)
        Log.d(TAG, message)
        asyncInterface?.onError(novissi, level, message)
        ussdController.cancel()
    }

    private fun fireUpdate(level: String, message: String, isWarning: Boolean = false) {
        Log.d(MyUSSDServiceKT.TAG, level)
        Log.d(TAG, level)
        Log.d(TAG, message)
        asyncInterface?.onUpdate(level, message, isWarning)
    }

    private fun logError(
        novissi: MutableMap<String, String>,
        errorKey: String,
        errorMessage: String,
        step: String,
        message: String
    ) {
        Log.d(TAG, "Novissi $novissi $errorMessage")
        if (novissi["errors"] == null) {
            novissi["errors"] = Gson().toJson(mapOf(errorKey to errorMessage))
        } else {
            val typeConverter = object : TypeToken<MutableMap<String, String>>() {}.type
            val errors: MutableMap<String, String> = Gson().fromJson(
                novissi["errors"],
                typeConverter
            )
            errors[errorKey] = errorMessage
            novissi["errors"] = Gson().toJson(errors)
        }
        asyncInterface?.onProcessed(novissi, step, message)
        ussdController.cancel()
    }

    interface AsyncInterface {
        fun onUpdate(step: String, message: String? = null, isWarning: Boolean = false)
        fun onProcessed(
            novissi: MutableMap<String, String>,
            step: String,
            message: String
        )

        fun onError(novissi: MutableMap<String, String>, step: String, message: String)
    }

    companion object {
        val TAG = ProcessNovissiAsyncTask::class.java.simpleName
        var simSlot = 0
    }
}