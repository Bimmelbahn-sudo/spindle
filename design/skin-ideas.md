# Spindle — Skin & Theme Ideation Catalog

Spindle reads whatever's playing via the system media session
(`NotificationListenerService` + `MediaSessionManager`) and renders it as a
skinned player — album art, track info, and transport controls — in-app and
on a home-screen widget. The signature feature is the skin picker in
Settings: a complete visual identity, from vintage-retro to
premium-futuristic. This doc catalogs the *next* wave of skins, beyond the
two already shipped (**Turntable**, **Flux**), organized by category, plus
cross-cutting theme axes and a build-order recommendation.

## How to read this

- **34 new skin concepts** across the five requested categories. Turntable
  and Flux are called out briefly at the top of their category for
  continuity, but aren't respecced — they're already built.
- Palette values for Turntable/Flux below are pulled directly from the
  shipped code (`TurntableSkin.kt`, `design/index.html`), not reinvented, so
  this doc lines up with what's actually running.
- **Widget ceiling:** Glance/RemoteViews render a static layout — the only
  native "in motion" trick is the indeterminate circular progress drawable
  the README already describes for Turntable/Flux's widget (a spinning
  accent ring, decoupled from real rotation speed or position). Below,
  that's called "spinner anchor" wherever a skin has a natural circle to
  hang it on. Everything else ships as a well-composed static frame plus
  transport tap targets. A couple of skins (Slate, Grid) can instead use a
  real *determinate* `ProgressBar`, which RemoteViews does support, for an
  accurate — if unanimated — seek position.
- **Audio-reactive caveat:** Spindle reads media-session metadata and
  playback state, not raw audio. Anywhere a skin below "reacts to the
  music" (Pulse, Amp's bars), read that as *simulated* reactive — driven by
  playback position or a procedural loop — unless real per-app audio
  capture via Android's `Visualizer` API is confirmed feasible later; it's
  commonly restricted for capturing another app's audio session.
- Every skin gets a one-word product name in the `Turntable` / `Flux`
  naming convention (Mixtape, Jewel, Wheel, Orb…) so it reads like a real
  settings-picker entry, not a category label.

## Contents

- [At a glance](#at-a-glance)
- [1. Retro Physical Media](#1-retro-physical-media)
- [2. Retro Digital](#2-retro-digital)
- [3. Futuristic](#3-futuristic)
- [4. Minimal / Modern](#4-minimal--modern)
- [5. Playful / Novelty](#5-playful--novelty)
- [Theme Axes](#theme-axes)
- [Roadmap: What to Build Next](#roadmap-what-to-build-next)

## At a glance

| # | Skin | Category | Vibe | Compose effort | Widget spin anchor |
|---|------|----------|------|-----------------|---------------------|
| — | Turntable *(shipped)* | Retro physical media | Flat-retro vinyl deck | — | Yes (record label) |
| — | Flux *(shipped)* | Futuristic | Holographic glass disc | — | Yes (ring) |
| 1 | Mixtape | Retro physical media | Cassette / mixtape culture | Medium | Yes (2 reels) |
| 2 | Reel | Retro physical media | Studio reel-to-reel | Medium | Yes (2 reels) |
| 3 | Cartridge | Retro physical media | 8-track car stereo | Easy | No |
| 4 | Jewel | Retro physical media | Discman / portable CD | Medium | Yes (disc window) |
| 5 | Caddy | Retro physical media | MiniDisc | Easy | No |
| 6 | Boombox | Retro physical media | 80s boombox | Medium | Yes (reels) |
| 7 | Horn | Retro physical media | Antique gramophone | Hard | Yes (label) |
| 8 | Jukebox | Retro physical media | 50s diner jukebox | Hard | Partial |
| 9 | Dial | Retro physical media | Transistor radio | Easy | No |
| 10 | Wheel | Retro digital | iPod Classic click-wheel | Medium | Yes (wheel edge) |
| 11 | Amp | Retro digital | Winamp-style skin | Easy | No |
| 12 | Rack | Retro digital | Hi-fi receiver + VU meters | Medium | No |
| 13 | Dash | Retro digital | Car head-unit | Easy | No |
| 14 | Nano | Retro digital | Early flash MP3 player | Easy | Partial |
| 15 | Brick | Retro digital | Feature-phone player | Easy | No |
| 16 | Pages | Retro digital | Teletext / videotext | Easy–Medium | No |
| 17 | Visor | Futuristic | Tactical cyberpunk HUD | Hard | Partial |
| 18 | Frost | Futuristic | Glassmorphic, daylight | Medium | No |
| 19 | Orb | Futuristic | Ambient breathing orb | Easy | Yes (glow ring) |
| 20 | Pulse | Futuristic | Spectrum / waveform reactive | Hard* | No |
| 21 | Mono | Futuristic | Phosphor terminal | Easy | No |
| 22 | Nova | Futuristic | Particle / quantum core | Hard | No |
| 23 | Slate | Minimal / modern | Clean Material baseline | Easy | Determinate bar |
| 24 | Headline | Minimal / modern | Big kinetic typography | Easy | No |
| 25 | Ink | Minimal / modern | Monochrome halftone | Medium | No |
| 26 | Bleed | Minimal / modern | Full-bleed cover art | Easy | No |
| 27 | Grid | Minimal / modern | Swiss / editorial grid | Easy | Determinate bar |
| 28 | Zine | Minimal / modern | Paper / scrapbook zine | Medium | No |
| 29 | Pixel | Playful / novelty | 8-bit game sprite | Medium | No (icon swap) |
| 30 | Vapor | Playful / novelty | Vaporwave | Medium | No |
| 31 | Arcade | Playful / novelty | Neon arcade cabinet | Medium | Partial |
| 32 | Collage | Playful / novelty | Sticker scrapbook | Medium | No |
| 33 | Plush | Playful / novelty | Claymation mascot | Medium | No (pose swap) |
| 34 | Panel | Playful / novelty | Comic pop-art panel | Medium | No |

*Pulse's effort is rated Hard mainly for the audio-access dependency, not the rendering itself — see its entry.

---

## 1. Retro Physical Media

> **Shipped, for reference:** **Turntable** — flat-retro vinyl deck; coral
> `#E8645A` body, navy `#2B356A` deck panel, cream `#F2E7D0` label ring,
> blue `#33429C` tonearm. Record spins via an `Animatable` rotation while
> playing; the tonearm lifts off via a `graphicsLayer` tilt on pause.

### Mixtape

*A cassette deck where the album art is the hand-scrawled sticker on the tape shell.*

- **Concept:** cassette/Walkman — the art print sits inside the little cassette window like a mixtape label.
- **Era / vibe:** early 80s–90s home-taping culture; warm, personal, slightly lo-fi.
- **Album art:** cropped square onto the cassette's paper label between the two reel windows, corners "taped down."
- **Motion:** two reels spin at slightly different simulated radii (tape visibly builds up on one side as it "plays"), reusing the same rotating-circle + `Animatable` trick as Turntable's record, just doubled; pause freezes both reels mid-turn, a hair of slack visibly goes taut.
- **Controls:** chunky piano-key transport buttons (rewind/play/FF) below the shell; seek = drag a tiny tape-counter wheel or tap along the visible tape path.
- **Palette:** `#2E2E2E` `#E8B4B8` `#F4E9D8` `#C1272D`
- **Compose effort:** Medium — same rotating-circle + `Animatable` primitive as Turntable's record, instanced twice at small scale, plus a `Path` bezier for the tape ribbon between reels.
- **Widget:** static deck face; each reel hub hosts the indeterminate spinner ring while playing (two small rings instead of one big one — a distinct widget silhouette); prev/play/next as tap zones.

### Reel

*Open-reel studio deck — big spinning reels with a VU-meter soul.*

- **Concept:** reel-to-reel tape machine; album art printed on the box lid propped behind the reels.
- **Era / vibe:** 60s–70s recording-studio / audiophile.
- **Album art:** square card visible leaning behind the reels, like a tape box lid.
- **Motion:** two large reels spin with visible persistence-of-vision blur at speed; pausing eases into a gentle coast-down (not an abrupt stop) via a decelerating `tween`.
- **Controls:** large chrome toggle switches for transport; a rotary tension knob doubles as the scrub dial for seek.
- **Palette:** `#6B4226` `#C9C9C9` `#D62828` `#161616`
- **Compose effort:** Medium — reuses the rotation primitive at a bigger radius; the coast-down deceleration curve and tape-path wobble are the only genuinely new animation work.
- **Widget:** wide static deck face (best on 4×2+ widget sizes); a spinner ring per reel while playing; collapse to one "hero" reel if space is tight.

### Cartridge

*The chunky, four-program 8-track — simplest of the tape family.*

- **Concept:** 8-track cartridge; art is the cartridge's wide paper face label.
- **Era / vibe:** early–mid 70s car-stereo Americana.
- **Album art:** letterboxed rectangle across the cartridge's front face, exactly like a real 8-track label.
- **Motion:** a small cutaway window shows a capstan wheel turning continuously (one looping tape, no rewind — just like the real format); a soft "click" nudge on program change; pause stops the capstan.
- **Controls:** four wide "program" tabs (1–4) across the top double as coarse seek/chapter jumps; a single rocker switch for play/stop.
- **Palette:** `#E3B23C` `#BF5B04` `#6B8E4E` `#3E2E23`
- **Compose effort:** Easy — mostly a flat rounded-rect cartridge shape; the only moving part is one small capstan circle, cheapest of the tape trio.
- **Widget:** fully static cartridge face; no natural spin anchor (the mechanism is hidden in the real hardware too) — rely on a small blinking "playing" dot instead.

### Jewel

*A Discman-era CD spinning behind a jewel-case-clear lid.*

- **Concept:** portable CD player; album art is printed on the disc's top face, exactly like a real CD.
- **Era / vibe:** late 80s–2000s Discman/portable-CD nostalgia — the bridge between analog and digital.
- **Album art:** wraps the disc's visible face (donut-shaped, fading out near the center hole), seen through a hinged lid window.
- **Motion:** disc spins fast (a CD's linear read speed reads quicker than vinyl's 33/45 rpm) with a rainbow specular sheen sweeping across it as it turns; a laser sled translates outward along a straight rail as the track progresses — literally visualizing seek position; pause decelerates the spin to a stop and freezes the sled.
- **Controls:** small circular button cluster beside the lid (matches real Discman layout) for prev/play/next; seek = the laser sled's position along its rail, draggable.
- **Palette:** `#E9ECEF` `#2A6F97` `#7FD8E8` `#1B1B1F`
- **Compose effort:** Medium — reuses roughly 80% of Turntable's record-rotation code verbatim at a faster duration; new work is a `sweepGradient` sheen pass and a linear sled `translate` keyed to `state.progress`.
- **Widget:** the disc window is a perfect circle — drop the indeterminate accent ring straight into it, one of the strongest widget fits in the catalog; sled position redraws as a static snapshot on each periodic refresh.

### Caddy

*MiniDisc's sealed shell — all the format's minimalism, none of the visible disc.*

- **Concept:** MiniDisc player; since the disc itself is hidden inside its plastic caddy, album art lives on the device's small LCD instead.
- **Era / vibe:** late 90s Sony-flavored tech-minimalism.
- **Album art:** shown as a tiny, low-res LCD thumbnail rather than anywhere physical — the one skin in this category where art is deliberately *not* on a spinning object.
- **Motion:** a segment-style disc glyph on the LCD "spins" (cycles a handful of rotation frames) while playing; a small shutter icon slides; pause freezes the glyph and blinks a "PAUSE" indicator, like the real device's display.
- **Controls:** minimalist edge-mounted buttons plus a decorative "hold" lock switch; seek = a digital counter readout, tap-and-hold to scrub.
- **Palette:** `#4B3F72` `#1B1B2F` `#9BE8E8` `#D9D9D9`
- **Compose effort:** Easy — no big spinning canvas at all, just a static device shell plus a tiny animated glyph; cheapest of the physical-media set alongside Cartridge.
- **Widget:** fully static (the real device is a sealed shell, so nothing looks "wrong" here) — LCD glyph shown as a play/pause icon swap; the least "alive" widget, but the most honest to the source hardware.

### Boombox

*Twin cassette decks, a chrome grille, and a strut-worthy shoulder handle.*

- **Concept:** boombox/ghetto-blaster; art appears on a cassette inserted in one of the twin decks, or centered on a small EQ display.
- **Era / vibe:** 80s hip-hop / street-culture boombox.
- **Album art:** on a small cassette label in the right deck (reuses Mixtape's label treatment at a smaller scale), mirrored by a blank deck on the left for symmetry.
- **Motion:** cassette reels spin (Mixtape's rig, reused); a small EQ/spectrum readout between the decks bounces (simulated); a chrome highlight glints across the grille on track change; pause flatlines the EQ and stops the reels.
- **Controls:** big rubberized transport buttons centered between the speaker grilles; a volume-knob-styled dial doubles as the seek scrub.
- **Palette:** `#111111` `#E8E8E8` `#FFD60A` `#E63946`
- **Compose effort:** Medium — mostly an assembly of already-solved pieces (Mixtape's reels + a bar-graph EQ); the work is layout/composition rather than new rendering technique.
- **Widget:** cassette reel(s) get the spinner-ring trick; speaker grilles render as a static repeating-dot mesh; EQ bars shown frozen (widgets can't animate a bar graph).

### Horn

*A brass gramophone horn with a tonearm that tracks the record like the genuine mechanical article.*

- **Concept:** gramophone/phonograph; art sits on the record label glimpsed under the tonearm, horn is pure ornament (and the biggest set-piece in the catalog).
- **Era / vibe:** 1900s–1920s Victorian/antique parlor.
- **Album art:** small circular label at record center, viewed past the horn's throat.
- **Motion:** the record turns beneath a tonearm that slowly, continuously traverses inward as playback progresses — the arm's radial distance from center *is* the seek position, a genuinely skeuomorphic progress indicator; the needle subtly bobs on simulated grooves; pause lifts the whole arm off the record, an unambiguous "stopped" affordance.
- **Controls:** no modern buttons — small brass side-levers for prev/next; play/pause is the needle-drop/lift gesture itself; seek = drag the tonearm directly across the record.
- **Palette:** `#3E2723` `#C99A2E` `#EDE0C8` `#7B241C`
- **Compose effort:** Hard — the tonearm's progress-linked polar path needs real trig (angle derived from `state.progress`), plus a bespoke horn silhouette via cubic-Bezier `Path`s; the most bespoke geometry of any physical-media skin.
- **Widget:** the record label hosts the spinner ring; the tonearm angle can be computed from `progress` and redrawn as a static snapshot each refresh — a nice "frozen mid-motion" touch for very little extra cost.

### Jukebox

*Diner-neon jukebox with a swinging record-select arm behind curved glass.*

- **Concept:** jukebox; art shown on the record visible through the glass dome, plus a lit "now selecting" title card.
- **Era / vibe:** 1950s American diner.
- **Album art:** disc visible spinning behind curved glass, echoing Turntable's disc but framed in chrome and neon.
- **Motion:** the busiest skin in the catalog — a mechanical arm swings to "select" a record on track change, the disc drops onto the platter and spins, neon tubes around the dome pulse/chase continuously; pause dims the neon and stops the disc.
- **Controls:** illuminated push-buttons in a numeric keypad row (like real song-select buttons); seek = a chase-light strip along the dome edge that fills as it plays.
- **Palette:** `#F2E9DC` `#C1272D` `#2EC4B6` `#D9D9D9`
- **Compose effort:** Hard — the most assembled "scene" of any skin (dome, arm mechanism, chase lights, keypad); each part is simple alone but there are more of them than anywhere else in the catalog.
- **Widget:** collapses hard — show a static front-glass view with the spinner ring on the visible disc only; drop the arm mechanism and chase lights (rendered as a static "on" glow) entirely.

### Dial *(bonus)*

*A wood-cased transistor radio where seeking feels like tuning a station.*

- **Concept:** transistor/tube radio; album art shown through the radio's small perforated fabric-grille window as a soft, backlit rectangle.
- **Era / vibe:** 1950s–60s valve-radio warmth.
- **Album art:** glows softly behind a woven speaker-cloth texture, more felt than seen — the most abstracted art treatment in this category.
- **Motion:** a tuning needle glides across a lit frequency scale as the seek position changes; a warm backlight "breathes" gently while playing; pause stops the needle and dims the backlight to amber standby.
- **Controls:** large knurled knobs for prev/next (twist = skip); a horizontal frequency-scale ruler is the seek control (drag = tune/scrub) — reuses the tuning metaphor throughout.
- **Palette:** `#8C6D46` `#E8C87E` `#B33F40` `#2B2B2B`
- **Compose effort:** Easy — a flat wood-case rect, a `drawLine` needle rotating/translating across a static scale, and a soft glow behind a repeating-texture grille; no big canvas geometry.
- **Widget:** no circular disc to anchor a spinner; show the tuning needle's position as a static snapshot per refresh, plus a soft "on-air" backlight glow to imply life.

---

## 2. Retro Digital

### Wheel

*The click-wheel now-playing screen, muscle memory included.*

- **Concept:** iPod Classic; art shown small, on a screen — not on a spinning disc at all.
- **Era / vibe:** mid-2000s Apple minimalism.
- **Album art:** square thumbnail on the device's little "now playing" screen, exactly like the real iPod UI.
- **Motion:** no ambient motion on the chrome itself; a tiny animated equalizer glyph on-screen dances while playing and freezes mid-bar when paused; a soft highlight ring travels around the wheel's rim when touched.
- **Controls:** the click-wheel itself — circular drag = seek (angle traveled maps to `seekTo(fraction)`), center click = play/pause, left/right zones of the wheel = prev/next, matching real iPod muscle memory exactly.
- **Palette:** `#FFFFFF` `#D9D9D9` `#2B2B2B` `#5A5A5D`
- **Compose effort:** Medium — the wheel is a `drawArc` ring with angle-based drag-gesture detection (`detectDragGestures` + `atan2` math), plus a small screen composited with the iPod's chrome font; the interaction model is new even though no shape is complex.
- **Widget:** the wheel's circular edge is a natural spinner-ring anchor; the screen shows static art + track text — very legible at small widget sizes, one of the best widget candidates in the whole catalog.

### Amp

*A flat, skinnable faceplate straight out of 1999 Winamp culture.*

- **Concept:** Winamp-style skin; art appears as a small thumbnail window, title scrolls in a marquee.
- **Era / vibe:** late-90s PC/internet skin culture.
- **Album art:** tiny square window in the corner of a flat rectangular faceplate — deliberately minor, type does the work.
- **Motion:** classic spectrum-analyzer bars bounce (simulated — see the audio-reactive caveat above) and a scrolling marquee title loops continuously while playing; pause drops the bars to a flat baseline and freezes the marquee mid-scroll.
- **Controls:** tiny flat pixel-button row (rewind/play/pause/stop/FF), exactly like the real skin; seek = a thin horizontal slider with a draggable diamond-shaped nub.
- **Palette:** `#0D0D0D` `#00FF41` `#3B3B58` `#8A8AA8`
- **Compose effort:** Easy — flat rectangles, a monospace/LED-style font, and a procedural bar-height animation; zero rotation or custom path geometry, the cheapest build in the entire catalog.
- **Widget:** fully static faceplate; bars frozen at a fixed "idle" pattern (or omitted entirely); no circular anchor exists, so play/pause relies purely on an icon-state swap.

### Rack

*A component hi-fi receiver with real analog VU needles.*

- **Concept:** hi-fi stereo receiver; art shown on a small window/screen in the rack face, flanked by needle meters.
- **Era / vibe:** 70s–80s home hi-fi rack.
- **Album art:** small square window centered in the rack fascia, between the two VU meters.
- **Motion:** left/right VU needles swing to simulated levels with a bit of overshoot-and-settle physics (spring-damped, not linear); pause lets both needles fall to zero and rest.
- **Controls:** chunky mechanical-style piano-key buttons with retro iconography; a rotary volume-styled knob doubles as the seek scrub.
- **Palette:** `#1C1C1C` `#B0B0B0` `#FFB000` `#D62828`
- **Compose effort:** Medium — the rack chrome is flat rects, but needle physics (rotation with spring overshoot via `Animatable` + a spring `AnimationSpec`) for two independent channels is the one genuinely fiddly bit.
- **Widget:** needles frozen at a fixed, good-looking angle (e.g., −3dB) as a snapshot; no spinner anchor, but reads great as a still "hi-fi" image regardless.

### Dash

*An aftermarket car head-unit with a glowing tuner-style seek bar.*

- **Concept:** car stereo head-unit; art shown on the small dashboard display, DIN-slot sized.
- **Era / vibe:** 90s–2000s aftermarket car audio (Pioneer/Kenwood-style).
- **Album art:** small rectangle on the head-unit's display, next to a live EQ bar graph.
- **Motion:** EQ bars dance (simulated) and a scanning glow sweeps across a frequency-ruler-style seek bar; pause dims the backlight slightly (an "ignition-off" cue) and drops the bars.
- **Controls:** flat DIN-style buttons; seek reuses the tuning-dial metaphor from Dial — drag along a ruler to scrub, styled like tuning a radio frequency.
- **Palette:** `#0B0C10` `#00F5D4` `#FF6B00` `#1F2833`
- **Compose effort:** Easy — flat dashboard rects, a bar-graph EQ, and one sweeping-glow gradient animation; no rotation, no custom paths.
- **Widget:** fully static dashboard face; EQ bars frozen, tuner needle shown at a static position; no spinner anchor (rectangular display).

### Nano

*A candy-colored flash-memory MP3 player from the format's Wild West years.*

- **Concept:** early flash/USB-stick MP3 player; art shown on a postage-stamp-sized screen.
- **Era / vibe:** early-2000s Rio/Creative-Zen-era flash players, translucent candy plastic.
- **Album art:** tiny, low-res screen thumbnail — the device is barely bigger than the screen itself.
- **Motion:** a small bouncing "now playing" icon or 3-bar mini-equalizer animates on the screen; a thin ring of light around the device's edge fills as the track plays; pause freezes both.
- **Controls:** a tiny 4-way nav pad or a side scroll-wheel; seek = the light ring around the device edge, drag to scrub.
- **Palette:** `#FF6EC7` `#7CFC00` `#FFD23F` `#F5F5F5`
- **Compose effort:** Easy — a small static device silhouette plus a couple of tiny animated glyphs; the translucent-plastic look is just a semi-transparent fill with a soft inner highlight, no shaders needed.
- **Widget:** the device silhouette is small and roughly rounded — the edge ring can host the spinner trick; otherwise a straightforward static device-face snapshot.

### Brick

*An indestructible 2000s feature phone playing polyphonic-era MP3s.*

- **Concept:** feature-phone music player; art rendered as dithered, low-res monochrome pixel art — degraded on purpose to match the era's tiny LCD.
- **Era / vibe:** early-2000s candybar-phone nostalgia.
- **Album art:** posterized/dithered down to the phone's native LCD resolution and palette — the one skin where "worse" fidelity is the correct fidelity.
- **Motion:** a deliberately choppy, low-frame-rate (~4–5fps) animated icon, on-theme rather than a limitation; signal-bar and battery glyphs sit in the corner for flavor; pause swaps in a blocky "PAUSED" banner.
- **Controls:** a physical numeric-keypad-style D-pad (2/8 = prev/next, 5 = play/pause); seek = a segmented signal-bar-style meter (5 blocks fill in).
- **Palette:** `#9EAD86` `#2B2E1F` `#C8D6B0` `#4A4A42`
- **Compose effort:** Easy — intentionally low-fidelity monochrome bitmap rendering (draw small, scale up with nearest-neighbor) is simpler than smooth vector work; genuinely one of the cheapest skins to build.
- **Widget:** loses almost nothing by being static, since the reference hardware itself was never smooth — arguably the most "honest" widget in the whole catalog.

### Pages *(bonus)*

*A teletext/videotext page as the now-playing screen.*

- **Concept:** broadcast teletext; art is abstracted away entirely into blocky mosaic "text-mode" character art.
- **Era / vibe:** 70s–90s broadcast-television teletext/videotex services.
- **Album art:** rendered as a coarse grid of solid-color block characters approximating the art's dominant regions — closer to ANSI art than a photo.
- **Motion:** a page-number counter ticks, a cursor blinks, and text "types in" line by line on track change; pause holds the cursor solid and shows a static "PAGE HELD" style banner.
- **Controls:** buttons styled as chunky primary-color remote-control keys (red/green/yellow/blue "fastext" keys map to prev/play/next/menu); seek = a page-number field you can key digits into or drag.
- **Palette:** `#000000` `#FFD400` `#00A651` `#00AEEF`
- **Compose effort:** Easy–Medium — the block-mosaic art conversion is a simple grid-sampling algorithm (average color per cell, no real dithering needed); everything else is flat rects and a monospace bitmap font.
- **Widget:** a static "page" snapshot reads perfectly well frozen (teletext was never animated beyond a blinking cursor anyway) — genuinely at home as a still widget.

---

## 3. Futuristic

> **Shipped, for reference:** **Flux** — holographic-glass deck; near-black
> `#05060F` background, cyan `#2FE6FF` / violet `#7C5CFF` / magenta
> `#FF59D6` conic-gradient ring, orbiting light dots. The ring spins via the
> same `Animatable` rotation pattern as Turntable's record, orbit dots
> counter-rotate, and reduced-motion users get a still frame.

### Visor

*A tactical AR heads-up display, not a holographic nightclub.*

- **Concept:** cyberpunk HUD; art projected as a scanline-textured holo-panel with a targeting reticle, deliberately more "military readout" than Flux's iridescent glass.
- **Era / vibe:** near-future tactical/augmented-reality HUD, kept distinct from Flux's palette on purpose.
- **Album art:** floats inside a bracketed HUD frame with scanlines and chromatic-aberration fringing at the edges.
- **Motion:** a targeting reticle orbits the art, data readouts tick and count, scanlines sweep continuously; pause triggers a glitch/static freeze-frame instead of a smooth stop — the reticle stutters and locks.
- **Controls:** minimal-chrome glyph buttons that only fully render when tapped (an "invisible until needed" HUD feel); seek = a radial arc scrubber traced around the HUD ring.
- **Palette:** `#05070A` `#39FF6A` `#FFB300` `#FF3B30`
- **Compose effort:** Hard — glitch/scanline/chromatic-aberration effects are best done with an AGSL `RuntimeShader` (API 33+), with a plainer fallback needed on older devices; the most shader-dependent skin in the catalog.
- **Widget:** static HUD snapshot with the scanline texture pre-baked in; the targeting-reticle ring is a natural spinner anchor.

### Frost

*Soft frosted glass over a pastel blur — daylight futurism instead of Flux's deep-space one.*

- **Concept:** glassmorphic panel; art is blurred hugely into a full-bleed backdrop with a small sharp thumbnail on top.
- **Era / vibe:** 2020s "big tech" soft-UI futurism — light, airy, daytime-friendly, a deliberate contrast to Flux's darkness.
- **Album art:** a heavily blurred version fills the background behind a frosted card; a crisp small copy sits in the card itself.
- **Motion:** the frosted card breathes very subtly (scale ~1.0 to 1.02) and a specular highlight sweeps across the glass surface while playing; pause stills the sweep and slightly desaturates the card.
- **Controls:** soft pill-shaped frosted buttons floating over the blur; seek = a thin frosted capsule track.
- **Palette:** `#F4F7F9` `#A8DADC` `#457B9D` + translucent white overlays
- **Compose effort:** Medium — real-time blur needs `Modifier.graphicsLayer` + `RenderEffect`/`Modifier.blur` (reliable from API 31/12+, needs a pre-blurred-bitmap fallback below that), plus a specular sweep gradient; mostly composition work once blur is sorted.
- **Widget:** blur must be pre-baked into a static bitmap (widgets can't blur in real time) — ships as a static frosted-card snapshot; no spinner anchor, relies on a subtle static highlight instead.

### Orb

*Album art stops being a picture and becomes light.*

- **Concept:** ambient glowing orb; instead of showing the art, its dominant colors are extracted and become the orb's gradient — the most abstracted "album art" treatment in the whole catalog.
- **Era / vibe:** calm/wellness futurism, a "focus mode" aesthetic — deliberately the quietest skin on offer.
- **Album art:** not shown literally at all (maybe a tiny reference swatch) — its palette *becomes* the visual.
- **Motion:** the orb pulses/breathes in size and glow intensity on a slow, gentle loop while playing; when paused it contracts to a small, dim resting glow, like it's asleep.
- **Controls:** almost no visible chrome — swipe left/right on the orb for prev/next, tap to play/pause; seek = a faint ring around the orb's circumference that thins or thickens with position.
- **Palette:** `#2D1B4E` `#FF9F6B` `#6C5CE7` `#FFD9A0`
- **Compose effort:** Easy — a single animated `Brush.radialGradient` circle with a scale/alpha "breathe" loop; probably the single cheapest build in the entire catalog relative to its visual payoff.
- **Widget:** the orb itself doubles as the spinner anchor (a glow ring around it while playing, gone when paused) — one of the most natural widget fits available, and the flagship "quiet mode" pick.

### Pulse

*The now-playing screen as a spectrum analyzer — if the platform will let it react to real audio.*

- **Concept:** waveform/spectrum-reactive display; art sits dimmed behind a full-width animated bar or radial spectrum.
- **Era / vibe:** modern "music visualizer" futurism — a slicker, mobile-native take on Winamp-style visualization.
- **Album art:** full-bleed, dimmed behind the bars so the visualizer reads clearly on top.
- **Motion:** bars/rings animate continuously while playing, still on pause. **Caveat:** Spindle reads session metadata, not raw audio — genuine amplitude/FFT data for *another app's* stream generally needs the `Visualizer` API, which is commonly restricted for capturing a different app's session. Treat this as "simulated-reactive" (driven by playback position or a procedural noise loop) unless real capture access is confirmed feasible.
- **Controls:** buttons live as notches/gaps within the bar visualization itself; seek = drag anywhere across the whole display, it doubles as the scrubber.
- **Palette:** `#05080D` `#FF006E` `#FB5607` `#3A86FF` + a full rainbow sweep across all bars
- **Compose effort:** Hard overall — rendering animated bars is Easy on its own, but the rating reflects the audio-access dependency risk; shipping a version that's honestly "procedurally lively" rather than "reactive" is the safe default.
- **Widget:** bars frozen at a static snapshot pattern; no spinner anchor (rectangular); a periodic refresh can swap in a new frozen frame each update for a cheap illusion of life.

### Mono

*A single-color phosphor terminal — deliberately the least visually rich skin, on purpose.*

- **Concept:** minimal monochrome terminal readout; art rendered as ASCII/character-mosaic art.
- **Era / vibe:** green-phosphor CRT terminal / DOS-era computing, filtered through a sci-fi lens.
- **Album art:** converted to an ASCII-art mosaic (luminance-per-cell mapped to characters), redrawn character-by-character on track change.
- **Motion:** a blinking text cursor, a scrolling "now playing" log line typed out like a boot sequence; pause holds the cursor solid with an idle `>` prompt.
- **Controls:** bracket-glyph keyboard-style buttons (`[<<]` `[>]` `[>>]`); seek = a text progress bar built from characters (`[####------]`).
- **Palette:** `#0C0F0A` `#33FF66` (amber alt: `#1A1200` / `#FFB000`)
- **Compose effort:** Easy — monospace text rendering, a blinking-cursor `Animatable` alpha, and a simple luminance-to-character conversion for the art; no custom path geometry.
- **Widget:** a static text/ASCII snapshot; a blinking cursor isn't reliable in a widget, so render it solid; a periodic refresh can rewrite one line to imply activity cheaply.

### Nova *(bonus)*

*A swirling particle core — Flux's more explosive, more literal-sci-fi cousin.*

- **Concept:** particle-field/quantum-core visualization; art's colors seed a field of drifting light particles around a bright core.
- **Era / vibe:** deep-space/energy-core sci-fi — bigger and busier than Flux, for users who want maximum "futuristic" density.
- **Album art:** a small glowing core at the center, its dominant color sampled from the art; the art image itself isn't shown directly.
- **Motion:** dozens of small particles drift and orbit the core, occasionally flaring brighter in bursts, while playing; on pause, particles slow and settle into a static ring, core dims.
- **Controls:** glyph buttons embedded in the particle field, appearing as brighter fixed points; seek = a thin arc of particles around the core that "fills in" with position.
- **Palette:** `#05050A` `#FFFFFF` `#FFD700` `#7B2FF7`
- **Compose effort:** Hard — a real per-particle system (position/velocity state for dozens of points, redrawn every frame) is the most simulation-heavy skin in the catalog; needs care to stay performant on `Canvas`.
- **Widget:** static "frozen field" snapshot only; no clean spinner anchor given the scattered (non-ring) layout — the least widget-friendly of the futuristic set.

---

## 4. Minimal / Modern

### Slate

*The deliberately unopinionated baseline — clean Material 3, nothing extra.*

- **Concept:** clean Material now-playing screen; art shown as a standard rounded-square cover thumbnail.
- **Era / vibe:** contemporary Android/Material You — timeless rather than era-coded.
- **Album art:** a straightforward rounded-corner square, no treatment applied.
- **Motion:** a shared-element-style crossfade on track change; Material's own built-in play↔pause icon morph; a simple linear progress fill — nothing exotic, that's the point.
- **Controls:** standard Material icon-button row plus a linear seek slider.
- **Palette:** `#FFFBFE` `#1C1B1F` `#6750A4` `#E7E0EC`
- **Compose effort:** Easy — close to `Theme.kt`'s existing default `MaterialTheme` scaffold already in the codebase; almost no bespoke rendering needed.
- **Widget:** the most natively "widget-shaped" skin — a standard RemoteViews/Glance layout that can use a real *determinate* `ProgressBar` for an accurate (if non-animated) seek position, which most other skins can't.

### Headline

*Huge, kinetic type does the job album art usually does.*

- **Concept:** big-typography now-playing screen; art shrinks to a tiny accent or disappears — the track title *is* the visual.
- **Era / vibe:** contemporary editorial/poster design, content-forward.
- **Album art:** reduced to a small corner swatch, or omitted entirely in favor of a color pulled from it.
- **Motion:** the track title kinetically slides/scales in on change and subtly pulses in weight while playing, like a slow heartbeat on the baseline; stills completely on pause.
- **Controls:** oversized bold letterform/glyph "buttons"; seek = the title's own underline filling like a progress bar.
- **Palette:** `#000000` `#FFFFFF` `#FF3B30` (accent ideally sampled live from the art)
- **Compose effort:** Easy — big text layout plus a simple weight/scale animation; no image-heavy rendering at all.
- **Widget:** one of the easiest and most scalable widgets in the catalog — a static bold-text card reads perfectly at any widget size, from a 1×1 up.

### Ink

*Grayscale, halftone-dot editorial print.*

- **Concept:** monochrome now-playing screen; art desaturated and screen-printed with a halftone dot pattern.
- **Era / vibe:** newspaper/zine print design.
- **Album art:** converted to pure grayscale, then rendered through a halftone dot-grid (dot size follows local luminance) — a screen-print effect, not a filter.
- **Motion:** the halftone dot sizes subtly breathe/shift while playing, like "wet ink"; stills completely ("dry") on pause.
- **Controls:** buttons drawn as woodcut/stamp-style icons; seek = a hand-drawn-looking sketchy line that "inks in" as progress advances.
- **Palette:** `#111111` `#FFFFFF` `#888888` `#000000`
- **Compose effort:** Medium — halftone rendering needs a custom per-cell luminance sample and dot draw loop (or a `RuntimeShader` for smoother results) rather than a simple color filter.
- **Widget:** ages perfectly as a still image — halftone print never needed motion to read as designed; fully static widget with no loss of identity.

### Bleed

*The cover art fills the entire screen, edge to edge.*

- **Concept:** full-bleed cover art; the photo itself is the whole background, chrome is minimal overlay.
- **Era / vibe:** contemporary streaming-app "art-forward" design, timeless.
- **Album art:** fills the entire screen edge-to-edge, darkened/blurred at the edges only enough for text legibility.
- **Motion:** a slow ambient Ken-Burns pan-and-zoom drifts across the cover while playing; freezes the instant it's paused.
- **Controls:** a minimal translucent icon row floats over a bottom scrim; seek = a thin line at the very bottom edge.
- **Palette:** scrim `#000000` at partial opacity, text `#FFFFFF`, accent sampled live from the art
- **Compose effort:** Easy — the Ken-Burns effect is just an `Animatable` scale/translate on an `Image`; the simplest "premium-feeling" skin in the catalog for the effort involved.
- **Widget:** one of the most native widget patterns already common on Android — "full-bleed art plus overlay text" is exactly what a static widget does best; no loss going from animated to static.

### Grid

*International Typographic Style — Helvetica, red, and a ruler line.*

- **Concept:** Swiss/editorial grid layout; art occupies one fixed grid cell, everything else is disciplined typography.
- **Era / vibe:** mid-century Swiss graphic design.
- **Album art:** cropped tightly into a fixed-ratio grid cell (e.g., the left third of the screen), never centered or floating.
- **Motion:** a thin red ruler line fills left-to-right as a progress indicator; type weight shifts subtly on track change; the ruler freezes on pause.
- **Controls:** grid-aligned typographic labels ("PREV / PLAY / NEXT" in caps) rather than icons; seek = the red ruler line itself, draggable.
- **Palette:** `#FFFFFF` `#000000` `#E30613` `#F2F2F2`
- **Compose effort:** Easy — pure typographic grid layout (Compose `Row`/`Column` weights) plus one ruler-line animation; no custom drawing needed.
- **Widget:** static grid layout; the red ruler can use a real determinate `ProgressBar` for an accurate seek snapshot, like Slate.

### Zine

*A cut-and-paste scrapbook page — washi tape, a "polaroid," a rubber stamp.*

- **Concept:** paper/zine collage; art appears as a taped-in photo cutout with rough edges.
- **Era / vibe:** DIY punk-zine/scrapbook craft aesthetic.
- **Album art:** shown as a "polaroid" photo with visible washi-tape corners, slightly rotated off-grid.
- **Motion:** the photo cutout wiggles very subtly like paper caught in a breeze while playing; tape corners flutter faintly; everything stills completely on pause.
- **Controls:** buttons styled as rubber stamps you "press" (a squash effect on tap); seek = a torn strip of paper tape that unrolls with progress.
- **Palette:** `#EFE6D8` `#2B2B2B` `#C1272D` `#F4F1EA`
- **Compose effort:** Medium — torn-paper/tape edges need custom jagged `Path` shapes and drop shadows for cutout depth; the idle wiggle is a small extra animation layer.
- **Widget:** ages fine as a still image — the scrapbook aesthetic is inherently a flat pinned photo, so a static widget loses nothing.

---

## 5. Playful / Novelty

### Pixel

*An 8-bit game cartridge's now-playing screen.*

- **Concept:** retro-game/pixel skin; art dithered down to a tiny fixed palette, like a game sprite.
- **Era / vibe:** NES/Game Boy-era 8-bit gaming.
- **Album art:** dithered to a 4-shade (Game Boy-style) or limited 8-bit palette mosaic — literally pixelated down, not just styled to look pixelated.
- **Motion:** a 2–4 frame sprite-style bob loop plays while "running" (playing); a chunky pixel-block progress bar fills in squares; pause freezes on frame one, exactly like a paused game.
- **Controls:** on-screen D-pad + A/B button sprites; seek = the chunky pixel-block meter, tap-to-jump.
- **Palette:** `#0F380F` `#306230` `#8BAC0F` `#9BBC0F`
- **Compose effort:** Medium — needs a small pixel-art rendering pipeline (nearest-neighbor upscaling, sprite-sheet frame stepping, palette-limited dithering of the art) rather than smooth vector drawing.
- **Widget:** a static sprite frame works well — pick the "idle" frame for paused and an "action" frame for playing; a 2-state icon swap is true to how retro game icons behave anyway.

### Vapor

*Pink-cyan gradient grid horizon, a statue bust, endless scanlines.*

- **Concept:** vaporwave; art duotone-filtered and placed on a tilted 3D "pedestal" plane above a receding grid.
- **Era / vibe:** vaporwave internet-art aesthetic — an 80s-mall-nostalgia pastiche, not a real historical era.
- **Album art:** duotone-filtered (pink/cyan) and mounted on a tilted plane like a museum placard.
- **Motion:** an infinite scrolling perspective grid recedes toward the horizon continuously; the art "statue" plane slowly rotates; pause stops the grid scroll and the rotation.
- **Controls:** buttons rendered as chrome 3D "bust" icons (a vaporwave signature); seek = the grid's vanishing point shifts along the horizon with progress.
- **Palette:** `#FF6AD5` `#AD8CFF` `#8795E8` `#94D0FF`
- **Compose effort:** Medium — the infinite grid is a classic perspective-line trick (draw receding horizontal lines with looping offset, doable in plain `Canvas` math) plus a duotone color filter on the art.
- **Widget:** the grid doesn't map to a circular anchor — ships as a static "frozen horizon" snapshot; still striking without motion since the aesthetic is inherently graphic/flat.

### Arcade

*A neon-tube arcade-cabinet marquee, "INSERT COIN" and all.*

- **Concept:** neon arcade cabinet; art framed inside the cabinet's marquee like a game's title art.
- **Era / vibe:** 80s video-arcade neon.
- **Album art:** sits inside an arcade marquee frame with chase-light bulbs around the border.
- **Motion:** marquee chase lights animate continuously around the frame while playing; on pause, the screen flashes a blinking "PAUSED" (or, playfully, "INSERT COIN") in arcade marquee font.
- **Controls:** a big red arcade button for play/pause, joystick-sprite tilt for prev/next; seek = the chase-light strip itself, filling with progress.
- **Palette:** `#050014` `#FF00FF` `#00FFF7` `#FFEA00`
- **Compose effort:** Medium — glow/bloom on multiple neon-tube paths plus a timed sequential chase-light loop; contained, but definitely more than flat rects.
- **Widget:** a static marquee snapshot with lights baked in "on"; if a circular button element is used for play/pause, that can host the spinner ring.

### Collage

*A pinned photo on a cork-board scrapbook page, stickers and all.*

- **Concept:** sticker/scrapbook collage; art is a photo cutout pinned/taped among decorative stickers.
- **Era / vibe:** 2000s scrapbooking/sticker-culture craft aesthetic.
- **Album art:** shown as a photo cutout with peeling tape corners, surrounded by unrelated decorative stickers (stars, hearts) for texture.
- **Motion:** layered stickers show a very subtle handheld parallax (optionally tied to the accelerometer for tilt) while playing; everything settles/stills completely on pause.
- **Controls:** physical-feeling stickers/pins you tap as buttons; seek = a strip of washi tape with tick marks along it.
- **Palette:** `#D8C3A5` `#FF5D8F` `#4CD4B0` `#2B2B2B`
- **Compose effort:** Medium — many overlapping layered assets (stickers, tape, cutout), each with its own slight rotation/shadow; assembling the layered look is the main work, individual pieces are simple.
- **Widget:** an excellent static fit — a scrapbook page is inherently a flat pinned composition, loses nothing frozen.

### Plush

*A soft claymation charm that squishes when you press it.*

- **Concept:** claymation/plush mascot; art shown inside a soft, blobby "clay frame" cutout mask.
- **Era / vibe:** stop-motion claymation / kawaii plush-toy aesthetic.
- **Album art:** masked into a rounded, slightly irregular blob shape, like a clay frame around a photo.
- **Motion:** a gentle squash-and-stretch bounce loop, deliberately stepped at a low frame rate (2–3fps) for authentic claymation "chatter" rather than smooth interpolation, while playing; on pause the character "sits still" (or "closes its eyes" if a mascot face is added).
- **Controls:** buttons as squishy clay blobs that visibly squash on tap; seek = a felt ribbon sliding through a little buckle.
- **Palette:** `#F6BD60` `#F7EDE2` `#84A59D` `#F28482`
- **Compose effort:** Medium — soft blobby shapes are easy geometry; the effort is in the squash-and-stretch easing curves and the deliberately choppy low-fps timing, which needs custom `AnimationSpec` work rather than defaults.
- **Widget:** a two-frame static swap — a "resting pose" for paused and a "mid-bounce pose" for playing, rather than smooth animation; fits the claymation "frame-stepped" identity naturally.

### Panel

*The now-playing screen as a comic-book panel, halftone dots and all.*

- **Concept:** comic/pop-art panel; art halftone-filtered inside a bordered comic panel with a caption box for the track title.
- **Era / vibe:** 1960s Ben-Day-dot pop art / classic comic-book printing.
- **Album art:** halftone-dot filtered in bold color (reuses Ink's dot technique, in color) and framed inside a thick-bordered comic panel.
- **Motion:** halftone dots shimmer/shift slightly; a comic "POW"-style burst sticker pulses on track change; a speech bubble displays the track title; pause freezes the frame and adds a comic "…" ellipsis bubble.
- **Controls:** buttons styled as comic action-word bursts ("PLAY!", "SKIP!"); seek = a filmstrip of small comic panels that advances frame by frame with progress.
- **Palette:** `#FFE800` `#E4032E` `#0057B7` `#000000`
- **Compose effort:** Medium — color halftone-dot fill (similar technique to Ink) plus comic panel border paths and a starburst shape; assembling comic-specific chrome is the bulk of the work.
- **Widget:** a static comic-panel snapshot; halftone dots and panel borders don't need motion to read correctly; pause state can bake in the "…" bubble directly.

---

## Theme Axes

Cross-cutting knobs that apply *across* skins, independent of which one is active.

### Light / Dark

- Today, `SpindleTheme` (`Theme.kt`) hardcodes default Material `lightColorScheme()`/`darkColorScheme()` off `isSystemInDarkTheme()` at the app-shell level, but skins currently paint over that with their own fixed palette — Turntable is coral-on-navy regardless of system theme, Flux is dark-only.
- Proposal: let each skin declare whether it's theme-*aware* (Slate, Headline, Bleed, Ink, and Grid could each ship a light **and** dark variant of their palette) versus theme-*fixed* (Turntable, Flux, Arcade, Vapor, and Visor are identity-defining enough to stay one mode regardless of system setting, the way Flux already does).
- Compose hook: add a `supportsLightDark: Boolean` to `PlayerSkin` and branch the palette inside `Render` off `isSystemInDarkTheme()` only for the flexible ones.

### Accent color derived from album art (per-track dynamic color)

- Distinct from Android 12+ Material You (`dynamicColorScheme`, which follows the *wallpaper*) — this is per-*track*: sample 1–2 dominant colors from `NowPlaying.artwork` (`androidx.palette`'s `Palette.from(bitmap).generate()`, or a cheap manual average-color pass since it's already an `ImageBitmap`) and feed them into whichever accent slot a skin exposes.
- Animate the transition with `animateColorAsState` across track changes (~400–600ms crossfade) so it never jump-cuts.
- Best fits: Bleed (full-bleed art practically demands it), Orb (the entire point of Orb is "art becomes light"), Pulse, Frost, Headline's accent letter. Weakest fits: Amp and Mono, whose identity is a fixed, period-correct palette — dynamic tinting would undercut the nostalgia.

### "Quiet vs. vivid" intensity

- A single global slider in Settings that scales: animation speed/amplitude, glow/bloom radius, saturation, and whether secondary decorative motion (particles, chase lights, needle overshoot) runs at all.
- **Quiet:** motion halved, glow/blur ~40%, desaturated ~15%, no secondary decorative motion. Doubles as a battery-saver mode and an accessibility "reduce motion" mode — mirrors what Flux's own CSS reference already does with `prefers-reduced-motion`, and can hook into Android's animator-duration-scale / an in-app toggle the same way.
- **Vivid:** full bloom, full saturation, all secondary motion on (glints, particles, chase lights, VU overshoot).
- Cheap to build: one `Float` in a settings store, read once per skin, used to scale existing animation specs/alpha — no new render paths needed.

### Seasonal / limited drops

- Reskin an *existing* engine with a swapped palette and a couple of extra ornament draws — don't build new geometry. Examples: Turntable "Sakura" (spring pastel-pink/green vinyl label), Flux "Aurora" (winter green/violet gradient swap), Horn "Tinsel" (a wreath on the gramophone horn), Jukebox "Fourth of July" (red/white/blue neon), Orb "Snow Globe" (drifting particle flecks inside the orb each December).
- Ship as a palette+overlay *variant* of the parent skin (same `Render`, swapped `Color` constants plus one optional decorative draw call), gated by a date range or a settings toggle — not a whole new `PlayerSkin` implementation.
- A strong marketing lever (App Store screenshot refresh, a "your December skin is here" re-engagement notification) for near-zero engineering cost.

---

## Roadmap: What to Build Next

Weighing three things for each candidate: **effort**, how different it *feels* from
Turntable/Flux (**distinctiveness**), and how much of the existing
disc-rotation rig — the `Record()`-style `Canvas` + `Animatable` rotation +
`graphicsLayer` lift/tilt pattern already proven in `TurntableSkin.kt` — can
be **reused** as-is.

| Order | Pick | Effort | Disc-motif reuse | Why now |
|-------|------|--------|-------------------|---------|
| 1 | **Jewel** (CD/Discman) | Medium | High | Reuses ~80% of `Record()`'s rotation code verbatim; closes the "90s–2000s digital" gap between analog Turntable and futuristic Flux — highest reuse-to-distinctiveness ratio in the catalog. |
| 2 | **Mixtape** (Cassette) | Medium | High (2× reels) | Still the same rotating-circle + `Animatable` primitive, just doubled — but the silhouette (twin reels, no center disc) and widget signature feel genuinely different, not a recolor. |
| 3 | **Wheel** (iPod Classic) | Medium | Low, deliberately | No spinning album art at all — validates that `PlayerSkin` holds up for something structurally different (a screen + a circular *input*, not a circular *animation*) before more skins accumulate. Outsized nostalgia/marketing payoff for the effort. |
| 4 | **Amp** (Winamp) | Easy | None | The cheapest build in the catalog — flat rects, no rotation math — and it reaches a completely different nostalgia audience (PC/internet culture vs. physical-media collectors), which matters more for catalog breadth than engineering novelty at this point. |

**Bonus 5th, if there's room:** **Orb** — near-zero effort (one radial-gradient
circle plus a breathe animation) and the fastest way to add a "calm/quiet"
option to a lineup that's currently all high-detail chrome.

**Explicitly deferred:** Jukebox, Horn, and Visor are all Hard-rated,
multi-part scenes. Save them for after the skin-switching infrastructure
(settings picker, per-skin previews) has a few cheap wins proving it out —
building the hardest, most bespoke skins first risks over-investing in
polish before the picker UX itself is validated.
