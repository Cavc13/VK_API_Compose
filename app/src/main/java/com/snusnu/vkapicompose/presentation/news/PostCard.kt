package com.snusnu.vkapicompose.presentation.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.snusnu.vkapicompose.R
import com.snusnu.vkapicompose.domain.FeedPost
import com.snusnu.vkapicompose.domain.StatisticItem
import com.snusnu.vkapicompose.domain.StatisticType
import com.snusnu.vkapicompose.ui.theme.DarkRed

@Composable
fun CardPost(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onViewItemClickListener: (StatisticItem) -> Unit,
    onShareItemClickListener: (StatisticItem) -> Unit,
    onCommentItemClickListener: ( StatisticItem) -> Unit,
    onLikeItemClickListener: (StatisticItem) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            PostHeader(feedPost)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = feedPost.contentText,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            AsyncImage(
                model = feedPost.contentImageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentDescription = "post image",
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            PostStatistics(
                statistics = feedPost.statistics,
                onViewItemClickListener = onViewItemClickListener,
                onShareItemClickListener = onShareItemClickListener,
                onCommentItemClickListener = onCommentItemClickListener,
                onLikeItemClickListener = onLikeItemClickListener,
                isLiked = feedPost.isLiked
            )
        }
    }
}

@Composable
private fun PostHeader(
    feedPost: FeedPost
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = feedPost.communityImageUrl,
            contentDescription = "avatar of community",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = feedPost.communityName,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = feedPost.publicationDate,
                color = MaterialTheme.colors.onSecondary
            )
        }
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "settings",
            tint = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
private fun PostStatistics(
    statistics: List<StatisticItem>,
    onViewItemClickListener: (StatisticItem) -> Unit,
    onShareItemClickListener: (StatisticItem) -> Unit,
    onCommentItemClickListener: (StatisticItem) -> Unit,
    onLikeItemClickListener: (StatisticItem) -> Unit,
    isLiked: Boolean
) {
    Row {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(
                iconResId = R.drawable.ic_eye,
                text = formatStatisticCount(viewsItem.count),
                onItemClickListener = {
                    onViewItemClickListener(viewsItem)
                }
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val shareItem = statistics.getItemByType(StatisticType.SHARES)
            IconWithText(
                iconResId = R.drawable.ic_share,
                text = formatStatisticCount(shareItem.count),
                onItemClickListener = {
                    onShareItemClickListener(shareItem)
                }
            )
            val commentItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(
                iconResId = R.drawable.ic_comment,
                text = formatStatisticCount(commentItem.count),
                onItemClickListener = {
                    onCommentItemClickListener(commentItem)
                }
            )
            val likeItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(
                iconResId = if(isLiked) R.drawable.ic_like_set else R.drawable.ic_like,
                text = formatStatisticCount(likeItem.count),
                onItemClickListener = {
                    onLikeItemClickListener(likeItem)
                },
                tint = if(isLiked) DarkRed else MaterialTheme.colors.onSecondary
            )
        }
    }
}

private fun formatStatisticCount(count: Int): String {
    return if (count > 100_000) {
        String.format("%sK", (count/ 1_000))
    } else if (count > 1_000) {
        String.format("%.1fK", (count / 1_000f))
    } else {
        count.toString()
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalAccessException("This type is unknown")
}

@Composable
private fun IconWithText(
    iconResId: Int,
    text: String,
    onItemClickListener: () -> Unit,
    tint: Color = MaterialTheme.colors.onSecondary
) {
    Row(
        modifier = Modifier.clickable {
            onItemClickListener()
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(iconResId),
            contentDescription = "settings",
            tint = tint
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSecondary
        )
    }
}