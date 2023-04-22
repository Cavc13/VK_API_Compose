package com.snusnu.vkapicompose.ui.theme.screens.instagram_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snusnu.vkapicompose.R

@Composable
fun InstagramProfileCard(
    viewModel: InstagramViewModel
) {
    val isFollowed = viewModel.isFollowing.observeAsState(false)

    Card(
        modifier = Modifier.padding(8.dp),
        backgroundColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 4.dp
        ),
        border = BorderStroke(1.dp, MaterialTheme.colors.onBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_instagram),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(8.dp),
                )
                UserStatistics(title = "Posts", value = "6,950")
                UserStatistics(title = "Followers", value = "436M")
                UserStatistics(title = "Following", value = "76")
            }
            Text(
                text = "Instagram",
                fontSize = 32.sp,
                fontFamily = FontFamily.Cursive
            )
            Text(
                text = "#YoursToMake",
                fontSize = 14.sp
            )
            Text(
                text = "www.facebook.com/emotional_health",
                fontSize = 14.sp
            )
            FollowButton(isFollowed = isFollowed.value) {
                viewModel.changeFollowingStatus()
            }
        }
    }
}

@Composable
private fun FollowButton(
    isFollowed: Boolean,
    clickListener: () -> Unit
) {
    Button(
        onClick = {
            clickListener()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (!isFollowed) {
                MaterialTheme.colors.onBackground
            } else {
                MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
            }
        )
    ) {
        Text(
            text = if (isFollowed) "Unfollow" else "Follow",
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
private fun UserStatistics(
    title: String,
    value: String
) {
    Column(
        modifier = Modifier.height(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = value,
            fontSize = 24.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )
    }
}