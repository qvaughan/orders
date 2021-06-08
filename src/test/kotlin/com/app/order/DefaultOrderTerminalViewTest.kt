package com.app.order

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.io.PrintStream

class DefaultOrderTerminalViewTest {

    lateinit var view: DefaultOrderTerminalView
    lateinit var mockStream: PrintStream

    @BeforeEach
    fun beforeEach(){
        mockStream = mock()
        System.setOut(mockStream)
        view = DefaultOrderTerminalView()
    }

    @Test
    fun  `prompt for user to enter order`() {
        view.promptForOrder()
        val prompt: Any = "Enter order or q to quit:"
        verify(mockStream).println(prompt)
    }

}