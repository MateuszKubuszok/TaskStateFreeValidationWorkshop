package io.scalawave.workshop.free

import io.scalawave.workshop.common.Config

/**
 * Sad workaround for interpreters needing to share common state. But we can do better than that!
 */
case class ConfigStore(var config: Config)
