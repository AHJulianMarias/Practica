package com.example.practica

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    private lateinit var radioGroup: RadioGroup
    private lateinit var radioboton: RadioButton
    private lateinit var checkboxLectura: CheckBox
    private lateinit var checkboxMusica: CheckBox
    private lateinit var checkboxDeporte: CheckBox
    private lateinit var checkboxArte: CheckBox
    private lateinit var textoSpinner: String
    private lateinit var spinnerPaises: Spinner

    private lateinit var mostrarResText: TextView
    private val KEY_MOSTRAR_RESULTADO = "key_mostrar_resultado"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonGuardar: Button = findViewById(R.id.botonMostrarResultado)
        val paises = arrayOf(
            "España",
            "Argentina",
            "Francia",
            "Brasil",
            "Alemania",
        )


        //SPINNER

        spinnerPaises = findViewById<Spinner>(R.id.spinnerPaises)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paises)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPaises.adapter = adapter
        spinnerPaises.onItemSelectedListener = this


        //SEEKBAR
        val seekB = findViewById<SeekBar>(R.id.seekBar)
        val textoSeekB = findViewById<TextView>(R.id.valorSeekBar)
        seekB.max = 10
        seekB.min = 0
        mostrarResText = findViewById(R.id.mostrarResultado)

        if (savedInstanceState != null) {
            // Recuperamos y establecemos el texto guardado del EditText
            val savedText = savedInstanceState.getString(KEY_MOSTRAR_RESULTADO)
            mostrarResText.text = savedText

        }

        seekB.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            /**
             * Método que se llama cada vez que cambio el progreso
             * seekBar contiene la referencia a la seekBar
             * progress contiene el valor actual de la seekbar
             * fromUser indica si el cambio lo ha producido el usuario al arastar la barra
             */
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Actualiza el TextView mientras se mueve la SeekBar
                textoSeekB.text = progress.toString()
            }

            /**
             * Se llama caundo el usuario comienza a tocar la seekbar
             */
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // No se necesita implementación en este caso
            }

            /**
             * Se llama cuando el usuario deja de tocarla
             */
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // No se necesita implementación en este caso
            }
        })

        botonGuardar.setOnClickListener {

            // vistas + asignación de texto de nombre, apellidos y mail
            val nombreInput = findViewById<EditText>(R.id.nombreInp)
            val apellidoInput = findViewById<EditText>(R.id.apellidosInp)
            val emailInp = findViewById<EditText>(R.id.emailInp)
            if (nombreInput.text.toString() == "") {
                mostrarToast(nombreInput)
            } else if (apellidoInput.text.toString() == "") {
                mostrarToast(apellidoInput)
            } else if (emailInp.text.toString() == "") {
                mostrarToast(emailInp)
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInp.text.toString())
                    .matches()
            ) {
                mostrarToast(
                    emailInp,
                    android.util.Patterns.EMAIL_ADDRESS.matcher(emailInp.text.toString()).matches()
                )
            } else {
                //llamada a textoRadioButton()
                val radiobotonText = textoRadioButton()
                //llamada a textoCheckbox()
                val stringAficiones = textoCheckbox()


                val switch = findViewById<Switch>(R.id.switch1)
                if (switch.isChecked) {
                    mostrarResText.text = getString(
                        R.string.nombre_apellidos_email_sexo_pais_de_origen_hobbies_nivel_de_satisfaccion_suscripcion_al_boletin_si,
                        "${nombreInput.text.toString().trim()}.",
                        "${apellidoInput.text.toString().trim()}.",
                        "${emailInp.text.toString().trim()}.",
                        "$radiobotonText.",
                        "$textoSpinner.",
                        stringAficiones,
                        "${textoSeekB.text.toString()}."
                    ).trimMargin()


                } else {
                    mostrarResText.text = getString(
                        R.string.nombre_apellidos_email_sexo_pais_de_origen_hobbies_nivel_de_satisfaccion_suscripcion_al_boletin_no,
                        "${nombreInput.text.toString()}.",
                        "${apellidoInput.text.toString()}.",
                        "${emailInp.text.toString()}.",
                        "$radiobotonText.",
                        "$textoSpinner.",
                        stringAficiones,
                        "${textoSeekB.text.toString()}."
                    ).trimMargin()

                }

            }

        }

    }

    /**
     * Funcion que acepta diferente numero de parametros.
     * Numero de parametros = 1 -> se refiere a un campo EditText que está vacio (nombre, apellidos, email)
     * Numero de parametros = 2 -> email no valido, solo comprueba que el segundo parametro sea un booleano, no hace la comprobacion de que sea False, no le vi la necesidad
     */


    private fun mostrarToast(editT: EditText) {
        Toast.makeText(
            this,
            "El apartado " + editT.hint.toString().replace(":", "")
                .lowercase() + " no puede estar vacio.",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun mostrarToast(editT: EditText, verificado: Boolean) {
        if (verificado) {
            Toast.makeText(
                this,
                "El apartado " + editT.hint.toString().replace(":", "")
                    .lowercase() + " no es válido.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun textoRadioButton(): String {
        radioGroup = findViewById(R.id.radioGroup)
        val selectedRadioId = radioGroup.checkedRadioButtonId
        radioboton = findViewById(selectedRadioId)
        return radioboton.text.toString()
    }

    private fun textoCheckbox(): String {
        checkboxLectura = findViewById(R.id.checkBoxLectura)
        checkboxMusica = findViewById(R.id.cbMusic)
        checkboxDeporte = findViewById(R.id.cBDeporte)
        checkboxArte = findViewById(R.id.cBArt)
        //meto los checkbox en una facil para recorrerles mas facilmente
        val arraycb = arrayOf(checkboxLectura, checkboxMusica, checkboxDeporte, checkboxArte)
        var stringAficiones = ""
        for (cb in arraycb) {
            if (cb.isChecked) {
                stringAficiones += cb.text.toString().lowercase() + ", "
            }
        }

        if (stringAficiones == "") {
            // si esta vacio devuelve el texto de que no ha escogido ningun hobbie
            return getString(R.string.no_has_escogido_ningun_hobbie)

        } else {

            /*
            poner el texto bonito, la ultima "," por un "."
            El texto original seria: "musica, arte, deporte, "
            Tras el if y el trim quedaria asi
            "musica, arte, deporte."
             */
            val charstringAficiones = stringAficiones.trim().toCharArray()
            if (charstringAficiones[charstringAficiones.size - 1] == ',') {
                charstringAficiones[charstringAficiones.size - 1] = '.'
                /*
                si sigue habiendo otra "," es por que las opcion son 2 o mas, aqui se cambia la penultima "," del string original para que sea un " y"
                el texto actual seria "musica, arte, deporte."
                No existe un replaceLast, asi que damos la vuelta al string, usamos un replaceFirst, que sería la ultima "," pero ha pasado a ser la primera, y hacemos el replace
                El texto quedaria:
                "musica, arte y deporte."
                Solamente se ejecutará si sigue habiendo "," en el string
                 */
                stringAficiones = String(charstringAficiones)
                if ("," in stringAficiones) {
                    stringAficiones = stringAficiones.reversed().replaceFirst(",", "y")
                    stringAficiones = stringAficiones.reversed().replace("y", " y")
                }

            }
            return stringAficiones
            // Primera terroristada para cambiar la ultima "," por una " y"
//            var indexComa: Int = 0
//            for (i in 0 until stringAficiones.length - 1) {
//                if (charstringAficiones[i] == ',') {
//                    indexComa = i
//                }
//            }
//            charstringAficiones[indexComa] = 'y'
//            stringAficiones = String(charstringAficiones)
//            stringAficiones = stringAficiones.replace("y", " y")

        }


    }

    /**
     * funcion para dar valor al textoSpinner
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        textoSpinner = spinnerPaises.selectedItem.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_MOSTRAR_RESULTADO, mostrarResText.text.toString())

    }
}