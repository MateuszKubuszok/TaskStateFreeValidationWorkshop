package workshop.free

import workshop.common.Config

/**
 * Sad workaround for interpreters needing to share common state. But we can do better than that!
 */
case class ConfigStore(var config: Config)
