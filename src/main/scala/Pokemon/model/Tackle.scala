package Pokemon.model

class Tackle extends ActiveSkill {

	val name = Tackle.name

	val minHpDmg = Tackle.minHpDmg
	val maxHpDmg = Tackle.maxHpDmg
}


object Tackle extends ActiveSkillCharacteristics {
	
  val name = "Tackle"
  val minHpDmg = 40
  val maxHpDmg = 45
}