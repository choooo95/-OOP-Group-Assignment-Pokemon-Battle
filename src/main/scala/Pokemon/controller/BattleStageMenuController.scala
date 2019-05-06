package Pokemon.controller
import Pokemon.MainPokemon
import Pokemon.model._
import scalafxml.core.macros.sfxml
import scalafx.event.ActionEvent
import scalafx.stage.Stage

@sfxml
class BattleStageMenuController() {

	var dialogStage : Stage  = null
	var skillChosen : Skill = null
	var potionChosen : Potion = null
	var menuChosen : String = null


	def handleAttack(action: ActionEvent) {

		menuChosen = "attack"
		skillChosen = MainPokemon.battle.showSkillMenu()
		dialogStage.close()
	}


	def handleItem(action: ActionEvent) {
		menuChosen = "item"
		potionChosen = MainPokemon.battle.showPotionMenu()
		dialogStage.close()
	}


	def handleRun(action: ActionEvent) {

		menuChosen = "run"
		dialogStage.close()
	}
}