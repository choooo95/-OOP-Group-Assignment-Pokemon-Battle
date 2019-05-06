package Pokemon.controller
import Pokemon.MainPokemon
import Pokemon.model._
import scalafxml.core.macros.sfxml
import scalafx.scene.input.MouseEvent
import scalafx.stage.Stage
import scalafx.scene.control.{Label}

@sfxml
class SequelBoxController(

	private val sequelBoxLabel :  Label

) {

	private var user: Pokemon = MainPokemon.battle.user
	private var bot: Pokemon = MainPokemon.battle.bot
	var dialogStage: Stage = null
	var sequelBoxClicked: Boolean = false
	var winner: Pokemon = null

	sequelBoxLabel.text = MainPokemon.battle.sequelText


	def handleSequelBoxClicked(action: MouseEvent) {
		
		sequelBoxClicked = true
		dialogStage.close()
	}
}