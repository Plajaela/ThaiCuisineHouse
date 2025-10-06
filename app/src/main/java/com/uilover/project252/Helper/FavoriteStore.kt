package com.uilover.project252.Helper

import android.content.Context
import android.util.Log

object FavoriteStore {
    private const val FILE = "favorites_prefs"
    private const val KEY  = "favorite_ids"

    private fun prefs(ctx: Context) =
        ctx.getSharedPreferences(FILE, Context.MODE_PRIVATE)

    fun getIds(ctx: Context): MutableSet<String> =
        prefs(ctx).getStringSet(KEY, emptySet())?.toMutableSet() ?: mutableSetOf()

    fun isFavorite(ctx: Context, id: Int): Boolean =
        prefs(ctx).getStringSet(KEY, emptySet())?.contains(id.toString()) == true

    fun toggle(ctx: Context, id: Int) {
        val set = getIds(ctx)                    // <- ชุดใหม่ (ไม่ใช่ reference เดิม)
        val s = id.toString()
        if (set.contains(s)) set.remove(s) else set.add(s)
        prefs(ctx).edit().putStringSet(KEY, set).apply()
        Log.d("Favorite", "after toggle id=$id -> $set")
    }

    fun getAll(ctx: Context): Set<String> =
        prefs(ctx).getStringSet(KEY, emptySet()) ?: emptySet()
}
