package org.databaseconfiguration.data

import java.lang.annotation.Documented

@Documented
case class Client(_id: String, name: String, inboundFeedUrl: String)
