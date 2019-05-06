package Pokemon.model

class Growl extends DebuffSkill {

	val name = Growl.name
	var defDamage = Growl.defDmg
}


object Growl extends DebuffSkillCharacteristics {

  val name = "Growl"
  val defDmg = 5
}