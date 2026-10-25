package eu.darken.capod.common.upgrade

import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import testhelpers.BaseTest
import java.time.Instant

/**
 * This build ships with every Pro gate open. The fake repo below deliberately reports a settled,
 * error-free FREE entitlement to prove the gates — not the purchase state — decide access.
 */
class ProGatesTest : BaseTest() {

    init {
        // BaseTest closes the gates so the legacy test suite drives the upstream gated paths;
        // this test is about the shipped (open) configuration, so re-open them.
        Gates.setAll(true)
    }

    private class FreeInfo : UpgradeRepo.Info {
        override val type: UpgradeRepo.Type = UpgradeRepo.Type.FOSS
        override val isPro: Boolean = false
        override val isSettled: Boolean = true
        override val upgradedAt: Instant? = null
        override val error: Throwable? = null
    }

    private class FreeRepo : UpgradeRepo {
        override val storeSite: String = ""
        override val upgradeSite: String = ""
        override val betaSite: String = ""
        override val upgradeInfo = MutableStateFlow<UpgradeRepo.Info>(FreeInfo())
        override suspend fun refresh() = Unit
    }

    @Test
    fun `every Pro gate is marked true`() {
        listOf(
            Gates.IS_PRO,
            Gates.IS_PRO_SETTLED,
            Gates.IS_PRO_FOR_UI,
            Gates.DEVICE_SETTINGS,
            Gates.PRESS_CONTROLS,
            Gates.THEME_SETTINGS,
            Gates.DEVICE_PROFILES,
            Gates.ANC_TILE,
            Gates.WIDGET_CONFIGURATION,
            Gates.HOME_WIDGETS,
            Gates.STATUS_SCREEN,
        ).forEach { it shouldBe true }
    }

    @Test
    fun `entitlement helpers allow a free repo`() = runTest {
        val repo = FreeRepo()
        repo.isPro() shouldBe true
        repo.isProSettled() shouldBe true
        repo.isProForUi() shouldBe true
    }

    @Test
    fun `closing the gates restores the free entitlement checks`() = runTest {
        Gates.setAll(false)
        val repo = FreeRepo()
        repo.isPro() shouldBe false
        repo.isProForUi() shouldBe false
        // Settled + error-free + no purchase is the one case isProSettled() denies.
        repo.isProSettled() shouldBe false
    }
}
