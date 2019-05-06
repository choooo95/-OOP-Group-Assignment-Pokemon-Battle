package Pokemon.model

import scalafx.beans.property.{ObjectProperty, StringProperty}

class Bulbasaur(givenNameS: String, levelI: Int) extends Pokemon {

	val givenName = new StringProperty(givenNameS)
	var level = ObjectProperty[Int](if (levelI > 0) levelI else 1)
	val baseStat = Stat(ObjectProperty[Int](120 + (level.value * 25)), ObjectProperty[Int](15 + (level.value * 5)), "Grass")
	var currentStat = Stat(ObjectProperty[Int](baseStat.hp.value), ObjectProperty[Int](baseStat.defense.value), baseStat.element)
	def pokemonName = new StringProperty(Bulbasaur.pokemonName)
	def skills = Bulbasaur.skills
	def potions = Bulbasaur.potions
}


object Bulbasaur extends PokemonCharacteristics {

  val pokemonName = "Bulbasaur"
  val skills = List(new Growl(), new Tackle())
}