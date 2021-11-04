package com.paf.synthlib.common.views

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.paf.synthlib.ui.theme.Orange
import com.paf.synthlib.ui.theme.SynthLibTheme

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Orange)
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    SynthLibTheme() {
        LoadingView()
    }
}