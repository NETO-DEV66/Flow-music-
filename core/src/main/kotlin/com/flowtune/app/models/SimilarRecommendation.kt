package com.flowtune.app.models

import com.music.innertube.models.YTItem
import com.flowtune.app.db.entities.LocalItem

data class SimilarRecommendation(
  val title: LocalItem,
  val items: List<YTItem>,
)
