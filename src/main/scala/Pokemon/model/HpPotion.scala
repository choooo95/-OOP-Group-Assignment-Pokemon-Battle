package Pokemon.model

class HpPotion extends Potion {

  val name = HpPotion.name
  val hpHeal = HpPotion.hpHeal
}

object HpPotion extends HpPotionCharacteristics {

  val name = "Hp Potion"
  val hpHeal = 40
}
