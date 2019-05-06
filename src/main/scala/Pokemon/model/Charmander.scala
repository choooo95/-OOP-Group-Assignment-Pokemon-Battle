package Pokemon.model

import scalafx.beans.property.{ObjectProperty, StringProperty}

class Charmander(givenNameS: String, levelI: Int) extends Pokemon {

	val givenName: StringProperty = new StringProperty(givenNameS)
	var level = ObjectProperty[Int](if (levelI > 0) levelI else 1)
	val baseStat = Stat(ObjectProperty[Int](130 + (level.value * 30)), ObjectProperty[Int](10 + (level.value * 2)), "Fire")
	var currentStat = Stat(ObjectProperty[Int](baseStat.hp.value), ObjectProperty[Int](baseStat.defense.value), baseStat.element)
	def pokemonName = new StringProperty(Charmander.pokemonName)
	def skills = Charmander.skills
	def potions = Charmander.potions
}


object Charmander extends PokemonCharacteristics {
	
  val pokemonName = "Charmander"
  val skills = List(new Scratch(), new Growl())
}