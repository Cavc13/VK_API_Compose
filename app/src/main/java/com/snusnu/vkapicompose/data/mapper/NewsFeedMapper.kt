package com.snusnu.vkapicompose.data.mapper

import com.snusnu.vkapicompose.data.model.ResponseDto
import com.snusnu.vkapicompose.data.model.WallCommentsDTO
import com.snusnu.vkapicompose.data.model.WallCommentProfileDTO
import com.snusnu.vkapicompose.data.model.WallCommentContentDTO
import com.snusnu.vkapicompose.domain.entity.FeedPost
import com.snusnu.vkapicompose.domain.entity.PostComment
import com.snusnu.vkapicompose.domain.entity.StatisticItem
import com.snusnu.vkapicompose.domain.entity.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

class NewsFeedMapper {

    fun mapResponseToPosts(responseDto: ResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()

        val posts = responseDto.newsFeedContentDto.posts
        val groups = responseDto.newsFeedContentDto.groups

        posts.forEach{ post ->
            val group  = groups.find { it.id == post.communityId.absoluteValue } ?: return@forEach
            val countLikes = post.likes?.userLikes
            val feedPost = FeedPost(
                id = post.id,
                communityId = post.communityId,
                communityName = group.name,
                publicationDate = mapTimestampToDate(post.date),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(type = StatisticType.LIKES, post.likes?.count ?: 0),
                    StatisticItem(type = StatisticType.VIEWS, post.views?.count ?: 0),
                    StatisticItem(type = StatisticType.COMMENTS, post.comments?.count ?: 0),
                    StatisticItem(type = StatisticType.SHARES, post.reposts?.count ?: 0)
                ),

                isLiked = if(countLikes != null ) countLikes > 0 else false
            )
            result.add(feedPost)
        }
        return result
    }

    fun mapWallCommentsResponseToPostComments(response: WallCommentsDTO) : List<PostComment> {
        val commentsList: List<WallCommentContentDTO> = response.wallComments
        val profilesList: List<WallCommentProfileDTO> = response.wallCommentProfiles
        val postCommentsList = mutableListOf<PostComment>()

        for(comment in commentsList) {
            if (comment.textComment.isBlank()) continue
            val author = profilesList.find {
                it.idCommentProfile == comment.idOwnerComment
            } ?: continue

            val postComment = PostComment(
                id = comment.id,
                authorName = "${author.firstNameCommentProfile} ${author.lastNameCommentProfile}",
                authorAvatarUrl = author.photoCommentProfile,
                comment = comment.textComment,
                publicationDate = mapTimestampToDate(comment.dateComment)
            )
            postCommentsList.add(postComment)
        }

        return postCommentsList
    }

    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp * 1_000)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
    }
}