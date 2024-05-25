package com.example.budgetmanager

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {
    lateinit var budgetDisplay:TextView
    lateinit var budgetDialog: Dialog
    lateinit var expensesDialog: Dialog
    lateinit var expensesDisplay:TextView
    lateinit var balanceDisplay:TextView
    lateinit var deleteDialog: Dialog

    private lateinit var myRecyclerView: RecyclerView
    private lateinit var transection: ArrayList<Transection>
    private lateinit var Db:AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        budgetDisplay=findViewById(R.id.budget)
        expensesDisplay=findViewById(R.id.expense)
        balanceDisplay=findViewById(R.id.balance)
        myRecyclerView=findViewById(R.id.transectionRv)

        deleteDialog= Dialog(this)
        deleteDialog.setContentView(R.layout.delete_msg_dialog)
        deleteDialog.findViewById<TextView>(R.id.textView8).setOnClickListener {
            deleteDialog.dismiss()
        }
        deleteDialog.findViewById<TextView>(R.id.textView9).setOnClickListener {
            deleteTable()
            deleteDialog.dismiss()
        }

        budgetDialog= Dialog(this)
        budgetDialog.setContentView(R.layout.add_budget_layout)
        budgetDialog.findViewById<Button>(R.id.addBudgetBtn).setOnClickListener {
          if (budgetDialog.findViewById<EditText>(R.id.budgetInput).text.toString().isNotEmpty()) {
              val amount=budgetDialog.findViewById<EditText>(R.id.budgetInput).text
              insertToDb("New budget",amount.toString().toInt())
              budgetDialog.dismiss()
              amount.clear()
          }
        }
        expensesDialog= Dialog(this)
        expensesDialog.setContentView(R.layout.add_expenses_layout)
        expensesDialog.findViewById<Button>(R.id.addExpBtn).setOnClickListener {
            if (expensesDialog.findViewById<EditText>(R.id.ExpAmount).text.toString().isNotEmpty()){
                val title=expensesDialog.findViewById<EditText>(R.id.ExpTitle).text
                val amount=expensesDialog.findViewById<EditText>(R.id.ExpAmount).text
                insertToDb(title.toString(),-amount.toString().toInt())
                expensesDialog.dismiss()
                title.clear()
                amount.clear()
            }
        }

        findViewById<ImageView>(R.id.addBudget).setOnClickListener {
            budgetDialog.show()
        }
        findViewById<ImageView>(R.id.addExpenses).setOnClickListener {
            expensesDialog.show()
        }

        transection= arrayListOf()
        myRecyclerView.layoutManager=LinearLayoutManager(this)
        Db=Room.databaseBuilder(this,AppDatabase::class.java,"transection").build()

        fatchAll()

        findViewById<ImageView>(R.id.deleteAll).setOnClickListener {
            deleteDialog.show()
        }

    }
    fun insertToDb(title:String, amount:Int){
        GlobalScope.launch {
            Db.transectionDao().insert(Transection(0,title,amount))
        }
    }
    fun fatchAll(){
        Db.transectionDao().getAll().observe(this,{
            transection= it as ArrayList<Transection>
            updateUi()
            val adapter=TransectionAdapter(transection)
            myRecyclerView.adapter=adapter
            adapter.setItemClickListener(object :TransectionAdapter.onItemClickListener{
                override fun onItemClicked(position: Int) {
                    GlobalScope.launch {
                        Db.transectionDao().delete(transection[position])
                    }
                    fatchAll()
                }

            })
        })

    }
    fun updateUi(){
        val totalBalance=transection.map { it.amount }.sum()
        val totalExpenses=transection.filter { it.amount<0 }.map { it.amount }.sum().absoluteValue
        val totalBudget=transection.filter { it.amount>=0 }.map { it.amount }.sum()

        budgetDisplay.text=totalBudget.toString()
        expensesDisplay.text=totalExpenses.toString()
        if (totalBalance<0){
            balanceDisplay.setTextColor(ContextCompat.getColor(this,R.color.remove))
        }
        balanceDisplay.text=totalBalance.toString()

    }
    fun deleteTable(){
        GlobalScope.launch {
            Db.transectionDao().deleteAll()
        }
    }

}