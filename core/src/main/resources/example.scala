package com.couchmate.chat

import akka.actor._
import com.couchmate.common.ChatMessage
import scala.util.Try
import play.api.libs.json._
import play.api.libs.iteratee.Concurrent.Channel

object Consumer {
  def props(channel: Channel[ChatMessage]) = Props(new Consumer(channel))
}

class Consumer(channel: Channel[ChatMessage]) extends Actor with ActorLogging {
  def receive: Receive = {
    case message: String =>
      Try(Json.parse(message)).flatMap { json =>
        Try {
          val chat = json.validate[ChatMessage].get
          channel.push(chat)
        }
      }.recover {
        case ex: Throwable => log.error(ex, s"Failed to parse message $message")
      }
  }
}
