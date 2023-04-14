package com.safetyheads.data.`object`

object YouTubeApi {
    const val YOUTUBE_API_BASE_URL = "https://www.googleapis.com/youtube/v3/"
    const val YOUTUBE_CHANNEL_ID = "UCE8z3lh2LxX7WDKjli3gleg"
    const val YOUTUBE_API_PART_VIDEO = "snippet"
    const val YOUTUBE_API_PART_CHANNEL = "snippet,brandingSettings"
    const val YOUTUBE_API_PART_PLAYLISTS = "snippet,contentDetails"
    const val YOUTUBE_API_PART_PLAYLIST_ITEMS = "snippet"
    const val YOUTUBE_API_ORDER = "date"
    const val YOUTUBE_API_VIDEO_MAX_RESULTS = 1
    const val YOUTUBE_API_PLAYLIST_ITEMS_MAX_RESULT = 50
    const val YOUTUBE_DEFAULT_URL = "https://i.ytimg.com/vi/DqO01e_FWD4/hqdefault_live.jpg"
}