package services.utils

import java.security.SecureRandom

/**
  * Created by Elliott on 4/8/17.
  */
object Utilities {
  def currentTimeSeconds: Long = System.currentTimeMillis() / 1000

  @inline
  def randomNextInt(begin: Int, bound: Int) = new SecureRandom().nextInt(bound - begin) + begin
}
