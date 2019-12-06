package com.djay.espressotest

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initEditText()
        initSpinner()
        initList()
    }

    /**
     * Initialize EditText with a TextWatcher
     */
    private fun initEditText() {
        editText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!isStringOnlyAlphabet(s.toString()))
                    editText.error = getString(R.string.only_alphabets_allowed)
                else
                    editText.error = null
            }
        })
    }

    /**
     * Initialize ListView with OnItemClickListener
     */
    private fun initList() {
        val listItem = resources.getStringArray(R.array.ListItem)
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                Toast.makeText(
                    this@MainActivity,
                    listItem[position] + " clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    /**
     * Initialize Spinner with ViewTypes-array and OnItemSelectedListener
     */
    private fun initSpinner() {
        val viewTypes = resources.getStringArray(R.array.ViewType)
        val adapter = ArrayAdapter(this, R.layout.spinner_item, viewTypes)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                resetCurrentView(viewTypes[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                //
            }
        }
    }

    /**
     * Resets current selected View visibility based on the selected spinner item
     */
    private fun resetCurrentView(viewType: String) {
        var view: View = editText
        when (viewType) {
            getString(R.string.editText) -> view = editText
            getString(R.string.textView) -> view = textView
            getString(R.string.checkBox) -> view = checkbox
            getString(R.string.listView) -> view = listView
        }
        editText.visibility = View.GONE
        textView.visibility = View.GONE
        checkbox.visibility = View.GONE
        listView.visibility = View.GONE
        view.visibility = View.VISIBLE
        tvSelection.text = getString(R.string.view_is_selected, viewType)
    }


    /**
     * Checks if String only contains alphabet
     */
    private fun isStringOnlyAlphabet(str: String?): Boolean {
        return (str != ""
                && str != null
                && str.matches(Regex("^[a-zA-Z]*$")))
    }
}
