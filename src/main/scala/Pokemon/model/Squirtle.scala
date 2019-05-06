package Pokemon.model

import scalafx.beans.property.{ObjectProperty, StringProperty}

class Squirtle(givenNameS: String, levelI: Int) extends Pokemon {
	
	val givenName: StringProperty = new StringProperty(givenNameS)
	var level = ObjectProperty[Int](if (levelI > 0) levelI else 1)
	val baseStat = Stat(ObjectProperty[Int](100 + (level.value * 20)), ObjectProperty[Int](20 + (level.value * 10)), "Water")
	var currentStat = Stat(ObjectProperty[Int](baseStat.hp.value), ObjectProperty[Int](baseStat.defense.value), baseStat.element)
	def pokemonName = new StringProperty(Squirtle.pokemonName)
	def skills = Squirtle.skills
	def potions = Squirtle.potions
}


object Squirtle extends PokemonCharacteristics {

  val pokemonName = "Squirtle"
  val skills = List(new Tackle(), new TailWhip())
}