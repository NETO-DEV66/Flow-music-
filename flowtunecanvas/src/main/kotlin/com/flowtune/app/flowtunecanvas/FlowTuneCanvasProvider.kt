package com.flowtune.app.flowtunecanvas

import com.flowtune.app.canvas.CanvasArtwork
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class flowtuneCanvasManifest(val items: List<flowtuneCanvasItem> = emptyList())

@Serializable data class flowtuneCanvasItem(val song: String, val artist: String, val url: String)

object flowtuneCanvasProvider {
  private const val BASE_URL = "https://raw.githubusercontent.com/NETO-DEV66/Flow-tune-/main/app/canvas.json"

  private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    explicitNulls = false
  }

  private val client by lazy {
    HttpClient(OkHttp) {
      install(ContentNegotiation) { json(json) }
      install(HttpTimeout) {
        connectTimeoutMillis = 12_000
        requestTimeoutMillis = 18_000
        socketTimeoutMillis = 18_000
      }
      install(ContentEncoding) {
        gzip()
        deflate()
      }
      install(HttpCache)
      expectSuccess = false
    }
  }

  private data class CacheEntry(
    val value: flowtuneCanvasManifest?,
    val expiresAtMs: Long,
  )

  private var manifestCache: CacheEntry? = null
  // Cache TTL 1 minute (re-fetches json index every minute max for instant updates)
  private val ttlMs = 60_000L

  private suspend fun fetchManifest(): flowtuneCanvasManifest? {
    val currentCache = manifestCache
    if (currentCache != null && currentCache.expiresAtMs > System.currentTimeMillis()) {
      return currentCache.value
    }

    return try {
      val manifest: flowtuneCanvasManifest = client.get(BASE_URL).body()

      manifestCache = CacheEntry(value = manifest, expiresAtMs = System.currentTimeMillis() + ttlMs)
      manifest
    } catch (e: Exception) {
      null
    }
  }

  suspend fun getBySongArtist(
    song: String,
    artist: String,
  ): CanvasArtwork? {
    if (song.isBlank() || artist.isBlank()) return null

    val manifest = fetchManifest() ?: return null

    val target =
      manifest.items.firstOrNull { item ->
        val matchSong =
          song.contains(item.song, ignoreCase = true) || item.song.contains(song, ignoreCase = true)
        val matchArtist =
          artist.contains(item.artist, ignoreCase = true) ||
            item.artist.contains(artist, ignoreCase = true)
        matchSong && matchArtist
      }

    if (target != null) {
      return CanvasArtwork(
        name = target.song,
        artist = target.artist,
        videoUrl = target.url,
        animated = target.url
      )
    } else {
      return null
    }
  }
}
