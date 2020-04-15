package com.example.transitoncloneissue

import androidx.transition.Transition
import androidx.transition.TransitionSet
import io.mockk.mockk
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IssueTest {

    @Test
    fun shouldNotLeakListenersToOriginalSet() {
        // given we have a prototype with a listener already set
        val prototypeSet = TransitionSet()
        val expectedListener = mockk<Transition.TransitionListener>()
        prototypeSet.addListener(expectedListener)

        // when adding listeners to cloned sets
        prototypeSet.clone().addListener(mockk())
        prototypeSet.clone().addListener(mockk())

        // then
        val listeners = prototypeSet.getInternalListeners()
        assertEquals("Listeners added to cloned objects should not appear in original one", 1, listeners.size)
        assertTrue(listeners.contains(expectedListener))
    }
}

private fun Transition.getInternalListeners() = Transition::class.java.getDeclaredField("mListeners").apply { this.isAccessible = true }.get(this) as ArrayList<Transition.TransitionListener>
