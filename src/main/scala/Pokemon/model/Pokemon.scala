package Pokemon.model

import scalafx.beans.property.{ObjectProperty, StringProperty}
import scala.util.Random


case class Stat(hp: ObjectProperty[Int], defense: ObjectProperty[Int], element: String)


abstract class Pokemon {

	val givenName: StringProperty
	var level: ObjectProperty[Int]
	val baseStat: Stat
	var currentStat: Stat
	def pokemonName: StringProperty
	def skills: List[Skill]
	def potions: List[Potion]

	def randomSkill: Skill = {

		val rand = new Random()
		skills(rand.nextInt(skills.length))
	}


	def attack(skill: Skill, target: Pokemon): String = {

		if (skill.isInstanceOf[DebuffSkill]) {

			val defSkill = skill.asInstanceOf[DebuffSkill]
			val defDamage = (level.value * 2) + defSkill.defDamage
			val damagedDef = target.currentStat.defense.value - defDamage
			target.currentStat.defense.value = if (damagedDef > 0) damagedDef else 0
			defDamage + " DEF damage!"

		} else {

			val hpSkill = skill.asInstanceOf[ActiveSkill]
			val rand = new Random()
			val hpDamage = (level.value * 4) + hpSkill.minHpDmg + rand.nextInt((hpSkill.maxHpDmg - hpSkill.minHpDmg) + 1) - (target.currentStat.defense.value / 3).toInt
			val damage = if (hpDamage > 0) hpDamage else 0
			val damagedHp = target.currentStat.hp.value - damage
			target.currentStat.hp.value = if (damagedHp > 0) damagedHp else 0
			damage + " HP damage!" 
		}
	}


	def recover(pot: Potion): String = {

		if (pot.isInstanceOf[HpPotion]) {

			val hpPot = pot.asInstanceOf[HpPotion]
			val hpHeal = (level.value * 3) + hpPot.hpHeal
			val healedHp = currentStat.hp.value + hpHeal
			currentStat.hp.value = if (healedHp < baseStat.hp.value) healedHp else baseStat.hp.value
			hpHeal + " HP!"

		} else {

			val defPot = pot.asInstanceOf[DebuffPotion]
			val defHeal = (level.value * 2) + defPot.defHeal
			val healedDef = currentStat.defense.value + defHeal
			currentStat.defense.value = if (healedDef < baseStat.defense.value) healedDef else baseStat.defense.value
			defHeal + " DEF!"
		}
	}
}