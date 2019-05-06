package Pokemon.controller

import Pokemon.model.{Bulbasaur, Charmander, Pokemon, Squirtle, PokemonBoard}
import scalafx.event.ActionEvent
import scalafx.scene.control.{Alert, TextField}
import scalafx.stage.{Stage, WindowEvent}
import scalafxml.core.macros.sfxml

@sfxml
class NewPokemonDialogController () {

	var dialogStage : Stage  = null
	private var _pokemon : Pokemon = null
	var nextClicked = false


	def bulbasaurChosen(actionEvent: ActionEvent) {

		_pokemon = new Bulbasaur("", 1)
	}


	def charmanderChosen(actionEvent: ActionEvent) {

		_pokemon = new Charmander("", 1)
	}


	def squirtleChosen(actionEvent: ActionEvent) {

		_pokemon = new Squirtle("", 1)
	}


	def handleNextButton(action: ActionEvent) : Unit = {

		var errorMessage = ""

		if (_pokemon == null) {

			val alert = new Alert(Alert.AlertType.Error) {

				initOwner(dialogStage)
				title = "Choose a Pokemon"
				headerText = "Please choose a Pokemon"
				contentText = "Pokemon is not chosen!\n"
			}.showAndWait()

		} else {

			PokemonBoard.pokemon = _pokemon
			nextClicked = true
			dialogStage.close()
		}
	}
}

