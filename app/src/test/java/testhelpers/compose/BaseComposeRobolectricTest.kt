package testhelpers.compose

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import eu.darken.capod.common.debug.logging.Logging
import eu.darken.capod.common.upgrade.Gates
import io.mockk.unmockkAll
import org.junit.AfterClass
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import testhelpers.TestApplication
import testhelpers.logging.JUnitLogger

/**
 * Base class for JVM Compose UI tests.
 *
 * Wires up Robolectric + JUnit 4 + createComposeRule() in one place so individual
 * tests don't repeat the @RunWith / @Config / @get:Rule preamble. Subclasses just
 * declare @Test methods and use `composeRule`.
 *
 * Use this for tests that render Composables via `composeRule.setContent { ... }`.
 * For non-Compose Robolectric tests, extend [testhelpers.BaseTest] with the same
 * @RunWith / @Config annotations on the subclass.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33], application = TestApplication::class)
abstract class BaseComposeRobolectricTest {

    @get:Rule
    val composeRule: ComposeContentTestRule = createComposeRule()

    init {
        Logging.clearAll()
        Logging.install(JUnitLogger())
        // Gates are JVM-global: BaseTest closes them for the legacy unit tests, so pin the
        // shipped (open) default here to keep Compose tests independent of execution order.
        Gates.setAll(true)
    }

    companion object {
        // Class-level cleanup (not @After) so it cannot race the Compose rule's
        // per-test teardown. Mirrors BaseTest's @AfterAll behavior for JUnit 4.
        @JvmStatic
        @AfterClass
        fun afterClass() {
            unmockkAll()
            Logging.clearAll()
        }
    }
}
