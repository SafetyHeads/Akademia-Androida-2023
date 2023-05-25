package com.safetyheads.data.network.repository

import com.safetyheads.akademiaandroida.domain.entities.Media
import com.safetyheads.akademiaandroida.domain.entities.Video
import com.safetyheads.akademiaandroida.domain.repositories.VideoRepository
import com.safetyheads.data.network.mapper.VideoMapper
import com.safetyheads.data.network.mapper.VideosMapper
import com.safetyheads.data.network.service.YouTubeService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class VideoRepositoryImpl(
    private val youTubeService: YouTubeService,
    private val videoMapper: VideoMapper,
    private val videosMapper: VideosMapper,
    private val apiKey: String
) : VideoRepository {

    override suspend fun getVideo(previousFilmDate: String): Flow<Result<Video>> =
        flow {
            val retrofitYouTubeVideo =
                youTubeService
                    .getVideo(
                        apiKey,
                        YouTubeApiConsts.YOUTUBE_CHANNEL_ID,
                        YouTubeApiConsts.YOUTUBE_API_PART_VIDEO,
                        YouTubeApiConsts.YOUTUBE_API_ORDER,
                        YouTubeApiConsts.YOUTUBE_API_VIDEO_MAX_RESULTS,
                        previousFilmDate
                    )
            val video = videoMapper.transform(retrofitYouTubeVideo)
            emit(Result.success(video))
        }.catch { exception ->
            emit(Result.failure(exception))
        }

    override suspend fun getAllVideos(): Flow<Result<List<Media>>> =
        callbackFlow {
            val retrofitYouTubeVideos =
                youTubeService
                    .getAllVideos(
                        apiKey,
                        YouTubeApiConsts.YOUTUBE_CHANNEL_ID,
                        YouTubeApiConsts.YOUTUBE_API_PART_VIDEO,
                        YouTubeApiConsts.YOUTUBE_API_ORDER,
                        YouTubeApiConsts.YOUTUBE_API_PLAYLIST_ITEMS_MAX_RESULT,
                    )
            val videos = videosMapper.transform(retrofitYouTubeVideos)
            trySend(Result.success(videos))
            awaitClose {  }
        }.catch { exception ->
            emit(Result.failure(exception))
        }

}