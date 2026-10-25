package eu.darken.capod.common.upgrade

/**
 * Master Pro-feature gates for this build.
 *
 * Upstream hides extra features behind a GPlay in-app purchase or a FOSS sponsor record. Here
 * every gate defaults to open: both [UpgradeRepo] flavor implementations report an entitlement
 * and all downstream checks short-circuit to "allowed".
 *
 * The values are mutable (not `const`) so tests can close them and exercise the upstream gated
 * behavior — see `testhelpers.BaseTest`, which closes every gate for each test instance, and
 * `ProGatesTest`, which re-opens them. Production code never mutates them, so a shipped build
 * always runs with every gate `true`.
 */
object Gates {
    /** Entitlement reported by both [UpgradeRepo] flavor implementations. */
    var IS_PRO = true

    /** Backend safety-net gate ([isProSettled]). */
    var IS_PRO_SETTLED = true

    /** UI tap gate ([isProForUi]). */
    var IS_PRO_FOR_UI = true

    /** Device settings: Pro AAP commands and per-profile reactions. */
    var DEVICE_SETTINGS = true

    /** Stem-action mappings in the press-controls screen. */
    var PRESS_CONTROLS = true

    /** Theme mode/style/color controls in general settings. */
    var THEME_SETTINGS = true

    /** Free-tier device-profile limit on the overview. */
    var DEVICE_PROFILES = true

    /** Quick-settings ANC tile. */
    var ANC_TILE = true

    /** Widget configuration screen confirm gate. */
    var WIDGET_CONFIGURATION = true

    /** Home-screen Glance widgets (battery + ANC). */
    var HOME_WIDGETS = true

    /** Settings / upgrade status screen Pro presentation. */
    var STATUS_SCREEN = true

    /** Opens or closes every gate at once. Used by tests; production never calls this. */
    fun setAll(open: Boolean) {
        IS_PRO = open
        IS_PRO_SETTLED = open
        IS_PRO_FOR_UI = open
        DEVICE_SETTINGS = open
        PRESS_CONTROLS = open
        THEME_SETTINGS = open
        DEVICE_PROFILES = open
        ANC_TILE = open
        WIDGET_CONFIGURATION = open
        HOME_WIDGETS = open
        STATUS_SCREEN = open
    }
}
