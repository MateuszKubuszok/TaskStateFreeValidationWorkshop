package io.scalawave.workshop.common

import org.slf4j.LoggerFactory

trait Logging {

  protected val logger = LoggerFactory.getLogger(getClass)
}
