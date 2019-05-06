package Pokemon.model

class TailWhip extends DebuffSkill {

	val name = TailWhip.name
	var defDamage = Growl.defDmg
}


object TailWhip extends DebuffSkillCharacteristics {
	
  val name = "TailWhip"
  val defDmg = 6
}