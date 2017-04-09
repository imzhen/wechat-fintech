package services.message

/**
  * Created by Elliott on 4/8/17.
  */

import scala.xml.{Node, Utility}

object XMLHelper {

  @inline
  def text(node: Node, label: String) = (node \ label).text

  def stringify(node: Node, stripComments: Boolean = true): String = {
    Utility.serialize(Utility.trim(node), stripComments = true).toString
  }
}
