package com.markfoundry.spindle.skin

/** All skins available to the user. Add new skins here as they're built. */
val AllSkins: List<PlayerSkin> = listOf(
    TurntableSkin,
    FluxSkin,
)

fun defaultSkin(): PlayerSkin = AllSkins.first()

fun skinById(id: String?): PlayerSkin = AllSkins.firstOrNull { it.id == id } ?: defaultSkin()
