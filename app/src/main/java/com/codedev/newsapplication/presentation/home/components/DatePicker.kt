package com.codedev.newsapplication.presentation.home.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@Composable
fun CustomDatePicker(
    dialogState: MaterialDialogState,
    datePicked: MutableState<LocalDate>
) {
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {
        datepicker(
            initialDate = datePicked.value
        ) { date ->
            datePicked.value = date
        }
    }
}