package com.example.movies_app.presentation.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.movies_app.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VideoPlayerScreen(
    navController: NavController,
    videoId: String
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = "Trailer",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.White
            )
        }, navigationIcon = {
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable { navController.popBackStack() }
                    .padding(start = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .size(32.dp)
                    .background(Color(0xFF252836)),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }, actions = {
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable { navController.popBackStack() }
                    .padding(start = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .size(32.dp)
                    .background(Color(0xFF252836)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = "Back",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0XFF1F1D2B)))
    }) {
        Column(
            modifier = Modifier
                .background(Color(0xFF1F1D2B))
                .padding(top = it.calculateTopPadding(), bottom = it.calculateTopPadding())
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val lifecycleOwner = LocalLifecycleOwner.current

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(180.dp)
                    .width(327.dp)
                    .background(Color.Black)
            ) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        val youTubePlayerView = YouTubePlayerView(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            lifecycleOwner.lifecycle.addObserver(this)
                        }

                        youTubePlayerView.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                Log.d("videoLink", "onReady: $videoId")
                                youTubePlayer.loadVideo(videoId, 0f)

                            }
                        })

                        youTubePlayerView
                    }
                )


            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "The Batman",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.White, modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.calendar),
                    contentDescription = "Calendar Icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Release Date: ",
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Text(
                    text = "March 2, 2022",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "|", color = Color.Gray, fontSize = 12.sp)

                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.film),
                    contentDescription = "Calendar Icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Action",
                    color = Color.White,
                    fontSize = 12.sp
                )

            }
            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Synopsis",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.White, modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "THE BATMAN is an edgy, action-packed" +
                        " \nthriller that depicts Batman in his early \n years, " +
                        "struggling to balance rage with\n righteousness as he investigates a\n disturbing" +
                        " mystery that has terrorized\n Gotham. Robert Pattinson delivers a raw, intense portrayal of\n" +
                        " Batman as a disillusioned, desperate \nvigilante" +
                        " awakened by the realization.. More",
                color = Color.White,
                textAlign = TextAlign.Start
            )
        }


    }


}

