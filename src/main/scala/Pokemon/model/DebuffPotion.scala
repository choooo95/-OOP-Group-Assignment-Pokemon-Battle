package Pokemon.model

class DebuffPotion extends Potion {

  val name = DebuffPotion.name
  val defHeal = DebuffPotion.defHeal
}

object DebuffPotion extends DebuffPotionCharacteristics {

  val name = "Debuff Potion"
  val defHeal = 20
}