package Pokemon.model

trait PokemonCharacteristics {
	
  val pokemonName: String
  val skills: List[Skill]
  val potions: List[Potion] = List(new HpPotion(), new DebuffPotion())
 }