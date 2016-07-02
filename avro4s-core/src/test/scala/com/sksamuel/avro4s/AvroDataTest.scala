package com.sksamuel.avro4s

import java.io.File

import org.scalatest.{Matchers, WordSpec}

class AvroDataTest extends WordSpec with Matchers {

  "AvroData" should {
    "be able to read its own output" in {

      val pepperoni = Pizza("pepperoni", Seq(Ingredient("pepperoni", 12, 4.4), Ingredient("onions", 1, 0.4)), false, false, 98)
      val hawaiian = Pizza("hawaiian", Seq(Ingredient("ham", 1.5, 5.6), Ingredient("pineapple", 5.2, 0.2)), false, false, 91)

      val os = AvroOutputStream.data[Pizza](new File("pizzas.avro"))
      os.write(pepperoni)
      os.write(hawaiian)
      os.close()

      val is = AvroInputStream.data[Pizza](new File("pizzas.avro"))
      val pizzas = is.iterator.toList
      pizzas shouldBe List(pepperoni, hawaiian)
      is.close()
    }
  }
}
