package Pokemon.model

class Scratch extends ActiveSkill {

	val name = Scratch.name

	val minHpDmg = Scratch.minHpDmg
	val maxHpDmg = Scratch.maxHpDmg
}


object Scratch extends SkillCharacteristics {

  val name = "Scratch"
  val minHpDmg = 40
  val maxHpDmg = 50
}

