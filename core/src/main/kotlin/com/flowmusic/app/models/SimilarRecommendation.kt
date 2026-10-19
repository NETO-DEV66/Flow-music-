package com.flowmusic.app.models

import com.music.innertube.models.YTItem
import com.flowmusic.app.db.entities.LocalItem

data class SimilarRecommendation(
  val title: LocalItem,
  val items: List<YTItem>,
)
