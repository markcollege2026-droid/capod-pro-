package testhelpers

import eu.darken.capod.common.debug.Bugs
import eu.darken.capod.common.debug.logging.Logging
import eu.darken.capod.common.upgrade.Gates
import eu.darken.capod.common.debug.logging.Logging.Priority.VERBOSE
import eu.darken.capod.common.debug.logging.log
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterAll
import testhelpers.logging.JUnitLogger


open class BaseTest {
    init {
        Logging.clearAll()
        Logging.install(JUnitLogger())
        // JVM-global and written by anything that starts a debug recording. Reset per test instance
        // and not in a companion teardown: the JUnit 5 @AfterAll below never fires under the JUnit 4
        // Robolectric runner that the recorder tests use.
        Bugs.isDebug.value = false
        // This build ships with every Pro gate open (Gates defaults to true). The unit tests were
        // written against the upstream gated behavior, so close the gates for each test instance
        // and let them drive the original entitlement code paths. Tests for the open build itself
        // (ProGatesTest) re-open them explicitly.
        Gates.setAll(false)
        testClassName = this.javaClass.simpleName
    }

    companion object {
        private var testClassName: String? = null

        @JvmStatic
        @AfterAll
        fun onTestClassFinished() {
            unmockkAll()
            log(testClassName!!, VERBOSE) { "onTestClassFinished()" }
            Logging.clearAll()
        }
    }
}
