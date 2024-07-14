package com.group4.edusurveyor.module.detail.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.group4.edusurveyor.R
import com.group4.edusurveyor.databinding.DetailActivityBinding
import com.group4.edusurveyor.module.detail.viewmodel.DetailViewModel
import com.group4.edusurveyor.module.main.model.SurveyRecordModel
import com.group4.edusurveyor.utils.getSerializable
import com.group4.edusurveyor.repository.local.helper.SurveyDatabase


class DetailActivity: AppCompatActivity() {

    private lateinit var binding: DetailActivityBinding
    private var viewModel = DetailViewModel(SurveyDatabase(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = DetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.new_survey)

        binding.contentView.btnSubmit.setOnClickListener {
            viewModel.modelData?.let {
                wrapModel(it.title, false)
                val count = viewModel.updateData(viewModel.modelData!!)
                if(count > 0) {
                    Toast.makeText(
                        this,
                        getString(R.string.record_has_been_updated),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.failed_update_record),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }?: run {
                showDialogSubmit()
            }
        }

        setAutoPrefill()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun setAutoPrefill() {
        val dataSurvey = intent.getSerializable("survey_record", SurveyRecordModel::class.java)
        dataSurvey?.let { model ->
            viewModel.modelData = model

            val ctView = binding.contentView
            ctView.etFullname.setText(model.fullName)
            if(model.gender == ctView.rbMale.text.toString()) {
                ctView.rbMale.isChecked = true
            } else {
                ctView.rbfemale.isChecked = true
            }
            ctView.etAge.setText(model.age.toString())
            val univs = resources.getStringArray(R.array.universities)
            ctView.spUniversity.setSelection(univs.indexOf(model.university))
            ctView.etMajor.setText(model.major)
            ctView.etJob.setText(model.job)

            supportActionBar?.title = model.title
            ctView.btnSubmit.text = getString(R.string.save)
        }
    }

    private fun showDialogSubmit() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Submit Survey")

        val viewInflated: View = LayoutInflater.from(this)
            .inflate(R.layout.dialog_submit_survey, binding.root, false)

        val etTitle = viewInflated.findViewById<EditText>(R.id.etSubmitAs)

        builder.setView(viewInflated)

        builder.setPositiveButton(android.R.string.ok
        ) { dialog, _ ->
            dialog.dismiss()
            wrapModel(etTitle.text.toString(), true)
            viewModel.modelData?.let {
                viewModel.insertNewData(it)
                Toast.makeText(this, getString(R.string.survey_has_been_recorded), Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        builder.setNegativeButton(android.R.string.cancel
        ) { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun wrapModel(title: String, isNew: Boolean) {
        var ctView = binding.contentView
        viewModel.modelData = SurveyRecordModel(
            if(isNew) 0 else viewModel.modelData?.id!!,
            title,
            ctView.etFullname.text.toString(),
            ctView.rgGender.findViewById<RadioButton>(ctView.rgGender.checkedRadioButtonId).text.toString(),
            ctView.etAge.text.toString().toInt(),
            ctView.spUniversity.selectedItem.toString(),
            ctView.etMajor.text.toString(),
            ctView.etJob.text.toString(),
            if(isNew) viewModel.getCurrentDateTime() else viewModel.modelData?.createAt!!,
            viewModel.getCurrentDateTime()
        )
    }
}