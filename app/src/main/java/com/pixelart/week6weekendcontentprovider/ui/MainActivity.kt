package com.pixelart.week6weekendcontentprovider.ui

import android.app.AlertDialog
import android.app.Dialog
import android.database.Cursor
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.View
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pixelart.week6weekendcontentprovider.R
import com.pixelart.week6weekendcontentprovider.adapter.MemoAdapter
import com.pixelart.week6weekendcontentprovider.data.entity.Memo
import com.pixelart.week6weekendcontentprovider.utils.URI_LOADER
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_layout.view.*

class MainActivity : AppCompatActivity(){


    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MemoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MemoAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.getMemos().observe(this, Observer {
            adapter.submitList(it)
        })
        initItemTouchHelper()
    }

    private fun initItemTouchHelper(){
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(holder: ViewHolder, direction: Int) {

                if (direction == ItemTouchHelper.LEFT)
                {
                    mainViewModel.delete(adapter.getMemoAt(holder.adapterPosition))
                }
                else if (direction == ItemTouchHelper.RIGHT && holder.adapterPosition != RecyclerView.NO_POSITION)
                {
                    val title = adapter.getMemoAt(holder.adapterPosition).title
                    val body = adapter.getMemoAt(holder.adapterPosition).memoBody
                    val id = adapter.getMemoAt(holder.adapterPosition).id
                    adapter.notifyItemChanged(holder.adapterPosition)
                    updateMemoDialog(id!!, title, body).show()

                }
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun onClick(view: View){
        addMemoDialog().show()
    }

    private fun addMemoDialog(): Dialog {
        return this.let {
            val builder = AlertDialog.Builder(it)
            val view = layoutInflater.inflate(R.layout.dialog_layout, null)
            var title: String
            var body: String

            view.tvDialogTitle.text = "Add Memo"

            builder.setView(view)
                .setPositiveButton("Save"){dialog, _ ->
                    if (view.etMemoTitle.text!!.isNotBlank() && view.etMemoBody.text!!.isNotBlank()) {
                        title = view.etMemoTitle.text.toString()
                        body = view.etMemoBody.text.toString()

                        mainViewModel.insert(Memo(title = title, memoBody = body))
                        Toast.makeText(this, "Memo Saved", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(this, "Fields must not be blank", Toast.LENGTH_SHORT).show()
                        addMemoDialog().show()
                    }
                }
                .setNegativeButton("Cancel"){dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        }
    }

    private fun updateMemoDialog(id: Long, title: String, body: String): Dialog{
        return this.let {
            val builder = AlertDialog.Builder(it)
            val view = layoutInflater.inflate(R.layout.dialog_layout, null)
            view.tvDialogTitle.text = "Update Memo"
            view.etMemoTitle.setText(title)
            view.etMemoBody.setText(body)

            builder.setView(view)
                .setPositiveButton("Save"){dialog, _ ->
                    if (view.etMemoTitle.text!!.isNotBlank() && view.etMemoBody.text!!.isNotBlank()) {

                        val etTitle = view.etMemoTitle.text.toString()
                        val etBody = view.etMemoBody.text.toString()

                        mainViewModel.update(Memo(id, etTitle, etBody))
                        Toast.makeText(this, "Memo Updated", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    } else {
                        Toast.makeText(this, "Fields must not be blank", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel"){dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        }
    }
}
