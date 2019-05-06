package Pokemon.model

trait ActiveSkillCharacteristics extends SkillCharacteristics{
	
  val minHpDmg: Int
  val maxHpDmg: Int
}