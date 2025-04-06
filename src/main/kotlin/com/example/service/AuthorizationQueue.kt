package com.example.service

import com.example.model.StartSessionRequest
import kotlinx.coroutines.channels.Channel

object AuthorizationQueue {
    val queue = Channel<StartSessionRequest>(Channel.UNLIMITED)
}
