package com.tunevol.app.models

import com.music.innertube.models.YTItem
import com.tunevol.app.db.entities.LocalItem

data class SimilarRecommendation(
  val title: LocalItem,
  val items: List<YTItem>,
)
