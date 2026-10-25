package eu.darken.capod.common.upgrade

import kotlinx.coroutines.flow.first
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * One-shot entitlement check used by the Glance widgets. [Gates.IS_PRO] is open in this build,
 * so the flow is only read when the gate is closed.
 */
suspend fun UpgradeRepo.isPro(): Boolean = Gates.IS_PRO || upgradeInfo.first().isPro

/**
 * Backend safety-net gate. All gates are open in this build, so this resolves immediately
 * without a billing refresh, a settle wait or a timeout.
 */
@Suppress("UNUSED_PARAMETER")
suspend fun UpgradeRepo.isProSettled(timeout: Duration = 5.seconds): Boolean =
    Gates.IS_PRO_SETTLED || upgradeInfo.first().isPro

/**
 * UI tap gate. Always allowed in this build: a cold-start billing handshake can never bounce a
 * user to the upgrade screen.
 */
@Suppress("UNUSED_PARAMETER")
suspend fun UpgradeRepo.isProForUi(timeout: Duration = 3.seconds): Boolean =
    Gates.IS_PRO_FOR_UI || upgradeInfo.first().isPro
