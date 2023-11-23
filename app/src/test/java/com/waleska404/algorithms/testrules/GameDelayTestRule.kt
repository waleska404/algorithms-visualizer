package com.waleska404.algorithms.testrules

import com.waleska404.algorithms.ui.core.config.Speed
import com.waleska404.algorithms.ui.core.config.setGameDelay
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class GameDelayTestRule : TestWatcher() {
    override fun starting(description: Description) {
        super.starting(description)
        setGameDelay(Speed.NO_DELAY)
    }

    override fun finished(description: Description) {
        super.finished(description)
        setGameDelay(Speed.AVERAGE)
    }
}