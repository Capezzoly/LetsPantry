package com.modolodavide.letspantry.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.modolodavide.letspantry.R
import com.modolodavide.letspantry.data.Ingrediente
import java.util.*

class IngredienteAdapter(private val context: Context, val listaIngrediente: List<Ingrediente>, val ingredienteListener: IngredienteListener) :
    RecyclerView.Adapter<IngredienteAdapter.ViewHolder>() {
    //recupero i vari elementi del singolo item Elemento
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.IngredienteNome)
        val quantita: TextView = itemView.findViewById(R.id.IngredienteQuantita)
        val scadenza: TextView = itemView.findViewById(R.id.IngredienteScadenza)
        val linea: View = itemView.findViewById(R.id.lineData)
    }

    override fun getItemCount(): Int = listaIngrediente.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_ingrediente, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingrediente = listaIngrediente[position]

        //recupero il giorno attuale
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH) + 1 //+1 perché in Kotlin i mesi della classe Calendar vanno da 0 a 11
        val year = c.get(Calendar.YEAR)

        with(holder) {
            //imposto i vari dati dell'ingrediente
            nome.text = ingrediente.nome
            val quantitatext = ingrediente.quantita.toString() + " u"
            quantita.text = quantitatext
            val scadenzatext =
                ingrediente.scadenzaGiorno.toString() + " " + getMeseITA(ingrediente.scadenzaMese)
            scadenza.text = scadenzatext
            //in base alla scadenza dell'ingrediente l'aspetto sarà differente
            if (ingrediente.scadenzaAnno < year || (ingrediente.scadenzaAnno >= year && ingrediente.scadenzaMese < month) || (ingrediente.scadenzaAnno >= year && ingrediente.scadenzaMese == month && ingrediente.scadenzaGiorno < day)) {
                linea.setBackgroundColor(ContextCompat.getColor(context, R.color.colorReddo))
                scadenza.setTextColor(ContextCompat.getColor(context, R.color.colorReddo))
            }
            else {
                linea.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
                scadenza.setTextColor(ContextCompat.getColor(context, R.color.colorText1))
            }

            holder.itemView.setOnLongClickListener {
                ingredienteListener.onIngredienteListener(ingrediente, holder.layoutPosition)
                true
            }

        }
    }

    interface IngredienteListener{
        fun onIngredienteListener(ingrediente: Ingrediente, position: Int)
    }

    //funzione per tradurre il mese in italiano
    private fun getMeseITA(mese: Int): String {
        return when (mese) {
            1 -> "Gen"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "Mag"
            6 -> "Giu"
            7 -> "Lug"
            8 -> "Ago"
            9 -> "Set"
            10 -> "Ott"
            11 -> "Nov"
            12 -> "Dic"
            else -> "Error"
        }
    }

}