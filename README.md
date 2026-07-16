# Spindle

A music widget for Android that turns whatever you're playing into a spinning
record you can control — styled as a vintage vinyl deck or a futuristic player,
your choice.

By **markFoundry**.

## What it is

Spindle reads your phone's active media session — Spotify, YouTube Music,
podcasts, anything that publishes media controls — and shows the current track
as a rotating disc with play/pause, next, previous, and seek. A home-screen
widget mirrors it. Pick a **vibe** in settings; the app and widget share the
theme.

- **No login, no API keys, no account.** Spindle uses Android's system media
  controls, not any streaming service's API.
- **Works for free and premium accounts** of any supported player.
- **One-time setup:** grant Notification access — the same permission
  smartwatches use to control media.

## Vibes

- **Analog** — black-lacquer vinyl, brushed tonearm, warm brass.
- **Flux** — holographic glass, orbiting light, electric cyan.

## How it works

Android exposes the active media session through `NotificationListenerService`
+ `MediaSessionManager`. Spindle discovers the current `MediaController`, reads
its metadata (title, artist, album art) and playback state, and issues
transport commands (play/pause/skip/seek). No streaming-service SDK, no OAuth.

The in-app player renders the full spinning disc (Compose animation). The
home-screen widget shows the album art with a continuously spinning accent ring
(an indeterminate progress drawable — the one animation widgets allow) plus
transport buttons; a full disc spin isn't possible inside a widget.

> **Android only.** iOS provides no public API for a third-party app to read or
> control another app's media session (`MediaRemote` is private and gets apps
> rejected from the App Store), so this concept cannot exist on iOS.

## Stack

- **Kotlin + Jetpack Compose** — in-app player, animated disc, theming
- **Jetpack Glance / RemoteViews** — home-screen widget
- **`NotificationListenerService` + `MediaSessionManager`** — media access

## Status

Early scaffolding. Design direction and architecture decided; implementation in
progress.
