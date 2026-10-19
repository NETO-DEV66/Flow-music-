package com.flowmusic.dsp.core.models

import kotlinx.serialization.Serializable

@Serializable
enum class FilterType {
  PK,
  LSC,
  HSC,
  LPQ,
  HPQ
}
