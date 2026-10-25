package eu.darken.capod.common.upgrade

/**
 * Master Pro-feature gates for this build.
 *
 * Upstream hides extra features behind a GPlay in-app purchase or a FOSS sponsor record. Here
 * every gate is open: both [UpgradeRepo] flavor implementations report an entitlement and all
 * downstream checks short-circuit to "allowed". Keep every value `true` to leave all Pro
 * features unlocked; close one again to re-lock just that surface.
 */
object Gates {
    /** Entitlement reported by both [UpgradeRepo] flavor implementations. */
    const val IS_PRO = true

    /** Backend safety-net gate ([isProSettled]). */
    const val IS_PRO_SETTLED = true

    /** UI tap gate ([isProForUi]). */
    const val IS_PRO_FOR_UI = true

    /** Device settings: Pro AAP commands and per-profile reactions. */
    const val DEVICE_SETTINGS = true

    /** Stem-action mappings in the press-controls screen. */
    const val PRESS_CONTROLS = true

    /** Theme mode/style/color controls in general settings. */
    const val THEME_SETTINGS = true

    /** Free-tier device-profile limit on the overview. */
    const val DEVICE_PROFILES = true

    /** Quick-settings ANC tile. */
    const val ANC_TILE = true

    /** Widget configuration screen confirm gate. */
    const val WIDGET_CONFIGURATION = true

    /** Home-screen Glance widgets (battery + ANC). */
    const val HOME_WIDGETS = true

    /** Settings / upgrade status screen Pro presentation. */
    const val STATUS_SCREEN = true
}
